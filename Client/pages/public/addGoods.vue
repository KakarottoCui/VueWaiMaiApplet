<template>
	<view class="u-p-25">

		<u-form :model="form" ref="uForm">
			<u-form-item label="商品名" label-width="110" prop="name">
				<u-input v-model="form.name" />
			</u-form-item>
			<u-form-item label="简介" prop="memo">
				<u-input type="textarea" v-model="form.memo" />
			</u-form-item>
			<u-form-item label="类别" >
				<u-input :disabled="true" @click="showSel=true" v-model="selName" type="select" />
			</u-form-item>
			<u-form-item label="价格" prop="price">
				<u-input v-model="form.price" type="text" />
			</u-form-item>
			<u-form-item label="库存" prop="num">
				<u-input v-model="form.num" type="text" />
			</u-form-item>

			<view class="u-flex u-m-t-25 u-p-b-25 u-border-bottom">
				<view class="item">
					<ygy-upload-img title="简介图" :size="200" :num="1" :iconCloseStyle="info.iconCloseStyle"
						@saveImg="getImgList($event,'pic1')"></ygy-upload-img>
				</view>
			</view>

			<view class="u-flex u-m-t-25 u-border-bottom">
				<text>详情图</text>
				<view class="u-flex u-m-l-20 u-p-b-25 u-border-bottom">
					<view class="item">
						<ygy-upload-img title="" :size="200" :num="1" :iconCloseStyle="info.iconCloseStyle"
							@saveImg="getImgList($event,'pic2')"></ygy-upload-img>
					</view>
				</view>
				<view class="u-flex u-m-l-20 u-p-b-25 u-border-bottom">
					<view class="item">
						<ygy-upload-img title="" :size="200" :num="1" :iconCloseStyle="info.iconCloseStyle"
							@saveImg="getImgList($event,'pic3')"></ygy-upload-img>
					</view>
				</view>

			</view>

			<u-button type="primary" class="u-m-t-50" @click="submit"    :ripple="true">发布
				 </u-button>


			<!-- <u-form-item label="水果">
				<u-checkbox-group>
					<u-checkbox v-model="item.checked" v-for="(item, index) in checkboxList" :key="index" :name="item.name">
						{{ item.name }}
					</u-checkbox>
				</u-checkbox-group>
			</u-form-item>
			<u-form-item label="味道">
				<u-radio-group v-model="radio">
					<u-radio v-for="(item, index) in radioList" :key="index" :name="item.name" :disabled="item.disabled">
						{{ item.name }}
					</u-radio>
				</u-radio-group>
			</u-form-item>
			<u-form-item label="开关"><u-switch slot="right" v-model="switchVal"></u-switch></u-form-item> -->
		</u-form>
		<u-select v-model="showSel" :list="typeList" @confirm="confirm"></u-select>
	</view>
</template>

<script>
	import ygyUploadImg from '../../components/ygy-upload-img/ygy-upload-img.vue'
	import appRequest from "@/common/appRequestUrl.js";
	export default {
		components: {
			ygyUploadImg
		},
		data() {
			return {
				form: {
					name: "",
					num: "",
					memo: "",
					price: "",
					stat: 0,
					pic1: "",
					pic2: "",
					pic3: "",
					typeFK: "",
					type:2
				},user:"",
				selName: "",
				showSel: false,
				typeList: [],
				info: {
					iconCloseStyle: { //关闭图标样式
						fontSize: '60rpx',
						color: "#f40"
					}
				},rules: {
				name: [
					{
								required: true,
								message: '请填写商品名',
								trigger: ['change','blur']
					}
				],num: [{
								required: true,
								message: '请填写库存',
								trigger: ['change','blur']
					},
					{
								type: "number",
								message: '库存格式异常',
								trigger: ['change','blur']
					}
				],memo: [
					{
								required: true,
								message: '请填写详情',
								trigger: ['change','blur']
					}
				],price: [{
								required: true,
								message: '请填写价格',
								trigger: ['change','blur']
					},
					{
								type: "number",
								message: '价格格式异常',
								trigger: ['change','blur']
					}
				]
				}
			}
		},
		onLoad:function() {
			this.getGoodsType();
		},
		onShow() {
			this.user = appRequest.getUserInfo();
			if(!this.user){
				uni.switchTab({
					url:"../index/index"
				})
			}
		},
		methods: {
			submit(){
				let _this = this;
				this.$refs.uForm.validate(valid => {
					if (valid) {
						
						// if( _this.form.pic1 == "" || _this.form.pic2 == "" || _this.form.pic3 == "" ){
						// 	_this.$api.msg('请上传全部图片');
						// 	return;
						// }
						
						// if(!_this.form.typeFK){
						// 	_this.$api.msg('请选择类型');
						// 	return;
						// }
						_this.form.type = 2;
						appRequest.request({
							method: "POST",
							url: appRequest.saveGoods,
							data:{
								data:JSON.stringify(_this.form)
							},
							success: function(res) {
								if (res.data.code == 200) {
									uni.showModal({
										title:"成功",
										content:"商品信息提交成功",
										showCancel:false,
										success:function(res){
											if(res.confirm){
												uni.switchTab({
													url:"../user/user"
												})
											}
										}
									})({
										
									})
								} else {
									_this.$api.msg('保存失败');
								}
							},
							fail: function(res) {
								_this.$api.msg("请求异常");
							}
						})
						
						
					} else {
						_this.$api.msg('验证异常');
					}
				});
			},
			confirm(e) {
				this.selName = e[0].label;
				this.form.typeFK = e[0].value;
			},
			getGoodsType() {

				let _this = this;
				appRequest.request({
					method: "POST",
					url: appRequest.getGoodsType,
					success: function(res) {
						if (res.data.code == 200) {
							_this.typeList = [];
							res.data.data.map(function(obj, index, arr) {
								_this.typeList.push({
									label: obj.name,
									value: obj.id
								})
							});
						} else {
							_this.$api.msg('类型获取失败');
						}
						
						
						
					},
					fail: function(res) {
						_this.$api.msg("请求异常");
					}
				})
			},
			getImgList(arr, name) {
				this.form[name] = arr[0];
			}
		},onReady() {
		this.$refs.uForm.setRules(this.rules);
	}
	}
</script>

<style lang="scss">

</style>
