<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <div class="col">
        <!-- onclick="location.reload() ::: 클릭 시 새로고침 -->
        <q-btn
          color="primary"
          :disable="loading"
          label="문제 List"
          @click="ProblemList"
        />
        <q-btn
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제집 List"
          onclick="location.reload()"
        />
        <q-btn
          id="problemCreate"
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제집 등록"
          @click="ProblemSetCreate"
        />
      </div>
      <div class="col">
        <q-table
          title="문제집 리스트"
          :data="problemSetList"
          :columns="columns"
          row-key="name"
          :filter="filter"
          :loading="loading"
          @row-click="problemDetail"
        >
          <template v-slot:top>
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
import axios from "axios";
import routes from "src/router/routes";

export default {
  data() {
    return {
      loading: false,
      filter: "",
      categoryNo: "",
      rowCount: 10,
      errored: false,
      keyword: null,
      selection: "제목",
      options: ["제목", "문제집번호", "작성자"],
      columns: [
        {
          name: "problemSetNo",
          required: true,
          label: "문제집 번호",
          align: "left",
          field: "problemSetNo",
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: "title",
          sortable: true
        },
        {
          name: "nickname",
          label: "작성자",
          required: true,
          align: "left",
          field: "nickname",
          sortable: true
        },
        {
          name: "regiTime",
          label: "작성일자",
          required: true,
          align: "left",
          field: "regiTime",
          sortable: true
        }
      ],
      problemSetList: []
    };
  },
  methods: {
    //문제집 전체 리스트 반환
    selectproblemSetList: function() {
      this.loading = true;
      axios
        .get("problem/problemset/problemSetSelect")
        .then(response => {
          this.problemSetList = response.data;
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "조회 실패"
          });
        })
        .finally(() => {
          this.loading = false;
        });
    },
    //문제 검색 및 검색 예외처리
    searchProblem: function() {
      this.loading = true;
      axios
        .get(`/problem/search/${this.selection}/${this.keyword}`)
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
    // problemList로 이동
    problemList() {
      this.loading = true;
      this.$router.push({
        name: "Problem"
      });
    },
    //ProblemSetCreate page로 이동
    ProblemSetCreate() {
      this.loading = true;
      this.$router.push({
        name: "ProblemSetCreate"
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
    this.selectproblemSetList();
  }
};
</script>
<style>
#selectbox {
  width: 70px;
}
</style>
