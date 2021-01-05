<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <div class="row">
        <!-- 상단 문제 부가 정보 입력 테이블 -->
        <q-markup-table separator="cell">
          <tbody>
            <tr>
              <td class="header">문제 제목</td>
              <td class="content">
                <q-input v-model="item.problem.title" borderless />
              </td>
            </tr>
            <tr>
              <td class="header">문제 카테고리</td>
              <td class="content">
                <div class="row q-gutter-md">
                  <q-select
                    class="selectbox col"
                    filled
                    v-model="selectLarge"
                    :options="largeList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="대분류"
                    @input="selectCategoryMediumList"
                  />
                  <q-select
                    class="selectbox col"
                    filled
                    v-model="selectMedium"
                    :options="mediumList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="중분류"
                    @input="selectCategorySmallList"
                  />
                  <q-select
                    class="selectbox col"
                    filled
                    v-model="selectSmall"
                    :options="smallList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="소분류"
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td class="header">문제 작성자</td>
              <td class="content">{{ nickname }}</td>
            </tr>
          </tbody>
        </q-markup-table>
      </div>
      <!-- 문제 내용 입력 -->
      <div class="row" id="editor">
        <!-- 객관식, 주관식, 서술형을 나누는 탭 -->
        <q-card style="padding-left:20px;padding-right:20px;">
          <q-tabs
            v-model="tab"
            dense
            class="text-grey"
            active-color="primary"
            indicator-color="primary"
            align="justify"
            narrow-indicator
          >
            <q-tab
              name="객관식"
              label="객관식"
              @click.prevent="setType('객관식')"
            />
            <q-tab
              name="주관식"
              label="주관식"
              @click.prevent="setType('주관식')"
            />
            <q-tab
              name="서술형"
              label="서술형"
              @click.prevent="setType('서술형')"
            />
          </q-tabs>

          <q-separator />
          <p style="font-size:20px; margin-top:10px;margin-bottom:10px">
            문제
          </p>
          <!-- 문제 내용 입력 -->
          <quasar-tiptap
            class="shadow-3"
            v-bind="options"
            @update="onUpdate"
            style="margin-top:10px;margin-bottom:10px"
          />
          <!-- 문제 타입에 따른 문제 정답 입력 -->
          <q-tab-panels v-model="tab" animated>
            <q-tab-panel class="panel" name="객관식">
              <p style="font-size:20px; margin-top:10px;margin-bottom:10px">
                선지(정답체크)
              </p>
              <q-input
                v-for="(choi, index) in choiceList"
                :key="index"
                filled
                style="margin-top:10px;margin-bottom:10px"
                v-model="choi.choice"
                @keypress.enter="createInput"
                autofocus
              >
                <template v-slot:before>
                  <q-checkbox v-model="choi.check" />
                  <q-chip
                    color="primary"
                    text-color="white"
                    :label="index + 1"
                  />
                </template>
              </q-input>
              <q-btn
                round
                color="primary"
                icon="add"
                @click="createInput"
                style="margin-top:10px;margin-bottom:10px"
              />
            </q-tab-panel>

            <q-tab-panel class="panel" name="주관식">
              <q-input
                filled
                style="margin-top:10px;margin-bottom:10px"
                label="정답"
                v-model="item.problemAnswer.answer"
              />
            </q-tab-panel>

            <q-tab-panel class="panel" name="서술형">
              <q-input
                filled
                autogrow
                style="margin-top:10px;margin-bottom:10px"
                label="정답"
                v-model="item.problemAnswer.answer"
              />
              <q-input
                filled
                style="margin-top:10px;margin-bottom:10px"
                label="정답 키워드"
                hint="','로 구분"
                v-model="item.problemAnswer.keyword"
              />
            </q-tab-panel>
          </q-tab-panels>
          <!-- 해설 입력 -->
          <q-input
            filled
            autogrow
            style="margin-top:10px;margin-bottom:10px"
            label="해설"
            v-model="item.problemAnswer.solution"
          />
        </q-card>
      </div>
      <!-- 해쉬태그 입력 -->
      <div class="row self-center hashtag">
        <q-chip
          v-for="(hashTag, index) in item.hashTag"
          :key="index"
          removable
          @remove="deleteHashTagByBtn(index)"
          color="primary"
          text-color="white"
        >
          #{{ hashTag }}
        </q-chip>
        <q-input
          rounded
          outlined
          dense
          v-model="hashTagText"
          @keypress.space="createHashTagByKeyboard"
          @keypress.enter="createHashTagByKeyboard"
          label="HashTag"
        />
      </div>
      <hr />
      <!-- 버튼들 -->
      <div class="row">
        <div class="col">
        <q-btn outline color="primary" label="목록으로 돌아가기" @click="goToProblem" />
        </div>
        <div class="col">
          <q-btn color="primary" label="문제 등록" @click="insertProblem" style="float:right"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import { firebaseAuth } from "src/boot/firebase";
