<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册账户</title>
	<#include "*/common/head.ftlh">
</head>
<body>
<div id="app">
	<a-layout class="mt-12">
		<a-header>
			<div class="grid place-items-center">
				<a href="https://casks.me">
					<img src="/image/hoyo.png" alt="米哈游原神">
				</a>
			</div>
		</a-header>
		<a-content class="mt-12 mx-20">
			<a-form ref="formRef"
							:model="data.formData"
			>
				<a-form-item>
					<a-input v-model:value="data.formData.account"
					         size="large"
					         placeholder="邮箱"
					/>
				</a-form-item>
				<a-form-item>
					<a-input v-model:value="data.formData.account"
					         size="large"
					         placeholder="验证码"
					/>
				</a-form-item>
				<a-form-item>
					<a-input v-model:value="data.formData.account"
					         size="large"
					         placeholder="密码为8-30位（不能全是字母或数字）"
					/>
				</a-form-item>
				<a-form-item>
					<a-input v-model:value="data.formData.account"
					         size="large"
					         placeholder="确认密码"
					/>
				</a-form-item>
				<a-form-item name="checkNick" v-bind="formTailLayout">
					<a-checkbox v-model:checked="">阅读并同意 <a>用户协议、隐私政策</a></a-checkbox>
				</a-form-item>
				<a-form-item v-bind="formTailLayout">
					<a-button type="primary" class="w-full" size="large" @click="onCheck">注册</a-button>
				</a-form-item>
			</a-form>
		</a-content>
	</a-layout>
</div>
<script>
  Vue.createApp({
    components: {
      ALayout: antd.Layout,
      AHeader: antd.Layout.Header,
      AContent: antd.Layout.Content,
      AButton: antd.Button,
      AForm: antd.Form,
      AFormItem: antd.FormItem,
      AInput: antd.Input,
	    ACheckbox: antd.Checkbox
    },
    setup() {
      const data = Vue.reactive({
        formData: {
          account: ''
        }
      })

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
#app {

}

.ant-layout {
	background: #ffffff;
}

.ant-layout-header {
	background: #ffffff;
}

.ant-input {
	border-radius: 5px;
}

.ant-btn-lg {
	border-radius: 5px;
	height: 50px;
	font-size: 18px;
}

.ant-form-item {
	margin: 0 0 20px;
}
</style>
</body>
</html>
