<template>
  <div class="login">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
    <div class="loginPart">
      <h2>用户登录</h2>
        <el-form>
          <div class="inputElement">
            <el-input v-model="username" placeholder="请输入用户名/手机号 "></el-input>
          </div>
          <div class="inputElement">
            <el-input v-model="password" placeholder="请输入密码 "></el-input>
          </div>
          <div>
            <el-button type="primary" >登录</el-button>
          </div>
          <div style="text-align: right;color: white;">
            <el-link type="warning">没有账号？去注册</el-link>
          </div>
        </el-form>
      </div>
  </div>
</template>

<script>
export default {
  name: "test",
  data() {
    return {
      imgSrc: require('@/assets/bg.jpg'),
      username: '',
      password: ''
    }
  }
}
</script>


<style scoped>
.loginPart{
  position:absolute;
  /*定位方式绝对定位absolute*/
  top:50%;
  left:50%;
  /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
  transform:translate(-50%,-50%);
  /*实现块元素百分比下居中*/
  width:450px;
  padding:50px;
  background: rgba(0,0,0,.5);
  /*背景颜色为黑色，透明度为0.8*/
  box-sizing:border-box;
  /*box-sizing设置盒子模型的解析模式为怪异盒模型，
  将border和padding划归到width范围内*/
  box-shadow: 0px 15px 25px rgba(0,0,0,.5);
  /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
  border-radius:15px;
  /*边框圆角，四个角均为15px*/
}
.loginPart h2{
  margin:0 0 30px;
  padding:0;
  color: #fff;
  text-align:center;
  /*文字居中*/
}
.loginPart .inputbox{
  position:relative;
}
.loginPart .inputElement input{
  width: 100%;
  padding:10px 0;
  font-size:16px;
  color:#fff;
  letter-spacing: 1px;
  /*字符间的间距1px*/
  margin-bottom: 30px;
  border:none;
  border-bottom: 1px solid #fff;
  outline:none;
  /*outline用于绘制元素周围的线
  outline：none在这里用途是将输入框的边框的线条使其消失*/
  background: transparent;
  /*背景颜色为透明*/
}

.login{
  width:100%;
  height:100%;
}
</style>
