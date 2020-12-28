<template>
  <div class="q-pa-md">
    <div class="column">
      <!-- 상단 제목 및 돌아가기 버튼 -->
      <div class="col" id="header-title">
        <h3>문제집 제목</h3>
      </div>
      <div class="col" id="header-btn">
        <div class="row">
          <div class="col-10"></div>
          <div class="col-2">
            <q-btn color="primary" label="돌아가기" />
          </div>
        </div>
      </div>
      <!-- contents -->
      <div class="col">
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <q-table
              title="문제집 정보"
              :data="data"
              :columns="columns"
              row-key="name"
              hide-header
              hide-bottom
            />
          </div>
        </div>
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <p>총 문제 : {{ item.problemList.length }}</p>
          </div>
        </div>
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <q-item clickable v-ripple>
              <q-item-section>
                <q-item-label>Content filtering</q-item-label>
                <q-item-label caption>
                  Set the content filtering level to restrict apps that can be
                  downloaded
                </q-item-label>
              </q-item-section>
            </q-item>

            <q-item clickable v-ripple>
              <q-item-section>
                <q-item-label>Password</q-item-label>
                <q-item-label caption>
                  Require password for purchase or use password to restrict
                  purchase
                </q-item-label>
              </q-item-section>
            </q-item>
            <q-item clickable v-ripple>
              <q-item-section>
                <q-item-label>Password</q-item-label>
                <q-item-label caption>
                  Require password for purchase or use password to restrict
                  purchase
                </q-item-label>
              </q-item-section>
            </q-item>
            <q-item clickable v-ripple>
              <q-item-section>
                <q-item-label>Password</q-item-label>
                <q-item-label caption>
                  Require password for purchase or use password to restrict
                  purchase
                </q-item-label>
              </q-item-section>
            </q-item>
            <div class="q-pa-lg flex flex-center">
              <q-pagination
                v-model="current"
                :max="5"
                input
                input-class="text-orange-10"
              />
            </div>
            <q-separator spaced />
          </div>
        </div>
      </div>
      <!-- 하단 문제집 삭제,수정, 시작하기 버튼 -->
      <div class="col">
        <div class="row">
          <div class="col-7"></div>
          <div class="col-5">
            <q-btn color="primary" label="문제집 삭제" />
            <q-btn color="primary" label="문제집 수정" />
            <q-btn color="primary" label="문제풀이 시작" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import routes from "src/router/routes";

export default {
  name: "ProblemDetailByProblemSetInfo",
  data() {
    return {
      current: 3,
      columns: [
        { name: "title", align: "left", label: "title", field: "title" },
        {
          name: "content",
          style: "width:100vw",
          label: "content",
          field: "content",
          align: "left"
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
      data: [
        {
          title: "문제집 제목",
          content: ""
        },
        {
          title: "문제 작성자",
          content: ""
        },
        {
          title: "문제 작성일자",
          content: ""
        }
      ]
    };
  },
  methods: {
    selectProblemByNo: function() {
      Axios.get(
        "/problem/problemset/problemSetSelectByNo/" +
          this.$route.params.problemSetNo
      )
        .then(Response => {
          console.log(Response.data);
          this.item = Response.data;
          this.data[0].content = this.item.problemSet.title;
          this.data[1].content = this.item.problemSet.nickname;
          this.data[2].content = this.item.problemSet.regiTime;
        })
        .catch(error => {
          this.$q.notify({
            color: "negative",
            textColor: "white",
            icon: "error",
            message: "조회 실패"
          });
          this.goToproblemList();
        })
        .finally(() => {
          this.loading = false;
        });
    }
  },
  created() {
    this.selectProblemByNo();
  }
};
</script>
<style>
.q-btn {
  margin-left: 10px;
}
.q-table {
  width: 500px;
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
