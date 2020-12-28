<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <div class="row">
        <q-markup-table separator="cell">
          <tbody>
            <tr>
              <td class="header">문제 제목</td>
              <td class="content"><q-input v-model="item.problem.title" borderless /></td>
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
        <quasar-tiptap v-bind="options" @update="onUpdate" />
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { QuasarTiptap, RecommendedExtensions } from 'quasar-tiptap'
import 'quasar-tiptap/lib/index.css'

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
        content: '',
        editable: true,
        extensions: [
          ...RecommendedExtensions,
          // other extenstions
          // name string, or custom extension
        ],
        toolbar: [
          'add-more',
          'separator',
          'bold',
          'italic',
          'underline',
          'strike',
          'code',
          'separator',
          'heading',
          'font-family',
          'fore-color',
          'back-color',
          'format_clear',
          'separator',
          'align-dropdown',
          'indent',
          'outdent',
          'line-height',
          'separator',
          'horizontal',
          'bullet_list',
          'ordered_list',
          'todo_list',
          'separator',
          'blockquote',
          'separator',
          'undo',
          'redo',
        ]
      },
      json: '',
      html: ''
    };
  },
  components: {
    QuasarTiptap,
  },
  methods: {
    onUpdate ({ getJSON, getHTML }) {
      this.json = getJSON()
      this.html = getHTML()
      //console.log('html', this.html)
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
</style>
