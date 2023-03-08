<template>
  <div class="dashboard-container">
    <iframe src="http://192.168.68.207:7576" style="height:1000px;width: 100%;"/>
    <div class="dashboard-text">name: {{ name }}</div>
    <iframe
      src="http://192.168.68.207:7576?graph=cpu&showPercentage=true&theme=light"
      style="border-radius: 20px"
      allowtransparency="true"
      frameborder="0"
    />
    <iframe
      src="http://192.168.68.207:7576?graph=ram&showPercentage=true&theme=light"
      style="border-radius: 20px"
      allowtransparency="true"
      frameborder="0"
    />
    <iframe
      src="http://192.168.68.207:7576?graph=network&showPercentage=true&theme=light"
      style="border-radius: 20px"
      allowtransparency="true"
      frameborder="0"
    />
    <iframe
      src="http://192.168.68.207:7576?graph=storage&multiView=true&showPercentage=true&theme=light"
      style="border-radius: 20px"
      allowtransparency="true"
      frameborder="0"
    />
    <!--    <component :is="currentRole" />-->
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'

export default {
  name: 'Dashboard',
  components: { adminDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
