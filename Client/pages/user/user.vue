<template>  
    <view class="container">  
		
		<view class="user-section">
			<image class="bg" src="/static/user-bg.jpg"></image>
			<view class="user-info-box">
				<view class="portrait-box">
					<image style="background-color: white;" class="portrait" :src="getHead()"></image>
				</view>
				<view class="info-box">
					<text class="username">{{user.name || '游客'}}</text>
				</view>
			</view>
			<!-- <view class="vip-card-box">
				<image class="card-bg" src="/static/vip-card-bg.png" mode=""></image>
				<view class="b-btn">
					立即开通
				</view>
				<view class="tit">
					<text class="yticon icon-iLinkapp-"></text>
					DCloud会员
				</view>
				<text class="e-m">DCloud Union</text>
				<text class="e-b">开通会员开发无bug 一测就上线</text>
			</view> -->
		</view>
		
		
		<view class="u-flex u-direction-row u-row-center" v-if="!user">
			<view class="container">
				<view class="list-cell " style="text-align: center;">
					<text class="cell-tit" style="color: #909399;">游客无法享受购物的快乐</text>
				</view>
				<view class="list-cell log-out-btn" @click="toLogin">
					<text class="cell-tit">前往登录</text>
				</view>
			</view>
		</view>
		
		
		
		<view v-if="user"
			class="cover-container"
			:style="[{
				transform: coverTransform,
				transition: coverTransition
			}]"
			@touchstart="coverTouchstart"
			@touchmove="coverTouchmove"
			@touchend="coverTouchend"
		>
			<image class="arc" src="/static/arc.png"></image>
			
			
			
			<view v-if="user.type == 2" style="height: 60vh;background-color: white;">
				<view class="tj-sction u-p-t-20 u-font-30">
					商家专区
				</view>
				
				<view class="" >
					<u-tabs :list="list" :is-scroll="false" :current="current" @change="change"></u-tabs>
					<view class="u-p-20 u-border-top" v-if="current == 0">
						<u-button @click="showAddGoods()" type="primary" size="mini" :plain="true">新增商品</u-button>
					</view>
					
					
					<u-cell-group v-if="current == 0">
							<u-cell-item v-for="(item,index) in storeGoods" :key="index" :title="item.name+(item.stat == 0 ?'（待上架）':(item.stat == 1 ? '（待审核）':'（在售）'))" :label="'售价:'+item.price+'   库存:'+item.num" :arrow="false">
									<image class="u-m-r-15" style="width: 80rpx;height: 80rpx;" mode="aspectFill" slot="icon"  :src="picUrl + item.pic1 "></image>
								<u-button size="mini" slot="right-icon" :type="item.stat == 0 ? 'primary':'warning' " @click="goodsChange(item,0)">{{item.stat == 0 ?'上架':'下架'}}</u-button>
							</u-cell-item>
					</u-cell-group>
				</view>
				
			</view>
			
			<view v-if="user.type == 3">
				<view class="tj-sction u-p-t-20 u-font-30">
					管理员专区
				</view>
				<view class="">
					<u-tabs :list="list" :is-scroll="false" :current="current" @change="change"></u-tabs>
					
					<u-cell-group v-if="current == 0">
							<u-cell-item v-for="(item,index) in unCheckList" :key="index" :title="item.memo+(item.stat == 0 ?'（待审核）':'')" :label="'店主：'+item.name" :arrow="false">
									<!-- <u-badge count="99" :absolute="false" slot="right-icon"></u-badge> -->
								<u-button size="mini" slot="right-icon" :type="item.stat == 0 ? 'primary':'warning' " @click="storeChange(item,0)">{{item.stat == 0 ?'通过':'停用'}}</u-button>
							</u-cell-item>
					</u-cell-group>
					
					<u-cell-group v-if="current == 1">
							<u-cell-item v-for="(item,index) in goodsList" :key="index" :title="item.name+(item.stat == 1 ?'（待审核）':(item.stat == 3 ? '（在售）':'（未上架）'))" :label="'售价:'+item.price+'   库存:'+item.num" :arrow="false">
									<image class="u-m-r-15" style="width: 80rpx;height: 80rpx;" mode="aspectFill" slot="icon"  :src="picUrl + item.pic1 "></image>
								<u-button size="mini" slot="right-icon" :type="item.stat == 1  ?'success':(item.stat == 3 ?'warning':'primary')" @click="goodsChange(item,1)">{{item.stat == 1  ?'通过':(item.stat == 3 ?'下架':'上架')}}</u-button>
							</u-cell-item>
					</u-cell-group>
					
				</view>
			</view>
			
			
			
			<!-- 订单 -->
			<view class="order-section"  v-if="user.type == 1">
				<view class="order-item" @click="navTo('/pages/order/order?index=0')" hover-class="common-hover"  :hover-stay-time="50">
					<text class="yticon icon-shouye"></text>
					<text>全部订单</text>
				</view>
				<view class="order-item" @click="navTo('/pages/order/order?index=1')"  hover-class="common-hover" :hover-stay-time="50">
					<text class="yticon icon-daifukuan"></text>
					<text>待发货</text>
				</view>
				<view class="order-item" @click="navTo('/pages/order/order?index=2')" hover-class="common-hover"  :hover-stay-time="50">
					<text class="yticon icon-yishouhuo"></text>
					<text>待收货</text>
				</view>
				<view class="order-item" @click="navTo('/pages/order/order?index=3')" hover-class="common-hover"  :hover-stay-time="50">
					<text class="yticon icon-shouhoutuikuan"></text>
					<text>待评价</text>
				</view>
			</view>
			<!-- 浏览历史 -->
			<view class="history-section icon" v-if="user.type == 1">
				<view class="sec-header">
					<text>其他功能</text>
				</view>

				<list-cell icon="icon-shezhi1" iconColor="#e07472" title="设置" border="" @eventClick="navTo('/pages/set/set')"></list-cell>
				<list-cell icon="icon-share" iconColor="#3f80de" title="退出登录" border="" @eventClick="toLogout"></list-cell>
			</view>
		</view>
			
		
    </view>  
