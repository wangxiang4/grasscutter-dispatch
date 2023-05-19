<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册账户</title>
    <#include "*/common/head.ftlh">
</head>
<body>
<div id="app">
    <a-button type="primary">Primary Button</a-button>
</div>
<script>
  Vue.createApp({
    components: {
      // 注册 Ant Design Vue 组件
      'a-button': antd.Button
    },
    setup() {
      const data = Vue.reactive({ message: 'Hello, Vue 3!' })
      axiosInstance.get('/auth/login/get/1510539584287346688').then(resp => {
        console.log(resp)
      }).catch(function (error) {
        console.error(error);
      });
      return {
        data
      }
    }
  }).mount('#app')
</script>
<style>
#app{

}
</style>
</body>
</html>
