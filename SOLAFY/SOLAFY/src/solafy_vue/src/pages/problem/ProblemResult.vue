<template>
  <div class="q-pa-md">
    <div class="column">
      <!-- 상단 제목 및 돌아가기 버튼 -->
      <div class="col" id="header-title">
        <h3 v-html="itemProblem.problem.title"></h3>
      </div>
      <!-- contents -->
      <div class="col">
        <div class="row justify-center">
          <q-tab-panel name="alarms">
            <div class="text-h6">채점 결과</div>
            <h5>{{ resultText }}</h5>
          </q-tab-panel>
        </div>
        <br />
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <div class="q-pa-md">
              <q-table
                id="table"
                title="추천 문제 List"
                :data="recommendProblemList"
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
            <q-btn
              id="btn"
              color="primary"
              label="돌아가기"
              @click="goToProblemList"
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
      // 추천문제 list
      recommendProblemList: [],
      //pagination custumizing
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 3
        // rowsNumber: xx if getting data from a server
      },
      //문제 정답결과에 따른 text 반환
      resultText: {
        fail: "오답입니다.",
        success: "정답입니다."
      },
      //result table columns에 대한 설정
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
      //유사문제 추천 table에 대한 columns 설정
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
      itemProblem: {
        problem: {
          problemNo: 0,
          multipleChoice: "",
          title: "",
          contents: "",
          categoryNo: "",
          type: 0,
          regiTime: "",
          nickname: ""
        }
      }
    };
  },
  methods: {
    /**
     * @Method설명 : params로 넘어오는 문제 결과를 확인하여 text값 반환
     * @변경이력 :
     */
    checkResult: function() {
      if (this.$route.params.result === "false") {
        this.resultText = this.resultText.fail;
      } else {
        this.resultText = this.resultText.success;
      }
    },
    /**
     * @Method설명 : 풀이한 문제에 대한 값을 problemNo를 기준으로 가져옴
     * @변경이력 :
     */
    selectProblemByNo: function() {
      this.showLoading();
      Axios.get("problem/" + this.$route.params.problemNo)
        .then(Response => {
          this.itemProblem = Response.data;
        })
        .catch(error => {
          notify("red", "white", "error", "문제정보 조회 실패");
          this.$router.go(-1);
          this.goToProblemList();
        })
        .finally(() => {
          this.loading = false;
        });
    },
    /**
     * @Method설명 : ProblemSet Contents - table 반환
     * @변경이력 :
     */
    selectRecommendProblemListmByNo: function() {
      //   this.showLoading();
      Axios.get("problem/recommend/" + this.$route.params.problemNo)
        .then(Response => {
          console.log(Response.data);
          this.recommendProblemList = Response.data;
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "조회 실패"
          });
          this.goToProblemList();
        })
        .finally(() => {
          this.loading = false;
        });
    },

    /**
     * @Method설명 : show LoadingPage
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
     * @Method설명 : ProblemSetList 이동
     * @변경이력 :
     */
    goToProblemList: function() {
      this.$router.push({
        name: "Problem"
      });
    }
  },
  created() {
    this.selectProblemByNo();
    this.selectRecommendProblemListmByNo();
    this.checkResult();
  },
  computed: {
    pagesNumber() {
      return Math.ceil(
        this.recommendProblemList.length / this.pagination.rowsPerPage
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
