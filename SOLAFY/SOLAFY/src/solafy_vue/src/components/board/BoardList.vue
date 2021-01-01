<template>
  <div>
    <!-- <router-view></router-view> -->
    <q-markup-table v-if="showList">
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
                <q-btn color="secondary" label="글쓰기" @click="gotoWrite" />
                <q-select
                  style="margin-left:300px; width:120px"
                  filled
                  v-model="selection"
                  :options="boardSearchKeywords"
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
          <th
            v-for="(column, index) in boardColumns"
            class="text-left"
            :key="index"
          >
            {{ column.label }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="notice in notices"
          :key="notice.articleNo"
          @click="gotoDetail(notice.articleNo)"
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
          @click="gotoDetail(article.articleNo)"
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
    <div v-else>
      yes
    </div>
  </div>
</template>
<script>
import Axios from "axios";

// vuex state에 저장된 값 불러오기
import { mapState } from "vuex";

export default {
  name: "boardlist",
  data() {
    return {
      selection: "제목",
      articles: [],
      notices: [],
      errored: null,
      loading: null,
      keyword: null,
      // columns: [],
      // options: [],
      showList: true
    };
  },
  // props: ["boardType"],
  methods: {
    selectAllArticles: function() {
      Axios.get(`/${this.boardType}/selectAllArticles`)
        .then(response => {
          this.articles = response.data;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    selectAllNotices: function() {
      Axios.get(`/${this.boardType}/selectAllNotices`)
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
        Axios.get(`/${this.boardType}/selectArticleByTitle/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else if (this.selection === "작성자") {
        Axios.get(`/${this.boardType}/selectArticleByNickname/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
        // 질문 게시판 한정
      } else if (this.selection === "문제번호") {
        Axios.get(`/${this.boardType}/selectArticleByProblemNo/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      }
    },
    gotoWrite: function() {
      console.log("going to move to writing page");
      console.log(this.articleNo);
      this.$router.push({
        name: `${this.boardType}-board-write`
      });
    },

    gotoDetail: function(articleNo) {
      console.log(articleNo);
      this.$router.push({
        name: `${this.boardType}-board-detail`,
        params: { articleNo: articleNo }
      });
      //this.showList = false;
    }, // 목록으로 돌아갈때는 입력(바인딩)했던 내용 초기화 후
    // showFlag를 list로 바꿈
    goToBoard: function() {
      this.resetForm();
      this.showFlag = "list";
    },
    // 입력 form 초기화는 title, contents, isNotice에 적용된다
    resetForm: function() {
      this.article.title = null;
      this.article.contents = null;
      this.article.isNotice = false;
    }
  },
  computed: {
    ...mapState(["boardColumns", "boardSearchKeywords", "boardType"])
  },
  created() {
    // this.options = this.boardSearchKeywords;
    // this.columns = this.boardColumns;
    this.selectAllArticles();
    this.selectAllNotices();
  }
};
</script>
