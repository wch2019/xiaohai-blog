<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div style="padding: 3px 0;float: right;">
          <el-radio-group v-model="form.path" size="medium" @change="getList">
            <el-radio-button label="/">全部</el-radio-button>
            <el-radio-button label="/image/">图片</el-radio-button>
            <el-radio-button label="/avatar/">头像</el-radio-button>
          </el-radio-group>
        </div>
        <el-page-header style="padding: 10px 0;" :content="form.path" @back="goBack" />
      </div>
      <el-table :data="fileList" style="width: 100%" @row-dblclick="handle">
        <el-table-column prop="name" label="名称">
          <template slot-scope="scope">
            <i v-if="!scope.row.nameSuffix" class="el-icon-folder-opened" />
            <i v-else-if="picture(scope.row.nameSuffix)" class="el-icon-picture" />
            <i v-else class="el-icon-document" />

            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" width="180" />
        <el-table-column prop="updateTime" label="更新时间" align="center" width="180" />
        <el-table-column prop="nameSuffix" label="类型" align="center" width="100" />
        <el-table-column prop="size" label="文件大小" align="center" width="100" />
      </el-table>
      <!--      <div v-if="fileList.length==0">-->
      <!--        空屏展示-->
      <!--      </div>-->
      <!--      <el-col-->
      <!--        v-for="(o, index) in fileList"-->
      <!--        :key="o"-->
      <!--        :span="3"-->
      <!--        :offset="index/6 < 1 && index>6 ? 0 : 1"-->
      <!--        style="margin-bottom: 20px;height: 220px"-->
      <!--      >-->
      <!--        <el-tooltip :content="o.name" placement="top">-->
      <!--          <el-card :body-style="{ padding: '0px'}">-->
      <!--            <img v-if="o.nameSuffix" :src="trimmedValue(o.name)" class="image">-->
      <!--            <img v-else src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">-->
      <!--            <div style="padding: 14px; text-align: center">-->
      <!--              <span>{{ stateFormat(o.name) }}</span>-->
      <!--            </div>-->
      <!--          </el-card>-->
      <!--        </el-tooltip>-->
      <!--      </el-col>-->
    </el-card>
  </div>
</template>

<script>
import { getFile } from '@/api/file/file'

export default {
  name: 'Index',
  data() {
    return {
      form: {
        path: '/'
      },
      fileList: []
    }
  },
  created() {
    this.getList('/')
  },
  methods: {
    getList(path) {
      this.form.path = path
      getFile(this.form).then(response => {
        this.fileList = response.data
      })
    },
    trimmedValue(inputValue) {
      return 'http://192.168.32.1:8089/api/file/' + inputValue
    },
    // 内容过长隐藏展示
    stateFormat(name) {
      if (!name) return ''
      if (name.length > 18) { // 超过长度10的内容隐藏
        return name.slice(0, 18) + '...'
      }
      return name
    },
    // 验证是否是图片类型
    picture(name) {
      const acceptedImageTypes = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
      if (acceptedImageTypes.indexOf(name) === -1) {
        return false
      }
      return true
    },
    // 双击行
    handle(row, column, event, cell) {
      if (!row.nameSuffix) {
        this.getList(row.path)
      }
      if (this.picture(row.nameSuffix)) {
        return 'http://192.168.32.1:8089/api/file/' + row.path
      }
    },
    goBack() {
      const key = this.form.path
      console.log(key)
      const pathArray = key.split('/') // 将路径按照斜杠分割成数组
      pathArray.pop() // 删除数组最后一个元素（即最后一个斜杠）
      let path = '/'
      for (let i = 0; i < pathArray.length; i++) {
        if (pathArray[i]) {
          path += pathArray[i] + '/'
        }
      }
      console.log(path.substring(0, path.length - 1))
      this.getList(path.substring(0, path.length - 1))
    }
  }
}
</script>

<style scoped>

.image {
  width: 100%;
  height: 100px;
  display: block;
}
</style>
