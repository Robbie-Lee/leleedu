/*Jquery function*/
$(function(){
	tool.autoSwitchIcon2Active();
	tool.autoLogin();
	$('[data-toggle="tooltip"]').tooltip();
	$('input.date-input').each(function(){
		var $this = $(this), 
			isBody = typeof $this.attr('data-per') == 'undefined'?true:false,
		options = {
			perElement: isBody?document.body:document.getElementById($this.attr('data-per')),
			top: isBody?0:50,
			left: isBody?0:$('div.llas-sidebar').width()
		};
		
		$this.ionDatePicker({
			lang: 'zh-cn',
			format: 'YYYY-MM-DD',
			perElement: isBody?document.body:document.getElementById($this.attr('data-per')),
			top: isBody?0:50,
			left: isBody?0:$('div.llas-sidebar').width()
		});
	});
	//清除a,button focus状态下的蓝色边框
	$(document).delegate('button,a','focus',function(){
		this.blur();
	});
	
	//导航栏变化
	$('div.sidebar-title').on('click', function(e){
		var $thisNav = $(this).parents('div.sidebar-nav'), $thisUl = $thisNav.find('ul.sidebar-trans'), isThisActive = $thisNav.is('.sidebar-nav-active'),
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
	//切换导航栏宽度
	$('div.sidebar-switch').on('click', function(e){
		var $llasBody = $('div.llas-body'), $icon = $(this).find('span.glyphicon');
		$llasBody.toggleClass('llas-sidebar-mini');
		tool.iconSwitch($icon, iconClassName.sidebar.fold.right, iconClassName.sidebar.fold.left)
		if($llasBody.hasClass('llas-sidebar-mini')){
			tool.iconSwitch($icon, iconClassName.sidebar.fold.left, iconClassName.sidebar.fold.right)
		}
	});
	
	//表单验证
	if($(".llas-valid-form").length > 0){
		$form = $(".llas-valid-form");
		$form.each(function(){
			if($(this).is('.error-info-div')){
				$(this).validate({
			    	errorContainer: '.contact-error',
			    	errorElement: 'span',
			    	errorClass: 'error-message',
			    	errorPlacement: function(error, element) { 
			    	     error.appendTo(element.parents('form').children('.contact-error')); 
			    	},
					showErrors:function(errorMap,errorList) {
						this.defaultShowErrors();
						$('div.contact-error').find("span.error-message").first().show().html('信息格式填写有误，请您仔细检查！').siblings('span.error-message').remove();
			    	}
				});
			}else{
				$(this).validate({});
			}
		});
	}
});
//工具
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
		if($('#login').length > 0) {
			if(cookie.check('username')){
				llasLoginForm.loginName.value = cookie.get('username');
				llasLoginForm.password.value = this.randomWord(false, 8);
				llasLoginForm.rememberMe.checked = true;
			}
		}
	},
	formatDate: function(time, format){
		return moment(time).format(format);
	},
	acceptDiscountStr: function(isAccept){
		if(isAccept || isAccept == 'true'){
			return '是';
		}
		return '否';
	},
	randomWord: function(randomFlag, min, max){
	    var str = "",
	        range = min,
	        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
	    // 随机产生
	    if(randomFlag){
	        range = Math.round(Math.random() * (max-min)) + min;
	    }
	    for(var i=0; i<range; i++){
	        pos = Math.round(Math.random() * (arr.length-1));
	        str += arr[pos];
	    }
	    return str;
	},
    relaceVariable: function(str, string) {
        var newStr = '';
        newStr = str.replace(new RegExp("\\${\\w+}", "g"), function(word) {
            var key = word.replace(/\${/g, '').replace(/}/g, '');
            return string;
        });
        return newStr;
    },
    html_encode: function(str){
  	  var s = "";   
  	  if (!str || str.length == 0)
  		  return "";   
  	    s = str.replace(/&/g, "&amp;");
  		s = s.replace(/ /g, "&nbsp;");
  		s = s.replace(/\t/g, "&nbsp;");
  		s = s.replace(/</g, "&lt;");
  		s = s.replace(/>/g, "&gt;");
  		s = s.replace(/"/g, "&quot;");
  		s = s.replace(/'/g, "&#39;");
  		s = s.replace(/\r/g, "<br/>");
  		s = s.replace(/\n/g, "<br/>");
  		s = s.replace(/\\/g, "\\\\");
  	  return s; 		
    },
    highlightingOperationRow: function(id){
		if(id != 0){
			$('#' + id).addClass('animated bounce');
			llas.currentRowId = 0;
			setTimeout(function(){
				$('#' + id).removeClass('animated bounce');
			},2000);
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
	expiredays: 30,
	currentRowId: 0,
	vaild: {
		username: /^[0-9a-zA-Z]+$/,
		password: [/[0-9]+/,/[a-zA-Z]+/,/((?=[\x21-\x7e]+)[^A-Za-z0-9])/],
	},
	modalTitle: {
		'createClass':'新增课程',
		'editClass':'编辑课程',
		'createTeacher': '新增教师',
		'teacherSearch': '编辑教师'	
	},
	page:{
		"data":"",
		"id":"pagination",//显示页码的元素
	    "maxshowpageitem":5,//最多显示的页码个数
	    "pagelistcount":20,//每页显示数据个数
	    "callBack":function(result){
	    }
	},
	initSelectStr: '<option value="">请选择</option>',
	noDataTrHTML: '<tr class="no-data-tr"><td colspan="100"><span class="no-data-icon">您还没有数据，赶紧去创建吧！</span></td></tr>',
	tableRowDeleteButton: '<button type="button" class="btn btn-link llas-left" onclick="deleteTableRowData(this, \'${type}\');">删除</button>',
	tableRowHandLine: '<span class="btn-link llas-left">|</span>',
	tableRowEditButton: '<button type="button" class="btn btn-link llas-left" data-id="" onclick="editTableRowData(this, \'${type}\');">编辑</button>'
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
//ajax结束之后的操作
var ajaxDone = {
		login: function(result, _form){
			var $errorInfo = $('#login-error-info');
			
			if(0 == result.resultFlag) {
				var username = $('#inputEmail3').val();
				//成功记录cookie, 选中remember me记录cookie
				if(_form.rememberMe.checked && !cookie.check('username')){
					cookie.set('username', username, llas.expiredays);
				}else if(!_form.rememberMe.checked) {
					cookie.del('username');
				}
				$errorInfo.addClass('sr-only');
				window.location.href = result.authUrl;
			}else {
				$errorInfo.removeClass('sr-only').find('label').text(result.loginStatus);
			}
		},
		grade: function(result){
			var $gradeSelect = $('select.course-grade'), optionsHtml = llas.initSelectStr;
			for(var key in result){
				optionsHtml += '<option value="'+result[key].scoreIndex+'">'+result[key].scoreDescription+'</option>';
			}
			$gradeSelect.html(optionsHtml);
		},
		teacherList: function(result){
			var $teacherList = $('select.teacher-list'), optionsHtml = llas.initSelectStr;;
			for(var key in result.elements){
				optionsHtml += '<option value="'+result.elements[key].name+'">'+result.elements[key].name+'</option>';
			}
			$teacherList.html(optionsHtml);
		},
		editClass: function(result){
			this.createClass(result);
		},
		createClass: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'classSearch');
				layer.closeAll('page');
			}else{
				layer.alert('创建失败，请检查您填写的信息是否合理!', {icon: 2});
			}
		},
		editTeacher: function(result){
			this.createTeacher(result);
		},
		createTeacher: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'teacherSearch');
				layer.closeAll('page');
			}else{
				layer.alert('创建失败，请检查您填写的信息是否合理!', {icon: 2});
			}	
		},
		classSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			var handHtml = tool.relaceVariable(llas.tableRowEditButton , 'editClass') + llas.tableRowHandLine + tool.relaceVariable(llas.tableRowDeleteButton, 'deleteClass');
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].classId+'">';
				tbodyHTML += '<td class="classId">'+elements[key].classId+'</td>';
				tbodyHTML += '<td class="className">'+elements[key].className+'</td>';
				tbodyHTML += '<td class="startDate llas-nowrap">'+tool.formatDate(elements[key].startDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="endDate llas-nowrap">'+tool.formatDate(elements[key].endDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="classTime">'+elements[key].classTime+'</td>';
				tbodyHTML += '<td class="classCount">'+elements[key].classCount+'</td>';
				tbodyHTML += '<td class="classRoom">'+elements[key].classRoom+'</td>';
				tbodyHTML += '<td class="teacherName">'+elements[key].teacherName+'</td>';
				tbodyHTML += '<td>¥<span class="classPrice">'+elements[key].classPrice+'</span></td>';
				tbodyHTML += '<td  class="scoreLevel llas-nowrap" data-value="'+elements[key].scoreLevel.scoreIndex+'">'+elements[key].scoreLevel.scoreDescription+'</td>';
				tbodyHTML += '<td class="acceptDiscount" data-value="'+elements[key].acceptDiscount+'">'+tool.acceptDiscountStr(elements[key].acceptDiscount)+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].registerCount+'</td>';
				tbodyHTML += '<td class="classDescription" data-value="'+tool.html_encode(elements[key].classDescription)+'"><div class="note-text-div"><span class="glyphicon glyphicon-eye-open" data-container="#date-body" data-toggle="tooltip" data-placement="left" title="'+tool.html_encode(elements[key].classDescription)+'"></span></div></td>';
				tbodyHTML += '<td>'+ handHtml +'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			$('span[data-toggle="tooltip"]').tooltip();
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);
		},
		teacherSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements; 
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].teacherId+'" data-birthday="'+tool.formatDate(elements[key].birthDay, 'YYYY-MM-DD')+'">';
				tbodyHTML += '<td class="name">'+elements[key].name+'</td>';
				tbodyHTML += '<td class="teacherId">'+elements[key].teacherId+'</td>';
				tbodyHTML += '<td class="llas-nowrap sex">'+elements[key].sex+'</td>';
				tbodyHTML += '<td class="llas-nowrap degree">'+elements[key].degree+'</td>';
				tbodyHTML += '<td class="major">'+elements[key].major+'</td>';
				tbodyHTML += '<td class="college">'+elements[key].college+'</td>';
				tbodyHTML += '<td class="age">'+elements[key].age+'</td>';
				tbodyHTML += '<td class="teachAge">'+elements[key].teachAge+'</td>';
				tbodyHTML += '<td class="classFeeRate">'+elements[key].classFeeRate+'</td>';
				tbodyHTML += '<td class="llas-nowrap">¥<span class="minClassFee">'+elements[key].minClassFee+'</span></td>';
				tbodyHTML += '<td class="checkInTime">'+tool.formatDate(elements[key].checkInTime, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="phone">'+elements[key].phone+'</td>';
				tbodyHTML += '<td class="status" data-value="'+elements[key].status+'">'+teacherManager.getStatusText(elements[key].status)+'</td>';
				tbodyHTML += '<td>'+tool.relaceVariable(llas.tableRowEditButton, 'editTeacher')+'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);
		},
		studentSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			var handHtml = tool.relaceVariable(llas.tableRowEditButton , 'editStudent');
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].studentId+'">';
				tbodyHTML += '<td class="name">'+elements[key].name+'</td>';
				tbodyHTML += '<td class="studentId">'+elements[key].studentId+'</td>';
				tbodyHTML += '<td class="sex llas-nowrap">'+elements[key].sex+'</td>';
				tbodyHTML += '<td class="school llas-nowrap">'+elements[key].school+'</td>';
				tbodyHTML += '<td class="grade" data-value="'+elements[key].grade+'">'+studentManager.gradeList[elements[key].grade]+'</td>';
				tbodyHTML += '<td class="guarderName">'+elements[key].guarderName+'</td>';
				tbodyHTML += '<td class="guarder" data-value="'+elements[key].guarder+'">'+studentManager.guarderList[elements[key].guarder]+'</td>';
				tbodyHTML += '<td class="guarderPhone">'+elements[key].guarderPhone+'</td>';
				tbodyHTML += '<td><a href="#">查看</a></td>';
				tbodyHTML += '<td  class="scoreLevel llas-nowrap" data-value="'+elements[key].scoreLevel.scoreIndex+'">'+elements[key].scoreLevel.scoreDescription+'</td>';
				tbodyHTML += '<td class="discountRate">'+elements[key].discountRate+'</td>';
				tbodyHTML += '<td class="note" data-value="'+tool.html_encode(elements[key].note)+'"><div class="note-text-div"><span class="glyphicon glyphicon-eye-open" data-container="#date-body" data-toggle="tooltip" data-placement="left" title="'+tool.html_encode(elements[key].note)+'"></span></div></td>';
				tbodyHTML += '<td>'+ handHtml +'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			$('span[data-toggle="tooltip"]').tooltip();
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);			
		},
		editStudent: function(result){
			this.createStudent(result);
		},
		createStudent: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'studentSearch');
				layer.closeAll('page');
			}else{
				layer.alert('创建失败，请检查您填写的信息是否合理!', {icon: 2});
			}	
		},
		attendSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].classId+'">';
				tbodyHTML += '<td class="classId">'+elements[key].classId+'</td>';
				tbodyHTML += '<td class="className">'+elements[key].className+'</td>';
				tbodyHTML += '<td class="startDate llas-nowrap">'+tool.formatDate(elements[key].startDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="endDate llas-nowrap">'+tool.formatDate(elements[key].endDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="classTime">'+elements[key].classTime+'</td>';
				tbodyHTML += '<td class="classCount">'+elements[key].classCount+'</td>';
				tbodyHTML += '<td class="classRoom">'+elements[key].classRoom+'</td>';
				tbodyHTML += '<td class="teacherName">'+elements[key].teacherName+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].checkinCount+'</td>';
				tbodyHTML += '<td>';
				if(elements[key].classCount > elements[key].checkinCount){
					tbodyHTML += '<button type="button" class="btn btn-link llas-left" data-id="'+elements[key].classId+'" onclick="attendManager.checkIn(this);">打卡</button></td>';
				}else{
					tbodyHTML += '<span class="" style="llas-success-text">结束</span>';
				}
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);			
		},
		checkInAfter: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'attendSearch');
			}else{
				layer.alert('打卡失败，请您稍后重试!', {icon: 2});
			}
		}
	};
