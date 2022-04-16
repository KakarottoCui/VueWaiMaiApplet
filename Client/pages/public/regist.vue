<template>
	<view class="container">
		<view class="left-bottom-sign"></view>
		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="left-top-sign">Rsgist</view>
			<view class="welcome">
				欢迎注册
			</view>
			<view class="input-content">
				<view class="input-item">
					<text class="tit">用户名</text>
					<input 
						type="text" 
						v-model="user.name" 
						placeholder="请输入用户名"
						maxlength="11"
					/>
				</view>
				<view class="input-item">
					<text class="tit">密码</text>
					<input 
						type="password"
						placeholder="输入密码"
						placeholder-class="input-empty"
						maxlength="20"
						password 
						v-model="pass"
					/>
				</view>
				<view class="input-item">
					<text class="tit">重复密码</text>
					<input 
						type="text"
						placeholder="重复输入密码"
						placeholder-class="input-empty"
						maxlength="20"
						password 
						v-model="pass2"
					/>
				</view>
				<view class="input-item">
					<text class="tit">注册类型</text>
					<view>
						<label class="radio" style="margin-right: 50rpx;"><radio value="r1" :checked="user.type==1"  @click="user.type=1" />消费者</label>
						<label class="radio"><radio value="r2"  :checked="user.type==2" @click="user.type=2" />商家</label>
					</view>
				</view>
				<view class="input-item" v-if="user.type == 2">
					<text class="tit">店铺名称</text>
					<input 
						type="text"
						placeholder="商家注册请输入店铺名称"
						placeholder-class="input-empty"
						maxlength="20" 
						v-model="user.memo"
					/>
				</view>
				
				<view class="input-item">
					<text class="tit">电话</text>
					<input 
						type="number"
						placeholder="请输入电话"
						placeholder-class="input-empty"
						maxlength="11" 
						v-model="user.phone"
					/>
				</view>
				
				<view class="input-item" v-if="user.type == 1">
					<text class="tit">收货地址</text>
					<input 
						type="text"
						placeholder="请输入收货地址"
						placeholder-class="input-empty"
						maxlength="50" 
						v-model="user.address"
					/>
				</view>
				
			</view>
			<button class="confirm-btn" @click="toLogin" :disabled="logining">注册</button>
		</view>
	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	import {  
        mapMutations  
    } from 'vuex';
	
	export default{
		data(){
			return {
				user:{
					name:"",
					pass:"",
					type:1,
					memo:"",
					phone:"",
					address:""
				},
				pass:"",
				pass2:"",
				logining: false,
			}
		},
		onLoad(){
			
		},
		methods: {
			
			radioChange(evt){
				this.current = evt.detail.value;
			},
			...mapMutations(['login']),
			navBack(){
				uni.navigateBack();
			},
			toLogin(){
				this.logining = true;
				
				if(!this.user.name || !this.pass || !this.pass2 || !this.user.phone || ( this.user.type == 1 && !this.user.address )){
					this.$api.msg('请填写完整');
					this.logining = false;
					return;
				}
				
				if(this.user.phone.length != 11){
					this.$api.msg('请填写正确的手机号');
					this.logining = false;
					return;
				}
				
				if(!this.user.memo && this.user.type == 2 ){
					this.$api.msg('注册商家请填写店铺名称');
					this.logining = false;
					return;
				}
				
				if(this.pass != this.pass2){
					this.$api.msg('密码不一致');
					this.logining = false;
					return;
				}
				this.user.pass = btoa(this.pass);
				if(this.user.type == 1){
					this.user.memo = "";
				}else{
					this.user.address = "";
				}
				let _this = this;
				appRequest.request({
					method: "POST",
					data: {
						data:JSON.stringify(_this.user)  
					},
					url: appRequest.regist,
					success: function(res) {
						
						if(res.data.code == 200){
							uni.showModal({
								title:"注册成功",
								content:"用户注册成功，将跳转登录页面，登录后可使用全部功能",
								showCancel:false,
								confirmText:"跳转登录",
								success:function(res){
									if(res.confirm){
										uni.redirectTo({
											url:"login"
										});
									}
								}
							});
						}else{
							_this.$api.msg(res.data.msg);
							_this.logining = false;
						}
				
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
						_this.logining = false;
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
		padding-top: 20px;
		position:relative;
		min-width: 100vw;
		min-height: 100vh;
		background: #fff;
	}
	.wrapper{
		position:relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 20upx;
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
