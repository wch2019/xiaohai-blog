<script>
export default {
  name: 'DiskDetails',
  props: ['diskDetails', 'hardDisk'],
  data() {
    return {}
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
            <el-progress
              type="circle"
              :percentage="hardDisk.usage"
              :stroke-width="30"
              width="150"
              :show-text="true"
              color="#6f7ad3"
            />
            <span style="margin-top:10px;font-size: 15px;color: #999;">{{ hardDisk.used }} / {{ hardDisk.total }}</span>
          </div>
        </div>
        <div style="width:45%;margin-left: 5%;">
          <h4>Markdown图片： {{ hardDisk.markUsed }}</h4>
          <h4>其他：{{ hardDisk.otherUsed }}</h4>
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

::v-deep .el-progress__text {
  font-size: 14px !important;
}
</style>
