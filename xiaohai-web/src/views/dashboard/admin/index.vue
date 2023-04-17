<template>
  <div class="dashboard-editor-container">
    <!-- 用户信息 -->
    <el-card class="drop-shadow">
      <div style="text-align: center">
        {{ a + word }}
      </div>
    </el-card>
    <panel-group/>

    <el-row :gutter="24">
      <div style="padding:1% 20% 1% 20%">
        <h4 style="text-align: center;margin: 20px">
          <mallki class-name="mallki-text" text="近一年文章贡献度"/>
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
          最近一年创作: {{oneYear}}次 最长连续创作: {{longest}}日 最近持续创作: {{continuous}}日
        </div>
      </div>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <el-card class="box-card" style=" margin-bottom: 32px;">
          <div slot="header" class="clearfix">
            <span>分类</span>
          </div>
          <pie-chart :chart-data="pieChart"/>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <el-card class="box-card" style=" margin-bottom: 32px;">
          <div slot="header" class="clearfix">
            <span>标签云</span>
          </div>
          <tag-cloud :data="hotTag" style="height: 400px;width: 80%"/>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <el-card class="box-card" style=" margin-bottom: 32px;">
          <div slot="header" class="clearfix">
            <span>文章阅读量排行</span>
          </div>
          <el-table :data="rank" style="width: 100%;padding-top: 15px;" height="400" max-height="400">
            <el-table-column label="标题">
              <template slot-scope="scope">
                <el-link :underline="false" @click="onClick(scope.row)">{{ scope.row.title }}</el-link>
              </template>
            </el-table-column>
            <el-table-column label="阅读量" prop="pageView" align="center"/>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-card class="box-card" style=" margin-bottom: 32px;">
        <div slot="header" class="clearfix">
          <span>一周访问量</span>
        </div>
        <line-chart :chart-data="lineChartData"/>
      </el-card>
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
import { getWord, getRank, getContribution } from '@/api/dashboard/index'

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
      // 标签云
      hotTag: [],
      // 流量线图
      lineChartData: {
        PVData: [100, 120, 161, 134, 105, 160, 165],
        UVData: [120, 82, 91, 154, 162, 140, 145],
        IPData: [110, 72, 81, 144, 162, 140, 135]
      },
      // 分类饼状图
      pieChart: {},
      // 文章阅读量排行
      rank: [],
      locale: {
        months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        days: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        on: '：',
        less: '少',
        more: '多'
      },
      // 近一年文章贡献度图
      timeValue: [],
      // 最近一年文章贡献
      oneYear: 0,
      // 最长连续创作
      longest: 0,
      // 最近持续持续
      continuous: 0,
      // 获取时间语句
      a: this.greetings(),
      // 随机输出毒鸡汤
      word: ''
    }
  },
  created() {
    this.getWord()
    this.getRank()
    this.getContribution()
  },
  methods: {
    getWord() {
      getWord().then(response => {
        this.word = response.data
      })
    },
    getRank() {
      getRank().then(response => {
        this.pieChart = response.data.category
        this.hotTag = response.data.tags
        this.rank = response.data.rank
      })
    },
    getContribution() {
      getContribution().then(response => {
        this.timeValue = response.data.timeValue
        this.oneYear = response.data.oneYear
        this.longest = response.data.longest
        this.continuous = response.data.continuous
      })
    },
    greetings() {
      const date = new Date()
      if (date.getHours() >= 6 && date.getHours() < 8) {
        return '晨起披衣出草堂，轩窗已自喜微凉🌅！'
      } else if (date.getHours() >= 8 && date.getHours() < 12) {
        return '上午好🌞！'
      } else if (date.getHours() >= 12 && date.getHours() < 18) {
        return '下午好☕！'
      } else if (date.getHours() >= 18 && date.getHours() < 24) {
        return '晚上好🌃！'
      } else if (date.getHours() >= 0 && date.getHours() < 6) {
        return '偷偷向银河要了一把碎星，只等你闭上眼睛撒入你的梦中，晚安🌛！'
      }
    }
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
