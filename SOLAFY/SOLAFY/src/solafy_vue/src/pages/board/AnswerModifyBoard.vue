<template>
  <div class="doc-container">
    <h3>AnswerModifyBoard</h3>
    <router-view></router-view>
  </div>
</template>

<script>
import Axios from "axios";

// 컴포넌트에서 vuex사용
import { mapMutations } from "vuex";

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
      options: ["제목", "작성자", "문제번호"],
      boardType: "answermodify",
      // 댓글, 게시글, 공지글의 정보를 담는 변수
      replies: [],
      articles: [],
      notices: [],

      errored: false,
      keyword: null,

      showFlag: "list",
      columns: [
        {
          name: "articleNo",
          required: true,
          label: "글번호",
          align: "left",
          field: row => row.articleNo,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "nickname",
          required: true,
          label: "닉네임",
          align: "left",
          field: row => row.nickname,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "problemNo",
          required: true,
          label: "문제번호",
          align: "left",
          field: row => row.problemNo,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "title",
          required: true,
          label: "제목",
          align: "left",
          field: row => [row.title, row.isNotice],
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "regiTime",
          required: true,
          label: "작성시간",
          align: "left",
          field: row => row.regiTime,
          format: val => `${val}`,
          sortable: true
        }
      ]
    };
  },
  methods: {
    ...mapMutations(["SETBOARDCOLUMNS", "SETBOARDSEARCHKEYWORDS"])
  },
  created() {
    this.$store.commit("SETBOARDSEARCHKEYWORDS", this.options);
    this.$store.commit("SETBOARDCOLUMNS", this.columns);
    this.$store.commit("SETBOARDTYPE", this.boardType);
    console.log(this.options, this.columns, this.boardType);
  }
};
</script>

<style></style>
