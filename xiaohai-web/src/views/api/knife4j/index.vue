
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
    top: 50px;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .iframe {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 95%;
    border: none;
  }
</style>
