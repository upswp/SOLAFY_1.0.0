<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <div class="col">
        <q-btn
          class="q-ml-sm"
          color="primary"
          :disable="loading"
          label="모의고사 등록"
          @click="goPracticeCreate"
        />
      </div>
      <div class="col">
        <q-table
          title="모의고사 리스트"
          :data="practiceList"
          :columns="columns"
          row-key="name"
          :loading="loading"
          @row-click="goPracticeDetail"
          no-data-label="검색 결과가 없습니다"
        >
          <template v-slot:top-right>
            <q-input
              bottom-slots
              v-model="keyword"
              label="검색어를 입력해주세요"
              counter
              maxlength="12"
              @keyup.enter="search"
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
                <q-icon name="search" @click="search" />
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
      rowCount: 10,
      errored: false,
      keyword: null,
      option: "제목",
      practiceList: [],
      options: ["제목", "모의고사 번호", "작성자"],
      columns: [
        {
          name: "practiceNo",
          required: true,
          label: "번호",
          align: "left",
          field: "practiceNo",
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
      ]
    };
  },
  created() {
    this.selectAll();
  },
  methods: {
    //모의고사 전체 리스트 반환
    selectAll() {
      this.loading = true;
      axios
        .get("practicetest/selectall")
        .then(response => {
          this.practiceList = response.data;
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
    search() {
      this.loading = true;
      if (this.keyword == "") {
        this.selectAll();
        return;
      }

      let path = "/practicetest/selectby";
      if (this.option == "제목") {
        path += "title/";
      } else if (this.option == "모의고사 번호") {
        path += "no/";
      } else {
        path += "writer/";
      }
      path += this.keyword;

      axios
        .get(path)
        .then(response => {
          this.practiceList = response.data;
        })
        .catch(error => {
          notify("negative", "white", "error", "조회 실패");
        })
        .finally(() => (this.loading = false));
    },
    goPracticeCreate() {
      this.$router.push("/practicecreate");
    },
    goPracticeDetail(evt, row) {
      this.loading = true;
      this.$router.push("/practicedetail/" + row.practiceNo);
    }
  }
};
</script>

<style>
#selectbox {
  width: 100px;
}
</style>
