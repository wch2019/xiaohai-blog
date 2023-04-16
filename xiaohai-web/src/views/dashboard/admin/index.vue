<template>
  <div class="dashboard-editor-container">
    <panel-group/>

    <el-row :gutter="24">
      <div style="padding:1% 20% 1% 20%">
        <h4 style="text-align: center;margin: 20px">
          <mallki class-name="mallki-text" text="近一年文章数"/>
        </h4>
        <calendar-heatmap
          :end-date="new Date().toLocaleDateString()"
          :values="timeValue"
          :locale="locale"
          tooltip-unit="文章数"
          :max="6"
          :range-color="[ '#dae2ef', '#c0ddf9', '#73b3f3', '#3886e1', '#17459e']"
        />
        <div style="padding-left:5%;padding-bottom:2%;font-size: 12px">
          最近一年提交: 123次 最长连续提交: 3天 最近持续提交: 0天
        </div>
      </div>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="e-title">分类</div>
          <pie-chart :chart-data="pieChart"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="e-title">标签云</div>
          <tag-cloud :data="hotTag" style="height: 400px;width: 80%"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper ">
          <div class="e-title">文章阅读量排行</div>
          <el-table :data="list" style="width: 100%;padding-top: 15px;" height="400" max-height="400">
            <el-table-column label="标题">
              <template slot-scope="scope">
                <el-link :underline="false" @click="onClick(scope.row)">{{ scope.row.title }}</el-link>
              </template>
            </el-table-column>
            <el-table-column label="阅读量" prop="quantity" align="center"/>
          </el-table>
        </div>
      </el-col>
    </el-row>
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div class="e-title">一周访问量</div>
      <line-chart :chart-data="lineChartData"/>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import PieChart from './components/PieChart'
import Mallki from '@/components/TextHoverEffect/Mallki'
import 'vue-calendar-heatmap/dist/vue-calendar-heatmap.css'
import { CalendarHeatmap } from 'vue-calendar-heatmap/dist/vue-calendar-heatmap.common'

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    LineChart,
    Mallki,
    PieChart,
    CalendarHeatmap
  },
  data() {
    return {
      hotTag: [
        { name: '民族舞' },
        { name: '书法' },
        { name: '象棋' },
        { name: '象棋7' },
        { name: '象棋' },
        { name: '象棋' },
        { name: '象棋' },
        { name: '象棋' },
        { name: '象棋' },
        { name: '围棋' },
        { name: '太极' }
      ],
      lineChartData: {
        PVData: [100, 120, 161, 134, 105, 160, 165],
        UVData: [120, 82, 91, 154, 162, 140, 145],
        IPData: [110, 72, 81, 144, 162, 140, 135]
      },
      pieChart: {
        nameData: ['测试', 'Technology', 'Forex', 'Gold', 'Forecasts'],
        valueData: [
          { value: 320, name: '测试' },
          { value: 240, name: 'Technology' },
          { value: 149, name: 'Forex' },
          { value: 100, name: 'Gold' },
          { value: 59, name: 'Forecasts' }
        ]
      },
      list: [
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
        { date: '2023-04-01', count: 1 },
        { date: '2023-04-02', count: 2 },
        { date: '2023-04-03', count: 3 },
        { date: '2023-04-04', count: 4 },
        { date: '2023-04-05', count: 5 },
        { date: '2023-04-06', count: 6 }
      ]
    }
  },
  methods: {}
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
