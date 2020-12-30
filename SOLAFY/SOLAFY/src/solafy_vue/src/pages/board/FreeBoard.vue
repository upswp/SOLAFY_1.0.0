<template>
  <div class="doc-container">
    <h3>FreeBoard</h3>
    <!-- 글 목록보기 시작 -->
    <div v-if="showFlag == 'list'">
      <!-- q-markup-table 시작 -->
      <!-- <board-list boardType="free"> </board-list> -->
      <q-markup-table>
        <thead>
          <tr>
            <td colspan="5" style="width:100%">
              <q-input
                bottom-slots
                v-model="keyword"
                label="검색어를 입력해주세요"
                counter
                maxlength="12"
                @keyup.enter="selectArticle"
              >
                <template v-slot:before>
                  <q-btn color="secondary" label="글쓰기" @click="showWrite" />
                  <q-select
                    style="margin-left:300px; width:120px"
                    filled
                    v-model="selection"
                    :options="options"
                    label="검색조건"
                  />
                </template>
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

    <!-- 글 등록하기 시작 (showFlag가 write일 경우) -->
    <div v-if="showFlag == 'write'">
      <q-page class="q-pa-md">
        <!-- final card 시작 -->
        <q-card flat bordered>
          <q-item>
            <q-item-section>
              게시글 작성
            </q-item-section>
          </q-item>
          <q-item>
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
              </q-avatar>
            </q-item-section>

            <q-item-section>
              <q-item-label>
                <q-input
                  v-model="article.title"
                  label="제목"
                  stack-label
                  aria-placeholder="제목을 입력해주세요"
                  dense
              /></q-item-label>
              <q-item-label caption>
                닉네임 : 파베에서 로그인 정보 가져오기
              </q-item-label>
            </q-item-section>
          </q-item>
          <q-separator />
          <q-card-section>
            <q-card-section class="col-4">
              <q-input
                v-model="article.contents"
                filled
                type="textarea"
                label="내용"
                aria-placeholder="내용을 입력해주세요"
                autogrow
              />
            </q-card-section>
          </q-card-section>
          <q-card-section align="right"
            ><q-checkbox v-model="article.notice" label="공지사항 여부" />
          </q-card-section>
          <q-separator />
          <q-card-actions align="right">
            <q-btn color="primary" label="글 등록" @click="createArticle" />
            <q-btn color="red" label="취소" @click="goToFreeBoard" />
          </q-card-actions>
        </q-card>
      </q-page>
      <!-- 글 등록 form 끝 -->
    </div>
    <!-- 글 등록하기 끝 -->

    <!-- 글 상세보기 시작 (showFlag가 detail일 경우) -->
    <div v-else-if="showFlag == 'detail'">
      <q-page class="q-pa-md">
        <!-- final card 시작 -->
        <q-card flat bordered>
          <q-item>
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
              </q-avatar>
            </q-item-section>

            <q-item-section>
              <q-item-label overline> # {{ article.articleNo }} </q-item-label>
              <q-item-label
                ><q-chip
                  dense
                  color="orange"
                  text-color="white"
                  v-if="article.notice"
                >
                  공지 </q-chip
                >{{ article.title }}</q-item-label
              >
              <q-item-label caption>
                닉네임 : {{ article.nickname }} <br />
                작성시간 : {{ article.regiTime }}
              </q-item-label>
            </q-item-section>
          </q-item>
          <q-separator />
          <q-card-section>
            <q-card-section class="col-4"
              >{{ article.contents }}
            </q-card-section>
          </q-card-section>
          <q-card-section align="right">
            <q-rating
              id="likeArea"
              v-model="likeBtn"
              max="1"
              size="1em"
              color="red"
              color-selected="red-9"
              icon="favorite_border"
              icon-selected="favorite"
              icon-half="favorite"
              no-dimming
            />
            <label for="#likeArea">
              LikeCount :
              {{ article.likeCount + likeBtn }}
            </label>
          </q-card-section>
          <q-separator />
          <q-card-actions align="right">
            <q-btn
              color="primary"
              label="글 수정"
              @click="showFlag = 'update'"
            />
            <q-btn color="red" label="글 삭제" @click="deleteArticle" />
            <q-btn color="green" label="글 목록보기" @click="goToFreeBoard" />
          </q-card-actions>
          <q-separator />
          <q-card-section align="left">
            <free-reply-write
              :articleNo="this.article.articleNo"
              @freeReplyChanged="showChangedReply(article.articleNo)"
            ></free-reply-write>
          </q-card-section>
          <q-separator />
          <q-card-section>
            <free-reply-row
              v-for="(reply, index) in replies"
              :reply="reply"
              :key="index"
              @freeReplyChanged="showChangedReply(reply.articleNo)"
            ></free-reply-row>
          </q-card-section>
        </q-card>
      </q-page>
    </div>
    <!-- 글 상세 보기 끝 -->

    <!-- 글 수정하기 시작 (showFlag가 update일 경우) -->
    <div v-else-if="showFlag == 'update'">
      <q-page class="q-pa-md">
        <!-- final card 시작 -->
        <q-card flat bordered>
          <q-item>
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
              </q-avatar>
            </q-item-section>

            <q-item-section>
              <q-item-label overline> #{{ article.articleNo }} </q-item-label>
              <q-item-label>
                <q-input
                  v-model="article.title"
                  label="제목"
                  stack-label
                  aria-placeholder="제목을 입력해주세요"
                  dense
              /></q-item-label>
              <q-item-label caption>
                닉네임 : {{ article.nickname }} <br />
                작성시간 : {{ article.regiTime }}
              </q-item-label>
            </q-item-section>
          </q-item>
          <q-separator />
          <q-card-section>
            <q-card-section class="col-4">
              <q-input
                v-model="article.contents"
                filled
                type="textarea"
                aria-placeholder="내용을 입력해주세요"
                autogrow
              />
            </q-card-section>
          </q-card-section>
          <q-card-section align="right"
            ><q-checkbox v-model="article.notice" label="공지사항 여부" />
          </q-card-section>
          <q-separator />
          <q-card-actions align="right">
            <q-btn color="primary" label="글 수정하기" @click="updateArticle" />
            <q-btn color="red" label="취소" @click="goToFreeBoard" />
          </q-card-actions>
        </q-card>
      </q-page>
    </div>

    <!-- 글 수정하기 끝 -->
  </div>
