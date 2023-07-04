<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import userDashboard from './user'

export default {
  name: 'Dashboard',
  components: { adminDashboard, userDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  watch: {
    '$route'() {
      if (!this.roles.includes('admin')) {
        this.currentRole = 'userDashboard'
      }
    }
  },
  created() {
    if (!this.roles.includes('admin')) {
      this.currentRole = 'userDashboard'
    }
  }
}
</script>
<style lang="scss" scoped>

</style>
