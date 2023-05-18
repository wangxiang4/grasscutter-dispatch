<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册账户</title>
    <#include "*/common/head.ftlh">
</head>
<body>
<div id="app">
    <div class="container mx-auto px-14 object-center">
        <div class="grid grid-rows-5 gap-3 grid-flow-col mt-32">
            <input class="bg-blue-500 rounded" type="username" placeholder="username"/>
            <input class="bg-blue-500 rounded" type="password" placeholder="password"/>
            <button class="bg-blue-500 rounded h-10">登陆</button>
        </div>
    </div>
</div>
<script>
  Vue.createApp({
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
</body>
</html>
