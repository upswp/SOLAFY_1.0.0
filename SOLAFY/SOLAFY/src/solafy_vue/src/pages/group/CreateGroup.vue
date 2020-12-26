<template>
  <div class="q-pa-md">
    <q-stepper v-model="step" ref="stepper" color="primary" header-nav animated>
      <q-step
        :name="1"
        title="Select campaign settings"
        icon="settings"
        :error="step < 3"
        :done="step > 1"
      >
        <div style="max-width: 350px;">
          <q-input
            filled
            bottom-slots
            v-model="text"
            label="그룹명"
            counter
            maxlength="12"
            :dense="dense"
          >
            <template v-slot:append>
              <q-icon
                v-if="text !== ''"
                name="close"
                @click="text = ''"
                class="cursor-pointer"
              />
            </template>

            <template v-slot:hint>
              Field hint
            </template>

            <template v-slot:after>
              <q-btn dense flat>중복확인</q-btn>
            </template>
          </q-input>
        </div>
      </q-step>

      <q-step
        :name="2"
        title="Create an ad group"
        caption="Optional"
        icon="create_new_folder"
        :done="step > 2"
      >
        An ad group contains one or more ads which target a shared set of
        keywords.
      </q-step>

      <q-step :name="3" title="Create an ad" icon="add_comment">
        Try out different ad text to see what brings in the most customers, and
        learn how to enhance your ads using features like ad extensions. If you
        run into any problems with your ads, find out how to tell if they're
        running and how to resolve approval issues.
      </q-step>

      <template v-slot:navigation>
        <q-stepper-navigation>
          <q-btn
            @click="$refs.stepper.next()"
            color="primary"
            :label="step === 3 ? 'Finish' : 'Continue'"
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
    </q-stepper>
  </div>
</template>

<script>
export default {
  data() {
    return {
      step: 2
    };
  }
};
</script>

<style></style>
