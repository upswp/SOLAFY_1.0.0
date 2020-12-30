<template>
  <div class="doc-container">
    <h3>FreeBoard</h3>
    <router-view :boardType="boardType"></router-view>
  </div>
</template>

<script>
import Axios from "axios";

// 컴포넌트에서 vuex사용
import { mapMutations } from "vuex";

// import BoardList from "../../components/board/BoardList.vue";
// import BoardDetail from "../../components/board/BoardDetail.vue";
// import BoardWrite from "../../components/board/BoardWrite.vue";
// import BoardUpdate from "../../components/board/BoardUpdate.vue";
/*
backup components
    글 목록보기 시작
    <div v-if="showFlag == 'list'">
      <board-list boardType="free"></board-list>
    </div>
    <!-- 글 목록보기 끝 -->

    <!-- 글 등록하기 시작 (showFlag가 write일 경우) -->
    <div v-if="showFlag == 'write'">
      <board-write boardType="free"></board-write>
    </div>
    <!-- 글 등록하기 끝 -->

    <!-- 글 상세보기 시작 (showFlag가 detail일 경우) -->
    <div v-else-if="showFlag == 'detail'">
      <board-detail boardType="free"></board-detail>
    </div>
    <!-- 글 상세 보기 끝 -->

    <!-- 글 수정하기 시작 (showFlag가 update일 경우) -->
    <div v-else-if="showFlag == 'update'">
      <board-update></board-update>
    </div>
*/
export default {
  components: {
    // BoardList,
    // BoardDetail,
    // BoardWrite,
    // BoardUpdate
  },
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
      likeBtn: 0,
      options: ["제목", "작성자"],
      boardType: "free",
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
    ...mapMutations(["SETBOARDCOLUMNS", "SETBOARDSEARCHKEYWORDS"]),
    goToWrite() {
      this.$router.push();
    },
    goToDetail() {
      this.$router.push();
    },
    goToUpdate() {
      this.$router.push();
    }
  },
  created() {
    this.$store.commit("SETBOARDSEARCHKEYWORDS", this.options);
    this.$store.commit("SETBOARDCOLUMNS", this.columns);
  }
};
</script>

<style></style>
