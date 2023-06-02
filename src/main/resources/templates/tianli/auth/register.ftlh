<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册账户</title>
	<#include "*/common/head.ftlh">
</head>
<body>
<div id="app">
	<a-layout v-if="!state.done" class="mt-12">
		<a-header>
			<div class="grid place-items-center">
				<a href="https://mihoyo.com">
					<img src="/image/mihoyo.png" alt="米哈游原神">
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
				<a-form-item name="username">
					<a-input v-model:value="state.modelRef.username"
									 size="large"
									 placeholder="用户名"
					/>
				</a-form-item>
				<a-form-item name="email">
					<a-input v-model:value="state.modelRef.email"
					         size="large"
					         placeholder="绑定邮箱（可选）"
					/>
				</a-form-item>
				<a-form-item name="code">
					<a-input v-model:value="state.modelRef.code"
					         size="large"
					         placeholder="验证码（可选）"
					>
						<template #suffix>
							<a-divider type="vertical"></a-divider>
							<a-button v-if="state.timerRuning" class="w-16" type="link">{{state.timeInterval}}</a-button>
							<a-button v-else :loading="state.captchaSendLoading" type="link" class="w-16" @click="handleGetMailCaptcha">{{ state.captchaSendLoading ? '' : '发送' }}</a-button>
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
					<a @click="() => state.privacyVisible = true">用户协议、隐私政策</a>
				</a-form-item>
				<a-form-item>
					<a-button type="primary" class="w-full" size="large" @click="handleSubmit">注册</a-button>
				</a-form-item>
			</a-form>
		</a-content>
	</a-layout>
	<a-result v-else title="注册成功 🥳 可返回登陆页面进行登陆 🤥">
		<template #icon>
			<i class="fa-solid fa-id-card text-8xl" style="color: #1890ff"/>
		</template>
		<template #extra>
			<a-button type="primary" @click="handleForwardingLogin">返回登陆</a-button>
		</template>
	</a-result>
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
			ADivider: antd.Divider,
			AResult: antd.Result
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
				loading: false,
				privacyVisible: false,
				modelRef: {
					username: undefined,
					email: undefined,
					code: undefined,
					password: undefined,
					confirmPassword: undefined,
					checkPolicy: undefined,
        },
				rulesRef: {
					username: [
						{ required: true, whitespace: true,  message: '用户名必填 🥴' },
						{ max: 60, message: '用户名太长啦 🤯' },
						{
							validator: async (rule, value) => {
								if (/^(?!null$|undefined$|^$).+/.test(value)) {
									try {
										const { data } = await axiosInstance.post('getAccountByUsername', null, { params: { username: value } })
										if(data) return Promise.reject('该用户名已存在 😮‍💨');
									} catch (e) {}
								}
								return Promise.resolve();
							},
							trigger: 'blur'
						}
					],
					email: [
						{ type: 'email',  message: '邮箱格式不正确 😱' }
					],
					code: [
						{ pattern: RegExp('^\\d{6}$'), message: '验证码格式不正确 🤕' }
					],
					password: [
						{ required: true, pattern: RegExp('^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,30}$'), message: '密码格式不正确 😰' }
					],
					confirmPassword: [
						{ required: true, whitespace: true, message: '确认密码必填 🥱' }
					]
				},
				timeInterval: 60,
				timer: null,
				timerRuning: false,
				captchaSendLoading: false,
				done: false,
      })

			async function handleGetMailCaptcha() {
				const { email } = await formElRef.value.validate(['email']).catch((err)=> {
					message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: '邮箱格式不正确',
					})
					throw err
				})
				if (!/^(?!null$|undefined$|^$).+/.test(email)) {
					return message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: '请先填写邮箱',
					})
				}
				try {
					state.captchaSendLoading = true;
					const result = await axiosInstance.post('sendMailCaptcha', null, { params: { recipient: email } })
					if (result.code == 200) {
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
					} else message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: result.msg,
					})
				} finally {
					state.captchaSendLoading = false;
				}
			}

			async function handleSubmit() {
				const formData = await formElRef.value.validate();
				try {
					state.loading = true;
					const result = await axiosInstance.post('sendMailCaptcha', formData)
					if (result.code == 200) {
						const account = result.data
						state.privacyVisible = false
						state.done = true
						console.log('注册成功你的账户信息是:', JSON.stringify(account))
					} else message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: result.msg || '注册失败',
					})
				} finally {
					state.loading = false;
				}
			}

			function handleForwardingLogin () {
				window.history.go(-1)
			}

      return {
				state,
				formElRef,
				handleGetMailCaptcha,
				handleSubmit,
				handleForwardingLogin
      }
    }
  }).mount('#app')
</script>
<style>
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
