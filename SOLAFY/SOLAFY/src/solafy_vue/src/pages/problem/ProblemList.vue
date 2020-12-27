<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <div class="col">
        <q-btn
          color="primary"
          :disable="loading"
          label="문제 List"
          @click="problemList"
        />
        <q-btn
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제집 List"
          @click="ProblemSetList"
        />
        <q-btn
          id="problemCreate"
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제등록"
          @click="ProblemCreate"
        />
      </div>
      <div class="col">
        <q-table
          title="문제 리스트"
          :data="problems"
          :columns="columns"
          row-key="name"
          :filter="filter"
          :loading="loading"
        >
          <template v-slot:top>
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="largeModel"
              :options="large"
              label="대분류"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="mediumModel"
              :options="medium"
              label="중분류"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="smallModel"
              :options="small"
              label="소분류"
            />
            <q-space />
            <q-select
              id="selectbox"
              filled
              v-model="selection"
              :options="options"
              label="검색조건"
            />
            <q-input
              bottom-slots
              v-model="keyword"
              label="검색어를 입력해주세요"
              counter
              maxlength="12"
              @keyup.enter="searchProblem"
            >
              <template v-slot:append>
                <q-icon
                  v-if="keyword !== ''"
                  name="close"
                  @click="keyword = ''"
                  class="cursor-pointer"
                />
                <q-icon name="search" @click="searchProblem" />
              </template>
            </q-input>
          </template>
          <template>
            <q-td>
              <q-btn name="수정" @click="problemDetail(problems.problemNo)">
                수정
              </q-btn>
              <template>
                <q-btn name="삭제" @click="problemDelete(problems.problemNo)">
                  삭제
                </q-btn>
              </template>
            </q-td>
          </template>
        </q-table>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  data() {
    return {
      largeModel: null,
      large: [],
      mediumModel: null,
      medium: [],
      smallModel: null,
      small: [],
      loading: false,
      filter: "",
      rowCount: 10,
      errored: false,
      keyword: null,
      problems: [],
      selection: "제목",
      options: ["제목", "문제번호", "작성자"],
      columns: [
        {
          name: "problemNo",
          required: true,
          label: "문제번호",
          align: "left",
          field: row => row.problemNo,
          format: val => `${val}`,
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: row => row.title,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "nickname",
          label: "작성자",
          required: true,
          align: "left",
          field: row => row.nickname,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "regiTime",
          label: "작성일자",
          required: true,
          align: "left",
          field: row => row.regiTime,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "etc",
          label: "비고",
          required: true,
          // field: row => row.problemNo,
          // format: val => `${val}`,
          align: "left"
        }
      ],
      data: [],
      original: []
    };
  },
  methods: {
    selectProblemList: function() {
      this.loading = true;
      Axios.get(`/problem`)
        .then(response => {
          this.problems = response.data;
          console.log(this.problems[0]);
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    searchProblem: function() {
      this.loading = true;
      Axios.get(`/search/${this.options}/${this.keyword}`)
        .then(response => {
          this.problems = response.data;
          console.log(this.problems[0]);
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },

    // emulate fetching data from server
    problemList() {
      this.loading = true;
    },
    ProblemSetList() {
      this.loading = true;
    },
    ProblemCreate() {
      this.loading = true;
    },
    problemDetail(problmeNo) {
      this.loading = true;
    },
    problemDelete(problmeNo) {
      this.loading = true;
    }
  },
  created() {
    this.selectProblemList();
  }
};
</script>
<style>
#selectbox {
  width: 70px;
}
</style>
