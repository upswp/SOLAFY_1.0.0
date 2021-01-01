<template>
  <div class="q-pa-md fit row justify-center content-center">
    <q-form class="q-gutter-md col-6" @submit="onSubmit">
      <q-stepper
        v-model="step"
        ref="stepper"
        color="primary"
        header-nav
        animated
      >
        <q-step
          :name="1"
          title="그룹 설정"
          icon="settings"
          :error="step < 3"
          :done="step > 1"
        >
          <!-- 그룹명 시작  -->
          <div style="max-width: 420px;">
            <q-input
              bottom-slots
              v-model="groupData.title"
              label="그룹명"
              counter
              maxlength="12"
              :dense="dense"
              :rules="[
                val => (val && val.length > 0) || '그룹명을 입력해주세요.'
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
              <!-- 중복 체크 시작 -->
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
              <!-- 중복 체크 끝-->
            </q-input>
          </div>
          <!-- 그룹명 끝 -->

          <!-- 그룹 소개 시작 -->
          <div style="max-width: 350px;">
            <q-input
              v-model="groupData.description"
              clearable
              autogrow
              color="green-8"
              label="그룹 소개"
              :shadow-text="textareaShadowText"
              @keydown="processTextareaFill"
              @focus="processTextareaFill"
              :rules="[
                val => (val && val.length > 0) || '그룹소개를 입력해주세요.'
              ]"
            />
          </div>
          <!-- 그룹 소개 끝-->

          <!-- 그룹 타입 시작 -->
          <div class="q-gutter-sm">
            <q-radio
              keep-color
              v-model="groupData.type"
              val="0"
              label="공개 그룹"
              color="cyan"
            />

            <q-radio
              keep-color
              v-model="groupData.type"
              val="1"
              label="비공개 그룹"
              color="orange"
            />
          </div>
          <!-- 그룹 타입 끝 -->
        </q-step>

        <q-step
          :name="2"
          title="추가 사항 있으면 넣기"
          icon="create_new_folder"
          :done="step > 2"
        >
          An ad group contains one or more ads which target a shared set of
          keywords.
        </q-step>

        <!-- 최종 확인 시작-->
        <q-step :name="3" title="Create an ad" icon="add_comment">
          <div class="row">
            <strong>그룹명 : {{ groupData.title }}</strong>
          </div>
          <div class="row">
            <strong>그룹 설명 : {{ groupData.description }}</strong>
          </div>
          <div class="row">
            <strong v-if="groupData.type == '0'">타입 : public</strong>
            <strong v-else>타입 : private</strong>
          </div>
        </q-step>
        <!-- 최종 확인 끝-->

        <!-- 페이지네이션 시작 -->
        <template v-slot:navigation>
          <q-stepper-navigation>
            <q-btn
              color="primary"
              :label="step === 3 ? 'Finish' : 'Continue'"
              type="submit"
            />
            <q-btn
              v-if="step > 1"
              flat
              color="primary"
              @click="$refs.stepper.previous()"
              label="Back"
              class="q-ml-sm"
            />
          </q-stepper-navigation>
        </template>
        <!-- 페이지네이션 끝 -->
      </q-stepper>
    </q-form>
  </div>
</template>

<script>
import { firebaseAuth } from "src/boot/firebase";
import { createGroup, selectCheckDuplicateName } from "src/api/Group/group.js";
import { notify } from "src/api/common.js";
export default {
  data() {
    return {
      groupData: {
        description: "",
        title: "",
        type: "0"
      },
      titleCheck: "",
      dense: false,
      inputModel: "",
      step: 1
    };
  },
  methods: {
    onSubmit() {
      if (this.check) {
        // ! 현재 step 값으로 다음 step으로 넘어갈지 경정
        if (this.step < 3) this.$refs.stepper.next();
        else {
          // ! checkgroup(param, success, fail)
          createGroup(
            this.groupData,
            Response => {
              notify("green", "white", "done_outline", "그룹 생성 완료");
              this.$router.go(-1);
            },
            error => {
              notify("red-6", "white", "warning", "그룹 생성 실패");
              this.$router.go(-1);
            }
          );
        }
      } else {
        notify("red-6", "white", "warning", "그룹명 중복 체크 필요");
      }
    },

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

    processInputFill(e) {
      if (e === void 0) {
        return;
      }

      if (e.keyCode === 27) {
        if (this.inputFillCancelled !== true) {
          this.inputFillCancelled = true;
        }
      } else if (e.keyCode === 9) {
        if (
          this.inputFillCancelled !== true &&
          this.inputShadowText.length > 0
        ) {
          stopAndPrevent(e);
          this.inputModel =
            (typeof this.inputModel === "string" ? this.inputModel : "") +
            this.inputShadowText;
        }
      } else if (this.inputFillCancelled === true) {
        this.inputFillCancelled = false;
      }
    },

    processTextareaFill(e) {
      if (e === void 0) {
        return;
      }

      if (e.keyCode === 27) {
        if (this.textareaFillCancelled !== true) {
          this.textareaFillCancelled = true;
        }
      } else if (e.keyCode === 9) {
        if (
          this.textareaFillCancelled !== true &&
          this.textareaShadowText.length > 0
        ) {
          stopAndPrevent(e);
          this.description =
            (typeof this.description === "string" ? this.description : "") +
            this.textareaShadowText;
        }
      } else if (this.textareaFillCancelled === true) {
        this.textareaFillCancelled = false;
      }
    }
  },
  computed: {
    textareaShadowText() {
      if (this.textareaFillCancelled === true) {
        return "";
      }

      const t =
          "This text\nwill be filled\non multiple lines\nwhen you press TAB",
        empty =
          typeof this.description !== "string" || this.description.length === 0;

      if (empty === true) {
        return t.split("\n")[0];
      } else if (t.indexOf(this.description) !== 0) {
        return "";
      }

      return t
        .split(this.description)
        .slice(1)
        .join(this.description)
        .split("\n")[0];
    },
    // ! title이 변경되었을 때
    DupCheck: function() {
      if (this.groupData.title == "" || this.groupData.title != this.titleCheck)
        return false;
      else return true;
    }
  }
};
</script>

<style></style>
