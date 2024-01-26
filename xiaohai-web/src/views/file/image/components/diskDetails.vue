<script >
export default {
  name: 'DiskDetails',
  props: ['diskDetails'],
  data() {
    return {

    }
  },
  mounted() {

  },
  methods: {

  }
}
</script>

<template>
  <div>
    <el-dialog
      title="容量详情"
      :visible.sync="diskDetails.show"
      width="30%"
    >

      <div style="display: flex;justify-content: flex-start; overflow-y: auto;">
        <div style="object-fit: contain; width: 40%;">
          <div style="display: flex;justify-content: space-between;flex-direction: column;align-items: center;">
            <el-progress type="circle" :percentage="50" :stroke-width="30" width="150" :show-text="true" color="#6f7ad3"></el-progress>
            <span style="margin-top:10px;font-size: 15px;color: #999;">100 GB / 200 GB</span>
          </div>
        </div>
        <div style="width:45%;margin-left: 5%;">
          <h4>Markdown图片：  50GB</h4>
          <el-progress :percentage="25" :stroke-width="10" :show-text="true" color="#6f7ad3"></el-progress>
          <h4>其他：50GB</h4>
          <el-progress :percentage="25" :stroke-width="10" :show-text="true" color="#6f7ad3"></el-progress>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.stacked-progress {
  position: relative;
  height: 100px; /* 根据需要调整高度 */
}

.stacked-progress-child {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}
</style>