function loginSystem(_this, _form){
	formSubmitMethod(_this, _form, 'login');
}
function searchByCondition(_this, _form, type){
	formSubmitMethod(_this, _form, type);
}
//form 表单提交函数
function formSubmitMethod(_btn, _form, method) {
	//表单验证
	var $form = $(_form);
	if($form.is('.llas-valid-form') && !$form.valid()){
		return false;
	}
	layer.load(2,{
		  shade: 0.2
	});
	var data = $form.serialize(), url = $form.attr('action'), type = $form.attr('method');
	$.ajax({
		url: url,
		type: type,
		data: data,
		dataType: 'json',
		context: _btn,
	})
	.done(function(result){
		
		if(typeof method == 'string'){
			ajaxDone[method](result, _form);
		}
		layer.closeAll('loading');
	})
	.fail(function(){
		layer.closeAll('loading');
		layer.alert('请求失败，请您稍后重试!', {icon: 2});
	});
}
function ajaxGetDataMethod(data, url, type, method, element) {
	var _form = null;
	$.ajax({
		url: url,
		type: type,
		data: data,
		dataType: 'json',
		context: element?element:null,
	})
	.done(function(result){
		if(typeof method == 'string'){
			ajaxDone[method](result, _form);
		}
	})
	.fail(function(){
		layer.alert('请求失败，请您稍后重试!', {icon: 2});
	});
}
function changeCheckboxValue(_this){
	var $this = $(_this), _target = $this.parents('div.checkbox').find($this.data('target')).get(0);
	if(_this.checked){
		_target.value = 'true';
	}else{
		_target.value = 'false';
	}
}
function fullEditPanelByTr($tr, $tmpl){
	var trData = $tr.data();
	$tmpl.find('[name]').each(function(){
		var nameVal = this.name;
		if(typeof this.name == 'object'){
			nameVal = this.name.name;
		}
		var $this = $(this),className = '.' + nameVal, text = '', $target = $tr.find(className);
		if($target.length > 0){
			text = $target.text();
			if($target.attr('data-value')){
				text = $target.attr('data-value');	
			}
		}else{
			text = trData[nameVal.toLowerCase()];
		}
		if(this.getAttribute('data-type') == 'CHECKBOX'){
			if(text == 'true'){
				$this.parents('div.checkbox').find('input[type="checkbox"]').get(0).checked = true;
			}
		}else{
			this.value = text;
		}
	});
	
}

