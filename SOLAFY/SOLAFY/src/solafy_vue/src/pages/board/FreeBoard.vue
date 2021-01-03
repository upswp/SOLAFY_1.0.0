<template>
  <div class="doc-container">
    <h3>FreeBoard</h3>
    <!-- 여기에 페이지들이 표시됩니다! -->
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
      // 게시판 타입
      boardType: "free",
      // 게시판 list에서 사용될 검색옵션
      options: ["제목", "작성자"],
      // 게시판 list에서 사용될 컬럼
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
        },
        {
          name: "likeCount",
          required: true,
          label: "좋아요수",
          align: "left",
          field: row => row.likeCount,
          format: val => `${val}`,
          sortable: true
        }
      ]
    };
  },
  methods: {
    // vuex에 저장되어있는 변수(state)를 바꿔주기 위한 메서드!(setter같은 기분)
    ...mapMutations(["SETBOARDCOLUMNS", "SETBOARDSEARCHKEYWORDS"])
  },
  created() {
    /**
     * 처음에 FreeBoard의 기본 값들을 vuex의 states에 세팅
     *
     * 순서대로 검색옵션 - 테이블컬럼정보 - 게시판타입(이후 mapping url에 적용)
     */
    this.$store.commit("SETBOARDSEARCHKEYWORDS", this.options);
    this.$store.commit("SETBOARDCOLUMNS", this.columns);
    this.$store.commit("SETBOARDTYPE", this.boardType);
  }
};
</script>

<style></style>
