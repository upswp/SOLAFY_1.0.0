<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <!-- 버튼들 -->
      <div class="col">
        <q-btn
          color="primary"
          :disable="loading"
          label="문제 List"
          @click="goToProblem"
        />
        <!-- onclick="location.reload() ::: 클릭 시 새로고침 -->
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
          @click="goToProblemSetCreateByProblemSetInfo"
        />
      </div>
      <!-- 문제집 리스트 테이블 -->
      <div class="col">
        <q-table
          title="문제집 리스트"
          :data="problemSetList"
          :columns="columns"
          row-key="name"
          :loading="loading"
          @row-click="goToProblemDetailByProblemSetInfo"
          no-data-label="검색결과가 없습니다"
        >
          <!-- 검색바 -->
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
          <!-- 테이블에 데이터가 없을 때 -->
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
import Axios from "axios";
import routes from "src/router/routes";
import { notify } from "src/api/common";

export default {
  data() {
    return {
      // 로딩 flag
      loading: false,
      // 검색 키워드
      keyword: "",
      // 검색 옵션
      option: "제목",
      // 문제집 리스트
      problemSetList: [],
      // select에 들어가는 검색 옵션
      options: ["제목", "문제집번호", "작성자"],
      // 테이블 컬럼
      columns: [
        // 문제집 번호
        {
          name: "problemSetNo",
          required: true,
          label: "문제집 번호",
          align: "left",
          field: "problemSetNo",
          sortable: true,
          style: "width = 10px"
        },
        // 제목
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: "title",
          sortable: true
        },
        // 작성자
        {
          name: "nickname",
          label: "작성자",
          required: true,
          align: "left",
          field: "nickname",
          sortable: true
        },
        // 작성일자
        {
          name: "regiTime",
          label: "작성일자",
          required: true,
          align: "left",
          field: "regiTime",
          sortable: true
        }
      ]
    };
  },
  methods: {
    /**
     * @Method설명 : 문제집 전체 리스트 반환
     * @변경이력 :
     */
    selectAllProblemSet() {
      this.loading = true;
      Axios
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
    /**
     * @Method설명 : 문제 검색 및 검색 예외처리
     * @변경이력 :
     */
    searchProblemSet() {
      this.loading = true;
      if(this.keyword==""){
        this.selectAllProblemSet();
        return;
      }
      if (this.option == "제목") {
        this.selectProblemSetByTitle();
      } else if (this.option == "문제집번호") {
        this.selectProblemSetByNo();
      } else {
        this.selectProblemSetByWriter();
      }
    },
    /**
     * @Method설명 : 문제집 제목으로 문제집 검색
     * @변경이력 :
     */
    selectProblemSetByTitle() {
      Axios
        .get("problem/problemset/problemSetSelectByTitle/" + this.keyword)
        .then(response => {
          this.problemSetList = response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
        })
        .finally(() => (this.loading = false));
    },
    /**
     * @Method설명 : 문제집 번호로 문제집 검색
     * @변경이력 :
     */
    selectProblemSetByNo() {
      Axios
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
    /**
     * @Method설명 : 문제집 작성자로 문제집검색
     * @변경이력 :
     */
    selectProblemSetByWriter() {
      Axios
        .get("problem/problemset/problemSetSelectByWriter/" + this.keyword)
        .then(response => {
          this.problemSetList = response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
        })
        .finally(() => (this.loading = false));
    },
    /**
     * @Method설명 : 문제 리스트로 이동
     * @변경이력 :
     */
    goToProblem() {
      this.loading = true;
      this.$router.push({
        name: "Problem"
      });
    },
    /**
     * @Method설명 : 문제집 생성페이지로 이동
     * @변경이력 :
     */
    goToProblemSetCreateByProblemSetInfo() {
      this.$router.push({
        name: "ProblemSetCreateByProblemSetInfo"
      });
    },
    /**
     * @Method설명 : 문제집 상세페이지로 이동
     * @변경이력 :
     */
    goToProblemDetailByProblemSetInfo(evt, row) {
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
    this.selectAllProblemSet();
  }
};
</script>
<style>
#selectbox {
  width: 80px;
}
</style>
