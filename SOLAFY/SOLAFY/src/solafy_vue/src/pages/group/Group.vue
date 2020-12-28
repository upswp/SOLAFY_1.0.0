<template
  ><div>
    <!-- tab bar 시작 -->
    <q-card>
      <q-tabs v-model="tab" narrow-indicator dense align="justify">
        <q-tab
          class="text-purple"
          name="notice"
          icon="announcement"
          label="공지"
        />
        <q-tab
          class="text-orange"
          name="problemSet"
          icon="source"
          label="문제집"
        />
        <q-tab class="text-teal" name="competition" icon="stars" label="대회" />
        <q-tab
          class="text-cyan"
          name="member"
          icon="supervised_user_circle"
          label="그룹원"
        />
        <q-tab
          class="text-red"
          name="modify"
          icon="verified_user"
          label="수정"
        />
      </q-tabs>

      <q-separator />

      <q-tab-panels v-model="tab" animated>
        <q-tab-panel name="notice">
          <div class="text-h6">그룹 공지</div>
          자유게시판 가지고와야함
        </q-tab-panel>

        <q-tab-panel name="problemSet">
          <div class="text-h6">그룹 문제집</div>
          문제집 가지고와야함
        </q-tab-panel>

        <q-tab-panel name="competition">
          <div class="text-h6">그룹 대회</div>
          대회가지고오기
        </q-tab-panel>
        <q-tab-panel name="member">
          <div>
            <!-- itemlist - 그룹 리스트 시작-->
            <q-table
              title="그룹원 보기"
              :data="groupMembers"
              :columns="columns"
              row-key="name"
              :filter="filter"
              gird-header
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
        </q-tab-panel>
        <q-tab-panel name="modify">
          <div class="text-h6">그룹 수정</div>
          지금 만들기
        </q-tab-panel>
      </q-tab-panels>
    </q-card>
    <!-- tab bar 끝 -->
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      tab: "notice",
      filter: "",
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
          name: "nickName",
          required: true,
          label: "닉네임",
          align: "left",
          field: row => row.nickName,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "statusMessage",
          align: "center",
          label: "소개",
          field: "statusMessage",
          sortable: true
        },

        {
          name: "grade",
          label: "그룹 책임",
          field: "grade",
          sortable: true,
          sort: (a, b) => parseInt(a, 10) - parseInt(b, 10)
        }
      ],
      groupMembers: []
    };
  },
  methods: {
    clickRow() {}
  },

  mounted() {
    axios
      .get("group/selectGroupMember/" + this.$route.params.groupNo)
      .then(Response => {
        this.groupMembers = Response.data;
        console.log(this.groupMembers);
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
      return Math.ceil(this.groupMembers.length / this.pagination.rowsPerPage);
    }
  }
};
</script>

<style></style>
