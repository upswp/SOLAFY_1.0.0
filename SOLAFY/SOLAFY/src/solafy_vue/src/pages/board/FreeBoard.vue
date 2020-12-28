<template>
  <div class="doc-container">
    <h3>FreeBoard</h3>

    <!-- 글 등록하기 시작 (showFlag가 write일 경우) -->
    <div v-if="showFlag == 'write'" class="column items-center">
      <!-- 글 등록 form 시작 -->
      <q-input
        v-model="article.title"
        label="제목"
        stack-label
        aria-placeholder="제목을 입력해주세요"
        dense
      />
      <div class="q-pa-md" style="max-width: 300px">
        <q-input
          v-model="article.contents"
          filled
          type="textarea"
          aria-placeholder="내용을 입력해주세요"
        />
      </div>
      <div class="q-gutter-sm">
        <q-checkbox v-model="article.notice" label="공지사항 여부" />
      </div>
      <q-btn color="primary" label="글 등록" @click="createArticle" />
      <q-btn color="red" label="취소" @click="goToFreeBoard" />
      <!-- 글 등록 form 끝 -->
    </div>
    <!-- 글 등록하기 끝 -->

    <!-- 글 상세보기 시작 (showFlag가 detail일 경우) -->
    <div v-else-if="showFlag == 'detail'" class="column items-center">
      <div>
        title: "{{ article.title }}" <br />
        contents: "{{ article.contents }}" <br />
        nickname: "{{ article.nickname }}" <br />
        likeCount: "{{ article.likeCount }}" <br />
        regiTime: "{{ article.regiTime }}" <br />
        group: "{{ article.group }}" <br />
        showFlag: "{{ showFlag }}" <br />
        notice: "{{ article.notice }}" <br />
        <q-btn color="primary" label="글 수정" @click="showFlag = 'update'" />
        <q-btn color="red" label="글 삭제" @click="deleteArticle" />
        <q-btn color="green" label="글 목록보기" @click="goToFreeBoard" />

        <!-- 댓글 작성란, 댓글 표시란 -->
        <!-- <reply-write :articleNo="this.article.articleno" />
      <reply-row
        v-for="(reply, index) in reply"
        :reply="reply"
        :key="index"
        @replyChanged="showChangedReply"
      /> -->
      </div>
    </div>
    <!-- 글 상세 보기 끝 -->

    <!-- 글 수정하기 시작 (showFlag가 update일 경우) -->
    <div v-else-if="showFlag == 'update'" class="column items-center">
      <div>
        <h3>수정!</h3>
        <!-- 글 수정 form 시작 -->
        <q-input
          v-model="article.title"
          label="제목"
          stack-label
          aria-placeholder="제목을 입력해주세요"
          dense
        />
        <div class="q-pa-md" style="max-width: 300px">
          <q-input
            v-model="article.contents"
            filled
            type="textarea"
            aria-placeholder="내용을 입력해주세요"
          />
        </div>
        <div class="q-gutter-sm">
          <q-checkbox v-model="article.notice" label="공지사항 여부" />
        </div>
        <q-btn color="primary" label="글 수정하기" @click="updateArticle" />
        <q-btn color="red" label="취소" @click="goToFreeBoard" />
        <!-- 글 수정 form 끝 -->
      </div>
    </div>

    <!-- 글 수정하기 끝 -->

    <!-- 글 목록보기 시작 -->
    <div v-if="showFlag == 'list'" class="column items-center">
      <!-- q-markup-table 시작 -->
      <q-markup-table>
        <thead>
          <tr>
            <td colspan="5">
              <!-- 글쓰기 버튼(showFlag가 list일 때만 표시한다) -->
              <div class="q-pa-md row q-gutter-xl">
                <q-btn color="secondary" label="글쓰기" @click="showWrite" />
                <q-select
                  filled
                  v-model="selection"
                  :options="options"
                  label="Standard"
                />
                <q-input
                  bottom-slots
                  v-model="keyword"
                  label="검색어를 입력해주세요"
                  counter
                  maxlength="12"
                  @keyup.enter="selectArticle"
                  class="col self-end"
                >
                  <template v-slot:append>
                    <q-icon
                      v-if="keyword !== ''"
                      name="close"
                      @click="keyword = ''"
                      class="cursor-pointer"
                    />
                    <q-icon name="search" @click="selectArticle" />
                  </template>
                </q-input>
              </div>
            </td>
          </tr>
          <tr>
            <th class="text-left">articleNo</th>
            <th class="text-left">nickname</th>
            <th class="text-left">title</th>
            <th class="text-left">regiTime</th>
            <th class="text-left">likeCount</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="notice in notices"
            :key="notice.articleNo"
            @click="showDetail(notice.articleNo)"
          >
            <td class="text-left">{{ notice.articleNo }}</td>
            <td class="text-left">{{ notice.nickname }}</td>
            <td class="text-left">
              <q-chip dense color="orange" text-color="white">
                공지
              </q-chip>
              {{ notice.title }}
            </td>
            <td class="text-left">{{ notice.regiTime }}</td>
            <td class="text-left">{{ notice.likeCount }}</td>
          </tr>
          <tr
            v-for="article in articles"
            :key="article.articleNo"
            @click="showDetail(article.articleNo)"
          >
            <td class="text-left">{{ article.articleNo }}</td>
            <td class="text-left">{{ article.nickname }}</td>
            <td class="text-left">
              {{ article.title }}
            </td>
            <td class="text-left">{{ article.regiTime }}</td>
            <td class="text-left">{{ article.likeCount }}</td>
          </tr>
        </tbody>
      </q-markup-table>
      <!-- q-markup-table 끝 -->
    </div>
    <!-- 글 목록보기 끝 -->
  </div>
