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
                    class="selectbox"
                    filled
                    v-model="selectLarge"
                    :options="largeList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="대분류"
                    @input="selectMediumList"
                  />
                  <q-select
                    class="selectbox"
                    filled
                    v-model="selectMedium"
                    :options="mediumList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="중분류"
                    @input="selectSmallList"
                  />
                  <q-select
                    class="selectbox"
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
      <div class="row">
        <quasar-tiptap id="editor" class="shadow-3" v-bind="options" @update="onUpdate" scrollable/>
      </div>
    </div>
    <div class="test">
      <h1></h1><p><img src="data:image/png;base64,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" caption="" ref=""></p><p></p>
    </div>
  </div>
</template>
<script>
import axios from "axios";
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
      nickname: "닉네임파베에서 받아오기",
      largeList: [],
      selectLarge: null,
      mediumList: [],
      selectMedium: null,
      smallList: [],
      selectSmall: null,
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
    onUpdate({ getJSON, getHTML }) {
      this.json = getJSON();
      this.html = getHTML();
      console.log("html", this.html);
    },
    selectLargeList() {
      axios
        .get(`/category/large`)
        .then(response => {
          this.largeList = response.data;
        })
        .catch(error => {
          alert(error);
        });
    },
    selectMediumList() {
      axios
        .get(`/category/medium/${this.selectLarge.categoryNo}`)
        .then(response => {
          this.mediumList = response.data;
        })
        .catch(error => {
          alert(error);
        });
    },
    selectSmallList() {
      axios
        .get(`/category/small/${this.selectMedium.categoryNo}`)
        .then(response => {
          this.smallList = response.data;
        })
        .catch(error => {
          alert(error);
        });
    }
  },
  created() {
    this.selectLargeList();
  }
};
</script>
<style scoped>
.header {
  text-align: center;
  width: 100px;
}
.content{
  width: 100vw;
}
.selectbox {
  width: 30%;
}
#editor{
  margin-top: 20px;
  min-height: 300px;
}
</style>
