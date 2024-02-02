<script>
export default {
  name: 'DiskDetails',
  props: ['diskDetails', 'hardDisk'],
  data() {
    return {}
  },
  mounted() {

  },
  methods: {}
}
</script>

<template>
  <div>
    <el-dialog
      title="容量详情"
      :visible.sync="diskDetails.show"
      width="30%"
    >

      <div class="capacity-manager--9S-ny">
        <div class="used-title--tyIeV">当前已使用</div>
        <div class="precent--fCSWV">
          <span data-over="false">{{ hardDisk.usage }}%</span>
          <span><label data-over="false">{{ hardDisk.used }}</label>/ {{ hardDisk.total }}</span>
        </div>
        <div class="process-container--FjhB-">
          <div
            :style="{ width: hardDisk.markUsage + '%' }"
            style="min-width: 5px; height: 100%; background-color: var(--theme_primary);"
          />
          <div
            :style="{ width: hardDisk.otherUsage + '%'}"
            style="min-width: 5px; height: 100%; background-color: var(--indigo_primary);"
          />
          <!--          <div style="width: 0.0001027%; min-width: 5px; height: 100%; background-color: var(&#45;&#45;seagreen_primary);" />-->
        </div>
        <div class="manager-items--ajHF0">
          <div class="manager-item--mpDRR">
            <div>
              <div style="width: 12px; height: 12px; border-radius: 50%; background: var(--theme_primary);" />
              <div>Markdown图片</div>
            </div>
            <div>{{ hardDisk.markUsed }}</div>
          </div>
          <div class="manager-item--mpDRR">
            <div>
              <div style="width: 12px; height: 12px; border-radius: 50%; background: var(--indigo_primary);" />
              <div>其他</div>
            </div>
            <div>{{ hardDisk.otherUsed }}</div>
          </div>
          <!--          <div class="manager-item&#45;&#45;mpDRR">-->
          <!--            <div>-->
          <!--              <div style="width: 12px; height: 12px; border-radius: 50%; background: var(&#45;&#45;seagreen_primary);" />-->
          <!--              <div>笔记</div>-->
          <!--            </div>-->
          <!--            <div>652.01 KB</div>-->
          <!--          </div>-->
        </div>
        <div />
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>

.manager-items--ajHF0 {
  background: #f5f5f6;
  border-radius: 10px;
  padding: 16px 20px 0;
}

.capacity-manager--9S-ny .precent--fCSWV {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-pack: justify;
  justify-content: space-between;
  -ms-flex-align: end;
  align-items: flex-end;
}

.precent--fCSWV > span:first-child {
  font-weight: 500;
  font-size: 36px;
  line-height: 140%;
  color: var(--context_primary);
}

.capacity-manager--9S-ny .precent--fCSWV > span:last-child {
  font-weight: 400;
  font-size: 14px;
  line-height: 170%;
  color: var(--context_primary);
}

.capacity-manager--9S-ny .process-container--FjhB- {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-direction: row;
  flex-direction: row;
  border-radius: 5px;
  height: 28px;
  background: var(--divider_secondary);
  overflow: hidden;
  margin: 8px 0 28px;
}

.manager-item--mpDRR {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: center;
  align-items: center;
  -ms-flex-pack: justify;
  justify-content: space-between;
  padding-bottom: 16px;
}

.manager-items--ajHF0 .manager-item--mpDRR > div:first-child {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: center;
  align-items: center;
}

.manager-items--ajHF0 .manager-item--mpDRR > div:first-child > div:last-child {
  margin-left: 12px;
  font-weight: 400;
  font-size: 14px;
  line-height: 170%;
  color: var(--context_primary);
}
</style>
