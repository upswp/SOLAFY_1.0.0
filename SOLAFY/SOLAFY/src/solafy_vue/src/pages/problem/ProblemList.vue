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
            <!-- @input = "~~~" ::: select 박스에서 다른 박스로 이벤트 불러서 연동할때-->
            <q-select
              id="selectbox"
              borderless
              filled
              :options="large"
              v-model="largeModel"
              label="대분류"
              @input="selectMedium"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="mediumModel"
              :options="medium"
              label="중분류"
              @input="selectSmall"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="smallModel"
              :options="small"
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
      largeModel: null,
      large: [],
      mediumModel: null,
      medium: [],
      smallModel: null,
      small: [],
      loading: false,
      filter: "",
      categoryNo: "",
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
          //categoryNo의 자리수가 부족할때 "0"으로 채우기 위한 padStart
          this.categoryNo = String(this.largeModel.value).padStart(2, "0");
          //console.log("this.categoryNo" + this.categoryNo);
          this.selectProblemByCategory();
          //대분류가 바뀔때 medium배열에 들어있는 값 초기화하여 쌓이는 것 방지.
          this.medium = [];
          // 대분류가 바뀔때 selectBox에서 값들이 null로 초기화 되도록
          this.mediumModel = null;
          this.smallModel = null;
          //for문을 돌며 DTO형태로 반환된 값들을 label과 value에 넣어 작업하기 위해
          response.data.forEach(element => {
            this.medium.push({
              label: element.categoryName,
              value: element.categoryNo
            });
          });
          console.log(this.medium[0]);
        })
        .catch(error => {
          alert(error);
          this.errored = true;
        });
    },
    //문제 소분류 반환
    selectSmall: function() {
      Axios.get(`/category/small/${this.mediumModel.value}`)
        .then(response => {
          this.categoryNo =
            String(this.largeModel.value).padStart(2, "0") +
            String(this.mediumModel.value).padStart(3, "0");
          console.log("this.categoryNo::::" + this.categoryNo);
          this.selectProblemByCategory();
          this.small = [];
          this.smallModel = null;
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
    // 카테고리 대분류, 중분류, 소분류에 따른 ProblemList 반환
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
    //소분류일때 카테고리NO 조합
    selectProblemByCategoryinSmall: function() {
      this.categoryNo =
        String(this.largeModel.value).padStart(2, "0") +
        String(this.mediumModel.value).padStart(3, "0") +
        String(this.smallModel.value).padStart(5, "0");
      //console.log("this.categoryNo::::" + this.categoryNo);
      this.selectProblemByCategory();
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
