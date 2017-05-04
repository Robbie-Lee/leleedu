var tool = {
	///
	iconSwitch: function($element, base, target){
		return $element.removeClass(base).addClass(target);
	},
	autoSwitchIcon2Active: function(){
		var $iconSpan = $('div.sidebar-nav-active').find('div.sidebar-title').find('span.glyphicon');
		this.iconSwitch($iconSpan, iconClassName.sidebar.title.right, iconClassName.sidebar.title.bottom);
	},
	autoLogin: function(){
		if($('#login').lengtn > 0) {
			if(cookie.check('username')){
				llasLoginForm.loginName.value = cookie.get('username');
				llasLoginForm.password.value = '';
				llasLoginForm.rememberMe.checked = true;
			}
		}
	}
};
var cookie = {
	set: function(c_name, value, expiredays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays );
		document.cookie = c_name + "=" + escape(value) +
		((expiredays == null) ? "" : ";expires="+exdate.toGMTString());
	},
	get: function(c_name) {
		if (document.cookie.length > 0) {
		  var c_start = document.cookie.indexOf(c_name + "="), c_end = '';
		  if(c_start != -1) { 
		    c_start = c_start + c_name.length + 1;
		    c_end = document.cookie.indexOf(";", c_start);
		    if (c_end == -1) c_end = document.cookie.length
		    return unescape(document.cookie.substring(c_start, c_end));
		    } 
		  }
		return '';
	},
	check: function (c_name) {
		var name = this.get(c_name);
		if (name != null && name != '') {
			
			return true;
		}else {
			return false;
		}
	},
	del: function(c_name) {
		this.set(c_name, '', -1);
	}
};
var llas = {
	expiredays: 30
};
var iconClassName = {
	'sidebar': {
		'fold':{
			'left': 'glyphicon-menu-left',
			'right': 'glyphicon-menu-right'
		},
		'title':{
			'right': 'glyphicon-triangle-right',
			'bottom': 'glyphicon-triangle-bottom'
		}
	},
};

$(function(){
	$('.date-input').each(function(){
		$(this).ionDatePicker({
			lang: 'zh-cn',
			format: 'YYYY/MM/DD'
		});
	});
});
/*Jquery function*/
$(function(){
	tool.autoSwitchIcon2Active();
	//�˵����л�
	$('div.sidebar-nav').on('click', function(e){
		var $thisNav = $(this), $thisUl = $thisNav.find('ul.sidebar-trans'), isThisActive = $thisNav.hasClass('sidebar-nav-active'),
			$llasSidebar = $('div.llas-sidebar'), $sidebarSwitch = $llasSidebar.find('div.sidebar-switch'),
			$sidebarNav = $llasSidebar.find('div.sidebar-nav'), sidebarLen = $sidebarNav.length, 
			navHeight = $thisNav.find('div.sidebar-title').height(), $icon = $sidebarNav.find('.sidebar-title').find('span.glyphicon');
		
		$sidebarNav.removeClass('sidebar-nav-active');
		$sidebarNav.find('ul.sidebar-trans').css('max-height',0);
		tool.iconSwitch($icon, iconClassName.sidebar.title.bottom, iconClassName.sidebar.title.right);
		if(isThisActive) return;
		$thisNav.addClass('sidebar-nav-active');
		tool.iconSwitch($thisNav.find('.sidebar-title').find('span.glyphicon'), iconClassName.sidebar.title.right, iconClassName.sidebar.title.bottom)
		$thisUl.css('max-height',$llasSidebar.height() - $sidebarSwitch.height() - (navHeight * sidebarLen));
	});
	//�˵�������
	$('div.sidebar-switch').on('click', function(e){
		var $llasBody = $('div.llas-body'), $icon = $(this).find('span.glyphicon');
		//�л����
		$llasBody.toggleClass('llas-sidebar-mini');
		//�л���ͷ
		tool.iconSwitch($icon, iconClassName.sidebar.fold.right, iconClassName.sidebar.fold.left)
		if($llasBody.hasClass('llas-sidebar-mini')){
			tool.iconSwitch($icon, iconClassName.sidebar.fold.left, iconClassName.sidebar.fold.right)
		}
	});
});
function loginSystem(_this, _form){
	formSubmitMethod(_this, _form);
}
//form 表单提交函数
function formSubmitMethod(_btn, _form) {
	//表单验证
	var $form = $(_form);
	if($form.hasClass('llas-form-valid') && !$form.valid()){
		return false;
	}
	var data = $form.serialize(), url = $form.attr('action'), type = $form.attr('method');
	$.ajax({
		url: url,
		type: type,
		data: data,
		dataType: 'json',
		context: _btn,
	})
	.done(function(result){
		if(1 === result.flag) {
			//成功记录cookie, 选中remember me记录cookie
			if(_form.rememberMe.checked && !cookie.check('username')){
				cookie.set('username', _form.loginName, llas.expiredays);
			}else if(!_form.rememberMe.checked) {
				cookie.del('username');
			}
			
		}else {
			
		}
	})
	.fail(function(){
		
	});
}

var newTableRow = {
	'd':{'title':''}
};
function editTableRowData(_this, type){
	$.post('model/'+type+'.html', {}, function(str){
		var $layerModel = $('#layer-modle');
//		alert(typeof $layerModel.load('model/'+type+'.html'));
		layer.open({
			type: 1,
			title: '新增课程',
			maxmin: true,
			shadeClose: false,
			scrollbar: false,
			skin: 'layui-layer-rim', 
			btn: ['确定', '取消'],
			btnAlign: 'c',
			area : ['800px' , '460px'],
			content: str,
			end: function(){
				$layerModel.html('');
			}
		 });
	});
}
function deleteTableRowData(_this, type){
	var data = $(_this).data();
	layer.confirm('确认删除这条数据吗？', {
		title:'确认信息',
		icon: 2,
		btn: ['取消', '确认'],
		btn1: function(index, layero){

		},
		btn2: function(index, layero){
			  
		}
	},function(index){
		layer.close(index);
	});
	
}
function newTableRowData(_this, type) {
	var $layerModel = $('#layer-modle');
	layer.open({
		type: 1,
		title: '新增课程',
		maxmin: true,
		shadeClose: false,
		scrollbar: false,
		skin: 'layui-layer-rim', 
		btn: ['确定', '取消'],
		btnAlign: 'c',
		area : ['800px' , '460px'],
		content: $layerModel.load('model/'+type+'.html'),
		end: function(){
			$layerModel.html('');
		}
	 });
}
