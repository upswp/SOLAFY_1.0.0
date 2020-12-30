<template>
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
</template>
<script>
import Axios from "axios";

export default {
  name: "boardlist",
  data() {
    return {
      showFlag: "list",
      selection: "제목",
      articles: [],
      notices: [],
      errored: null,
      loading: null,
      keyword: null
    };
  },
  props: ["boardType"],
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
    created() {
      console.log(this.$root);
    }
  }
};
</script>