</template>  
<script>  
	import listCell from '@/components/mix-list-cell';
	import appRequest from "@/common/appRequestUrl.js";
    import {  
        mapState 
    } from 'vuex';  
	let startY = 0, moveY = 0, pageAtTop = true;
    export default {
		components: {
			listCell
		},
		data(){
			return {
				picUrl:appRequest.getPicById,
				coverTransform: 'translateY(0px)',
				coverTransition: '0s',
				moving: false,
				user:"",
				current:"",
				list:[],
				unCheckList:[],
				goodsList:[],
				storeGoods:[],
			}
		},
		onLoad(){
		},
		onShow() {
			this.user = appRequest.getUserInfo();
			if(this.user){
				this.list = this.user.type == 3 ? [{name:"店铺审核"},{name:"商品审核"}] : [{name:"商品管理"},{name:"订单管理"}];
				
				if(this.user.type == 2){
					
					if(this.current == 0){
						this.getStoreGoods();
					}
					
				}else if(this.user.type == 3){
					if(this.current == 0){
						this.getUnchecked()
					}
				}
				
			}
			
		},
		// #ifndef MP
		onNavigationBarButtonTap(e) {
			const index = e.index;
			if (this.user) {
				this.navTo('/pages/set/set');
			}else{
				this.navTo("/pages/public/login")
			}
			
			// else if(index === 1){
			// 	// #ifdef APP-PLUS
			// 	const pages = getCurrentPages();
			// 	const page = pages[pages.length - 1];
			// 	const currentWebview = page.$getAppWebview();
			// 	currentWebview.hideTitleNViewButtonRedDot({
			// 		index
			// 	});
			// 	// #endif
			// 	uni.navigateTo({
			// 		url: '/pages/notice/notice'
			// 	})
			// }
		},
		// #endif
        computed: {
			...mapState(['hasLogin','userInfo'])
		},
        methods: {
			toLogout(){
				uni.showModal({
				    content: '确定要退出登录么',
				    success: (e)=>{
				    	if(e.confirm){
							uni.clearStorage();
				    		uni.switchTab({
				    			url:"../index/index"
				    		})
				    	}
				    }
				});
			},
			toLogin(){
				uni.navigateTo({
					url:"../public/login"
				})
			},
			goodsChange(item,type){
				let _this = this;
				
				switch (type){
					case 0://店铺上下架
						item['type'] = 1;
						item.stat = item.stat == 0 ? 1:0;
						break;
					case 1://管理员审核
						item['type'] = 1;
						item.stat = item.stat == 1 ? 3:1;
						break;
					case 2:
						item['type'] = 0;//管理员删除
						break;		
					default:
						break;
				}
				
				appRequest.request({
					method: "POST",
					url: appRequest.saveGoods,
					data:{
						data:JSON.stringify(item)
					},
					success: function(res) {
						_this.$api.msg(res.data.msg);
						_this.getStoreGoods();
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
					}
				})
			},
			showAddGoods(){
				uni.navigateTo({
					url:"../public/addGoods"
				});
			},
			getStoreGoods(){
				let _this = this;
				appRequest.request({
					method: "POST",
					url: appRequest.getGoodsByStore,
					success: function(res) {
						if(res.data.code == 200){
							_this.storeGoods = res.data.data
						}else{
							
						}
				
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
						item.stat = item.stat == 0 ? 1:0;
					}
				})
			},
			storeChange:function(item,type){
				
				switch (type){
					case 0: //店铺审核
					
						item.stat = item.stat == 0 ? 1:0;
						this.checkStore(item);
					
						break;
					default:
						break;
				}
			},
			checkStore:function(item){
				
				let _this = this;
				appRequest.request({
					method: "POST",
					url: appRequest.setUncheckStore,
					data:{
						data:JSON.stringify(item)
					},
					success: function(res) {
						
						if(res.data.code == 200){
							_this.getUnchecked();
						}else{
							_this.$api.msg(res.data.msg);
							item.stat = item.stat == 0 ? 1:0;
						}
				
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
						item.stat = item.stat == 0 ? 1:0;
					}
				})
			},
			getUnchecked(){
				let _this = this;
				appRequest.request({
					method: "POST",
					url: appRequest.getUncheckStore,
					success: function(res) {
						
						if(res.data.code == 200){
							_this.unCheckList = res.data.data
							_this.unCheckList.map(function(obj,index,arr){
								obj['checked'] = obj.stat == 1;
							})
						}else{
							_this.$api.msg(res.data.msg);
						}
				
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
					}
				})
			},getGoods(){
				let _this = this;
				appRequest.request({
					method: "POST",
					url: appRequest.getGoods,
					data:{
						data:JSON.stringify({
							validFlag:1
						})
					},
					success: function(res) {
						
						if(res.data.code == 200){
							_this.goodsList = res.data.data;
						}else{
							_this.$api.msg(res.data.msg);
						}
				
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
					}
				})
			},
			change(index) {
				this.current = index;
				if(this.user.type == 3){
					if(index == 0){
						this.getUnchecked();
					}else{
						this.getGoods();
					}
				}else if(this.user.type == 2){
					if(index == 0){
						this.getStoreGoods();
					}else{
						uni.navigateTo({
							url:"/pages/order/order?index=1"
						})
					}
				}
			},
			getHead:function(){
				switch (this.user.type){
					case 1:
						return '/static/user.jpg';
						break;
					case 2:
						return '/static/store.png';
						break;
					case 3:
						return '/static/admin.png';
						break;		
					default:
						return '/static/missing-face.png';
						break;
				}
			},
			
			/**
			 * 统一跳转接口,拦截未登录路由
			 * navigator标签现在默认没有转场动画，所以用view
			 */
			navTo(url){
				if(!this.user){
					url = '/pages/public/login';
				}
				uni.navigateTo({  
					url
				})  
			}, 
	
			/**
			 *  会员卡下拉和回弹
			 *  1.关闭bounce避免ios端下拉冲突
			 *  2.由于touchmove事件的缺陷（以前做小程序就遇到，比如20跳到40，h5反而好很多），下拉的时候会有掉帧的感觉
			 *    transition设置0.1秒延迟，让css来过渡这段空窗期
			 *  3.回弹效果可修改曲线值来调整效果，推荐一个好用的bezier生成工具 http://cubic-bezier.com/
			 */
			coverTouchstart(e){
				if(pageAtTop === false){
					return;
				}
				this.coverTransition = 'transform .1s linear';
				startY = e.touches[0].clientY;
			},
			coverTouchmove(e){
				moveY = e.touches[0].clientY;
				let moveDistance = moveY - startY;
				if(moveDistance < 0){
					this.moving = false;
					return;
				}
				this.moving = true;
				if(moveDistance >= 80 && moveDistance < 100){
					moveDistance = 80;
				}
		
				if(moveDistance > 0 && moveDistance <= 80){
					this.coverTransform = `translateY(${moveDistance}px)`;
				}
			},
			coverTouchend(){
				if(this.moving === false){
					return;
				}
				this.moving = false;
				this.coverTransition = 'transform 0.3s cubic-bezier(.21,1.93,.53,.64)';
				this.coverTransform = 'translateY(0px)';
			}
        }  
    }  
