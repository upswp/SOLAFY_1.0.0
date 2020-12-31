<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
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
                    v-model="selectLarge"
                    :options="largeList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="대분류"
                    @input="selectMediumList"
                  />
                  <q-select
                    class="selectbox col"
                    filled
                    v-model="selectMedium"
                    :options="mediumList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="중분류"
                    @input="selectSmallList"
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
      <div class="row">
        <q-btn color="primary" label="문제 등록" @click="InsertProblem" />
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

export default {
  name: "ProblemCreate",
  data() {
    return {
      item: {
        problem: {
          categoryNo: "",
          contents: "",
          multipleChoice: "",
          title: "",
          type: 0,
          uid: ""
        },
        problemAnswer: {
          answer: "",
          keyword: "",
          solution: ""
        },
        hashTag: [],
        problemSetNo: 0
      },
      nickname: "",
      largeList: [],
      selectLarge: null,
      mediumList: [],
      selectMedium: null,
      smallList: [],
      selectSmall: null,
      hashTagText: "",
      tab: "객관식",
      tab_pre: "객관식",
      choiceList: [],
      result: true,
      options: {
        content: "",
        editable: true,
        showToolbar: true,
        showBubble: true,
        extensions: [
          ...RecommendedExtensions
          // other extenstions
          // name string, or custom extension
        ],
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
      json: "",
      html: ""
    };
  },
  components: {
    QuasarTiptap
  },
  methods: {
    // quasar-tiptap안에 내용이 변경 될 때마다 html출력
    onUpdate({ getJSON, getHTML }) {
      this.json = getJSON();
      this.html = getHTML();
      //console.log("html", this.html);
    },
    // 카테고리 대분류 선택
    selectLargeList() {
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
    // 카테고리 중분류 선택
    selectMediumList() {
      axios
        .get("category/medium/" + this.selectLarge.categoryNo)
        .then(response => {
          this.mediumList = response.data;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 중분류 불러오기 실패");
        });
    },
    // 카테고리 소분류 선택
    selectSmallList() {
      axios
        .get("category/small/" + this.selectMedium.categoryNo)
        .then(response => {
          this.smallList = response.data;
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 소분류 불러오기 실패");
        });
    },
    // x버튼을 눌렀을 때 해쉬태그 리스트에서 제거
    removeHashTag(index) {
      this.item.hashTag.splice(index, 1);
    },
    // 엔터, 스페이스바 입력 시 해쉬태그 리스트에 등록
    insertHashTag() {
      this.item.hashTag.push(this.hashTagText);
      this.hashTagText = "";
    },
    // 클릭 시 Input을 하나 추가한다.
    addInput() {
      this.choiceList.push({ choice: "", check: false });
    },
    // 문제 등록 처리
    InsertProblem() {
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
      this.createProblem();
    },
    // 문제 등록
    createProblem() {
      axios
        .post("problem/create", this.item)
        .then(response => {
          this.updateFlag();
        })
        .catch(error => {
          console.log(error);
          this.result = false;
        });
    },
    // 문제의 flag를 0에서 1로 변경
    updateFlag() {
      axios
        .put("problem/updateflag/" + firebaseAuth.currentUser.uid)
        .then(response => {})
        .catch(error => {
          console.log(error);
          this.result = false;
        })
        .finally(() => {
          if (this.result) {
            notify("positive", "white", "done", "문제 등록 성공");
            this.$router.push({
              name: "Problem"
            });
          } else {
            notify("red", "white", "error", "문제 등록 실패");
          }
        });
    },
    // 탭 클릭 시 type 설정
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
    // input값 초기화
    clearInput() {
      this.item.problemAnswer.answer = "";
      this.item.problemAnswer.keyword = "";
      this.choiceList = [];
    },
    // User의 Nickname반환
    selectNickname() {
      axios
        .get("user/selectbyuid/" + firebaseAuth.currentUser.uid)
        .then(response => {
          this.nickname = response.data.nickname;
        })
        .catch(error => {
          notify("red", "white", "error", "닉네임 불러오기 실패");
          this.$router.go(-1);
        });
    }
  },
  created() {
    this.selectLargeList();
    this.selectNickname();
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
