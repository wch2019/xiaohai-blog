<template>
  <div>
    <svg-icon class="websiteSvg" icon-class="website" @click.native="goto" style="height: 2.5em;font-size: 18px;cursor: pointer" />
  </div>
</template>

<script>
export default {
  name: 'Website',
  data() {
    return {
      url: process.env.VUE_APP_BLOG_WEB_API
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
<style scoped>

</style>
