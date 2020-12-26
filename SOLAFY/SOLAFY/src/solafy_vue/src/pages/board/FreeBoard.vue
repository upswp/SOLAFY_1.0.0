<template>
  <div>
    <h3>FreeBoard</h3>
    <div class="q-pa-md" style="max-width: 300px">
      <div class="q-gutter-md">
        <q-badge color="secondary" multi-line>
          Selection: "{{ selection }}"
        </q-badge>
        <!-- 선택한 내용은 model에 담긴다 -->
        <q-select
          filled
          v-model="selection"
          :options="options"
          label="Standard"
        />
      </div>
    </div>
    <q-badge color="secondary" multi-line> keyword: "{{ keyword }}" </q-badge>
    <q-input
      bottom-slots
      v-model="keyword"
      label="검색어를 입력해주세요"
      counter
      maxlength="12"
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
    <q-table
      title="Treats"
      :data="articles"
      :columns="columns"
      row-key="name"
    />
  </div>
</template>

<script>
import Axios from "axios";
export default {
  data() {
    return {
      selection: "제목",
      options: ["제목", "작성자"],
      articles: [],
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
          field: row => row.title,
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
    selectArticle: function() {
      if (this.selection === "제목") {
        Axios.get(`/free/selectArticleByTitle/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
            console.log(this.articles[0]);
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else {
        Axios.get(`/free/selectArticleByNickname/${this.keyword}`)
          .then(response => {
            this.articles = response.data;
            console.log(this.articles[0]);
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      }
    }
  },
  created() {
    this.selectAllArticles();
  }
};
</script>

<style></style>
