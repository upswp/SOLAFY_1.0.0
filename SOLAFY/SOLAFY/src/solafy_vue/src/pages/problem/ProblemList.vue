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
          @row-click="problemDetail"
        >
          <template v-slot:top>
            <q-select
              id="selectbox"
              borderless
              filled
              use-chips
              stack-label
              :options="large"
              v-model="largeModel"
              label="대분류"
              @change="selectMedium"
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
              @keyup.enter="searchProblem()"
            >
              <template v-slot:append>
                <q-icon
                  v-if="keyword !== ''"
                  name="close"
                  @click="keyword = ''"
                  class="cursor-pointer"
                />
                <q-icon name="search" @click="searchProblem()" />
              </template>
            </q-input>
          </template>
        </q-table>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import routes from "src/router/routes";

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
        }
      ]
    };
  },
  methods: {
    //문제 전체 리스트 반환
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
    //문제 검색 및 검색 예외처리
    searchProblem: function() {
      this.loading = true;
      Axios.get(`/problem/search/${this.selection}/${this.keyword}`)
        .then(response => {
          this.problems = response.data;
          if (this.problems.length === 0) {
            this.$q.notify({
              color: "red-6",
              textColor: "white",
              icon: "warning",
              message: "조회 실패"
            });
          }
          console.log(this.problems[0]);
        })
        .catch(error => {
          this.$q.notify({
            color: "red-6",
            textColor: "white",
            icon: "warning",
            message: "조회 실패"
          });
        })
        .finally(() => (this.loading = false));
    },
    //문제 대분류 반환
    selectLarge: function() {
      Axios.get(`/category/large`)
        .then(response => {
          response.data.forEach(element => {
            this.large.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.large);
        })
        .catch(() => {
          this.errored = true;
        });
    },
    //문제 중분류 반환
    selectMedium: function() {
      Axios.get(`/category/medium/${this.largeModel.value}`)
        .then(response => {
          response.data.forEach(element => {
            this.medium.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.medium[0]);
        })
        .catch(() => {
          this.errored = true;
        });
    },
    //문제 소분류 반환
    selectSmall: function() {
      Axios.get(`/category/small/${this.mediumModel.value}`)
        .then(response => {
          response.data.forEach(element => {
            this.small.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.small[0]);
        })
        .catch(() => {
          this.errored = true;
        });
    },

    // problemList로 이동
    problemList() {
      this.loading = true;
      this.$router.push({
        name: "Problem"
      });
    },
    // problemSetList로 이동
    ProblemSetList() {
      this.loading = true;
      this.$router.push({
        name: "ProblemSet"
      });
    },
    //problemCreate page로 이동
    ProblemCreate() {
      this.loading = true;
      this.$router.push({
        name: "ProblemCreate"
      });
    },
    //problemDetail page로 이동
    problemDetail(evt, row) {
      this.loading = true;
      this.$router.push({
        name: "ProblemDetail",
        params: {
          problemNo: row.problemNo
        }
      });
    }
  },
  created() {
    this.selectProblemList();
    this.selectLarge();
  }
};
</script>
<style>
#selectbox {
  width: 70px;
}
</style>
