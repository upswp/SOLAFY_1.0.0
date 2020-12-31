<template>
  <div class="q-pa-md">
    <div class="q-gutter-y-md">
      <q-tabs v-model="tab" class="bg-grey-2 text-teal shadow-2">
        <q-tab name="mails" icon="mail" label="Mails">
          <q-badge color="red-8" floating>2</q-badge>
        </q-tab>
        <q-tab name="alarms" icon="alarm" label="Alarms">
          <q-badge color="red-8" floating>10+</q-badge>
        </q-tab>
        <q-tab alert name="movies" icon="movie" label="Movies" />
      </q-tabs>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { notify } from "src/api/common.js";

export default {
  data() {
    return {
      tab: "mails"
    };
  },
  methods: {}
};
</script>
