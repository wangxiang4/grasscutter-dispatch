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
					>
						<template #suffix>
							<a-divider type="vertical"></a-divider>
							<a-button v-if="data.timerRuning" class="w-16" type="link">{{data.timeInterval}}</a-button>
							<a-button v-else type="link" class="w-16" @click="handleGetMailCaptcha">发送</a-button>
						</template>
					</a-input>
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
					<a-checkbox v-model:checked="">阅读并同意</a-checkbox>
					<a @click="() => { data.privacyVisible = true }">用户协议、隐私政策</a>
				</a-form-item>
				<a-form-item v-bind="formTailLayout">
					<a-button type="primary" class="w-full" size="large" @click="onCheck">注册</a-button>
				</a-form-item>
			</a-form>
		</a-content>
	</a-layout>
	<a-modal v-model:visible="data.privacyVisible"
					 width="620px"
					 title="隐私政策"
					 :footer="false"
	>
		<#include "*/common/policy.ftlh">
	</a-modal>
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
	    ACheckbox: antd.Checkbox,
			AModal: antd.Modal,
			ATypography: antd.Typography,
			ATypographyParagraph: antd.Typography.Paragraph,
			ATypographyText: antd.Typography.Text,
			ADivider: antd.Divider
    },
    setup() {
      const data = Vue.reactive({
        formData: {
          account: ''
        },
				privacyVisible: false,
				timeInterval: 60,
				timer: null,
				timerRuning: false
      })

			async function handleGetMailCaptcha() {
				const TIME_COUNT = 60;
				if (!data.timer) {
					data.timeInterval = TIME_COUNT;
					data.timerRuning = true;
					data.timer = setInterval(() => {
						if (data.timeInterval > 0 && data.timeInterval <= TIME_COUNT) {
							data.timeInterval = data.timeInterval - 1;
						} else {
							data.timerRuning = false;
							clearInterval(data.timer);
							data.timer = null;
						}
					}, 1000);
				}
			}

      axiosInstance.get('').then(resp => {
        console.log(resp)
      }).catch(function (error) {
        console.error(error);
      });

      return {
        data,
				handleGetMailCaptcha
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
