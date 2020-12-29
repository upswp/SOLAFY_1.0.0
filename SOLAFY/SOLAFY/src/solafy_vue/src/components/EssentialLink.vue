<template>
  <q-item clickable tag="a" @click="onclick">
    <q-item-section v-if="icon" avatar>
      <q-icon :name="icon" />
    </q-item-section>

    <q-item-section>
      <q-item-label>{{ title }}</q-item-label>
      <q-item-label caption>
        {{ caption }}
      </q-item-label>
    </q-item-section>
  </q-item>
</template>

<script>
import { firebaseAuth } from "boot/firebase";

export default {
  name: "EssentialLink",
  props: {
    title: {
      type: String,
      required: true
    },

    caption: {
      type: String,
      default: ""
    },

    link: {
      type: String,
      default: "#"
    },

    icon: {
      type: String,
      default: ""
    }
  },
  methods: {
    onclick() {
      if (this.title == "About" || firebaseAuth.currentUser != null)
        this.$router.push(this.link);
      else this.$router.push("/");
    }
  }
};
</script>
