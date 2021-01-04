<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="fixed-center col" v-if="loading">
      <q-spinner-dots color="primary" size="6em" />
    </div>
    <div class="col-md-6" v-else>
      <div class="row">
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
                    v-model="item.categoryLarge"
                    :options="largeList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="대분류"
                    @input="clickCategoryLargeList"
                  />
                  <q-select
                    class="selectbox col"
                    filled
                    v-model="item.categoryMedium"
                    :options="mediumList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="중분류"
                    @input="clickCategoryMediumList"
                  />
                  <q-select
                    class="selectbox col"
                    filled
                    v-model="item.categorySmall"
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
              <td class="content">{{ item.problem.nickname }}</td>
            </tr>
          </tbody>
        </q-markup-table>
      </div>
      <div class="row" id="editor">
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
          <quasar-tiptap
            class="shadow-3"
            v-bind="options"
            @update="onUpdate"
            style="margin-top:10px;margin-bottom:10px"
          />
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
                @keyup.enter="addInput"
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
                @click="addInput"
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
          <q-input
            filled
            autogrow
            style="margin-top:10px;margin-bottom:10px"
            label="해설"
            v-model="item.problemAnswer.solution"
          />
        </q-card>
      </div>
      <div class="row self-center hashtag">
        <q-chip
          v-for="(hashTag, index) in item.hashTag"
          :key="index"
          removable
          @remove="removeHashTag(index)"
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
          @keypress.space="insertHashTag"
          @keypress.enter="insertHashTag"
          label="HashTag"
        />
      </div>
      <hr />
      <div class="row" style="float:right">
        <q-btn
          color="primary"
          label="문제 수정"
          @click="updateProblem"
          id="btn"
        />
        <q-btn
          color="primary"
          label="수정 취소"
          @click="goToProblemDetail"
          id="btn"
        />
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { firebaseAuth } from "src/boot/firebase";
import { notify } from "src/api/common.js";
import { QuasarTiptap, RecommendedExtensions } from "quasar-tiptap";
import "quasar-tiptap/lib/index.css";
import ProblemDetailVue from "./ProblemDetail.vue";

