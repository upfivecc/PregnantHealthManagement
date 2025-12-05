<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* 引入Font Awesome图标库 */
@import url('https://cdn.bootcdn.net/ajax/libs/font-awesome/6.0.0/css/all.min.css');

/* 全局样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
  font-size: 14px;
  line-height: 1.5;
  color: #303133;
  background-color: #f5f5f5;
}

/* 确保Vue应用根元素正确填充视口 */
#app {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 确保在登录页面中，#app 元素正确居中内容 */
body.login-page #app {
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 登录页面背景样式 */
body.login-page {
  background: linear-gradient(135deg, #4361ee, #3f37c9) !important;
  height: 100vh !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  overflow: hidden !important;
  margin: 0 !important;
  padding: 0 !important;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif !important;
}
</style>