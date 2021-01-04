<template>
  <div class="q-pa-md">
    <div class="column">
      <!-- 상단 제목 및 돌아가기 버튼 -->
      <div class="col" id="header-title">
        <h3 v-html="item.problemSet.title"></h3>
      </div>
      <div class="col" id="header-btn">
        <div class="row">
          <div class="col-10"></div>
          <div class="col-2">
            <q-btn
              id="btn"
              color="primary"
              label="돌아가기"
              @click="goToProblemSetList"
            />
          </div>
        </div>
      </div>
      <!-- contents -->
      <div class="col">
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <q-table
              id="table"
              title="문제집 정보"
              :data="data"
              :columns="columns"
              row-key="name"
              hide-header
              hide-bottom
              style="max-width :500px;"
            />
          </div>
        </div>
        <div class="row justify-center">
          <div class="col-12 col-md-auto">
            <h5>총 문제 : {{ item.problemList.length }}</h5>
          </div>
        </div>
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <!-- <div>
              <q-item
                clickable
                v-ripple
                v-for="(problem, index) in item.problemList"
                :key="index"
              >
                <q-item-section>
                  <q-item-label v-html="problem.problemNo"></q-item-label>
                  <q-item-label caption v-html="problem.title"> </q-item-label>
                </q-item-section>
              </q-item>
            </div> -->
            <div class="q-pa-md">
              <q-table
                id="table"
                title="문제 List"
                :data="item.problemList"
                :columns="listColumns"
                hide-pagination
                :pagination.sync="pagination"
                style="max-width :500px;"
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
          <div class="col-7"></div>
          <div class="col-5">
            <q-btn
              id="btn"
              color="primary"
              label="문제집 삭제"
              @click="ProblemSetDeleteByProblemSetNo"
            />
            <q-btn
              id="btn"
              color="primary"
              label="문제집 수정"
              @click="goToProblemSetUpdate"
            />
            <q-btn
              id="btn"
              color="primary"
              label="문제풀이 시작"
              @click="goToProblemSolving"
            />
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
      // pagination custumizing
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 3
        // rowsNumber: xx if getting data from a server
      },
      //문제집정보
      item: {
        problemSet: {
          //문제집 번호
          problemSetNo: 0,
          //문제집 제목
          title: "",
          //문제집 작성일자
          regiTime: "",
          //문제집 작성자
          nickname: ""
        },
        //문제집이 가지고 있는 문제 list
        problemList: []
      },
      //문제집 정보 table 정보
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
      //문제 현황 table 정보
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
      // 테이블 데이터
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
    /**
     * @Method설명 :ProblemSet Contents - table 반환
     * @변경이력 :
     */
    selectProblemSetByProblemNo: function() {
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
    /**
     * @Method설명 :ProblemSet Delete 반환
     * @변경이력 :
     */
    ProblemSetDeleteByProblemSetNo: function() {
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
          this.goToProblemSetList();
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
    /**
     * @Method설명 :show LoadingPage
     * @변경이력 :
     */
    showLoading() {
      this.$q.loading.show();

      // hiding in 2s
      this.timer = setTimeout(() => {
        this.$q.loading.hide();
        this.timer = void 0;
      }, 2000);
    },
    /**
     * @Method설명 :ProblemDetailByProblem 이동
     * @변경이력 :
     */
    goToProblemSolving: function() {
      this.$router.push({
        name: "ProblemDetailByProblem"
      });
    },
    /**
     * @Method설명 :ProblemSetList 이동
     * @변경이력 :
     */
    goToProblemSetList: function() {
      this.$router.push({
        name: "ProblemSet"
      });
    },
    /**
     * @Method설명 :ProblemSet Update 이동
     * @변경이력 :
     */
    goToProblemSetUpdate: function() {
      this.$router.push({
        name: "ProblemSetUpdate"
      });
    }
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
  },
  created() {
    this.selectProblemSetByProblemNo();
  }
};
</script>
<style>
#btn {
  margin-left: 10px;
}
#table {
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