function editTableRowData(_this, type, isNew){
	var $layerModel = $('#layer-modle');
	if(!isNew) fullEditPanelByTr($(_this).parents('tr'),$layerModel);
	layer.open({
		type: 1,
		title: llas.modalTitle[type],
		shadeClose: false,
		scrollbar: false,
		zIndex: 110,
		skin: 'layui-layer-rim', 
		btn: ['确定', '取消'],
		btnAlign: 'c',
		btn1: function(index, layero){
			formSubmitMethod(_this, createForm, type);
		},
		btn2: function(index, layero){
			createForm.reset();
		},
		area: '740px',
		content: $layerModel,
		end: function(){
			createForm.reset();
		}
	 });
}
function newTableRowData(_this, type) {
	editTableRowData(_this, type, true);
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
//
var commonManager = {
	page: function(){
		page.init(document.getElementById('total-items').value, document.getElementById('cur-page').value, llas.page);
	}	
};
//课程管理模块
var classManager = {
	init: function(){
		//成绩等级
		ajaxGetDataMethod('', '/lele/score/level.json', 'get', 'grade', null);
		//获取教师信息
		ajaxGetDataMethod({status:0},'/lele/teacher/search.json', 'get', 'teacherList', null);
		//分页信息
		commonManager.page();
	}
};
//教师管理模块
var teacherManager = {
	init: function(){
		commonManager.page();
	},
	getStatusText: function(status){
		var text = '';
		switch(status){
			case 1:
			case '1':
				text = '休假'; break; 
			case 2: 
			case '2': 
				text = '离职'; break;
			default: 
				text = '正常'; break;
		}
		return text;
	}
};
//学生管理模块
var studentManager = {
	gradeList: ['请选择','一年级','二年级','三年级','四年级','五年级','六年级'],
	guarderList: ['请选择','爸爸','妈妈','爷爷','奶奶','姥爷','姥姥','其他'],
	init: function(){
		commonManager.page();
	},
};
//打卡管理模块
var attendManager = {
	init: function(){
		commonManager.page();
		//获取教师信息
		ajaxGetDataMethod({status:0},'/lele/teacher/search.json', 'get', 'teacherList', null);
	},
	checkIn: function(_this){
		
		layer.confirm('确认进行打卡吗？', {
			title:'打卡确认',
			icon: 1,
			btn: ['取消', '确认'],
			btn2: function(index, layero){
				ajaxGetDataMethod({classId:_this.getAttribute('data-id')}, '/lele/attend/checkin.json', 'POST', 'checkInAfter', _this);
			}
	    }, function(index, layero){
	    	layer.close(index);
		});
		
		
		
	}
};
//分页插件
var  page = {
    "pageId":"",
    "data":null,
    "maxshowpageitem":5,//最多显示的页码个数
    "pagelistcount": 20,//每一页显示的内容条数
    "init":function(listCount,currentPage,options){
      	this.data=options.data,
      	this.pageId=options.id,
      	this.maxshowpageitem=options.maxshowpageitem,//最多显示的页码个数
      	this.pagelistcount=options.pagelistcount//每一页显示的内容条数
      	page.initPage(listCount,currentPage);
    },
  /**
     * 初始化数据处理
     * @param listCount 列表总量
     * @param currentPage 当前页
     */
  "initPage":function(listCount,currentPage){
        var maxshowpageitem = page.maxshowpageitem;
        if(maxshowpageitem!=null&&maxshowpageitem>0&&maxshowpageitem!=""){
            page.maxshowpageitem = maxshowpageitem;
        }
        var pagelistcount = page.pagelistcount;
        if(pagelistcount!=null&&pagelistcount>0&&pagelistcount!=""){
            page.pagelistcount = pagelistcount;
        }   
        page.pagelistcount=pagelistcount;
        if(listCount<0){
            listCount = 0;
        }
        if(currentPage<=0){
            currentPage=1;
        }
     
        page.setPageListCount(listCount,currentPage);
   },
    /**
     * 初始化分页界面
     * @param listCount 列表总量
     */
    "initWithUl":function(listCount,currentPage){
        var pageCount = 1;
        if(listCount>=0){
            var pageCount = listCount%page.pagelistcount>0?parseInt(listCount/page.pagelistcount)+1:parseInt(listCount/page.pagelistcount);
        }
        var appendStr = page.getPageListModel(pageCount,currentPage);
        $("#"+page.pageId).html(appendStr);
    },
    /**
     * 设置列表总量和当前页码
     * @param listCount 列表总量
     * @param currentPage 当前页码
     */
    "setPageListCount":function(listCount,currentPage){
        listCount = parseInt(listCount);
        currentPage = parseInt(currentPage);
       
        page.initWithUl(listCount,currentPage);
        page.initPageEvent(listCount);
    },
    "initPageEvent":function(listCount){
        $("#"+page.pageId +">li[class='pageItem']").on("click",function(){
        	document.getElementById('cur-page').value = $(this).attr("page-data");
        	searchByCondition(this, searchFrom, 'classSearch');
            page.setPageListCount(listCount,$(this).attr("page-data"),page.fun);
        });
    },
    "getPageListModel":function(pageCount,currentPage){
        var prePage = currentPage-1;
        var nextPage = currentPage+1;
        var prePageClass ="pageItem";
        var nextPageClass = "pageItem";
        if(prePage<=0){
            prePageClass="pageItemDisable";
        }
        if(nextPage>pageCount){
            nextPageClass="pageItemDisable";
        }
        var appendStr ="";
        appendStr+="<li class='"+prePageClass+"' page-data='1' page-rel='firstpage'>首页</li>";
        appendStr+="<li class='"+prePageClass+"' page-data='"+prePage+"' page-rel='prepage'>&lt;上一页</li>";
        var miniPageNumber = 1;
        if(currentPage-parseInt(page.maxshowpageitem/2)>0&&currentPage+parseInt(page.maxshowpageitem/2)<=pageCount){
            miniPageNumber = currentPage-parseInt(page.maxshowpageitem/2);
        }else if(currentPage-parseInt(page.maxshowpageitem/2)>0&&currentPage+parseInt(page.maxshowpageitem/2)>pageCount){
            miniPageNumber = pageCount-page.maxshowpageitem+1;
            if(miniPageNumber<=0){
                miniPageNumber=1;
            }
        }
        var showPageNum = parseInt(page.maxshowpageitem);
        if(pageCount<showPageNum){
            showPageNum = pageCount;
        }
        for(var i=0;i<showPageNum;i++){
            var pageNumber = miniPageNumber++;
            var itemPageClass = "pageItem";
            if(pageNumber==currentPage){
                itemPageClass = "pageItemActive";
            }

            appendStr+="<li class='"+itemPageClass+"' page-data='"+pageNumber+"' page-rel='itempage'>"+pageNumber+"</li>";
        }
        appendStr+="<li class='"+nextPageClass+"' page-data='"+nextPage+"' page-rel='nextpage'>下一页&gt;</li>";
        appendStr+="<li class='"+nextPageClass+"' page-data='"+pageCount+"' page-rel='lastpage'>尾页</li>";
       return appendStr;

    }
}
//验证代码扩展
//username 中包含字母和数字

jQuery.validator.addMethod("username", function(value, element) {
	var flag = llas.vaild.username.test(value);
	return this.optional(element) || flag;   
}, $.validator.format('只接受英文字母和数字'));

jQuery.validator.addMethod("password1", function(value, element) {
	var rules = llas.vaild.password, count = 0;
	for(var i in rules){
		if(rules[i].test(value)){
			count ++;
		}
	}
	return this.optional(element) || count > 1 ;   
}, $.validator.format('您的密码太简单'));
jQuery.validator.addMethod("phone", function(value, element) {
	var reg = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
	return this.optional(element) || reg.test($.trim(value));   
}, $.validator.format('您的密码太简单'));