</template>

<script>
import Axios from "axios";
// 자유게시판 댓글 컴포넌트 가져오기
import FreeReplyWrite from "./freeReply/FreeReplyWrite.vue";
import FreeReplyRow from "./freeReply/FreeReplyRow.vue";
// import BoardList from "./material/BoardList.vue";
export default {
  components: {
    // BoardList,
    FreeReplyRow,
    FreeReplyWrite
  },
  data() {
    return {
      replies: [],
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
      likeBtn: 0,
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
          message: "*제목* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          message: "*내용* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }
      if (successFlag) {
        this.article.uid = "DFEIJC23WOSKXCNSWQ";
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
          this.getReplyRow(articleNo);
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
          timeout: 1,
          progress: true,
          position: "center",
          message: "*제목* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          timeout: 1,
          progress: true,
          position: "center",
          message: "*내용* 비어있으면 안돼요!",
          color: "oragne"
        });
        successFlag = false;
      }
      if (successFlag) {
        console.log("you are here");
        var flag = false;
        this.$q
          .dialog({
            title: "Confirm",
            message: "정말 수정하시겠습니까?",
            cancel: true,
            persistent: true
          })
          .onOk(() => {
            Axios.post(`/free/updateArticle`, this.article)
              .then(response => {
                if (response.data === "success") {
                  this.showFlag = "detail";
                  this.showList();
                  this.$q.notify({
                    progress: true,
                    message: "게시물 수정 성공",
                    color: "green"
                  });
                } else {
                  this.$q.notify({
                    progress: true,
                    message: "게시물 수정 실패",
                    color: "red"
                  });
                }
              })
              .catch(() => {
                this.errored = true;
              })
              .finally(() => (this.loading = false));
          })
          .onCancel(() => {
            return;
          })
          .onDismiss(() => {
            return;
          });
      } else {
        return;
      }
    },
    deleteArticle: function() {
      this.$q
        .dialog({
          title: "Confirm",
          message: "정말 삭제하시겠습니까?",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          Axios.delete(`/free/deleteArticle/${this.article.articleNo}`)
            .then(response => {
              if (response.data == "success") {
                this.showFlag = "list";
                this.showList();
                this.resetForm();
                this.$q.notify({
                  progress: true,
                  timeout: 3,
                  message: "삭제 완료!",
                  color: "green"
                });
              } else {
                this.$q.notify({
                  progress: true,
                  timeout: 3,
                  message: "삭제 실패!",
                  color: "red"
                });
              }
            })
            .catch(() => {
              this.errored = true;
            })
            .finally(() => (this.loading = false));
        })
        .onCancel(() => {
          return;
        })
        .onDismiss(() => {
          return;
        });
    },
    showList: function() {
      this.selectAllNotices();
      this.selectAllArticles();
    },
    // 댓글 목록
    getReplyRow: function(articleNo) {
      Axios.get(`freereply/selectReplies/${articleNo}`)
        .then(response => {
          this.replies = response.data;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    showChangedReply(articleNo) {
      // this.replies = reply;
      console.log("현재 게시물 번호", articleNo);
      this.getReplyRow(articleNo);
    }
  },
  created() {
    this.showList();
  }
};
</script>

<style></style>
