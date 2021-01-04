<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="fixed-center col" v-if="loading">
      <q-spinner-dots color="primary" size="6em" />
    </div>
    <div class="col-lg-6" v-else>
      <!-- 문제집 정보 입력 -->
      <div class="row">
        <q-markup-table separator="cell">
          <tbody>
            <tr>
              <td class="header">문제집 제목</td>
              <td class="content">
                <q-input v-model="item.problemSet.title" borderless />
              </td>
            </tr>
            <tr>
              <td class="header">문제집 작성자</td>
              <td class="content">{{ item.problemSet.nickname }}</td>
            </tr>
          </tbody>
        </q-markup-table>
      </div>
      <!-- 문제집의 총 문제 수 -->
      <div class="row justify-center">
        <h5>총 문제 : {{ item.problemList.length }}</h5>
      </div>
      <!-- 문제 추가 / 삭제 -->
      <div class="row justify-center">
        <!-- 문제집에 포함될 문제 리스트 -->
        <div class="col-6">
          <div class="row">
            <q-table
              title="추가된 문제 List"
              :data="item.problemList"
              :columns="columns"
              hide-pagination
              :pagination.sync="pagination1"
              style="width:100%;height:50vh"
            >
              <template #body-cell-button="props">
                <q-td>
                  <q-btn
                    dense
                    flat
                    round
                    color="red"
                    icon="remove"
                    @click="deleteProblemByBtn(props.rowIndex)"
                  />
                </q-td>
              </template>
            </q-table>
          </div>
          <div class="row justify-center">
            <q-pagination
              v-model="pagination1.page"
              :max="pagesNumber1"
              input
            />
          </div>
        </div>
        <!-- 문제집에 포함되지 않은 문제 -->
        <div class="col-6">
          <div class="row">
            <q-table
              title="추가가능한 문제 List"
              :data="remainProblemList"
              :columns="columns"
              hide-pagination
              :pagination.sync="pagination2"
              style="width:100%;height:50vh"
            >
              <template #body-cell-button="props">
                <q-td>
                  <q-btn
                    dense
                    flat
                    round
                    color="green"
                    icon="add"
                    @click="addProblem(props.rowIndex)"
                  />
                </q-td>
              </template>
            </q-table>
          </div>
          <div class="row justify-center">
            <q-pagination
              v-model="pagination2.page"
              :max="pagesNumber2"
              input
            />
          </div>
        </div>
      </div>
      <hr />
      <!-- 버튼들 -->
      <div class="row">
        <div class="col">
          <q-btn outline color="primary" label="수정취소" @click="goToProblemDetailByProblemSetInfo" />
        </div>
        <div class="col">
          <q-btn color="primary" label="문제수정" @click="updateProblemSet"  style="float:right"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import { firebaseAuth } from "src/boot/firebase";
import { notify } from "src/api/common.js";

