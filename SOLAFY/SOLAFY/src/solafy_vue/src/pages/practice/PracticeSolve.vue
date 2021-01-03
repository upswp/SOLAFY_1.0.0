<template>
  <div class="q-pa-md">
    <div class="column">
      <div class="col" id="header-title">
        <h3 v-html="itemProblemSet.title"></h3>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-8"></div>
          <div class="col-4">
            <q-btn color="primary" label="문제수정요청" />
            <q-btn color="primary" label="돌아가기" />
          </div>
        </div>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-8">
            <!-- 문제 내용 -->

            <!-- 마크다운으로 문제 내용 출력 -->
            <div>
              <quasar-tiptap
                class="shadow-3"
                v-bind="options"
                style="margin-top:10px;margin-bottom:10px"
              />
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
                :data="itemProblemSet.problems"
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
            <q-btn color="primary" label="답안제출" @click="goToResult" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import routes from "src/router/routes";
import { QuasarTiptap, RecommendedExtensions } from "quasar-tiptap";
import "quasar-tiptap/lib/index.css";

export default {
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

      itemProblemSet: {},

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

      options: {
        content: "문제를 클릭해주세요",
        editable: false,
        showToolbar: false,
        extensions: [...RecommendedExtensions]
      },
      result: false,
      resultList: []
    };
  },
  components: {
    QuasarTiptap
  },
  created() {
    this.selectProblemSetByNo();
  },
  methods: {
    selectFirstProblemByNo: function() {
      Axios.get("problem/" + this.itemProblemSet.problems[0].problemNo)
        .then(Response => {
          this.itemProblem = Response.data;
          if (this.itemProblem.problem.multipleChoice != null) {
            this.multipleChoice = this.itemProblem.problem.multipleChoice.split(
              ","
            );
          }
          this.options.content = this.itemProblem.problem.contents;
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "첫번째 문제 조회 실패"
          });
        })
        .finally(() => {
          this.loading = false;
        });
    },
    selectProblemByNo: function(evt, row) {
      var type = this.itemProblem.problem.type;
      //객관식일때
      if (type === 0) {
        console.log("객관식 type 처리중입니다.");
        // 답이 비어있지 않으면
        if (this.answerChecklist != null) {
          this.resultList[row + 1] = this.answerChecklist.toString;
          console.log("answerChecklist::::" + this.answerChecklist);
          console.log(
            "this.resultList[row + 1]::::" + this.resultList[row + 1].toString
          );
          //답이 공란인 상태이면
        } else {
          this.confirm();
          // 공란인 상태에서 다른 문제로 넘어가겠다.
          if (this.confirm()) {
            this.resultList[row + 1] = null;
            this.answerChecklist = null;
            //공란인 상태로 안넘어가고 현 페이지에 남아있겠다.
          } else {
            return;
          }
        }
        //주관식, 서술형일때
      } else {
        console.log("주관식,서술형 type 처리중입니다.");
        if (this.answerText != null) {
          this.resultList[row + 1] = this.answerText;
        } else {
          if (this.confirm()) {
            this.resultList[row + 1] = null;
            this.answerText = null;
          } else {
            return;
          }
        }
      }

      Axios.get("problem/" + row.problemNo)
        .then(Response => {
          this.itemProblem = Response.data;
          if (this.itemProblem.problem.multipleChoice != null) {
            this.multipleChoice = this.itemProblem.problem.multipleChoice.split(
              ","
            );
          }
          this.options.content = this.itemProblem.problem.contents;
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "조회 실패"
          });
          console.log("errormsg" + error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    confirm() {
      console.log("Start confirm!!");
      this.$q
        .dialog({
          title: "Confirm",
          message: "답안을 입력하시지 않았습니다. 이동하시겠습니까?",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          return true;
        })
        .onCancel(() => {
          return false;
        })
        .onDismiss(() => {
          return false;
        });
    },
    selectProblemSetByNo() {
      //   this.showLoading();
      Axios.get(
        "/practicetest/selectpracticeproblems/" + this.$route.params.practiceNo
      )
        .then(Response => {
          console.log(Response.data);
          this.itemProblemSet = Response.data;
          this.selectFirstProblemByNo();
          this.resultList.length = this.itemProblemSet.problems.length;
          // console.log(this.resultList.length + "length");
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "문제집 정보 조회 실패"
          });
          console.log("errormsg" + error);
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
              name: "PracticeResult",
              params: {
                practiceNo: this.$route.params.practiceNo,
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
        this.itemProblemSet.problems.length / this.pagination.rowsPerPage
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
