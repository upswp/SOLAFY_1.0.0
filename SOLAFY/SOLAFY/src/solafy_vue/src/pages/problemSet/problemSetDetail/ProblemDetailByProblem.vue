<template>
  <div class="q-pa-md">
    <div class="column">
      <div class="col" id="header-title">
        <h3 v-html="itemProblemSet.problemSet.title"></h3>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-8"></div>
          <div class="col-4">
            <q-btn color="primary" label="문제수정요청" id="btn" />
            <q-btn color="primary" label="돌아가기" id="btn" />
          </div>
        </div>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-8">
            <!-- 문제 내용 -->

            <!-- 마크다운으로 문제 내용 출력 -->
            <div>
              <q-markdown no-linkify>
                {{ itemProblem.problem.contents }}
              </q-markdown>
            </div>
            <!-- 객관식인 경우 선지 -->
            <div class="answer" v-if="itemProblem.problem.type === 0">
              <div v-for="(choice, index) in multipleChoice" :key="index">
                <q-checkbox :val="index + 1" v-model="answerChecklist">
                  <q-markdown>{{ index + 1 }}. {{ choice }}</q-markdown>
                </q-checkbox>
              </div>
            </div>
            <!-- 주관식인 경우 답안 입력란 -->
            <div class="answer" v-else-if="itemProblem.problem.type === 1">
              <q-input v-model="answerText" outlined dense />
            </div>
            <!-- 서술형인 경우 답안 입력란 -->
            <div class="answer" v-else-if="itemProblem.problem.type === 2">
              <q-input v-model="answerText" dense outlined autogrow />
            </div>
          </div>
          <div class="col-4">
            <div class="q-pa-md">
              <q-table
                title="문제 List"
                :data="itemProblemSet.problemList"
                :columns="listColumns"
                hide-pagination
                :pagination.sync="pagination"
                @row-click="selectProblemByNo"
              />
              <div class="q-pa-lg flex flex-center">
                <q-pagination
                  v-model="pagination.page"
                  :max="pagesNumber"
                  input
                  input-class="text-orange-10"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-10"></div>
          <div class="col-2">
            <q-btn
              color="primary"
              label="답안제출"
              @click="goToResult"
              id="btn"
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
  name: "problemSetListByProblem",
  data() {
    return {
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 3
        // rowsNumber: xx if getting data from a server
      },

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

      itemProblemSet: {
        problemSet: {
          problemSetNo: 0,
          title: "",
          regiTime: "",
          nickname: ""
        },
        problemList: []
      },

      multipleChoice: [],
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
        },

        hashTag: []
      },
      answerChecklist: [],
      answerText: "",
      loading: true,
      result: false
    };
  },
  created() {
    this.selectProblemSetByNo();
  },
  methods: {
    selectProblemByNo: function(evt, row) {
      //   this.showLoading();
      Axios.get("problem/" + row.problemNo)
        .then(Response => {
          this.itemProblem = Response.data;
          if (this.itemProblem.problem.multipleChoice != null) {
            this.multipleChoice = this.itemProblem.problem.multipleChoice.split(
              ","
            );
          }
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
    selectProblemSetByNo: function() {
      //   this.showLoading();
      Axios.get(
        "/problem/problemset/problemSetSelectByNo/" +
          this.$route.params.problemSetNo
      )
        .then(Response => {
          console.log(Response.data);
          this.itemProblemSet = Response.data;
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
    // 채점후 결과 페이지로 이동
    goToResult() {
      var type = this.itemProblem.problem.type;
      if (
        (type === 0 && this.answerChecklist == "") ||
        ((type === 1 || type === 2) && this.answerText == "")
      ) {
        this.$q.notify({
          color: "warning",
          textColor: "white",
          icon: "warning",
          message: "정답을 입력해주세요"
        });
        return;
      }
      this.answerChecklist.sort();
      // 정답 불러와서 비교
      if (type === 0 || type === 1) {
        this.loading = true;
        Axios.get("problem/answer/" + this.itemProblem.problem.problemNo)
          .then(Response => {
            var answer = Response.data.answer;
            if (type === 0 && answer === this.answerChecklist.toString()) {
              this.result = true;
            } else if (type === 1 && answer === this.answerText) {
              this.result = true;
            }
            this.$router.push({
              name: "ProblemResult",
              params: {
                problemNo: this.itemProblem.problem.problemNo,
                result: this.result
              }
            });
          })
          .catch(error => {
            this.$q.notify({
              color: "negative",
              textColor: "white",
              icon: "error",
              message: "채점 실패"
            });
          })
          .finally(() => {
            this.loading = false;
          });
      } else if (type === 2) {
        this.$q.notify({
          color: "positive",
          textColor: "white",
          icon: "done",
          message: "주관식입니다"
        });
      }
    }
  },
  computed: {
    pagesNumber() {
      return Math.ceil(
        this.itemProblemSet.problemList.length / this.pagination.rowsPerPage
      );
    }
  }
};
</script>
<style>
#btn {
  margin-left: 10px;
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