</template>

<script>
import Axios from "axios";
// 자유게시판 댓글 컴포넌트 가져오기
// import CommentWrite from "./freeReply/FreeReplyWrite";
// import CommentRow from "./freeReply/FreeReplyRow";
export default {
  data() {
    return {
      article: {
        title: null,
        contents: null,
        uid: "DFEIJC23WOSKXCNSWQ",
        likeCount: 0,
        notice: false,
        regiTime: null,
        isGroup: false,
        groupNo: 1,
        boardBNo: 0,
        nickname: null
      },
      showFlag: "list",
      selection: "제목",
      options: ["제목", "작성자"],
      articles: [],
      notices: [],
      errored: false,
      keyword: null,
      columns: [
        {
          name: "articleNo",
          required: true,
          label: "articleNo",
          align: "left",
          field: row => row.articleNo,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "nickname",
          required: true,
          label: "nickname",
          align: "left",
          field: row => row.nickname,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "title",
          required: true,
          label: "title",
          align: "left",
          field: row => [row.title, row.isNotice],
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "regiTime",
          required: true,
          label: "regiTime",
          align: "left",
          field: row => row.regiTime,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "likeCount",
          required: true,
          label: "listCount",
          align: "left",
          field: row => row.likeCount,
          format: val => `${val}`,
          sortable: true
        }
      ]
    };
  },
  methods: {
    selectAllArticles: function() {
      Axios.get(`/free/selectAllArticles`)
        .then(response => {
          this.articles = response.data;
          console.log(this.articles[0]);
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    selectAllNotices: function() {
      Axios.get(`/free/selectAllNotices`)
        .then(response => {
          this.notices = response.data;
          console.log(this.notices[0]);
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    selectArticle: function() {
      if (this.selection === "제목") {
        Axios.get(`/free/selectArticleByTitle/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else {
        // 제목이 아닌 경우 == 작성자일 경우
        Axios.get(`/free/selectArticleByNickname/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      }
    },
    // 글쓰기 버튼을 누르면 showFlag를 write로 변경
    showWrite: function() {
      this.showFlag = "write";
    },
    createArticle: function() {
      var successFlag = true;
      if (this.article.title === null || this.article.title == "") {
        this.$q.notify({
          message: "제목이 비어있으면 안돼요!",
          color: "red"
        });
        successFlag = false;
      }
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          message: "내용이 비어있으면 안돼요!",
          color: "red"
        });
        successFlag = false;
      }
      if (successFlag) {
        Axios.post(`/free/createArticle`, this.article)
          .then(response => {
            if (response.data === "success") {
              this.showFlag = "list";
              this.showList();
              this.resetForm();
              this.$q.notify({
                message: "게시물 등록 성공",
                color: "green"
              });
            } else {
              this.$q.notify({
                message: "게시물 등록 실패",
                color: "red"
              });
            }
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else {
        return;
      }
    },
    // 목록으로 돌아갈때는 입력(바인딩)했던 내용 초기화 후
    // showFlag를 list로 바꿈
    goToFreeBoard: function() {
      this.resetForm();
      this.showFlag = "list";
    },

    // 입력 form 초기화는 title, contents, isNotice에 적용된다
    resetForm: function() {
      this.article.title = null;
      this.article.contents = null;
      this.article.isNotice = false;
    },
    showDetail: function(articleNo) {
      this.showFlag = "detail";
      Axios.get(`/free/selectArticleByArticleNo/${articleNo}`)
        .then(response => {
          this.article = response.data;
          console.log(this.article.notice);
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    updateArticle: function() {
      var successFlag = true;
      if (this.article.title === null || this.article.title == "") {
        this.$q.notify({
          message: "제목이 비어있으면 안돼요!",
          color: "red"
        });
        successFlag = false;
      }
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          message: "내용이 비어있으면 안돼요!",
          color: "red"
        });
        successFlag = false;
      }
      if (successFlag) {
        Axios.post(`/free/updateArticle`, this.article)
          .then(response => {
            if (response.data === "success") {
              this.showFlag = "detail";
              this.showList();
              this.$q.notify({
                message: "게시물 수정 성공",
                color: "green"
              });
            } else {
              this.$q.notify({
                message: "게시물 수정 실패",
                color: "red"
              });
            }
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else {
        return;
      }
    },
    deleteArticle: function() {
      Axios.delete(`/free/deleteArticle/${this.article.articleNo}`)
        .then(response => {
          if (response.data == "success") {
            this.showFlag = "list";
            this.showList();
            resetForm();
            this.$q.notify({
              message: "삭제 완료!",
              color: "green"
            });
          } else {
            this.$q.notify({
              message: "삭제 실패!",
              color: "red"
            });
          }
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    showList: function() {
      this.selectAllNotices();
      this.selectAllArticles();
    }
  },
  created() {
    this.showList();
  }
};
</script>

<style></style>