export default {
  name: "ProblemSetCreateByProblemSetInfo",
  data() {
    return {
      // 로딩 flag
      loading: true,
      // 문제집에 포함되지 않은 문제 리스트
      remainProblemList: [],
      // 문제집 정보
      item: {
        // 문제집
        problemSet: {
          // 문제집 번호
          problemSetNo: 0,
          // 문제집 제목
          title: "",
          // 작성자 닉네임
          nickname: ""
        },
        // 문제 리스트
        problemList: []
      },
      // 문제집에 포함된 문제 리스트 페이지관련 변수
      pagination1: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 5
      },
      // 문제집에 포함되지않은 문제 리스트 페이지관련 변수
      pagination2: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 5
      },
      // 테이블 컬럼
      columns: [
        // 문제 번호
        {
          name: "problemNo",
          align: "left",
          label: "문제 번호",
          field: "problemNo",
          align: "center",
          style: "width:10vw"
        },
        // 문제 제목
        {
          name: "title",
          label: "문제 제목",
          field: "title",
          align: "center",
          style: "width:80vw"
        },
        // 문제 추가 / 삭제 버튼
        {
          name: "button",
          field: "button",
          align: "center",
          style: "width:10vw"
        }
      ]
    };
  },
  methods: {
    /**
     * @Method설명 : 문제집 정보 반환
     * @변경이력 :
     */
    selectProblemSetByNo() {
      Axios
        .get(
          "problem/problemset/problemSetSelectByNo/" +
            this.$route.params.problemSetNo
        )
        .then(Response => {
          this.item = Response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
          this.goToProblemDetailByProblemSetInfo();
          console.log(error);
        })
        .finally(() => {});
    },
    /**
     * @Method설명 : 문제집에 추가되지않은 문제 정보 반환
     * @변경이력 :
     */
    selectProblemList() {
      Axios
        .get("problem")
        .then(response => {
          // 이미 선택되어 있는 문제는 제거
          this.remainProblemList = response.data;
          this.item.problemList.forEach(el => {
            const idx = this.remainProblemList.findIndex(function(item) {
              return item.problemNo == el.problemNo;
            });
            this.remainProblemList.splice(idx, 1);
          });
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
          this.goToProblemDetailByProblemSetInfo();
          console.log(error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    /**
     * @Method설명 : 문제집 상세보기로 이동
     * @변경이력 :
     */
    goToProblemDetailByProblemSetInfo() {
      this.$router.push({
        name: "ProblemDetailByProblemSetInfo",
        params: {
          problemSetNo: this.item.problemSet.problemSetNo
        }
      });
    },
    /**
     * @Method설명 : 문제집 문제리스트에서 문제 제거
     * @변경이력 :
     */
    removeProblem(index) {
      this.remainProblemList.push(this.item.problemList[index]);
      this.item.problemList.splice(index, 1);
      // 정렬
      this.item.problemList.sort(function(p1, p2) {
        return p1.problemNo - p2.problemNo;
      });
      this.remainProblemList.sort(function(p1, p2) {
        return p1.problemNo - p2.problemNo;
      });
    },
    /**
     * @Method설명 : 문제리스트에 문제 추가
     * @변경이력 :
     */
    addProblem(index) {
      this.item.problemList.push(this.remainProblemList[index]);
      this.remainProblemList.splice(index, 1);
      // 정렬
      this.item.problemList.sort(function(p1, p2) {
        return p1.problemNo - p2.problemNo;
      });
      this.remainProblemList.sort(function(p1, p2) {
        return p1.problemNo - p2.problemNo;
      });
    },
    /**
     * @Method설명 : 수정사항을 DB에 반영
     * @변경이력 :
     */
    updateProblemSet() {
      // 예외 처리
      if (this.item.problemSet.title == "") {
        notify("warning", "white", "warning", "문제집 제목을 입력해주세요");
        return;
      }
      if (this.item.problemList.length == 0) {
        notify(
          "warning",
          "white",
          "warning",
          "문제는 최소 한 개 이상 선택되어야 합니다"
        );
        return;
      }
      // 문제 수정
      Axios
        .put("problem/problemset/updateProblemSet", this.item)
        .then(response => {
          if ((response.data = "success")) {
            notify("positive", "white", "done", "수정 성공");
            this.goToProblemDetailByProblemSetInfo();
          } else {
            notify("negative", "white", "error", "수정 실패");
          }
        })
        .catch(error => {
          notify("negative", "white", "error", "수정 실패");
          console.log(error);
        });
    }
  },
  computed: {
    pagesNumber1() {
      return Math.ceil(
        this.item.problemList.length / this.pagination1.rowsPerPage
      );
    },
    pagesNumber2() {
      return Math.ceil(
        this.remainProblemList.length / this.pagination2.rowsPerPage
      );
    }
  },
  created() {
    this.selectProblemSetByNo();
    this.selectProblemList();
  },
};
</script>
<style scoped>
.header {
  text-align: center;
  width: 100px;
}
.content {
  width: 100vw;
}
.button {
  margin-top: 10px;
}
</style>
