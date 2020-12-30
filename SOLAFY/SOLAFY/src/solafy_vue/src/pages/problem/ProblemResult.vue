<template>
  <div class="q-pa-md">
    <div class="column">
      <!-- 상단 제목 및 돌아가기 버튼 -->
      <div class="col" id="header-title">
        <h3>문제 타이틀</h3>
      </div>
      <!-- contents -->
      <div class="col">
        <div class="row justify-center">
          <q-tab-panel name="alarms">
            <div class="text-h6">채점 결과</div>
            Lorem ipsum dolor sit amet consectetur adipisicing elit.
          </q-tab-panel>
        </div>
        <br />
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <div class="q-pa-md">
              <q-table
                title="추천 문제 List"
                :data="item.problemList"
                :columns="listColumns"
                hide-pagination
                :pagination.sync="pagination"
              />
            </div>
            <div class="q-pa-lg flex flex-center">
              <q-pagination
                v-model="pagination.page"
                :max="pagesNumber"
                input
                input-class="text-orange-10"
              />
            </div>
            <q-separator spaced />
          </div>
        </div>
      </div>
      <!-- 하단 문제집 삭제,수정, 시작하기 버튼 -->
      <div class="col">
        <div class="row">
          <div class="col-10"></div>
          <div class="col-2">
            <q-btn color="primary" label="돌아가기" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import routes from "src/router/routes";

export default {
  name: "ProblemDetailByProblemSetInfo",
  data() {
    return {
      current: 3,
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 3
        // rowsNumber: xx if getting data from a server
      },
      columns: [
        { name: "title", align: "left", label: "title", field: "title" },
        {
          name: "content",
          style: "width:100vw",
          label: "content",
          field: "content",
          align: "left"
        }
      ],
      listColumns: [
        {
          name: "문제번호",
          align: "left",
          label: "문제번호",
          field: "problemNo"
        },
        {
          name: "문제제목",
          style: "width:100vw",
          label: "문제 제목",
          field: "title",
          align: "center"
        }
      ],
      item: {
        problemSet: {
          problemSetNo: 0,
          title: "",
          regiTime: "",
          nickname: ""
        },
        problemList: []
      },
      data: [
        {
          title: "문제집 제목",
          content: ""
        },
        {
          title: "문제 작성자",
          content: ""
        },
        {
          title: "문제 작성일자",
          content: ""
        }
      ]
    };
  },
  methods: {
    //ProblemSet Contents - table 반환
    selectProblemByNo: function() {
      //   this.showLoading();
      Axios.get(
        "/problem/problemset/problemSetSelectByNo/" +
          this.$route.params.problemSetNo
      )
        .then(Response => {
          console.log(Response.data);
          this.item = Response.data;
          this.data[0].content = this.item.problemSet.title;
          this.data[1].content = this.item.problemSet.nickname;
          this.data[2].content = this.item.problemSet.regiTime;
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "조회 실패"
          });
          this.goToproblemList();
        })
        .finally(() => {
          this.loading = false;
        });
    },
    //ProblemSetDelete 반환
    ProblemSetDelete: function() {
      this.showLoading();
      Axios.delete(
        "/problem/problemset/deleteProblemSet/" +
          this.item.problemSet.problemSetNo
      )
        .then(Response => {
          this.$q.notify({
            color: "positive",
            textColor: "white",
            icon: "done",
            message: "삭제 완료"
          });
          this.GoProblemSetList();
        })
        .catch(error => {
          this.$q.notify({
            color: "red",
            textColor: "white",
            icon: "error",
            message: "삭제 실패"
          });
        });
    },
    // show LoadingPage
    showLoading() {
      this.$q.loading.show();

      // hiding in 2s
      this.timer = setTimeout(() => {
        this.$q.loading.hide();
        this.timer = void 0;
      }, 2000);
    },
    //ProblemDetailByProblem 이동
    GoProblemSolving: function() {
      this.$router.push({
        name: "ProblemDetailByProblem"
      });
    },
    //ProblemSetList 이동
    GoProblemSetList: function() {
      this.$router.push({
        name: "ProblemSet"
      });
    },
    //ProblemSet Update 이동
    GoProblemSetUpdate: function() {
      this.$router.push({
        name: "ProblemSetUpdate"
      });
    }
  },
  created() {
    this.selectProblemByNo();
  },
  computed: {
    pagesNumber() {
      return Math.ceil(
        this.item.problemList.length / this.pagination.rowsPerPage
      );
    }
  },
  beforeDestroy() {
    if (this.timer !== void 0) {
      clearTimeout(this.timer);
      this.$q.loading.hide();
    }
  }
};
</script>
<style>
.q-btn {
  margin-left: 10px;
}
.q-table {
  width: 500px;
}
#header-title {
  height: 100px;
}
#header-btn {
  height: 150px;
}
#contents {
  padding-bottom: 20px;
}
</style>