export default {
  name: "ProblemUpdate",
  components: {
    QuasarTiptap
  },
  data() {
    return {
      // 현재 접속중인 사용자의 uid
      currentUserUid: firebaseAuth.currentUser.uid,
      // quasar-tiptap에 입력된 content가 json 변환되어 저장
      json: "",
      // quasar-tiptap에 입력된 content가 html로 변환되어 저장
      html: "",
      // 대분류 카테고리 리스트
      largeList: [],
      // 중분류 카테고리 리스트
      mediumList: [],
      // 소분류 카테고리 리스트
      smallList: [],
      // 해쉬태그 input에 바인딩되는 변수
      hashTagText: "",
      // 현재 선택하고 있는 탭
      tab: "객관식",
      // 이전에 선택되어있던 탭
      tab_pre: "객관식",
      // 객관식일 경우 선택된 정답 리스트
      choiceList: [],
      // 문제 등록 결과
      result: true,
      // 로딩 변수
      loading: true,
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
      },
      //받아오는 기존 문제에 대한 정보
      item: {
        //문제에 대한 정보
        problem: {
          //문제 번호
          problemNo: "",
          //카테고리번호
          categoryNo: "",
          //문제내용
          contents: "",
          //객관식 다지선택
          multipleChoice: "",
          //문제이름
          title: "",
          //객관,주관,서술 type
          type: 0,
          //사용자 nickname
          nickname: "",
          // 문제 작성자 uid
          uid: ""
        },
        //기존의 카테고리 대분류 정보
        categoryLarge: {
          categoryNo: 0,
          categoryName: ""
        },
        //기존의 카테고리 중분류 정보
        categoryMedium: {
          categoryNo: 0,
          categoryName: ""
        },
        //기존의 카테고리 소분류 정보
        categorySmall: {
          categoryNo: 0,
          categoryName: ""
        },
        //기존의 hashTag 정보
        hashTag: [],
        //문제에 대한 정답
        problemAnswer: {
          //문제 번호
          problemNo: 0,
          //문제 답
          answer: "",
          //문제 해설
          solution: "",
          //문제 답 구성 keword
          keyword: ""
        }
      }
    };
  },
  methods: {
    /**
     * @Method설명 : quasar-tiptap안에 내용이 변경 될 때마다 html출력
     * @변경이력 :
     */
    onUpdate({ getJSON, getHTML }) {
      this.json = getJSON();
      this.html = getHTML();
      //console.log("html", this.html);
    },
    /**
     * @Method설명 : 카테고리 대분류 선택
     * @변경이력 :
     */
    selectCategoryLargeList() {
      axios
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
     * @Method설명 :  카테고리 중분류 선택
     * @변경이력 :
     */
    selectCategoryMediumList() {
      axios
        .get("category/medium/" + this.item.categoryLarge.categoryNo)
        .then(response => {
          this.mediumList = response.data;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 중분류 불러오기 실패");
        });
    },
    /**
     * @Method설명 :  카테고리 소분류 선택
     * @변경이력 :
     */
    selectCategorySmallList() {
      axios
        .get("category/small/" + this.item.categoryMedium.categoryNo)
        .then(response => {
          this.smallList = response.data;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 소분류 불러오기 실패");
        });
    },
    /**
     * @Method설명 : LargeList selectbox click evt
     * @변경이력 :
     */
    clickCategoryLargeList() {
      this.selectCategoryMediumList();
      this.item.categoryMedium = null;
      this.smallList = [];
      this.item.categorySmall = null;
    },
    /**
     * @Method설명 : MediumList selectbox click evt
     * @변경이력 :
     */
    clickCategoryMediumList() {
      this.selectCategorySmallList();
      this.item.categorySmall = null;
    },
    /**
     * @Method설명 : x버튼을 눌렀을 때 해쉬태그 리스트에서 제거
     * @변경이력 :
     */
    removeHashTag(index) {
      this.item.hashTag.splice(index, 1);
    },
    /**
     * @Method설명 : 엔터, 스페이스바 입력 시 해쉬태그 리스트에 등록
     * @변경이력 :
     */
    insertHashTag() {
      this.item.hashTag.push(this.hashTagText);
      this.hashTagText = "";
    },
    /**
     * @Method설명 : 클릭 시 Input을 하나 추가한다.
     * @변경이력 :
     */
    addInput() {
      this.choiceList.push({ choice: "", check: false });
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
     * @Method설명 : 문제 수정 처리
     * @변경이력 :
     */
    updateProblem() {
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
        String(this.item.categoryLarge.categoryNo).padStart(2, "0") +
        String(this.item.categoryMedium.categoryNo).padStart(3, "0") +
        String(this.item.categorySmall.categoryNo).padStart(5, "0");
      // 문제 수정
      axios.put("problem/update", this.item).then(response => {
        if (response.data == "success") {
          notify("positive", "white", "done", "문제 수정 성공");
          this.$router.push({
            name: "ProblemDetail",
            params: {
              problemNo: this.$route.params.problemNo
            }
          });
        } else {
          notify("red", "white", "error", "문제 수정 실패");
        }
      });
    },
    /**
     * @Method설명 : 문제 정보 가져오기
     * @변경이력 :
     */
    selectProblemDetailByProblemNo() {
      axios
        .get("problem/" + this.$route.params.problemNo)
        .then(response => {
          this.item = response.data;
          // ! 기능 확인 필요
          if (item.problem.uid != currentUserUid) {
            notify("red", "white", "error", "권한이 없습니다.");
            this.$router.go(-1);
          }
          // item내의 hashtagDto List를 String List로 변경
          var list = [];
          response.data.hashTag.forEach(element => {
            list.push(element.hashTag);
          });
          this.item.hashTag = list;
          // 데이터 전처리
          this.tab = this.tab_pre =
            this.item.problem.type == 0
              ? "객관식"
              : this.item.problem.type == 1
              ? "주관식"
              : "서술형";
          this.options.content = this.item.problem.contents;
          // 카테고리 리스트 호출
          this.selectCategoryMediumList();
          this.selectCategorySmallList();

          // 답안 정보 호출
          this.selectProblemAnswerByProblemNo();
        })
        .catch(error => {
          notify("red", "white", "error", "문제 정보 가져오기 실패");
          this.$router.go(-1);
        });
    },
    /**
     * @Method설명 : 답안 정보 가져오기
     * @변경이력 :
     */
    selectProblemAnswerByProblemNo() {
      axios
        .get("problem/answer/" + this.$route.params.problemNo)
        .then(response => {
          this.item.problemAnswer = response.data;
          if (this.item.problem.type == 0) {
            this.item.problem.multipleChoice
              .split(",")
              .forEach((element, index) => {
                this.choiceList.push({
                  choice: element,
                  check: this.item.problemAnswer.answer
                    .split(",")
                    .includes(index + 1 + "")
                });
              });
          }
        })
        .catch(error => {
          notify("red", "white", "error", "문제 정보 가져오기 실패");
        })
        .finally(() => {
          this.loading = false;
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
     * @Method설명 : 문제상세보기
     * @변경이력 :
     */
    goToProblemDetail() {
      this.loading = true;
      this.$router.push({
        name: "ProblemDetail",
        params: {
          problemNo: this.$route.params.problemNo
        }
      });
    }
  },
  created() {
    this.selectProblemDetailByProblemNo();
    this.selectCategoryLargeList();
  }
};
</script>
<style scoped>
#btn {
  margin-left: 10px;
}
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
