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
        <!-- tiptap으로 문제 내용 출력 -->
        <div>
          <quasar-tiptap
            class="shadow-3"
            v-bind="options"
            style="margin-top:10px;margin-bottom:10px"
          />
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
            @click="goToProblem"
          />
          <q-btn color="primary" label="문제답안제출" @click="goToProblemResult" />
          <q-btn
            color="positive"
            label="문제수정요청"
            @click="goToAnswerModifyBoardDetail"
          />
          <q-btn
            color="positive"
            label="문제수정"
            outline
            @click="goToProblemUpdate"
          />
          <q-btn
            color="negative"
            label="문제삭제"
            outline
            @click="deleteProblem"
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
import Axios from "axios";
import { QuasarTiptap, RecommendedExtensions } from "quasar-tiptap";
import "quasar-tiptap/lib/index.css";
import { notify } from "src/api/common.js";

export default {
  name: "ProblemDetail",
  components: {
    QuasarTiptap
  },
  data() {
    return {
      // 로딩 flag
      loading: true,
      // 문제의 채점 결과
      result: false,
      // 문제(주관식, 서술형) 정답 입력값
      answerText: "",
      // String이던 item.problem.multipleChoice(문제 선지)를 배열로 저장
      multipleChoice: [],
      // 문제(객관식) 정답 입력값
      answerChecklist: [],
      // 문제 정보
      item: {
        // 문제
        problem: {
          // 문제 번호
          problemNo: 0,
          // 선지
          multipleChoice: "",
          // 제목
          title: "",
          // 내용
          contents: "",
          // 카테고리 번호
          categoryNo: "",
          // 문제 타입(객관식, 주관식, 서술형)
          type: 0,
          // 등록 시간
          regiTime: "",
          // 문제 작성자
          nickname: ""
        },
        // 대분류 카테고리
        categoryLarge: {
          // 대분류 카테고리 번호
          categoryNo: 0,
          // 대분류 카테고리 이름
          categoryName: ""
        },
        // 중분류 카테고리
        categoryMedium: {
          // 중분류 카테고리 번호
          categoryNo: 0,
          // 중분류 카테고리 이름
          categoryName: ""
        },
        // 소분류 카테고리
        categorySmall: {
          // 소분류 카테고리 번호
          categoryNo: 0,
          // 소분류 카테고리 이름
          categoryName: ""
        },
        // 해쉬태그 리스트(HashtagName : String)
        hashTag: []
      },
      // 테이블 컬럼
      columns: [
        // 테이블 항목
        { name: "title", align: "left", label: "title", field: "title" },
        // 테이블 항목에 따른 내용
        {
          name: "content",
          align: "left",
          label: "content",
          field: "content",
          style: "width:100vw"
        }
      ],
      // 테이블 데이터
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
      // quasar-tiptap 옵션
      options: {
        // 글 내용
        content: "",
        // 수정 가능 여부
        editable: false,
        // 툴바 표시 여부
        showToolbar: false,
        extensions: [...RecommendedExtensions]
      }
    };
  },
  methods: {
    /**
     * @Method설명 : 문제 번호를 통해 문제정보를 받아온 후 데이터 전처리
     * @변경이력 :
     */
    selectProblem(){
      Axios.get("problem/" + this.$route.params.problemNo)
        .then(Response => {
          this.item = Response.data;
          // 선지가 있다 = 객관식 문제이면
          if (this.item.problem.multipleChoice != null) {
            // ,로 구분 되어있는 선지를 배열로 분리
            this.multipleChoice = this.item.problem.multipleChoice.split(",");
          }
          // 문제 정보 테이블에 문제 제목 정보를 넣음
          this.data[0].content = this.item.problem.title;
          // 문제 정보 테이블에 카테고리 정보를 넣음
          this.data[1].content =
            this.item.categoryLarge.categoryName +
            ">" +
            this.item.categoryMedium.categoryName +
            ">" +
            this.item.categorySmall.categoryName;
          // 문제 정보 테이블에 문제 작성자 닉네임을 넣음
          this.data[2].content = this.item.problem.nickname;
          // 문제 정보 테이블에 문제 등록 시간을 넣음
          this.data[3].content = this.item.problem.regiTime;
          // quasar-tiptap의 content에 문제 내용을 넣음
          this.options.content = this.item.problem.contents;
        })
        .catch(error => {
          notify("negative","white","error","조회 실패");
          this.goToProblem();
        })
        .finally(() => {
          this.loading = false;
        });
    },
    /**
     * @Method설명 : 문제리스트로 이동
     * @변경이력 :
     */
    goToProblem() {
      this.$router.push({ name: "Problem" });
    },
    /**
     * @Method설명 : 채점 후 결과 페이지로 이동
     * @변경이력 :
     */
    goToProblemResult() {
      var type = this.item.problem.type;
      if (
        (type === 0 && this.answerChecklist == "") ||
        ((type === 1 || type === 2) && this.answerText == "")
      ) {
        notify("warning","white","warning","정답을 입력해주세요");
        return;
      }
      this.answerChecklist.sort();
      // 정답 불러와서 비교
      // 객관식이나 주관식 인 경우
      if (type === 0 || type === 1) {
        this.loading = true;
        Axios.get("problem/answer/" + this.item.problem.problemNo)
          .then(Response => {
            var answer = Response.data.answer;
            // 객관식인 경우, 입력한 정답이 문제 정답과 같을 때
            if (type === 0 && answer === this.answerChecklist.toString()) {
              this.result = true;
            } 
            // 주관식인 경우, 입력한 정답이 문제 정답과 같을 때
            else if (type === 1 && answer === this.answerText) {
              this.result = true;
            }
            // 채점 후 채점 결과 페이지로 이동
            this.$router.push({
              name: "ProblemResult",
              params: {
                problemNo: this.item.problem.problemNo,
                result: this.result
              }
            });
          })
          .catch(error => {
            notify("negative","white","error","채점 실패");
          })
          .finally(() => {
            this.loading = false;
          });
      } else if (type === 2) {
        notify("positive","white","done","주관식입니다");
      }
    },
    /**
     * @Method설명 : 답안 수정 요청 페이지로 이동
     * @변경이력 :
     */
    goToAnswerModifyBoardDetail() {},
    /**
     * @Method설명 : 문제 수정 페이지로 이동
     * @변경이력 :
     */
    goToProblemUpdate() {
      this.$router.push({
        name: "ProblemUpdate",
        params: {
          problemNo: this.item.problem.problemNo
        }
      });
    },
    /**
     * @Method설명 : 문제 삭제
     * @변경이력 :
     */
    deleteProblem() {
      Axios.delete("problem/delete/" + this.item.problem.problemNo)
        .then(Response => {
          notify("positive","white","done","삭제 완료");
          this.goToProblem();
        })
        .catch(error => {
          notify("negative","white","error","삭제 실패");
        });
    }
  },
  created() {
      this.selectProblem();
  }
};
</script>

<style scoped>
.content {
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
