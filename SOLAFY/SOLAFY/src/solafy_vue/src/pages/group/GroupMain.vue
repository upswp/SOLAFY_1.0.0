<template>
  <div>
    <!-- itemlist - 그룹리스트 시작-->
    <q-table
      title="그룹 리스트"
      :data="data"
      :columns="columns"
      row-key="name"
      :filter="filter"
      gird-header
      :pagination.sync="pagination"
      hide-pagination
      @row-click="clickRow"
    >
      <template v-slot:body-cell-groupNo="props">
        <q-td :props="props">
          <div :props="props" v-if="props.row.grade > 0 && props.row.grade < 4">
            <q-chip color="green" dense text-color="white" label="가입" />
          </div>
          <div :props="props" v-if="props.row.grade == 0">
            <q-chip color="red" dense text-color="white" label="미가입" />
          </div>
          <div :props="props" v-if="props.row.grade == 99">
            <q-chip color="orange" dense text-color="white" label="승인대기" />
          </div>
        </q-td>
      </template>

      <template v-slot:body-cell-type="props">
        <q-td :props="props">
           <div :props="props" v-if="props.row.type == 0">
            <q-chip color="green" dense text-color="white" label="Public" />
          </div>
          <div :props="props" v-if="props.row.type == 1">
            <q-chip color="red" dense text-color="white" label="Private" />
          </div>
        </q-td>
      </template>
      <template v-slot:top-right>
        <q-input
          borderless
          dense
          debounce="300"
          v-model="filter"
          placeholder="Search"
        >
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
      </template>
    </q-table>
    <!-- itemlist - 그룹리스트 끝-->
    <h1 :v-text="path"></h1>
    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="pagination.page"
        :max="pagesNumber"
        :direction-links="true"
      >
      </q-pagination>
    </div>
  </div>
</template>

<script>
import routes from "src/router/routes";
import { firebaseAuth } from "src/boot/firebase";
import {selectAllGroup} from "src/api/Group/group.js"
import { notify } from 'src/api/common';
export default {
  data() {
    return {
      filter: "",
      tab: "mails",
      path: "",
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 10
        // rowsNumber: xx if getting data from a server
      },
      columns: [
        {
          name: "groupNo",
          required: true,
          label: "상태",
          align: "left",
          field: row => row.groupNo,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "title",
          align: "center",
          label: "그룹명",
          field: "title",
          sortable: true
        },

        {
          name: "type",
          label: "그룹 타입",
          field: "type",
          sortable: true,
          sort: (a, b) => parseInt(a, 10) - parseInt(b, 10)
        }
      ],
      data: []
    };
  },
  methods: {
    clickRow(evt, row) {
      this.$router.push({
        name: "GroupDetail",
        params: {
          groupNo: row.groupNo,
          grade: row.grade
        }
      });
    }
  },
  mounted() {
    selectAllGroup(
      (Response)=>{
        this.data = Response.data;
      },
      (error)=>{
        notify("red-6", "white", "warning", "message");
      }
    );
  },
  computed: {
    pagesNumber() {
      return Math.ceil(this.data.length / this.pagination.rowsPerPage);
    }
  }
};
</script>

<style></style>
