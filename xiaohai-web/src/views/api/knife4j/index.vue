
<template>
  <div class="container">
    <iframe class="iframe" :src="url+'/doc.html'" />
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      url: process.env.VUE_APP_BASE_API
    }
  }
}
</script>
<style lang="scss" scoped>

  .container {
    position: absolute;
    top: 70px;
    left: 20px;
    right: 20px;
    height: calc(100vh - 110px);
  }

  .iframe {
    position: absolute;
    width: 100%;
    height: 95%;
    border: none;
  }
</style>
