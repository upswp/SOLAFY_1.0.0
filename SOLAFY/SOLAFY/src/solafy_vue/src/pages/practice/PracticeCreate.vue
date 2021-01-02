<template>
  <div class="q-pa-md" style="margin: 0 auto; float: center; width: 700px">
    <div class="col">
      <q-input filled v-model="form.title" label="제목" />
      <q-input filled v-model="form.startTime" label="시작 시각">
        <template v-slot:prepend>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-date v-model="form.startTime" mask="YYYY-MM-DD HH:mm">
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="완료" color="primary" flat />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>

        <template v-slot:append>
          <q-icon name="access_time" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-time
                v-model="form.startTime"
                mask="YYYY-MM-DD HH:mm"
                format24h
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="완료" color="primary" flat />
                </div>
              </q-time>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
      <q-input filled v-model="form.endTime" label="종료 시각">
        <template v-slot:prepend>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-date v-model="form.endTime" mask="YYYY-MM-DD HH:mm">
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="완료" color="primary" flat />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>

        <template v-slot:append>
          <q-icon name="access_time" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-time v-model="form.endTime" mask="YYYY-MM-DD HH:mm" format24h>
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="완료" color="primary" flat />
                </div>
              </q-time>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
      <q-table
        :data="selectedProblems"
        :columns="columns"
        row-key="name"
        no-data-label="선택된 문제가 없습니다"
      >
        <template v-slot:body-cell-action="props">
          <q-td :props="props">
            <q-btn
              rounded
              dense
              color="teal"
              icon="remove"
              @click="removeProblem(props.row)"
            />
          </q-td>
        </template>
        <template v-slot:bottom-row>
          <q-input
            filled
            style="width: 150px"
            v-model="problemNo"
            label="문제 번호"
            @keyup.enter="searchProblem"
          >
          </q-input>
        </template>
      </q-table>
      <div>
        <q-btn label="제출" color="primary" @click="onSubmit" />
        <q-btn label="취소" color="primary" @click="goPracticeList" />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { notify } from "src/api/common.js";
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { SessionStorage } from "quasar";

export default {
  data() {
    return {
      form: {
        title: "",
        uid: SessionStorage.getItem("loginUser").uid,
        startTime: null,
        endTime: null,
        problems: [],
        group: false
      },
      selectedProblems: [],
      problemNo: null,
      columns: [
        {
          name: "problemNo",
          required: true,
          label: "문제번호",
          align: "left",
          field: "problemNo",
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "category",
          label: " 카테고리",
          required: true,
          align: "left",
          field: "category"
        },
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: "title"
        },
        {
          name: "action",
          label: ""
        }
      ]
    };
  },
  methods: {
    onSubmit() {
      console.log(this.form);
      axios
        .post("practicetest/create", {
          title: this.form.title,
          uid: SessionStorage.getItem("loginUser").uid,
          startTime: this.form.startTime,
          endTime: this.form.endTime,
          limitTime: "",
          problems: this.form.problems,
          isGroup: false
        })
        .then(response => {
          notify("green", "white", "cloud_done", "모의고사 등록 성공");
          this.$router.push("/practicelist");
        })
        .catch(error => {
          console.log(error.message);
          notify("red", "white", "warning", "모의고사 등록 실패");
        });
    },
    searchProblem() {
      axios
        .get("problem/" + this.problemNo)
        .then(response => {
          this.selectedProblems.push({
            problemNo: response.data.problem.problemNo,
            category:
              response.data.categoryLarge.categoryName +
              "/" +
              response.data.categoryMedium.categoryName +
              "/" +
              response.data.categorySmall.categoryName,
            title: response.data.problem.title
          });
          this.form.problems.push({
            problemNo: response.data.problem.problemNo
          });
          this.problemNo = "";
        })
        .catch(error => {
          console.log(error);
          notify("negative", "white", "error", "조회 실패");
        });
    },
    removeProblem(selectedItem) {
      let idx = this.selectedProblems.indexOf(selectedItem);
      this.selectedProblems.splice(idx, 1);
      this.form.problems.splice(idx, 1);
    },
    goPracticeList() {
      this.$router.push("/practicelist");
    }
  }
};
</script>

<style></style>
