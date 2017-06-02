<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="Keywords" content="">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" type="text/css" href="/lele/resources/weui/weui.min.css">
<title>注册</title>
<style>
body{font-family: '微软雅黑';}
</style>
</head>
<body>
<div class="weui-toptips weui-toptips_warn js_tooltips" style="display: none;"></div>
<div class="page__bd">

	<form class="weui-cells weui-cells_form" id="form">
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input required" name="name" type="text" pattern="[0-9]*" placeholder="姓名">
			</div>
		</div>
		<div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">性别</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="sex">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </div>
        </div>
		<div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">年级</label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="sex">
                        <option value="1">一年级</option>
						<option value="1">二年级</option>
						<option value="1">三年级</option>
						<option value="1">四年级</option>
						<option value="1">五年级</option>
						<option value="1">六年级</option>
                    </select>
                </div>
         </div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">就读学校</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input required" name="name" type="text" placeholder="就读学校">
			</div>
		</div>
		<div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__hd">
                    <label for="" class="weui-label">监护人关系</label>
                </div>
                <div class="weui-cell__bd">
				<select class="weui-select" name="guarder">
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
				<input class="weui-input" name="name" type="text" placeholder="监护人姓名">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">监护人电话</label></div>
			<div class="weui-cell__bd">
				<input class="weui-input" name="name" type="text" required pattern="[0-9]*" placeholder="监护人电话">
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd"><label class="weui-label">说明</label></div>
			<div class="weui-cell__bd">
				<textarea class="weui-textarea" placeholder="说明" rows="3"></textarea>
			</div>
		</div>
		<div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:" id="signup-button">注册</a>
        </div>


	</form>
</div>
<script src="/lele/resources/common/jquery/jquery-1.11.1.min.js"></script>
<script src="/lele/resources/common/jquery/jquery.validate.js"></script>
<script type="text/javascript">
$(function(){

	$('#form').validate({
		errorContainer: '.js_tooltips',
		errorElement: 'span',
		errorClass: 'error',
		errorPlacement: function(error, element) { 
			error.appendTo(element.parents('body').children('.js_tooltips')); 
		},
		showErrors:function(errorMap,errorList) {
			this.defaultShowErrors();
			$('div.js_tooltips').find("span.error").last().show().siblings('span.error').remove();
		}
	});

	$('#signup-button').on('click',function(){
		if(!$('#form').valid()){
		}
		var $tooltips = $('.js_tooltips');
		$tooltips.css('display', 'block');
		setTimeout(function () {
			$tooltips.css('display', 'none');
		}, 2000);

	});
});
</script>
</body>
</html>
