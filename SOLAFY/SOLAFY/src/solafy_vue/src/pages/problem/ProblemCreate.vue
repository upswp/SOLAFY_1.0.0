<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <div>
        <q-markup-table separator="cell" style="width:100vw">
          <tbody>
            <tr>
              <td class="header">문제 제목</td>
              <td><q-input v-model="item.problem.title" borderless /></td>
            </tr>
            <tr>
              <td class="header">문제 카테고리</td>
              <td>
                <div class="row q-gutter-md">
                  <q-select
                    class="selectbox"
                    filled
                    v-model="selectLarge"
                    :options="largeList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="대분류"
                    @input = "selectMediumList"
                  />
                  <q-select
                    class="selectbox"
                    filled
                    v-model="selectMedium"
                    :options="mediumList"
                    option-value="categoryNo"
                    option-label="categoryName"
                    label="중분류"
                    @input = "selectSmallList"
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
              <td>{{ nickname }}</td>
            </tr>
          </tbody>
        </q-markup-table>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

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
      selectSmall: null
    };
  },
  methods: {
    selectLargeList() {
      axios
        .get(`/category/large`)
        .then(response => {
          this.largeList=response.data;
        })
        .catch(error => {
          alert(error);
        });
    },
    selectMediumList() {
      axios
        .get(`/category/medium/${this.selectLarge.categoryNo}`)
        .then(response => {
          this.mediumList=response.data;
        })
        .catch(error => {
          alert(error);
        });
    },
    selectSmallList() {
      axios
        .get(`/category/small/${this.selectMedium.categoryNo}`)
        .then(response => {
          this.smallList=response.data;
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
.selectbox {
  width: 30%;
}
</style>
