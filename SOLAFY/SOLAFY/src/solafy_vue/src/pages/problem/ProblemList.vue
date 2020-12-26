<template>
  <div class="q-pa-md">
    <div class="column" style="height: 150px">
      <div class="col">
        <q-btn
          color="primary"
          :disable="loading"
          label="문제 List"
          @click="problemList"
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
          @click="ProblemSetList"
        />
      </div>
      <div class="col">
        <q-table
          title="Treats"
          :data="data"
          :columns="columns"
          row-key="id"
          :filter="filter"
          :loading="loading"
        >
          <template v-slot:top>
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="largeModel"
              :options="large"
              label="대분류"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="mediumModel"
              :options="medium"
              label="중분류"
            />
            <q-select
              id="selectbox"
              borderless
              filled
              v-model="smallModel"
              :options="small"
              label="소분류"
            />
            <q-space />
            <q-input dense debounce="500" color="primary" v-model="filter">
              <template v-slot:append>
                <q-icon name="search" />
              </template>
            </q-input>
          </template>
        </q-table>
      </div>
    </div>
  </div>
</template>

<script>
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
      rowCount: 10,
      columns: [
        {
          name: "desc",
          required: true,
          label: "문제번호",
          align: "left",
          field: row => row.name,
          format: val => `${val}`,
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "title",
          label: "제목",
          field: "fat",
          sortable: true,
          align: "left",
          style: "width = 200px"
        },
        {
          name: "nickname",
          label: "작성자",
          field: "carbs",
          align: "left",
          style: "width = 40px",
          sortable: true
        },
        {
          name: "regiTime",
          label: "작성일자",
          field: "protein",
          align: "left",
          style: "width = 50px",
          sortable: true
        },
        {
          name: "etc",
          label: "비고",
          field: "sodium",
          align: "left",
          style: "width = 50px"
        }
      ],
      data: [],
      original: []
    };
  },
  methods: {
    // emulate fetching data from server
    problemList() {
      this.loading = true;
      setTimeout(() => {
        const index = Math.floor(Math.random() * (this.data.length + 1)),
          row = this.original[Math.floor(Math.random() * this.original.length)];
        if (this.data.length === 0) {
          this.rowCount = 0;
        }
        row.id = ++this.rowCount;
        const problemList = { ...row }; // extend({}, row, { name: `${row.name} (${row.__count})` })
        this.data = [
          ...this.data.slice(0, index),
          problemList,
          ...this.data.slice(index)
        ];
        this.loading = false;
      }, 500);
    },
    ProblemSetList() {
      this.loading = true;
      setTimeout(() => {
        const index = Math.floor(Math.random() * this.data.length);
        this.data = [
          ...this.data.slice(0, index),
          ...this.data.slice(index + 1)
        ];
        this.loading = false;
      }, 500);
    }
  }
};
</script>
<style>
#selectbox {
  width: 70px;
}
</style>
