<template>
  <div class="row">
    <div class="col-lg-3"></div>
    <div class="fixed-center col" v-if="loading">
      <q-spinner-dots color="primary" size="6em" />
    </div>
    <div class="col-lg-6" v-else>
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
      <div class="row justify-center">
        <h5>총 문제 : {{ item.problemList.length }}</h5>
      </div>
      <div class="row justify-center">
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
                    @click="removeProblem(props.rowIndex)"
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
        <q-space />
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
      <div class="row">
        <div class="col">
          <q-btn outline color="primary" label="수정취소" @click="goToproblemSetDetail" />
        </div>
        <div class="col">
          <q-btn color="primary" label="문제수정" @click="UpdateProblemSet"  style="float:right"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { firebaseAuth } from "src/boot/firebase";
import { notify } from "src/api/common.js";

export default {
  name: "ProblemSetCreateByProblemSetInfo",
  data() {
    return {
      item: {
        problemSet: {
          problemSetNo: 0,
          title: "",
          nickname: ""
        },
        problemList: []
      },
      remainProblemList: [],
      columns: [
        {
          name: "problemNo",
          align: "left",
          label: "문제 번호",
          field: "problemNo",
          align: "center",
          style: "width:10vw"
        },
        {
          name: "title",
          label: "문제 제목",
          field: "title",
          align: "center",
          style: "width:80vw"
        },
        {
          name: "button",
          field: "button",
          align: "center",
          style: "width:10vw"
        }
      ],
      pagination1: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 5
      },
      pagination2: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 5
      },
      loading: true
    };
  },
  methods: {
    // 문제집 정보 반환
    selectProblemSet() {
      axios
        .get(
          "problem/problemset/problemSetSelectByNo/" +
            this.$route.params.problemSetNo
        )
        .then(Response => {
          this.item = Response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
          this.goToproblemSetDetail();
          console.log(error);
        })
        .finally(() => {});
    },
    // 남은 문제 정보 반환
    selectProblemList() {
      axios
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
          this.goToproblemSetDetail();
          console.log(error);
        })
        .finally(() => {
          this.loading = false;
        });
    },
    // 문제집 상세보기로 이동
    goToproblemSetDetail() {
      this.$router.push({
        name: "ProblemDetailByProblemSetInfo",
        params: {
          problemSetNo: this.item.problemSet.problemSetNo
        }
      });
    },
    // 문제리스트에서 문제 제거
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
    // 문제리스트에 문제 추가
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
    // 문제 수정 버튼 클릭 시 db에 반영
    // Todo : 문제 제목이 입력안됬거나 문제집에 문제가 하나도 없을 때 수정 불가하도록 변경
    UpdateProblemSet() {
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
      axios
        .put("problem/problemset/updateProblemSet", this.item)
        .then(response => {
          if ((response.data = "success")) {
            notify("positive", "white", "done", "수정 성공");
            this.goToproblemSetDetail();
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
  created() {
    this.selectProblemSet();
    this.selectProblemList();
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
  }
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
