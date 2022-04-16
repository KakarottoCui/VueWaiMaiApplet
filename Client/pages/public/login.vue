<template>
	<view class="container">
		<view class="left-bottom-sign"></view>
		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="left-top-sign">LOGIN</view>
			<view class="welcome">
				欢迎回来！
			</view>
			<view class="input-content">
				<view class="input-item">
					<text class="tit">用户名</text>
					<input 
						type="text" 
						placeholder="请输入用户名"
						maxlength="11"
						v-model="data.name"
					/>
				</view>
				<view class="input-item">
					<text class="tit">密码</text>
					<input 
						type="text"
						v-model="data.pass"
						placeholder="请输入密码"
						placeholder-class="input-empty"
						maxlength="20"
						password 
					/>
				</view>
			</view>
			<button class="confirm-btn" @click="toLogin" :disabled="logining">登录</button>
<!-- 			<view class="forget-section">
				忘记密码?
			</view> -->
		</view>
		<view class="register-section">
			还没有账号?
			<text @click="toRegist">马上注册</text>
		</view>
	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js";
	import {  
        mapMutations  
    } from 'vuex';
	
	export default{
		data(){
			return {
				data:{
					name:"",
					pass:""
				},
				mobile: '',
				password: '',
				logining: false
			}
		},
		onLoad(){
			
		},
		methods: {
			//...mapMutations(['login']),
			inputChange(e){
				const key = e.currentTarget.dataset.key;
				this[key] = e.detail.value;
			},
			navBack(){
				uni.switchTab({
					url:"../index/index"
				});
			},
			toRegist(){
				uni.navigateTo({
					url:"regist"
				})
			},
			toLogin(){
				this.logining = true;
				
				if(!this.data.name || !this.data.pass){
					this.$api.msg('请填写完整');
					this.logining = false;
					return;
				}
				this.data.pass = btoa(this.data.pass);
				let _this = this;
				appRequest.request({
					method: "POST",
					data: {
						data:JSON.stringify(this.data)
					},
					url: appRequest.login,
					success: function(res) {
						let su = false;
						if(res.data.code == 200){
							_this.$api.msg('登录成功');
							
							try {
								uni.setStorageSync('user', res.data.data);
								su = true;
							} catch (e) {
								console.log(JSON.stringify(e));
								uni.clearStorage();
								_this.logining = false;
								_this.data.pass = "";
							}	
						}else{
							_this.$api.msg(res.data.msg);
							_this.logining = false;
							_this.data.pass = "";
						}
						if(su){
							uni.switchTab({
								url:"../index/index"
							});
						}
						
						
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
						_this.logining = false;
						_this.data.pass = "";
					}
				})

			}
		},

	}
</script>

<style lang='scss'>
	page{
		background: #fff;
	}
	.container{
		padding-top: 115px;
		position:relative;
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #fff;
	}
	.wrapper{
		position:relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;
	}
	.back-btn{
		position:absolute;
		left: 40upx;
		z-index: 9999;
		padding-top: var(--status-bar-height);
		top: 40upx;
		font-size: 40upx;
		color: $font-color-dark;
	}
	.left-top-sign{
		font-size: 120upx;
		color: $page-color-base;
		position:relative;
		left: -16upx;
	}
	.right-top-sign{
		position:absolute;
		top: 80upx;
		right: -30upx;
		z-index: 95;
		&:before, &:after{
			display:block;
			content:"";
			width: 400upx;
			height: 80upx;
			background: #b4f3e2;
		}
		&:before{
			transform: rotate(50deg);
			border-radius: 0 50px 0 0;
		}
		&:after{
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}
	.left-bottom-sign{
		position:absolute;
		left: -270upx;
		bottom: -320upx;
		border: 100upx solid #d0d1fd;
		border-radius: 50%;
		padding: 180upx;
	}
	.welcome{
		position:relative;
		left: 50upx;
		top: -90upx;
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0,0,0,.3);
	}
	.input-content{
		padding: 0 60upx;
	}
	.input-item{
		display:flex;
		flex-direction: column;
		align-items:flex-start;
		justify-content: center;
		padding: 0 30upx;
		background:$page-color-light;
		height: 120upx;
		border-radius: 4px;
		margin-bottom: 50upx;
		&:last-child{
			margin-bottom: 0;
		}
		.tit{
			height: 50upx;
			line-height: 56upx;
			font-size: $font-sm+2upx;
			color: $font-color-base;
		}
		input{
			height: 60upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			width: 100%;
		}	
	}

	.confirm-btn{
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		background: $uni-color-primary;
		color: #fff;
		font-size: $font-lg;
		&:after{
			border-radius: 100px;
		}
	}
	.forget-section{
		font-size: $font-sm+2upx;
		color: $font-color-spec;
		text-align: center;
		margin-top: 40upx;
	}
	.register-section{
		position:absolute;
		left: 0;
		bottom: 50upx;
		width: 100%;
		font-size: $font-sm+2upx;
		color: $font-color-base;
		text-align: center;
		text{
			color: $font-color-spec;
			margin-left: 10upx;
		}
	}
</style>
