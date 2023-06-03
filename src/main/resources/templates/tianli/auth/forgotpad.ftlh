<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>找回密码</title>
	<#include "*/common/head.ftlh">
</head>
<body>
<div id="app">
	<a-layout v-if="state.done != 2" class="mt-12">
		<a-header>
			<div class="grid place-items-center">
				<a href="https://mihoyo.com">
					<img src="/image/mihoyo.png" alt="米哈游原神">
				</a>
			</div>
		</a-header>
		<a-content class="mt-12 mx-20">
			<a-form v-if="state.done == 0" ref="formMailElRef"
							:model="state.modelMailRef"
							:rules="state.ruleMailRef"
							:scrollToFirstError="true"
							@keypress.enter="handleMailSubmit"
			>
				<a-form-item name="email">
					<a-input v-model:value="state.modelMailRef.email"
									 size="large"
									 placeholder="邮箱"
					/>
				</a-form-item>
				<a-form-item name="code">
					<a-input v-model:value="state.modelMailRef.code"
									 size="large"
									 placeholder="验证码"
					>
						<template #suffix>
							<a-divider type="vertical"></a-divider>
							<a-button v-if="state.timerRuning" class="w-16" type="link">{{state.timeInterval}}</a-button>
							<a-button v-else :loading="state.captchaSendLoading" type="link" class="w-16" @click="handleGetMailCaptcha">{{ state.captchaSendLoading ? '' : '发送' }}</a-button>
						</template>
					</a-input>
				</a-form-item>
				<a-form-item>
					<a-button type="primary" class="w-full" size="large" :loading="state.loading" @click="handleMailSubmit">下一步</a-button>
				</a-form-item>
			</a-form>
			<a-form v-if="state.done == 1" ref="formForgotpadElRef"
							:model="state.modelForgotpadRef"
							:rules="state.ruleForgotpadRef"
							:scrollToFirstError="true"
							@keypress.enter="handleForgotpadSubmit"
			>
				<a-form-item name="username">
					<a-select v-model:value="state.modelForgotpadRef.username"
										size="large"
										not-found-content="当前邮箱未绑定账户请绑定后在操作"
										:show-arrow="false"
										:options="state.accountList"
										placeholder="选择用户名"
										:field-names="{ label: 'username', key: 'username' }"
					/>
				</a-form-item>
				<a-form-item name="password">
					<a-input-password v-model:value="state.modelForgotpadRef.password"
														size="large"
														placeholder="密码为8-30位（不能全是字母或数字）"
					/>
				</a-form-item>
				<a-form-item name="confirmPassword">
					<a-input-password v-model:value="state.modelForgotpadRef.confirmPassword"
														size="large"
														placeholder="确认密码"
					/>
				</a-form-item>
				<a-form-item>
					<a-button type="primary" class="w-full" size="large" :loading="state.loading" @click="handleForgotpadSubmit">重新设置密码</a-button>
				</a-form-item>
			</a-form>
		</a-content>
	</a-layout>
	<a-result v-else title="密码修改成功 🥳 可返回登陆页面进行登陆 🤥">
		<template #icon>
			<i class="fa-solid fa-shield-cat text-8xl" style="color: #1890ff"/>
		</template>
		<template #extra>
			<a-button type="primary" @click="handleForwardingLogin">返回登陆</a-button>
		</template>
	</a-result>
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
			ADivider: antd.Divider,
			ASelect: antd.Select,
			AResult: antd.Result
		},
		setup() {
			const formMailElRef = Vue.ref();
			const formForgotpadElRef = Vue.ref();
			const message =	antd.message
			message.config({
				top: '30vh',
				duration: 2,
				maxCount: 1,
			})
			const state = Vue.reactive({
				loading: false,
				captchaSendLoading: false,
				modelMailRef: {
					email: undefined,
					code: undefined,
				},
				modelForgotpadRef: {
					username: undefined,
					email: undefined,
					password: undefined,
					confirmPassword: undefined,
					forgotpadToken: undefined,
				},
				ruleMailRef: {
					email: [
						{ required: true, type: 'email',  message: '邮箱格式不正确 😱' }
					],
					code: [
						{ required: true, pattern: RegExp('^\\d{6}$'), message: '验证码格式不正确 🤕' }
					],
				},
				ruleForgotpadRef: {
					username: [
						{ required: true, whitespace: true,  message: '用户名必选 🥴' },
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
				done: 0,
				accountList: []
			})

			async function handleGetMailCaptcha() {
				const { email } = await formMailElRef.value.validate(['email']).catch((err)=> {
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

			async function handleMailSubmit() {
				const formData = await formMailElRef.value.validate();
				try {
					state.loading = true;
					const result = await axiosInstance.post('getForgotpadToken', formData)
					if (result.code == 200) {
						const { data } = result
						state.modelForgotpadRef = data.result || {}
						state.accountList = data.extend || []
						state.modelForgotpadRef.username = state.accountList[0]?.username
						state.done = 1
					} else message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: result.msg || '邮箱验证失败',
					})
				} finally {
					state.loading = false;
				}
			}

			async function handleForgotpadSubmit() {
				const formData = await formForgotpadElRef.value.validate();
				formData.email = state.modelForgotpadRef.email
				formData.forgotpadToken = state.modelForgotpadRef.forgotpadToken
				if (state.modelForgotpadRef.password != state.modelForgotpadRef.confirmPassword) return message.open({
					icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
					content: '两次密码输入不一致',
				})
				try {
					state.loading = true;
					const result = await axiosInstance.post('resetPassword', formData)
					if (result.code == 200)
						state.done = 2
					else message.open({
						icon: Vue.h('i', { class: 'fa-regular fa-face-sad-tear mr-1' }),
						content: result.msg || '重置密码失败',
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
				formMailElRef,
				formForgotpadElRef,
				handleGetMailCaptcha,
				handleMailSubmit,
				handleForgotpadSubmit,
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

	.ant-select:not(.ant-select-customize-input) .ant-select-selector {
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
