<template>
  <div>
    <!-- itemlist - 자유게시판 시작-->
    <q-table
      grid
      title="그룹 리스트"
      :data="data"
      :columns="columns"
      row-key="name"
      :filter="filter"
      hide-header
      :pagination.sync="pagination"
      hide-pagination
      @row-click="clickRow"
    >
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
    <!-- itemlist - 자유게시판 끝-->
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
import axios from "axios";
import routes from "src/router/routes";
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
        rowsPerPage: 3
        // rowsNumber: xx if getting data from a server
      },
      columns: [
        {
          name: "groupNo",
          required: true,
          label: "번호",
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
        params: { groupNo: row.groupNo }
      });
    }
  },
  mounted() {
    axios
      .get("group/selectAllGroup")
      .then(Response => {
        this.data = Response.data;
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
  computed: {
    pagesNumber() {
      return Math.ceil(this.data.length / this.pagination.rowsPerPage);
    }
  }
};
</script>

<style></style>
