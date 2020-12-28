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
            <!-- itemlist - 그룹원 보기 시작-->
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
            <!-- itemlist - 그룹원 보기 끝-->
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
          <q-card>
            <q-tabs
              v-model="innertab"
              dense
              class="text-grey"
              active-color="primary"
              indicator-color="primary"
              align="justify"
              narrow-indicator
            >
              <q-tab name="group" label="그룹 수정" />
              <q-tab name="board" label="게시판 수정" />
              <q-tab name="groupMember" label="그룹원 관리" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="innertab" animated>
              <q-tab-panel name="group">
                <q-form class="q-gutter-md" @submit="onSubmit">
                  <div style="max-width: 440px;">
                    <q-input
                      filled
                      bottom-slots
                      v-model="groupData.title"
                      label="그룹명"
                      counter
                      maxlength="12"
                      :rules="[
                        val =>
                          (val && val.length > 0) || '그룹명을 입력해주세요.'
                      ]"
                    >
                      <template v-slot:append>
                        <q-icon
                          v-if="groupData.title !== ''"
                          name="close"
                          @click="groupData.title = ''"
                          class="cursor-pointer"
                        />
                      </template>
                      <!-- 중복 체크 -->
                      <template v-slot:after>
                        <q-btn
                          :text-color="checkColor"
                          flat
                          @click="titleDuplicate"
                          ><strong>Check</strong></q-btn
                        >
                      </template>
                    </q-input>
                  </div>
                  <!-- 그룹 소개 -->
                  <div style="max-width: 350px;">
                    <q-input
                      v-model="groupData.description"
                      filled
                      clearable
                      autogrow
                      color="green-8"
                      label="그룹 소개"
                      :rules="[
                        val =>
                          (val && val.length > 0) || '그룹소개를 입력해주세요.'
                      ]"
                    />
                  </div>
                  <div class="q-gutter-sm">
                    <q-radio
                      keep-color
                      v-model="groupType"
                      val="0"
                      label="공개 그룹"
                      color="cyan"
                    />

                    <q-radio
                      keep-color
                      v-model="groupType"
                      val="1"
                      label="비공개 그룹"
                      color="orange"
                    />
                  </div>
                  <br />
                  <q-btn color="primary" type="submit" label="수정" />
                  <q-btn flat color="primary" label="취소" class="q-ml-sm" />
                </q-form>
              </q-tab-panel>

              <q-tab-panel name="board">
                게시판 수정
              </q-tab-panel>

              <q-tab-panel name="groupMember">
                <div>
                  <!-- itemlist - 그룹원 보기 시작-->
                  <q-table
                    title="그룹원 관리"
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
                    <template v-slot:body-cell-btn="props">
                      <q-td :props="props">
                        <div>
                          <q-btn color="primary">권한</q-btn>
                          <q-btn color="orange">추방</q-btn>
                        </div>
                        <div class="my-table-details">
                          {{ props.row.details }}
                        </div>
                      </q-td>
                    </template>
                  </q-table>

                  <!-- itemlist - 그룹원 보기 끝-->
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
            </q-tab-panels>
          </q-card>
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
      innertab: "group",
      filter: "",
      path: "",
      // TODO : createGroup와 동일하니까 이거 묶어서 component로 만들기
      groupData: {
        description: "",
        title: "",
        type: ""
      },
      check: false,
      checkColor: "red",
      groupType: "",
      // 여기까지
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
        },
        {
          name: "btn",
          label: ""
        }
      ],
      groupMembers: []
    };
  },
  methods: {
    clickRow() {},
    // 듀플리케이트 부터 onSubmit까지 다 컴포넌트로 묶기
    titleDuplicate() {
      axios
        .get("group/checkDuplicateName/" + this.groupData.title)
        .then(Response => {
          if (Response.data == "success") {
            this.check = true;
            this.checkColor = "green";
          } else {
            this.check = false;
            this.checkColor = "red";
          }
        })
        .catch(error => {});
    },
    onSubmit() {
      if (this.check) {
        axios
          .put("group/updateGroup", this.groupData)
          .then(Response => {
            this.$q.notify({
              color: "green",
              textColor: "white",
              icon: "cloud",
              message: "그룹 수정 완료"
            });
          })
          .catch(error => {
            this.$q.notify({
              color: "red-6",
              textColor: "white",
              icon: "warning",
              message: "그룹 수정 실패"
            });
          });
      } else {
        this.$q.notify({
          color: "red-6",
          textColor: "white",
          icon: "warning",
          message: "그룹명 중복 체크 필요"
        });
      }
    }
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
    axios
      .get("group/selectGroupByNo/" + this.$route.params.groupNo)
      .then(Response => {
        this.groupData = Response.data;
        if (this.groupData.type == 1) this.groupType = "1";
        else this.groupType = "0";
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
