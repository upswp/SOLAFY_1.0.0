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
          @click="ProblemSetList"
        />
      </div>
      <div class="col">
        <q-table
          title="문제 리스트"
          :data="data"
          :columns="columns"
          row-key="id"
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
              <q-btn name="수정" @click="problemDetail(problem.no)">
                수정
              </q-btn>
              <template>
                <q-btn name="삭제" @click="problemDelete(problem.no)">
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
          name: "desc",
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
    // emulate fetching data from server
    problemList() {
      this.loading = true;
      setTimeout(() => {
        const index = Math.floor(Math.random() * (this.data.length + 1)),
          row = this.original[Math.floor(Math.random() * this.original.length)];
        if (this.data.length === 0) {
          this.rowCount = 0;
        }
        row.id = ++this.rowCount;
        const problemList = { ...row }; // extend({}, row, { name: `${row.name} (${row.__count})` })
        this.data = [
          ...this.data.slice(0, index),
          problemList,
          ...this.data.slice(index)
        ];
        this.loading = false;
      }, 500);
    },
    ProblemSetList() {
      this.loading = true;
      setTimeout(() => {
        const index = Math.floor(Math.random() * this.data.length);
        this.data = [
          ...this.data.slice(0, index),
          ...this.data.slice(index + 1)
        ];
        this.loading = false;
      }, 500);
    }
  }
};
</script>
<style>
#selectbox {
  width: 70px;
}
</style>
