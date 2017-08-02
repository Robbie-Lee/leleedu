<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="Keywords" content="">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" type="text/css" href="/resources/weui/weui.min.css">
<title>注册用户</title>
<style>
body{font-family: '微软雅黑';}
</style>
</head>
<body>
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
<div class="weui-toptips weui-toptips_warn js_tooltips" style="display: none;"></div>
<div class="page__bd">

	<form class="weui-cells weui-cells_form" id="weui-signup-form" action="register.json" method="POST">
		<input type="hidden" value="${wechatid}" name="studentId" id="student-id">
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" name="name" type="text" placeholder="姓名" data-required="true" data-descriptions="name" maxlength="16">
			</div>
		</div>
		<div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">性别</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="sex" data-required="true" data-descriptions="sex">
                    	<option value="">请选择</option>
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </div>
        </div>
		<div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">入学年份</label>
                </div>
                <div class="weui-cell__bd">
                    <input type="text" class="weui-input" name="attendYear" data-required="true" data-descriptions="attendYear" maxlength="4" placeholder="入学年份">
                </div>
         </div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">就读学校</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" name="school" type="text" placeholder="就读学校" maxlength="64">
			</div>
		</div>
		<div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">监护人关系</label>
                </div>
                <div class="weui-cell__bd">
				<select class="weui-select" name="guarder" data-required="true" data-descriptions="guarder">
					<option value="">请选择</option>
					<option value="1">爸爸</option>
					<option value="2">妈妈</option>
					<option value="3">爷爷</option>
					<option value="4">奶奶</option>
					<option value="5">姥爷</option>
					<option value="6">姥姥</option>
					<option value="7">其他</option>
				</select>
                </div>
         </div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">监护人姓名</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" name="guarderName" type="text" placeholder="监护人姓名" data-required="true" data-descriptions="guarderName" maxlength="16">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">监护人电话</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" name="guarderPhone" type="text" placeholder="监护人电话" data-describedby="phone" data-validate="phone">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">说明</label></div>
			<div class="weui-cell__bd">
				<textarea class="weui-textarea" name="note" placeholder="说明" rows="3" maxlength="512"></textarea>
			</div>
		</div>
		<div class="weui-btn-area">
            <input type="submit" class="weui-btn weui-btn_primary" value="注册">
        </div>


	</form>
</div>
<script src="/resources/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="/resources/weui/jquery-mvalidate.js"></script>
<script src="/resources/weui/weui.js"></script>
</body>
</html>
