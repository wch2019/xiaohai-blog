<template>
  <div class="dashboard-container">
    <!--    <iframe src="http://192.168.68.207:7576" style="height:1000px;width: 100%;"/>-->
    <div class="dashboard-text">name: {{ name }}</div>
    <!--    <iframe-->
    <!--      src="http://192.168.68.207:7576?graph=cpu&showPercentage=true&theme=light"-->
    <!--      style="border-radius: 20px"-->
    <!--      allowtransparency="true"-->
    <!--      frameborder="0"-->
    <!--    />-->
    <!--    <iframe-->
    <!--      src="http://192.168.68.207:7576?graph=ram&showPercentage=true&theme=light"-->
    <!--      style="border-radius: 20px"-->
    <!--      allowtransparency="true"-->
    <!--      frameborder="0"-->
    <!--    />-->
    <!--    <iframe-->
    <!--      src="http://192.168.68.207:7576?graph=network&showPercentage=true&theme=light"-->
    <!--      style="border-radius: 20px"-->
    <!--      allowtransparency="true"-->
    <!--      frameborder="0"-->
    <!--    />-->
    <!--    <iframe-->
    <!--      src="http://192.168.68.207:7576?graph=storage&multiView=true&showPercentage=true&theme=light"-->
    <!--      style="border-radius: 20px"-->
    <!--      allowtransparency="true"-->
    <!--      frameborder="0"-->
    <!--    />-->
    <component :is="currentRole" />
    <el-card class="box-card" shadow="hover" style="width: 100%;">
      <div slot="header">
        <span style="text-align: center">文章数</span>
      </div>
      <calendar-heatmap
        :end-date="new Date().toLocaleDateString()"
        :values="timeValue"
        :locale="locale"
        tooltip-unit="文章数"
        :max="6"
      />
      近一年提交
    </el-card>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import 'vue-calendar-heatmap/dist/vue-calendar-heatmap.css'
import { CalendarHeatmap } from 'vue-calendar-heatmap/dist/vue-calendar-heatmap.common'

export default {
  name: 'Dashboard',
  components: { adminDashboard, CalendarHeatmap },
  data() {
    return {
      currentRole: 'adminDashboard',
      locale: {
        months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        days: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        on: '：',
        less: '少',
        more: '多'
      },
      timeValue: [
        { date: '2023-04-02', count: 1 },
        { date: '2023-04-02', count: 2 },
        { date: '2023-04-03', count: 3 },
        { date: '2023-04-04', count: 4 },
        { date: '2023-04-05', count: 5 },
        { date: '2023-04-06', count: 6 }
      ]
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
