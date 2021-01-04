<template>
  <!-- https://codepen.io/metalsadman/full/ZgKexK -->
  <div>
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
          v-if="myInfo.grade < 2"
          class="text-red"
          name="modify"
          icon="verified_user"
          label="수정"
        />
      </q-tabs>

      <q-separator />

      <q-tab-panels v-model="tab" animated>
        <q-tab-panel name="notice">
          <free-board></free-board>
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

            <!-- TODO : 페이징 수정필요 -->
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
        <!-- 그룹 수정 tab 시작 -->
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
              <!-- 그룹수정 내부 그룹정보 수정 -->
              <q-tab-panel name="group">
                <q-form class="q-gutter-md" @submit="onSubmit">
                  <div style="max-width: 420px;">
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
                          :text-color="DupCheck ? 'green' : 'red'"
                          flat
                          @click="titleDuplicate"
                          :icon="DupCheck ? 'check' : 'warning'"
                          :disable="DupCheck"
                        >
                        </q-btn>
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
              <!-- 그룹수정 내부 그룹정보 수정 끝 -->

              <q-tab-panel name="board">
                게시판 수정
              </q-tab-panel>
              <!-- 그룹수정 내부 그룹원 수정-->
              <q-tab-panel name="groupMember">
                <div>
                  <q-table
                    flat
                    bordered
                    class="statement-table"
                    title="회원관리"
                    :data="groupMembers"
                    :hide-header="mode === 'grid'"
                    :columns="currencyColumns"
                    row-key="__index"
                    :grid="mode == 'grid'"
                    :filter="filter"
                    virtual-scroll
                    :pagination.sync="pagination"
                    :rows-per-page-options="[0]"
                  >
                    <template v-slot:top-right>
                      <q-input
                        outlined
                        dense
                        debounce="300"
                        v-model="filter"
                        placeholder="Search"
                      >
                        <template v-slot:append>
                          <q-icon name="search" />
                        </template>
                      </q-input>

                      <!-- grid버튼 -->
                      <q-btn
                        flat
                        round
                        dense
                        :icon="mode === 'grid' ? 'list' : 'grid_on'"
                        @click="mode = mode === 'grid' ? 'list' : 'grid'"
                      >
                        <q-tooltip
                          :disable="$q.platform.is.mobile"
                          v-close-popup
                          >{{ mode === "grid" ? "List" : "Grid" }}</q-tooltip
                        >
                      </q-btn>
                      <div class="q-pa-sm q-gutter-sm"></div>
                    </template>
                    <!-- grid버튼 -->

                    <!-- status -->
                    <template #body-cell-grade="props">
                      <q-td :props="props">
                        <q-chip
                          :color="
                            props.row.grade == '1'
                              ? 'green'
                              : props.row.grade == '2'
                              ? 'red'
                              : props.row.grade == '3'
                              ? 'primary'
                              : props.row.grade == '99'
                              ? 'orange'
                              : 'grey'
                          "
                          text-color="white"
                          dense
                          class="text-weight-bolder"
                          square
                          v-text="
                            props.row[props.col.name] == '1'
                              ? '그룹장'
                              : props.row[props.col.name] == '2'
                              ? '관리자'
                              : props.row[props.col.name] == '3'
                              ? '일반회원'
                              : props.row[props.col.name] == '99'
                              ? '가입대기'
                              : '정체불명'
                          "
                        ></q-chip>
                      </q-td>
                    </template>
                    <!-- status -->

                    <!-- action -->
                    <template #body-cell-action="props">
                      <q-td>
                        <q-btn
                          dense
                          flat
                          round
                          color="blue"
                          field="edit"
                          icon="edit"
                          @click="editItem(props.row)"
                        ></q-btn>
                        <q-btn
                          dense
                          flat
                          round
                          color="red"
                          field="deleteMember"
                          icon="delete_forever"
                          @click="confirmDelete(props.row)"
                        ></q-btn>
                      </q-td>
                    </template>
                    <!-- action -->

                    <template v-slot:item="props">
                      <div
                        class="q-pa-xs col-xs-12 col-sm-6 col-md-4 col-lg-3 grid-style-transition"
                        :style="props.selected ? 'transform: scale(0.95);' : ''"
                      >
                        <q-card :class="props.selected ? 'bg-grey-2' : ''">
                          <q-list dense>
                            <q-item v-for="col in props.cols" :key="col.name">
                              <q-item-section>
                                <q-item-label>{{ col.label }}</q-item-label>
                              </q-item-section>
                              <q-item-section side>
                                <q-chip
                                  v-if="col.name === 'grade'"
                                  :color="
                                    props.row.grade == '1'
                                      ? 'green'
                                      : props.row.grade == '2'
                                      ? 'red'
                                      : props.row.grade == '3'
                                      ? 'primary'
                                      : props.row.grade == '99'
                                      ? 'orange'
                                      : 'grey'
                                  "
                                  text-color="white"
                                  dense
                                  class="text-weight-bolder"
                                  square
                                  v-text="
                                    props.row.grade == '1'
                                      ? '그룹장'
                                      : props.row.grade == '2'
                                      ? '관리자'
                                      : props.row.grade == '3'
                                      ? '일반회원'
                                      : props.row.grade == '99'
                                      ? '가입대기'
                                      : '정체불명'
                                  "
                                ></q-chip>
                                <q-btn
                                  v-else-if="col.name === 'action'"
                                  dense
                                  flat
                                  color="primary"
                                  field="edit"
                                  icon="edit"
                                  @click="editItem(props.row)"
                                ></q-btn>
                                <q-item-label
                                  v-else
                                  caption
                                  :class="col.classes ? col.classes : ''"
                                  >{{ col.value }}</q-item-label
                                >
                              </q-item-section>
                            </q-item>
                          </q-list>
                        </q-card>
                      </div>
                    </template>
                  </q-table>
                  <!-- itemlist - 그룹원 보기 끝-->

                  <!-- 그룹원 정보를 수정할 수 있는 다이얼로그 -->
                  <q-dialog v-model="show_dialog">
                    <q-card style="width: 600px; max-width: 60vw">
                      <q-card-section>
                        <q-btn
                          round
                          flat
                          dense
                          icon="close"
                          class="float-right"
                          color="grey-8"
                          v-close-popup
                        ></q-btn>
                        <div class="text-h6">회원 관리</div>
                      </q-card-section>
                      <q-separator inset></q-separator>
                      <q-card-section class="q-pt-none">
                        <q-form class="q-gutter-md">
                          <q-list>
                            <q-item>
                              <q-item-section>
                                <q-item-label class="q-pb-xs"
                                  >닉네임</q-item-label
                                >
                                <q-input
                                  dense
                                  outlined
                                  v-model="editedItem.nickName"
                                  readonly
                                />
                              </q-item-section>
                            </q-item>
                            <q-item>
                              <q-item-section>
                                <q-item-label class="q-pb-xs"
                                  >상태 메세지</q-item-label
                                >
                                <q-input
                                  dense
                                  outlined
                                  v-model="editedItem.statusMessage"
                                  readonly
                                />
                              </q-item-section>
                            </q-item>
                            <q-item>
                              <q-item-section>
                                <q-item-label class="q-pb-xs"
                                  >가입 메세지</q-item-label
                                >
                                <q-input
                                  dense
                                  outlined
                                  v-model="editedItem.regiMessage"
                                  readonly
                                />
                              </q-item-section>
                            </q-item>
                            <q-item>
                              <q-item-section>
                                <q-item-label class="q-pb-xs"
                                  >상태</q-item-label
                                >
                                <div class="q-gutter-sm">
                                  <q-radio
                                    keep-color
                                    v-model="editedItem.grade"
                                    val="2"
                                    label="관리자"
                                    color="red"
                                  />
                                  <q-radio
                                    keep-color
                                    v-model="editedItem.grade"
                                    val="3"
                                    label="일반회원"
                                    color="primary"
                                  />
                                </div>
                              </q-item-section>
                            </q-item>
                          </q-list>
                        </q-form>
                      </q-card-section>
                      <q-card-section>
                        <q-card-actions align="right">
                          <q-btn
                            flat
                            label="Cancel"
                            color="warning"
                            dense
                            v-close-popup
                          ></q-btn>
                          <q-btn
                            flat
                            label="OK"
                            color="primary"
                            dense
                            v-close-popup
                            @click="updateRow"
                          ></q-btn>
                        </q-card-actions>
                      </q-card-section>
                    </q-card>
                  </q-dialog>
                  <!-- 그룹원 정보를 수정할 수 있는 다이얼로그 끝-->

                  <!-- 그룹원 수정 강제 탈퇴 다이얼로그 -->
                  <q-dialog v-model="confirm" persistent>
                    <q-card>
                      <q-card-section class="row items-center">
                        <q-avatar
                          icon="outlet"
                          color="red"
                          text-color="white"
                        />
                        <span class="q-ml-sm">
                          [{{ editedItem.nickName }}]님을 정말 탈퇴
                          시키겠습니까?
                        </span>
                      </q-card-section>

                      <q-card-actions align="right">
                        <q-btn
                          flat
                          label="취소"
                          color="primary"
                          v-close-popup
                        />
                        <q-btn
                          flat
                          label="탈퇴"
                          color="red"
                          @click="deleteMember()"
                          v-close-popup
                        />
                      </q-card-actions>
                    </q-card>
                  </q-dialog>
                  <!-- 그룹원 수정 강제 탈퇴 다이얼로그 끝 -->
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
              <!-- 그룹수정 내부 그룹원 수정 끝-->
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
import FreeBoard from "../board/FreeBoard.vue";
import { notify } from "src/api/common.js";
import {
  selectCheckDuplicateName,
  selectGroupByNo,
  updateGroup,
  updateGroupApplyConfirm,
  deleteGroupMember,
  selectGroupMember
} from "src/api/Group/group.js";
import { firebaseAuth } from "src/boot/firebase";

