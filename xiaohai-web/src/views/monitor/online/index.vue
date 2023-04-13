<template>
  <div class="app-container">
    <aside>
      当前在线用户数  {{ total }} <br>
    </aside>

    <el-table
      v-loading="loading"
      border
      style="margin-top: 10px"
      :data="onLineUserList"
    >
      <el-table-column label="用户名" align="center" prop="username" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column label="登录ip" align="center" prop="loginIp" />
      <el-table-column label="ip来源" align="center" prop="loginSource" />
      <el-table-column label="浏览器" align="center" prop="loginBrowser" />
      <el-table-column label="操作系统" align="center" prop="loginOs" />
      <el-table-column label="登录次数" align="center" prop="loginCount">
        <template slot-scope="scope">
          <el-tag type="success"> {{ scope.row.loginCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="有效时间(秒)" align="center" prop="time">
        <template slot-scope="scope">
          <el-tag type="warning"> {{ scope.row.time }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="登录时间" align="center" prop="loginDate" />
      <el-table-column label="过期时间" align="center" prop="logoutDate" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="$store.getters.permission.includes('monitor:online:delete')"
            size="mini"
            type="danger"
            plain
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >退出
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>

import { listOnLineUser, kickOut } from '@/api/monitor/online'

export default {
  name: 'Index',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 标签表格数据
      onLineUserList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询标签数据列表 */
    getList() {
      this.loading = true
      listOnLineUser(this.queryParams).then(response => {
        this.onLineUserList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id
      this.$confirm('是否确认退出"' + row.username + '"用户？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        kickOut(ids).then(response => {
          this.$message.success(response.msg)
        })
        this.getList()
      }).catch(() => {
        this.$message.info('已取消退出')
      })
    }
  }
}
</script>
