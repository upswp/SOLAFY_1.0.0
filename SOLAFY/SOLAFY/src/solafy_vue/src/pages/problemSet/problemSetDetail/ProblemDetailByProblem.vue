<template>
  <div class="q-pa-md">
    <div class="column">
      <div class="col" id="header-title">
        <h3>문제집 title</h3>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-8"></div>
          <div class="col-4">
            <q-btn color="primary" label="문제수정요청" />
            <q-btn color="primary" label="돌아가기" />
          </div>
        </div>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-8">
            <q-table
              grid
              :card-container-class="cardContainerClass"
              title="Treats"
              :data="data"
              :columns="columns"
              row-key="name"
              hide-header
              :pagination.sync="pagination1"
              :rows-per-page-options="rowsPerPageOptions"
            >
              <template v-slot:item="props">
                <div class="q-pa-xs col-xs-12 col-sm-6 col-md-4">
                  <q-card>
                    <q-card-section class="text-center">
                      Calories for
                      <br />
                      <strong>{{ props.row.name }}</strong>
                    </q-card-section>
                    <q-separator />
                    <q-card-section
                      class="flex flex-center"
                      :style="{ fontSize: props.row.calories + 'px' }"
                    >
                      <div>{{ props.row.calories }} g</div>
                    </q-card-section>
                  </q-card>
                </div>
              </template>
            </q-table>
          </div>
          <div class="col-4">
            <div class="q-pa-md">
              <q-table
                title="문제 List"
                :data="item.problemList"
                :columns="listColumns"
                hide-pagination
                :pagination.sync="pagination"
              />
              <div class="q-pa-lg flex flex-center">
                <q-pagination
                  v-model="pagination.page"
                  :max="pagesNumber"
                  input
                  input-class="text-orange-10"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="row">
          <div class="col-10"></div>
          <div class="col-2">
            <q-btn color="primary" label="답안제출" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
const deserts = ["Frozen Yogurt"];
const data = [];

deserts.forEach(name => {
  for (let i = 0; i < 24; i++) {
    data.push({
      name: name + " (" + i + ")",
      calories: 20 + Math.ceil(50 * Math.random())
    });
  }
});

data.sort(() => -1 + Math.floor(3 * Math.random()));

export default {
  data() {
    return {
      filter: "",
      pagination1: {
        page: 1,
        rowsPerPage: this.getItemsPerPage()
      },
      columns: [
        { name: "name", label: "Name", field: "name" },
        { name: "calories", label: "Calories (g)", field: "calories" }
      ],
      data,
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 3
        // rowsNumber: xx if getting data from a server
      },
      listColumns: [
        {
          name: "문제번호",
          align: "left",
          label: "문제번호",
          field: "problemNo"
        },
        {
          name: "문제제목",
          style: "width:100vw",
          label: "문제 제목",
          field: "title",
          align: "center"
        }
      ],
      item: {
        problemSet: {
          problemSetNo: 0,
          title: "",
          regiTime: "",
          nickname: ""
        },
        problemList: []
      },
      slide: "style",
      lorem:
        "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Itaque voluptatem totam, architecto cupiditate officia rerum, error dignissimos praesentium libero ab nemo."
    };
  },
  methods: {
    getItemsPerPage() {
      const { screen } = this.$q;
      if (screen.lt.sm) {
        return 3;
      }
      if (screen.lt.md) {
        return 6;
      }
      return 9;
    }
  },
  computed: {
    pagesNumber() {
      return Math.ceil(
        this.item.problemList.length / this.pagination.rowsPerPage
      );
    },
    cardContainerClass() {
      if (this.$q.screen.gt.xs) {
        return (
          "grid-masonry grid-masonry--" + (this.$q.screen.gt.sm ? "3" : "2")
        );
      }

      return void 0;
    },

    rowsPerPageOptions() {
      if (this.$q.screen.gt.xs) {
        return this.$q.screen.gt.sm ? [3, 6, 9] : [3, 6];
      }

      return [3];
    }
  },
  watch: {
    vertical(val) {
      this.navPos = val === true ? "right" : "bottom";
    },
    "$q.screen.name"() {
      this.pagination.rowsPerPage = this.getItemsPerPage();
    }
  }
};
</script>
<style>
.q-btn {
  margin-left: 10px;
}

#header-title {
  height: 100px;
}
#header-btn {
  height: 150px;
}
#contents {
  padding-bottom: 20px;
}
</style>