const defaultItem = {
  uid: "",
  groupNo: "",
  grade: "",
  statusMessage: "",
  regiMessage: "",
  nickName: ""
};

const currencyColumns = [
  {
    name: "nickName",
    align: "center",
    label: "닉네임",
    field: "nickName",
    sortable: true,
    classes: "text-black"
  },

  {
    name: "statusMessage",
    align: "center",
    label: "소개",
    field: "statusMessage",
    sortable: true,
    classes: "text-black"
  },
  {
    name: "grade",
    align: "center",
    label: "가입 상태",
    field: "grade",
    sortable: true
  },

  {
    name: "action",
    align: "left",
    label: "관리",
    field: "action"
  }
];

const currencies = [];
export default {
  components: { FreeBoard },
  data() {
    return {
      confirm: false,
      titleCheck: null, //그룹명 중복체크 icon표시 control
      show_dialog: false,
      editedIndex: -1,
      editedItem: defaultItem,
      mode: "list",
      currencyColumns: currencyColumns,
      groupMembers: [],

      // TODO : 페이징 관련 변수들
      page: 1,
      totalRecord: 0,
      pageCount: 1,
      pagination: {
        sortBy: "desc",
        descending: false,
        page: 1,
        rowsPerPage: 10
        // rowsNumber: xx if getting data from a server
      },
      //tab관련 변수들
      tab: "notice",
      innertab: "group",
      filter: "",
      path: "",

      // TODO : createGroup와 동일하니까 이거 묶어서 component로 만들기
      groupData: {},
      groupType: "",
      myInfo: {},
      // 여기까지

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
      ]
    };
  },
  methods: {
    //TODO : 회원을 클릭하면 어떤 이벤트를 발생시킬 것인가? 결정된 사항없음
    clickRow() {},
    // TODO :  듀플리케이트 부터 onSubmit까지 다 컴포넌트로 묶기
    titleDuplicate() {
      // ! selectCheckDuplicateName(param, success, fail)
      selectCheckDuplicateName(
        this.groupData.title,
        Response => {
          if (Response.data == "success") {
            this.titleCheck = this.groupData.title;
            notify("green", "white", "done_outline", "사용하셔도 좋습니다");
          } else if (Response.data == "fail") {
            notify("red-6", "white", "warning", "그룹명이 중복되었습니다");
          }
        },
        error => {
          notify("red-6", "white", "warning", "다시 시도해 주세요");
        }
      );
    },
    onSubmit() {
      if (this.DupCheck) {
        //그룹타입 확인
        if (this.groupData.type != this.groupType) {
          this.groupData.type = Number(this.groupType);
        }
        updateGroup(
          this.groupData,
          Response => {
            notify("green", "white", "done_outline", "그룹 수정 완료");
          },
          error => {
            notify("red-6", "white", "warning", "그룹 수정 실패");
          }
        );
      } else {
        notify("red-6", "white", "warning", "그룹명 중복 검사를 진행해주세요");
      }
    },
    permission() {},

    editItem(item) {
      this.editedIndex = this.groupMembers.findIndex(
        (v, i) => v.__index === item.__index
      );
      this.editedItem = Object.assign({}, item);
      this.editedItem.grade = this.editedItem.grade.toString();
      this.show_dialog = true;
    },
    close() {
      this.show_dialog = false;
      setTimeout(() => {
        this.editedItem = defaultItem;
        this.editedIndex = -1;
      }, 300);
    },
    updateRow() {
      // this.groupMembers.splice(this.editedIndex, 1, this.editedItem);
      // TODO : backend에서 return 해주는 값 변경해야 할 수도 있음
      updateGroupApplyConfirm(
        this.editedItem,
        Response => {
          this.getGroupMember();
        },
        error => {
          notify("red-6", "white", "warning", "정보 수정 실패");
        }
      );
    },
    getGroupMember() {
      selectGroupMember(
        this.$route.params.groupNo,
        Response => {
          this.groupMembers = Response.data;
          this.getMyInfo(); //그룹에서 나의 정보를 찾아서 저장해둠
        },
        error => {
          notify("red-6", "white", "warning", "그룹원 데이터 읽기 실패");
        }
      );
    },
    getGroupInfo() {
      selectGroupByNo(
        this.$route.params.groupNo,
        Response => {
          this.groupData = Response.data;
          if (this.groupData.type == 1) this.groupType = "1";
          else this.groupType = "0";
          this.titleCheck = this.groupData.title;
        },
        error => {
          notify("red-6", "white", "warning", "조회 실패");
        }
      );
    },
    confirmDelete(item) {
      this.editedIndex = this.groupMembers.findIndex(
        (v, i) => v.__index === item.__index
      );
      this.editedItem = Object.assign({}, item);
      this.confirm = true;
    },
    deleteMember() {
      deleteGroupMember(
        {
          uid: this.editedItem.uid,
          groupNo: this.editedItem.groupNo
        },
        Response => {
          this.getGroupMember();
        },
        error => {
          notify("red-6", "white", "warning", "탈퇴 실패");
        }
      );
    },
    //선택한 그룹에 대한 나의 정보를 찾는다.
    //TODO : 이진트리로 검색하면 더 빠를거 같습니다(수정 가능)
    getMyInfo() {
      this.groupMembers.forEach(element => {
        if (element.uid == firebaseAuth.currentUser.uid) {
          this.myInfo = element;
        }
      });
    }
  },

  mounted() {
    this.getGroupMember();
    this.getGroupInfo();
    // add indices
    this.groupMembers = this.groupMembers.map((v, i) => ({ ...v, __index: i }));
  },
  computed: {
    pagesNumber() {
      return Math.ceil(this.groupMembers.length / this.pagination.rowsPerPage);
    },
    // TODO : createGroup과 함께 컴포넌트화 시킬 수 있음
    DupCheck: function() {
      if (this.groupData.title == "" || this.groupData.title != this.titleCheck)
        return false;
      else return true;
    }
  }
};
</script>

<style></style>
