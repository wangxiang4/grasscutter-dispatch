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
			<a-form ref="formElRef"
							:model="state.modelRef"
							:rules="state.rulesRef"
							:scrollToFirstError="true"
							@keypress.enter="handleSubmit"
			>
				<a-form-item name="email">
					<a-input v-model:value="state.modelRef.email"
					         size="large"
					         placeholder="邮箱"
					/>
				</a-form-item>
				<a-form-item name="code">
					<a-input v-model:value="state.modelRef.code"
					         size="large"
					         placeholder="验证码"
					>
						<template #suffix>
							<a-divider type="vertical"></a-divider>
							<a-button v-if="state.timerRuning" class="w-16" type="link">{{state.timeInterval}}</a-button>
							<a-button v-else type="link" class="w-16" @click="handleGetMailCaptcha">发送</a-button>
						</template>
					</a-input>
				</a-form-item>
				<a-form-item name="password">
					<a-input-password v-model:value="state.modelRef.password"
					         size="large"
					         placeholder="密码为8-30位（不能全是字母或数字）"
					/>
				</a-form-item>
				<a-form-item name="confirmPassword">
					<a-input-password v-model:value="state.modelRef.confirmPassword"
					         size="large"
					         placeholder="确认密码"
					/>
				</a-form-item>
				<a-form-item name="checkPolicy">
					<a-checkbox v-model:checked="state.modelRef.checkPolicy">阅读并同意</a-checkbox>
					<a @click="() => { state.privacyVisible = true }">用户协议、隐私政策</a>
				</a-form-item>
				<a-form-item>
					<a-button type="primary" class="w-full" size="large" @click="handleSubmit">注册</a-button>
				</a-form-item>
			</a-form>
		</a-content>
	</a-layout>
	<a-modal v-model:visible="state.privacyVisible"
					 width="620px"
					 title="隐私政策"
					 :footer="false"
	><#include "*/common/policy.ftlh"></a-modal>
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
			AInputPassword: antd.InputPassword,
	    ACheckbox: antd.Checkbox,
			AModal: antd.Modal,
			ATypography: antd.Typography,
			ATypographyParagraph: antd.Typography.Paragraph,
			ATypographyText: antd.Typography.Text,
			ADivider: antd.Divider
    },
    setup() {
			const formElRef = Vue.ref();
			const message =	antd.message
			message.config({
				top: '30vh',
				duration: 2,
				maxCount: 1,
			})
      const state = Vue.reactive({
				privacyVisible: false,
				modelRef: {
					email: undefined,
					code: undefined,
					password: undefined,
					confirmPassword: undefined,
					checkPolicy: undefined,
        },
				rulesRef: {
					email: [
						{ required: true, whitespace: true, type: 'email',  message: '邮箱格式不正确 🥺' }
					],
					code: [
						{ required: true, whitespace: true, message: '验证码必填 😮' },
						{ pattern: RegExp('^\\d{6}$'), message: '验证码格式不正确 😤' }
					],
					password: [
						{ required: true, whitespace: true, pattern: RegExp('^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,30}$'), message: '密码格式不正确 😰' }
					],
					confirmPassword: [
						{ required: true, whitespace: true, message: '确认密码必填 🥱' }
					]
				},
				timeInterval: 60,
				timer: null,
				timerRuning: false
      })

			async function handleGetMailCaptcha() {
				await formElRef.value.validate(['email']).catch((err)=> {
					message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: '邮箱格式不正确',
					})
					throw err
				})

				axiosInstance.get('').then(resp => {
					console.log(resp)
				}).catch(function (error) {
					console.error(error)
				})

				const TIME_COUNT = 60
				if (!state.timer) {
					state.timeInterval = TIME_COUNT
					state.timerRuning = true
					state.timer = setInterval(() => {
						if (state.timeInterval > 0 && state.timeInterval <= TIME_COUNT) {
							state.timeInterval = state.timeInterval - 1
						} else {
							state.timerRuning = false
							clearInterval(state.timer)
							state.timer = null
						}
					}, 1000)
				}
			}

			async function handleLogin() {
				/*const data = await validForm();
				if (!data) return;
				try {
					loading.value = true;
					const userInfo = await userStore.login(toRaw({
						password: data.password,
						username: data.account,
						realKey: data.realKey,
						code: data.code,
					}));
					if (userInfo) {

					}
				} catch (error){
					formData.code='';
					formData.realKey='';
					await getCode();
				} finally {
					loading.value = false;
				}*/
			}

      return {
				state,
				formElRef,
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

.ant-input-affix-wrapper {
	border-radius: 5px;
}

.ant-btn-lg {
	border-radius: 5px;
	height: 50px;
	font-size: 18px;
}

.ant-form-item {
	margin: 0 0 18px;
}

.ant-message-notice-content {
	color: #FFFFFF;
	background: #000000A6;
	border-radius: 5px;
}
</style>
</body>
</html>
