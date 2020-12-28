<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="fixed-center col" v-if="loading">
      <q-spinner-dots color="primary" size="6em" />
    </div>
    <div class="col-md-6" v-else>
      <!-- 상단 문제 부가 정보 테이블 -->
      <div class="row">
        <q-table
          :data="data"
          :columns="columns"
          hide-header
          hide-bottom
          separator="cell"
        />
      </div>
      <!-- 문제 내용 -->
      <div class="content">
        <!-- 마크다운으로 문제 내용 출력 -->
        <div>
          <q-markdown no-linkify>{{ item.problem.contents }}</q-markdown>
        </div>
        <!-- 객관식인 경우 선지 -->
        <div class="answer" v-if="item.problem.type === 0">
          <div v-for="(choice, index) in multipleChoice" :key="index">
            <q-checkbox :val="index + 1" v-model="answerChecklist">
              <q-markdown>{{ index + 1 }}. {{ choice }}</q-markdown>
            </q-checkbox>
          </div>
        </div>
        <!-- 주관식인 경우 답안 입력란 -->
        <div class="answer" v-else-if="item.problem.type === 1">
          <q-input v-model="answerText" outlined dense />
        </div>
        <!-- 서술형인 경우 답안 입력란 -->
        <div class="answer" v-else-if="item.problem.type === 2">
          <q-input v-model="answerText" dense outlined autogrow />
        </div>
        <!-- 버튼들 -->
        <div class="q-gutter-md button">
          <q-btn
            color="primary"
            label="목록으로 돌아가기"
            outline
            @click="goToproblemList"
          />
          <q-btn color="primary" label="문제답안제출" @click="goToResult" />
          <q-btn
            color="positive"
            label="문제수정요청"
            @click="goToAnswerModify"
          />
          <q-btn
            color="positive"
            label="문제수정"
            outline
            @click="goToproblemUpdate"
          />
          <q-btn
            color="negative"
            label="문제삭제"
            outline
            @click="problemDelete"
          />
        </div>
        <hr />
        <!-- 해쉬태그 -->
        <div class="row">
          <p v-for="(ht, index) in item.hashTag" :key="index">
            #{{ ht.hashTag }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ProblemDetail",
  data() {
    return {
      columns: [
        { name: "title", align: "left", label: "title", field: "title" },
        {
          name: "content",
          align: "left",
          label: "content",
          field: "content",
          style: "width:100vw"
        }
      ],
      data: [
        {
          title: "문제 제목",
          content: ""
        },
        {
          title: "문제 카테고리",
          content: ""
        },
        {
          title: "문제 작성자",
          content: ""
        },
        {
          title: "작성일자",
          content: ""
        }
      ],
      multipleChoice: [],
      item: {
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
        categoryLarge: {
          categoryNo: 0,
          categoryName: ""
        },
        categoryMedium: {
          categoryNo: 0,
          categoryName: ""
        },
        categorySmall: {
          categoryNo: 0,
          categoryName: ""
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
    // 문제 정보 받아온 후 데이터 후 가공
    axios
      .get("problem/" + this.$route.params.problemNo)
      .then(Response => {
        this.item = Response.data;
        if (this.item.problem.multipleChoice != null) {
          this.multipleChoice = this.item.problem.multipleChoice.split(",");
        }
        this.data[0].content = this.item.problem.title;
        this.data[1].content =
          this.item.categoryLarge.categoryName +
          ">" +
          this.item.categoryMedium.categoryName +
          ">" +
          this.item.categorySmall.categoryName;
        this.data[2].content = this.item.problem.nickname;
        this.data[3].content = this.item.problem.regiTime;
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
  methods: {
    // 문제리스트로 이동
    goToproblemList() {
      this.$router.push({ name: "Problem" });
    },
    // 채점후 결과 페이지로 이동
    goToResult() {
      var type = this.item.problem.type;
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
        axios
          .get("problem/answer/" + this.item.problem.problemNo)
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
                problemNo: this.item.problem.problemNo,
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
    },
    goToAnswerModify() {},
    goToproblemUpdate() {},
    // 문제 삭제
    problemDelete() {
      axios
        .delete("problem/delete/" + this.item.problem.problemNo)
        .then(Response => {
          this.$q.notify({
            color: "positive",
            textColor: "white",
            icon: "done",
            message: "삭제 완료"
          });
          this.goToproblemList();
        })
        .catch(error => {
          this.$q.notify({
            color: "red",
            textColor: "white",
            icon: "error",
            message: "삭제 실패"
          });
        });
    }
  }
};
</script>

<style scoped>
.content {
  margin-left: 5%;
  margin-top: 2%;
}
.answer {
  margin-bottom: 10px;
}
.button {
  text-align: center;
  margin-bottom: 10px;
}
</style>
