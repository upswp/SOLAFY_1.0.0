<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <div class="col">
        <!-- onclick="location.reload() ::: 클릭 시 새로고침 -->
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
          onclick="location.reload()"
        />
        <q-btn
          id="problemCreate"
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제집 등록"
          @click="problemSetCreate"
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
          @row-click="problemSetDetail"
          no-data-label="검색결과가 없습니다"
        >
          <template v-slot:top-right>
            <q-input
              bottom-slots
              v-model="keyword"
              label="검색어를 입력해주세요"
              counter
              maxlength="12"
              @keyup.enter="searchProblemSet"
            >
              <template v-slot:before>
                <q-select
                  id="selectbox"
                  filled
                  v-model="option"
                  :options="options"
                  label="검색조건"
                />
              </template>
              <template v-slot:append>
                <q-icon
                  v-if="keyword !== ''"
                  name="close"
                  @click="keyword = ''"
                  class="cursor-pointer"
                />
                <q-icon name="search" @click="searchProblemSet" />
              </template>
            </q-input>
          </template>

          <template v-slot:no-data="{ message }">
            <div class="full-width row flex-center text-accent q-gutter-sm">
              <q-icon size="2em" name="sentiment_dissatisfied" />
              <b style="font-size:15px">{{ message }} </b>
            </div>
          </template>
        </q-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import routes from "src/router/routes";
import { notify } from "src/api/common";

export default {
  data() {
    return {
      loading: false,
      filter: "",
      categoryNo: "",
      rowCount: 10,
      errored: false,
      keyword: null,
      option: "제목",
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
    selectProblemSetList() {
      this.loading = true;
      axios
        .get("problem/problemset/problemSetSelect")
        .then(response => {
          this.problemSetList = response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
          this.$router.go(-1);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    //문제 검색 및 검색 예외처리
    searchProblemSet() {
      this.loading = true;
      if(this.keyword==""){
        this.selectProblemSetList();
        return;
      }
      if (this.option == "제목") {
        this.selectProblemByTitle();
      } else if (this.option == "문제집번호") {
        this.selectProblemByNo();
      } else {
        this.selectProblemByWriter();
      }
    },
    // 문제집 제목으로 검색
    selectProblemByTitle() {
      axios
        .get("problem/problemset/problemSetSelectByTitle/" + this.keyword)
        .then(response => {
          this.problemSetList = response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
        })
        .finally(() => (this.loading = false));
    },
    // 문제집 번호로 검색
    selectProblemByNo() {
      axios
        .get("problem/problemset/problemSetSelectByNo/" + this.keyword)
        .then(response => {
          this.problemSetList=[];
          this.problemSetList.push(response.data.problemSet);
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
        })
        .finally(() => (this.loading = false));
    },
    // 문제집 작성자로 검색
    selectProblemByWriter() {
      axios
        .get("problem/problemset/problemSetSelectByWriter/" + this.keyword)
        .then(response => {
          this.problemSetList = response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
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
    //problemSetCreate page로 이동
    problemSetCreate() {
      this.$router.push({
        name: "ProblemSetCreateByProblemSetInfo"
      });
    },
    //problemSetDetail page로 이동
    problemSetDetail(evt, row) {
      this.loading = true;
      this.$router.push({
        name: "ProblemDetailByProblemSetInfo",
        params: {
          problemSetNo: row.problemSetNo
        }
      });
    }
  },
  created() {
    this.selectProblemSetList();
  }
};
</script>
<style>
#selectbox {
  width: 80px;
}
</style>
