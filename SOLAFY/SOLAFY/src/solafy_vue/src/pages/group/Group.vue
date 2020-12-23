<template>
  <div>
    <!-- tab bar 시작 -->
    <q-tabs v-model="tab" class="text-teal">
      <q-tab name="mails" icon="mail" label="Mails" />
      <q-tab name="alarms" icon="alarm" label="Alarms" />
      <q-tab name="movies" icon="movie" label="Movies" />
    </q-tabs>
    <!-- tab bar 끝 -->

    <!-- itemlist - 자유게시판 시작-->
    <q-table
      title="그룹 리스트"
      :data="data"
      :columns="columns"
      row-key="name"
    />
    <!-- itemlist - 자유게시판 끝-->
    <h1 :v-text="path"></h1>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      tab: "mails",
      path: "",
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
  }
};
</script>

<style></style>
