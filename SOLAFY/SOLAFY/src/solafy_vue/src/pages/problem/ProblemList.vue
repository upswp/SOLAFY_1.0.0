<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <div class="col">
        <!-- onclick="location.reload() ::: 클릭 시 새로고침 -->
        <q-btn
          color="primary"
          :disable="loading"
          label="문제 List"
          onclick="location.reload()"
        />
        <q-btn
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제집 List"
          @click="goToProblemSetList"
        />
        <q-btn
          id="problemCreate"
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="문제등록"
          @click="goToProblemCreate"
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
          @row-click="goToproblemDetail"
        >
          <template v-slot:top>
            <!-- @input = "~~~" ::: select 박스에서 다른 박스로 이벤트 불러서 연동할때-->
            <q-select
              id="selectbox"
              borderless
              filled
              :options="selectLarge"
              v-model="largeList"
              label="대분류"
              @input="selectCategoryMediumList"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="mediumList"
              :options="selectMedium"
              label="중분류"
              @input="selectCategorySmallList"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="smallList"
              :options="selectSmall"
              label="소분류"
              @input="selectProblemByCategoryinSmall"
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
      //대분류 카테고리 리스트
      largeList: null,
      //선택된 대분류 카테고리
      selectLarge: [],
      //중분류 카테고리 리스트
      mediumList: null,
      //선택된 중분류 리스트
      selectMedium: [],
      //소분류 카테고리 리스트
      selectSmall: [],
      //선택된 소분류 리스트
      smallList: null,
      //로딩 변수에 대한 default
      loading: false,
      //문제 리스트의 filter기능 
      filter: "",
      //카테고리 번호
      categoryNo: "",
      //error 처리를 위한 변수
      errored: false,
      //검색어
      keyword: null,
      //문제들 list
      problems: [],
      //선택된 검색조건
      selection: "제목",
      //검색조건의 list
      options: ["제목", "문제번호", "작성자"],
      //table 구성 columns
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
     /**
     * @Method설명 : 문제 전체 리스트 반환
     * @변경이력 :
     */
    selectAllProblemList: function() {
      this.loading = true;
      Axios.get(`/problem`)
        .then(response => {
          this.problems = response.data;
          console.log(this.problems[0]);
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
          this.$router.go(-1);
        })
        .finally(() => (this.loading = false));
    },
     /**
     * @Method설명 : 문제 검색 및 검색 예외처리
     * @변경이력 :
     */
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
     /**
     * @Method설명 : 문제 대분류 반환
     * @변경이력 :
     */
    selectCategoryLargeList: function() {
      Axios.get(`/category/large`)
        .then(response => {
          response.data.forEach(element => {
            this.selectLarge.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.selectLarge);
        })
        .catch(error => {
          notify("red", "white", "error", "카테고리 대분류 불러오기 실패");
          this.$router.go(-1);
        });
    },
     /**
     * @Method설명 : 문제 중분류 반환
     * @변경이력 :
     */
    selectCategoryMediumList: function() {
      Axios.get(`/category/medium/${this.largeList.value}`)
        .then(response => {
          //categoryNo의 자리수가 부족할때 "0"으로 채우기 위한 padStart
          this.categoryNo = String(this.largeList.value).padStart(2, "0");
          //console.log("this.categoryNo" + this.categoryNo);
          this.selectProblemByCategory();
          //대분류가 바뀔때 medium배열에 들어있는 값 초기화하여 쌓이는 것 방지.
          this.selectMedium = [];
          // 대분류가 바뀔때 selectBox에서 값들이 null로 초기화 되도록
          this.mediumList = null;
          this.smallList = null;
          //for문을 돌며 DTO형태로 반환된 값들을 label과 value에 넣어 작업하기 위해
          response.data.forEach(element => {
            this.selectMedium.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.selectMedium[0]);
        })
        .catch(error => {
          alert(error);
          this.errored = true;
        });
    },
     /**
     * @Method설명 : 문제 소분류 반환
     * @변경이력 :
     */
    selectCategorySmallList: function() {
      Axios.get(`/category/small/${this.mediumList.value}`)
        .then(response => {
          this.categoryNo =
            String(this.largeList.value).padStart(2, "0") +
            String(this.mediumList.value).padStart(3, "0");
          console.log("this.categoryNo::::" + this.categoryNo);
          this.selectProblemByCategory();
          this.selectSmall = [];
          this.smallList = null;
          response.data.forEach(element => {
            this.selectSmall.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.selectSmall[0]);
        })
        .catch(() => {
          this.errored = true;
        });
    },

     /**
     * @Method설명 : 카테고리 대분류, 중분류, 소분류에 따른 ProblemList 반환
     * @변경이력 :
     */
    selectProblemByCategory: function() {
      Axios.get(`/problem/category/${this.categoryNo}`)
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
        });
    },
     /**
     * @Method설명 : 소분류일때 카테고리NO 조합
     * @변경이력 :
     */
    selectProblemByCategoryinSmall: function() {
      this.categoryNo =
        String(this.largeList.value).padStart(2, "0") +
        String(this.mediumList.value).padStart(3, "0") +
        String(this.smallList.value).padStart(5, "0");
      //console.log("this.categoryNo::::" + this.categoryNo);
      this.selectProblemByCategory();
    },
     /**
     * @Method설명 : problemSetList로 이동
     * @변경이력 :
     */
    goToProblemSetList() {
      this.loading = true;
      this.$router.push({
        name: "ProblemSet"
      });
    },
     /**
     * @Method설명 : problemCreate page로 이동
     * @변경이력 :
     */
    goToProblemCreate() {
      this.loading = true;
      this.$router.push({
        name: "ProblemCreate"
      });
    },
     /**
     * @Method설명 : problemDetail page로 이동
     * @변경이력 :
     */
    goToproblemDetail(evt, row) {
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
    this.selectAllProblemList();
    this.selectCategoryLargeList();
  }
};
</script>
<style>
#selectbox {
  width: 70px;
}
</style>
