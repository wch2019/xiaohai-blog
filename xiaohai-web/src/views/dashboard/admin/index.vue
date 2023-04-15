<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />
    <panel-group/>

    <el-row :gutter="24">
      <div class="chart-wrapper">
        <h3 style="text-align: center;margin: 20px">
          <mallki class-name="mallki-text" text="近一年文章数"/>
        </h3>
        <calendar-heatmap
          :end-date="new Date().toLocaleDateString()"
          :values="timeValue"
          :locale="locale"
          tooltip-unit="文章数"
          :max="6"
        />
        <div style="padding-left:5%;padding-bottom:2%;font-size: 12px">
          最近一年提交: 123次 最长连续提交: 3天 最近持续提交: 0天
        </div>
      </div>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
<!--          <pie-chart/>-->
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
<!--          <pie-chart/>-->
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <el-card>
          <div class="chart-wrapper ">
            <div class="e-title">文章阅读量排行</div>
            <el-table :data="list" style="width: 100%;padding-top: 15px;"  max-height="300">
              <el-table-column label="标题">
                <template slot-scope="scope">
                  <el-link :underline="false" @click="onClick(scope.row)">{{ scope.row.title }}</el-link>
                </template>
              </el-table-column>
              <el-table-column label="阅读量" prop="quantity" align="center"/>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <div class="chart-wrapper">
          <todo-list/>
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData"/>
    </el-row>

  </div>
</template>

<script>
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import PieChart from './components/PieChart'
import TodoList from './components/TodoList'
import Mallki from '@/components/TextHoverEffect/Mallki'
import 'vue-calendar-heatmap/dist/vue-calendar-heatmap.css'
import { CalendarHeatmap } from 'vue-calendar-heatmap/dist/vue-calendar-heatmap.common'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'DashboardAdmin',
  components: {
    GithubCorner,
    PanelGroup,
    LineChart,
    Mallki,
    PieChart,
    TodoList,
    CalendarHeatmap
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      list: [
        { title: 'aaa' },
        { title: 'aaa' },
        { title: 'aaa' },
        { title: 'aaa' },
        { title: 'aaa' },
        { title: 'aaa' },
        { title: 'aaa' }
      ],
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
  methods: {
    // handleSetLineChartData(type) {
    //   this.lineChartData = lineChartData[type]
    // }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

.test {
  height: 300px;
  overflow-y: scroll;
}
</style>
