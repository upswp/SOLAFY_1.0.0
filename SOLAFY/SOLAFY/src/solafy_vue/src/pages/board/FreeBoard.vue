<template>
  <div>
    <h3>FreeBoard</h3>
    <div class="q-pa-md" style="max-width: 300px">
      <div class="q-gutter-md">
        <q-badge color="secondary" multi-line> Model: "{{ model }}" </q-badge>

        <q-select filled v-model="model" :options="options" label="Standard" />
      </div>
    </div>
    <q-input
      bottom-slots
      v-model="text"
      label="Label"
      counter
      maxlength="12"
      :dense="dense"
    >
      <template v-slot:before>
        <q-icon name="flight_takeoff" />
      </template>

      <template v-slot:append>
        <q-icon
          v-if="text !== ''"
          name="close"
          @click="text = ''"
          class="cursor-pointer"
        />
        <q-icon name="search" />
      </template>

      <template v-slot:hint>
        Field hint
      </template>
    </q-input>
    <q-table
      title="Treats"
      :data="articles"
      :columns="columns"
      row-key="name"
    />
  </div>
</template>

<script>
import Axios from "axios";
export default {
  data() {
    return {
      model: null,
      options: ["제목", "작성자"],
      articles: [],
      errored: false,
      columns: [
        {
          name: "articleNo",
          required: true,
          label: "articleNo",
          align: "left",
          field: row => row.articleNo,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "nickname",
          required: true,
          label: "nickname",
          align: "left",
          field: row => row.nickname,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "title",
          required: true,
          label: "title",
          align: "left",
          field: row => row.title,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "regiTime",
          required: true,
          label: "regiTime",
          align: "left",
          field: row => row.regiTime,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "likeCount",
          required: true,
          label: "listCount",
          align: "left",
          field: row => row.likeCount,
          format: val => `${val}`,
          sortable: true
        }
      ]
    };
  },
  methods: {
    selectArticles: function() {
      Axios.get(`/free/selectArticles`)
        .then(response => {
          this.articles = response.data;
          console.log(this.articles[0]);
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    }
  },
  created() {
    this.selectArticles();
  }
};
</script>

<style></style>
