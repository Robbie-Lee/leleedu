<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="Keywords" content="">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" type="text/css" href="/resources/weui/weui.min.css">
<title>微信报名</title>
<style>
body{font-family: '微软雅黑';}
.weui-media-box__desc{color: #666;padding:10px 0;}
.weui-btn{line-height: 2.4;font-size:14px;margin-top: 12px;}
.enroll-cancel{float: right;cursor: pointer;}
.enroll-cancel:hover, .enroll-cancel:focus{color: #666;}
</style>
</head>
<body>
<div class="page">
<!-- loading toast -->
<div id="loadingToast" style="display:none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div>
</div>
<div id="dialogs" class="page">
<!--BEGIN dialog2-->
<div class="js_dialog" id="iosDialog2" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">
        <div class="weui-dialog__bd dialog-notice"></div>
        <div class="weui-dialog__ft">
            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" onclick="hideDialogMethod(this);">知道了</a>
        </div>
    </div>
</div>
<!--END dialog2-->
</div>
<div class="page__bd">
		<form>
        	<input type="hidden" value="${wechatid}" name="studentId" id="student-id">
       		<input type="hidden" value="5" name="pageSize" id="page-size">
       		<input type="hidden" value="1" name="curPage" id="cur-page">
        </form>
        
        
        <div id="grade-select-div">
	        <div class="weui-cells__title"><span>请选择年级</span></div>
	        <div class="weui-cells">
	            <a class="weui-cell weui-cell_access" href="javascript:;" data-grade="1" onclick="classManager.searchClass(this);">
	                <div class="weui-cell__bd">
	                    <p>一年级</p>
	                </div>
	                <div class="weui-cell__ft">
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access" href="javascript:;" data-grade="2" onclick="classManager.searchClass(this);">
	                <div class="weui-cell__bd">
	                    <p>二年级</p>
	                </div>
	                <div class="weui-cell__ft">
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access" href="javascript:;" data-grade="3" onclick="classManager.searchClass(this);">
	                <div class="weui-cell__bd">
	                    <p>三年级</p>
	                </div>
	                <div class="weui-cell__ft">
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access" href="javascript:;" data-grade="4" onclick="classManager.searchClass(this);">
	                <div class="weui-cell__bd">
	                    <p>四年级</p>
	                </div>
	                <div class="weui-cell__ft">
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access" href="javascript:;" data-grade="5" onclick="classManager.searchClass(this);">
	                <div class="weui-cell__bd">
	                    <p>五年级</p>
	                </div>
	                <div class="weui-cell__ft">
	                </div>
	            </a>
	            <a class="weui-cell weui-cell_access" href="javascript:;" data-grade="6" onclick="classManager.searchClass(this);">
	                <div class="weui-cell__bd">
	                    <p>六年级</p>
	                </div>
	                <div class="weui-cell__ft">
	                </div>
	            </a>
	        </div>
	        </div>
	        
	    <div class="list-class-info" style="display:none" id="class-list-div">
			<div class="weui-panel weui-panel_access">
				<div class="weui-panel__hd"><span>请选择课程</span><span onclick="cancel(this);" data-prev="grade-select-div" data-cur="class-list-div" class="enroll-cancel">返回</span></div>
				<div class="weui-panel__bd class-list">
				</div>
			</div>	    
			<div class="weui-loadmore weui-loadmore_line" id="nomore-div" style="display: none;">
            	<span class="weui-loadmore__tips">没有更多数据</span>
        	</div>
        	<div class="weui-loadmore" id="loadmore-div" style="display: none;">
            	<i class="weui-loading"></i>
            	<span class="weui-loadmore__tips">正在加载</span>
        	</div>	
        </div>
        <!--获取支付金钱-->
        <div class="weui-form-preview" id="class-pay-div" style="display: none;">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">付款金额</label>
                    <em class="weui-form-preview__value pay-price" >¥2400.00</em>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">收款方</label>
                    <span class="weui-form-preview__value">乐乐教育</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">课程</label>
                    <span class="weui-form-preview__value class-title"></span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">描述</label>
                    <span class="weui-form-preview__value class-note"></span>
                </div>
            </div>
            <div class="weui-form-preview__ft">
                <a class="weui-form-preview__btn weui-form-preview__btn_default" href="javascript:" data-prev="class-list-div" data-cur="class-pay-div" onclick="cancel(this);">返回</a>
                <button type="button" class="weui-form-preview__btn weui-form-preview__btn_primary" id="brand-pay-request" href="javascript:" onclick="classManager.getWechatPrepay(this);">付款</button>
            </div>
        </div>
        
        <div id="enroll-success-div" class="page msg_success js_show" style="display:none;">
		    <div class="weui-msg">
		        <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
		        <div class="weui-msg__text-area">
		            <h2 class="weui-msg__title">报名成功</h2>
		        </div>
		        <div class="weui-msg__opr-area">
		            <p class="weui-btn-area">
		                <a href="/wechat/enrollinfo.do?studentId=${wechatid}" class="weui-btn weui-btn_primary">查看已报课程</a>
		                <a href="javascript:location.reload();" class="weui-btn weui-btn_default">返回</a>
		            </p>
		        </div>
		    </div>
		</div>
		
		
        <div id="enroll-fail-div" class="page msg_warn js_show" style="display:none;">
		    <div class="weui-msg">
		        <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
		        <div class="weui-msg__text-area">
		            <h2 class="weui-msg__title">报名失败</h2>
		            <p class="weui-msg__desc">课程说明</p>
		        </div>
		        <div class="weui-msg__opr-area">
		            <p class="weui-btn-area">
		                <a href="javascript:location.reload();" class="weui-btn weui-btn_primary">重新报名</a>
		            </p>
		        </div>
		    </div>
		</div>
        
    </div>
<script src="/resources/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="/resources/common/js/jquery/jquery.validate.js"></script>

<script src="/resources/weui/weui.js"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
</body>
</html>