import { SessionStorage } from "quasar";
import { notify } from "src/api/common.js";
import { QuasarTiptap, RecommendedExtensions } from "quasar-tiptap";
import "quasar-tiptap/lib/index.css";

export default {
  name: "ProblemCreate",
  components: {
    QuasarTiptap
  },
  data() {
    return {
      // 작성자 닉네임
      nickname: SessionStorage.getItem("loginUser").nickname,
      // 대분류 카테고리 리스트
      largeList: [],
      // 선택된 대분류 카테고리
      selectLarge: null,
      // 중분류 카테고리 리스트
      mediumList: [],
      // 선택된 중분류 카테고리
      selectMedium: null,
      // 소분류 카테고리 리스트
      smallList: [],
      // 선택된 소분류 카테고리
      selectSmall: null,
      // 해쉬태그 input에 바인딩되는 변수
      hashTagText: "",
      // 현재 선택하고 있는 탭
      tab: "객관식",
      // 이전에 선택되어있던 탭
      tab_pre: "객관식",
      // 객관식일 경우 선택된 정답 리스트
      choiceList: [],
      // quasar-tiptap에 입력된 content가 html로 변환되어 저장
      html: "",
      // 문제 정보
      item: {
        // 문제
        problem: {
          // 카테고리 번호
          categoryNo: "",
          // 내용
          contents: "",
          // 선지
          multipleChoice: "",
          // 제목
          title: "",
          // 문제 타입(객관식, 주관식, 서술형)
          type: 0,
          // 작성자 uid
          uid: ""
        },
        // 문제 정답
        problemAnswer: {
          // 정답
          answer: "",
          // 정답 키워드
          keyword: "",
          // 해설
          solution: ""
        },
        // 해쉬태그
        hashTag: [],
        // 문제집 번호 : 문제 한 개만 등록 하므로 0을 주어서 문제집과 연결을 안함.
        problemSetNo: 0
      },
      // quasar-tiptap 옵션
      options: {
        // 글 내용
        content: "",
        // 수정 가능 여부
        editable: true,
        // 툴바 표시 여부
        showToolbar: true,
        // 메뉴 버블 사용 여부
        showBubble: true,
        extensions: [
          ...RecommendedExtensions
          // other extenstions
          // name string, or custom extension
        ],
        // quasar-tiptap 툴바 구성 요소
        toolbar: [
          "add-more",
          "separator",
          "bold",
          "italic",
          "underline",
          "strike",
          "code",
          "separator",
          "heading",
          "font-family",
          "fore-color",
          "back-color",
          "format_clear",
          "separator",
          "align-dropdown",
          "indent",
          "outdent",
          "line-height",
          "separator",
          "horizontal",
          "bullet_list",
          "ordered_list",
          "todo_list",
          "separator",
          "blockquote",
          "separator",
          "undo",
          "redo"
        ]
      }
    };
  },
  methods: {
    /**
     * @Method설명 : quasar-tiptap안에 내용이 변경 될 때마다 html에 저장
     * @변경이력 :
     */
    onUpdate({ getHTML }) {
      this.html = getHTML();
    },
    /**
     * @Method설명 : 카테고리 대분류 리스트 저장
     * @변경이력 :
     */
    selectCategoryLargeList() {
      Axios
        .get("category/large")
        .then(response => {
          this.largeList = response.data;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 대분류 불러오기 실패");
          this.$router.go(-1);
        });
    },
    /**
     * @Method설명 : 카테고리 중분류 리스트 저장
     * @변경이력 :
     */
    selectCategoryMediumList() {
      Axios
        .get("category/medium/" + this.selectLarge.categoryNo)
        .then(response => {
          this.mediumList = response.data;
          this.selectMedium=null;
          this.smallList=[];
          this.selectSmall=null;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 중분류 불러오기 실패");
        });
    },
    /**
     * @Method설명 : 카테고리 소분류 리스트 저장
     * @변경이력 :
     */
    selectCategorySmallList() {
      Axios
        .get("category/small/" + this.selectMedium.categoryNo)
        .then(response => {
          this.smallList = response.data;
          this.selectSmall=null;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 소분류 불러오기 실패");
        });
    },
    /**
     * @Method설명 : x버튼을 눌렀을 때 해쉬태그 리스트에서 제거
     * @변경이력 :
     */
    deleteHashTagByBtn(index) {
      this.item.hashTag.splice(index, 1);
    },
    /**
     * @Method설명 : 해쉬태그 리스트에 해쉬태그 등록
     * @변경이력 :
     */
    createHashTagByKeyboard() {
      this.item.hashTag.push(this.hashTagText);
      this.hashTagText = "";
    }, 
    /**
     * @Method설명 : 선지 Input을 하나 추가
     * @변경이력 :
     */
    createInput() {
      this.choiceList.push({ choice: "", check: false });
    },
    /**
     * @Method설명 : 문제 등록관련 데이터 전처리 후 등록
     * @변경이력 :
     */
    insertProblem() {
      // 객관식인 경우
      if (this.item.problem.type == 0) {
        // 선지 저장
        this.item.problem.multipleChoice = this.choiceList
          .map(el => {
            return el.choice;
          })
          .toString();
        // 정답 저장
        var tmp = [];
        for (var i = 0; i < this.choiceList.length; i++) {
          if (this.choiceList[i].check) {
            tmp.push(i + 1);
          }
        }
        this.item.problemAnswer.answer = tmp.toString();
      }
      // 내용 저장
      this.item.problem.contents = this.html;
      // uid 저장
      this.item.problem.uid = firebaseAuth.currentUser.uid;
      // 카테고리번호저장
      this.item.problem.categoryNo =
        String(this.selectLarge.categoryNo).padStart(2, "0") +
        String(this.selectMedium.categoryNo).padStart(3, "0") +
        String(this.selectSmall.categoryNo).padStart(5, "0");
      //문제 등록
      this.createProblem();
    },
    /**
     * @Method설명 : 문제 등록
     * @변경이력 :
     */
    createProblem() {
      Axios
        .post("problem/create", this.item)
        .then(response => {
          notify("positive", "white", "done", "문제 등록 성공");
            this.$router.push({
              name: "Problem"
            });
        })
        .catch(error => {
          console.log(error);
          notify("red", "white", "error", "문제 등록 실패");
        });
    },
    /**
     * @Method설명 : 탭 클릭 시 type 설정
     * @변경이력 :
     */
    setType(name) {
      this.$q.notify({
        progress: true,
        message:
          "이때까지 입력한 정답 데이터가 날아갑니다. \n넘어가시겠습니까?",
        color: "primary",
        icon: "warning",
        position: "center",
        // '네' 클릭 시 tab과 이전상태tab을 현재 클릭한 탭으로 변경
        // '아니오' 클릭 시 tab을 이전상태의 tab으로 변경
        actions: [
          {
            label: "네",
            color: "yellow",
            handler: () => {
              this.clearInput();
              this.tab = name;
              this.tab_pre = name;
            }
          },
          {
            label: "아니오",
            color: "white",
            handler: () => {
              this.tab = this.tab_pre;
            }
          }
        ]
      });
      if (name == "객관식") {
        this.item.problem.type = 0;
      } else if (name == "주관식") {
        this.item.problem.type = 1;
      } else {
        this.item.problem.type = 2;
      }
    },
    /**
     * @Method설명 : input값 초기화
     * @변경이력 :
     */
    clearInput() {
      this.item.problemAnswer.answer = "";
      this.item.problemAnswer.keyword = "";
      this.choiceList = [];
    },
    /**
     * @Method설명 : 문제리스트로 이동
     * @변경이력 :
     */
    goToProblem() {
      this.$router.push({ name: "Problem" });
    },
  },
  created() {
    this.selectCategoryLargeList();
  }
};
</script>
<style scoped>
.header {
  text-align: center;
  width: 100px;
}
.content {
  width: 100vw;
}
.selectbox {
  width: 30%;
}
#editor {
  margin-top: 20px;
}
.hashtag {
  margin-top: 20px;
}
.panel {
  padding: 0;
}
</style>