</script>  
<style lang='scss'>
	%flex-center {
	 display:flex;
	 flex-direction: column;
	 justify-content: center;
	 align-items: center;
	}
	%section {
	  display:flex;
	  justify-content: space-around;
	  align-content: center;
	  background: #fff;
	  border-radius: 10upx;
	}

	.user-section{
		height: 520upx;
		padding: 100upx 30upx 0;
		position:relative;
		.bg{
			position:absolute;
			left: 0;
			top: 0;
			width: 100%;
			height: 100%;
			filter: blur(1px);
			opacity: .7;
		}
	}
	.user-info-box{
		height: 180upx;
		display:flex;
		align-items:center;
		position:relative;
		z-index: 1;
		.portrait{
			width: 130upx;
			height: 130upx;
			border:5upx solid #fff;
			border-radius: 50%;
		}
		.username{
			font-size: $font-lg + 6upx;
			color: $font-color-dark;
			margin-left: 20upx;
		}
	}

	.vip-card-box{
		display:flex;
		flex-direction: column;
		color: #f7d680;
		height: 240upx;
		background: linear-gradient(left, rgba(0,0,0,.7), rgba(0,0,0,.8));
		border-radius: 16upx 16upx 0 0;
		overflow: hidden;
		position: relative;
		padding: 20upx 24upx;
		.card-bg{
			position:absolute;
			top: 20upx;
			right: 0;
			width: 380upx;
			height: 260upx;
		}
		.b-btn{
			position: absolute;
			right: 20upx;
			top: 16upx;
			width: 132upx;
			height: 40upx;
			text-align: center;
			line-height: 40upx;
			font-size: 22upx;
			color: #36343c;
			border-radius: 20px;
			background: linear-gradient(left, #f9e6af, #ffd465);
			z-index: 1;
		}
		.tit{
			font-size: $font-base+2upx;
			color: #f7d680;
			margin-bottom: 28upx;
			.yticon{
				color: #f6e5a3;
				margin-right: 16upx;
			}
		}
		.e-b{
			font-size: $font-sm;
			color: #d8cba9;
			margin-top: 10upx;
		}
	}
	.cover-container{
		background: $page-color-base;
		margin-top: -150upx;
		padding: 0 30upx;
		position:relative;
		background: #f5f5f5;
		padding-bottom: 20upx;
		.arc{
			position:absolute;
			left: 0;
			top: -34upx;
			width: 100%;
			height: 36upx;
		}
	}
	.tj-sction{
		@extend %section;
		.tj-item{
			@extend %flex-center;
			flex-direction: column;
			height: 140upx;
			font-size: $font-sm;
			color: #75787d;
		}
		.num{
			font-size: $font-lg;
			color: $font-color-dark;
			margin-bottom: 8upx;
		}
	}
	.order-section{
		@extend %section;
		padding: 28upx 0;
		margin-top: 20upx;
		.order-item{
			@extend %flex-center;
			width: 120upx;
			height: 120upx;
			border-radius: 10upx;
			font-size: $font-sm;
			color: $font-color-dark;
		}
		.yticon{
			font-size: 48upx;
			margin-bottom: 18upx;
			color: #fa436a;
		}
		.icon-shouhoutuikuan{
			font-size:44upx;
		}
	}
	.history-section{
		padding: 30upx 0 0;
		margin-top: 20upx;
		background: #fff;
		border-radius:10upx;
		.sec-header{
			display:flex;
			align-items: center;
			font-size: $font-base;
			color: $font-color-dark;
			line-height: 40upx;
			margin-left: 30upx;
			.yticon{
				font-size: 44upx;
				color: #5eba8f;
				margin-right: 16upx;
				line-height: 40upx;
			}
		}
		.h-list{
			white-space: nowrap;
			padding: 30upx 30upx 0;
			image{
				display:inline-block;
				width: 160upx;
				height: 160upx;
				margin-right: 20upx;
				border-radius: 10upx;
			}
		}
	}
	.list-cell{
		display:flex;
		align-items:baseline;
		padding: 20upx $page-row-spacing;
		line-height:60upx;
		position:relative;
		background: #fff;
		justify-content: center;
		&.log-out-btn{
			margin-top: 40upx;
			.cell-tit{
				color: $uni-color-primary;
				text-align: center;
				margin-right: 0;
			}
		}
		&.cell-hover{
			background:#fafafa;
		}
		&.b-b:after{
			left: 30upx;
		}
		&.m-t{
			margin-top: 16upx; 
		}
		.cell-more{
			align-self: baseline;
			font-size:$font-lg;
			color:$font-color-light;
			margin-left:10upx;
		}
		.cell-tit{
			flex: 1;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			margin-right:10upx;
		}
		.cell-tip{
			font-size: $font-base;
			color: $font-color-light;
		}
		switch{
			transform: translateX(16upx) scale(.84);
		}
	}
	
</style>