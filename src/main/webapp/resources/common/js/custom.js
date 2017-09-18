/*Jquery function*/
$(function(){
	tool.autoSwitchIcon2Active();
	tool.autoLogin();
	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover();
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
			}else{
				llasLoginForm.loginName.value = '';
				llasLoginForm.password.value = '';
				llasLoginForm.rememberMe.checked = false;
				$('#inputEmail3').val('');
				console.log($('#inputEmail3').val());
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
			for(var key in result){
				optionsHtml += '<option data-id="'+result[key].teacherId+'" value="'+result[key].name+'">'+result[key].name+'</option>';
			}
			$teacherList.html(optionsHtml);
		},
		studentList: function(result){
			var $studentList = $('#enroll-student-id'), optionsHtml = llas.initSelectStr, 
				studentInfo = '';
			for(var key in result.elements){
				
				studentInfo = result.elements[key].school +'&nbsp;&nbsp;&nbsp;&nbsp;'+ result.elements[key].attendYear + '年入学&nbsp;&nbsp;&nbsp;&nbsp;' 
				+ studentManager.guarderList[result.elements[key].guarder] + '电话：' + result.elements[key].guarderPhone;
				optionsHtml += '<option value="'+result.elements[key].studentId+'" data-subtext="'+studentInfo+'">'+result.elements[key].name+'</option>';
			}
			$studentList.html(optionsHtml).selectpicker('refresh');
		},
		classList: function(result){
			
			var $classList = $('#class-select'), optionsHtml = '', 
				classInfo = '';
				
			for(var key in result.elements){
				
				classInfo = '[课程号]' + result.elements[key].classId +'&nbsp;&nbsp;' + '[价格]' + result.elements[key].classPrice
				+'元&nbsp;&nbsp;[老师]' + result.elements[key].teacherName; 
				//+ '[最低成绩]' + result.elements[key].scoreLevel.scoreDescription;
				optionsHtml += '<option value="'+result.elements[key].classId+'" data-subtext="'+classInfo+'">'+result.elements[key].className+'</option>';
			}
			
			$classList.html(optionsHtml).selectpicker('refresh');
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
    	studentLook: function(result){
    		var tbodyHTML = '', elements = result;
    		
    		for(var key in elements) {
    			
				tbodyHTML += '<tr>';
				tbodyHTML += '<td class="name">'+elements[key].name+'</td>';
				tbodyHTML += '<td>'+elements[key].school+'</td>';
				tbodyHTML += '<td>'+elements[key].attendYear+'</td>';
				tbodyHTML += '<td>'+elements[key].guarderName+'['+studentManager.guarderList[elements[key].guarder]+']</td>';
				tbodyHTML += '<td>'+elements[key].guarderPhone+'</td>';
				tbodyHTML += '<td><input type="number" value=""></td>';
				tbodyHTML += '</tr>';   			
    			
    		}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
    		$('#look-student-table').find('tbody').html(tbodyHTML);
    		
		},
		registerStudentList: function(result){
			var $studentList = $('#drop-student-id'), optionsHtml = '', 
			studentInfo = '';
			for(var key in result){
				
				studentInfo = result[key].school +'&nbsp;&nbsp;&nbsp;&nbsp;'+ result[key].attendYear + '年入学&nbsp;&nbsp;&nbsp;&nbsp;' 
				+ studentManager.guarderList[result[key].guarder] + '电话：' + result[key].guarderPhone;
				optionsHtml += '<option value="'+result[key].studentId+'" data-subtext="'+studentInfo+'">'+result[key].name+'</option>';
			}
			$studentList.html(optionsHtml).selectpicker('refresh');			
		},
		classSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements, status_html;
			var handHtml = tool.relaceVariable(llas.tableRowEditButton , 'editClass') + llas.tableRowHandLine;
			for(var key in elements){
				if(elements[key].valid == 1){
					status_html = '<button type="button" class="btn btn-link llas-left" data-id="'+elements[key].classId+'" data-value="false" onclick="classManager.changeStatus(this, \'changeClassStatus\');">删除</button>';
					status_html +=   '<span class="btn-link llas-left">|</span>'
						+'<button type="button" class="btn btn-link llas-left" data-id="'+elements[key].classId+'" data-grade="'+elements[key].classGrade+'" onclick="classManager.enrollClass(this, \'studentList\');">报名</button>';
					if(elements[key].registerCount){
						status_html +=   '<span class="btn-link llas-left">|</span>'
							+'<button type="button" class="btn btn-link llas-left" data-id="'+elements[key].classId+'" onclick="classManager.dropClassMethod(this, \'registerStudentList\');">退课</button>';
						
					}
				}else{
					status_html = '<button type="button" class="btn btn-link llas-left" data-id="'+elements[key].classId+'" data-value="true" onclick="classManager.changeStatus(this, \'changeClassStatus\');">恢复</button>';
				}
				tbodyHTML += '<tr id="'+elements[key].classId+'" data-classgrade="'+elements[key].classGrade+'" data-registerlimit="'+elements[key].registerLimit+'">';
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
                tbodyHTML += '<td class=""><a href="javascript:;" data-id="'+elements[key].classId+'" onclick="classManager.lookStudent(this);">查看</a></td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].registerCount+'</td>';
				tbodyHTML += '<td class="registerTotalFee">'+elements[key].registerTotalFee+'</td>';
				tbodyHTML += '<td class="classDescription" data-value="'+tool.html_encode(elements[key].classDescription)+'"><div class="note-text-div"><span class="glyphicon glyphicon-eye-open" data-container="#date-body" data-toggle="tooltip" data-placement="left" title="'+tool.html_encode(elements[key].classDescription)+'"></span></div></td>';
				tbodyHTML += '<td>'+ handHtml + status_html+'</td>';
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
		changeClassStatus: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'classSearch');
				layer.closeAll('page');
			}else{
				layer.alert(result.errCode, {icon: 2});
			}	
		},
		enrollClass: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'classSearch');
				layer.closeAll('page');
			}else{
				layer.alert(result.errCode, {icon: 2});
			}	
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
				tbodyHTML += '<tr id="'+elements[key].studentId+'" data-studentid="'+elements[key].studentId+'" >';
				tbodyHTML += '<td class="name">'+elements[key].name+'</td>';
				//tbodyHTML += '<td class="studentId">'+elements[key].studentId+'</td>';
				tbodyHTML += '<td class="sex llas-nowrap">'+elements[key].sex+'</td>';
				tbodyHTML += '<td class="school llas-nowrap">'+elements[key].school+'</td>';
				tbodyHTML += '<td class="attendYear" data-value="'+elements[key].attendYear+'">'+elements[key].attendYear+'年</td>';
				tbodyHTML += '<td class="guarderName">'+elements[key].guarderName+'</td>';
				tbodyHTML += '<td class="guarder" data-value="'+elements[key].guarder+'">'+studentManager.guarderList[elements[key].guarder]+'</td>';
				tbodyHTML += '<td class="guarderPhone">'+elements[key].guarderPhone+'</td>';
				tbodyHTML += '<td><a href="javascript:;" data-id="'+elements[key].studentId+'" onclick="studentManager.lookSource(this);">查看</a></td>';
                tbodyHTML += '<td>'+elements[key].totalFee+'</td>';
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
		enrollInfo: function(result){
			
			var $tbody = $('#enroll-class-table').find('tbody'), tbodyHTML = '', elements = result.elements[0];
			if(result.elements.length > 0){
				for(var key in elements.enrollClass){
					tbodyHTML += '<tr id="'+elements.enrollClass[key].classId+'">';
					tbodyHTML += '<td class="className">'+elements.enrollClass[key].className+'</td>';
					tbodyHTML += '<td class="teacherName">'+elements.enrollClass[key].teacherName+'</td>';
					
//					tbodyHTML += '<td>';
//					if(elements.enrollClass[key].classCount > elements.enrollClass[key].classInfos[0].checkinCount){
//						tbodyHTML += '<span class="" style="llas-success-text">进行中</span>';
//					}else{
//						tbodyHTML += '<span class="" style="llas-success-text">已结束</span>';
//					}
//					tbodyHTML += '</td>';
					tbodyHTML += '<td class="startDate llas-nowrap">'+elements.enrollClass[key].classScore+'</td>';
					tbodyHTML += '</tr>';
				}
			}else{
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			if(result.elements.length > 0 && elements.scoreLevel){
				$('#enroll-course-grade').val(elements.scoreLevel.scoreIndex);
			}
			var dPage = {
				"data":"",
				"id":"pagination-class",
			    "maxshowpageitem":5,
			    "pagelistcount":5
			}
			page.init(result.totalElements, result.pageNumber, dPage);
			
		},
		studentScore: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'studentSearch');
				layer.closeAll('page');
			}else{
				layer.alert('操作失败，请您稍后重试', {icon: 2});
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
		},
		statisticSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].classId+'">';
				tbodyHTML += '<td class="classId">'+elements[key].classId+'</td>';
				tbodyHTML += '<td class="className">'+elements[key].className+'</td>';
				tbodyHTML += '<td class="teacherName">'+elements[key].teacherName+'</td>';
				tbodyHTML += '<td class="startDate llas-nowrap">'+tool.formatDate(elements[key].startDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="endDate llas-nowrap">'+tool.formatDate(elements[key].endDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="attendCount">'+elements[key].attendCount+'</td>';
				tbodyHTML += '<td class="studentCount">'+elements[key].studentCount+'</td>';
				tbodyHTML += '<td>¥<span class="teacherSalary">'+elements[key].teacherSalary+'</span></td>';
				tbodyHTML += '<td>¥<span class="teacherMinFee">'+elements[key].teacherMinFee+'</span></td>';
				tbodyHTML += '<td>¥<span class="teacherRateFee">'+elements[key].teacherRateFee+'</span></td>';
				tbodyHTML += '<td>¥<span class="totalFee">'+elements[key].totalFee+'</span></td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);			
		},
		userSearch: function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', status = '激活', targetStatus = 'false', targetText = '停用';
			if(result.length > 0){
				for(var i in result){
					status = '激活'; 
					targetStatus = 'false'; 
					targetText = '停用';
					if(!result[i].enable || result[i].enable == 'false') {
						status = '停用';
						targetStatus = 'true';
						targetText = '激活';
					}
					tbodyHTML += '<tr id="user-'+result[i].id+'">';
					tbodyHTML += '<td class="account">'+result[i].account+'</td>';
					tbodyHTML += '<td class="email">'+result[i].email+'</td>';
					tbodyHTML += '<td class="name">'+result[i].name+'</td>';
					tbodyHTML += '<td class="phone">'+result[i].phone+'</td>';
					tbodyHTML += '<td class="resourceId">'
						+'<a role="button" data-toggle="collapse" href="#collapse-'+result[i].id+'" aria-expanded="false" aria-controls="collapseExample">所属组</a>'
						
				
				+'<div class="collapse" id="collapse-'+result[i].id+'">'
				+'<div class="panel panel-success">'
				+'<div class="panel-body">';
				
				for(var j in result[i].role){
					tbodyHTML += '<div class="res-item" data-resid="'+result[i].role[j].id+'">&nbsp;&nbsp;'+result[i].role[j].name+'</div>';
					
				}
				tbodyHTML +='</div>'
						  +'</div>'
						  +'</div>'
						  +'</div></td>';
					tbodyHTML += '<td class="createTime llas-nowrap">'+tool.formatDate(result[i].createTime, 'YYYY-MM-DD')+'</td>';
					tbodyHTML += '<td class="modifyTime llas-nowrap">'+tool.formatDate(result[i].modifyTime, 'YYYY-MM-DD')+'</td>';
					tbodyHTML += '<td class="enable" data-value="'+result[i].enable+'">'+status+'</td>';
					tbodyHTML += '<td>';
					tbodyHTML += '<button type="button" class="btn btn-link llas-left" data-id="'+result[i].id+'" data-value="'+targetStatus+'" onclick="sysManager.changeStatus(this, \'changeUserStatus\');">'+targetText+'</button>';
					tbodyHTML += '<span class="btn-link llas-left">|</span>';
					tbodyHTML += '<button type="button" class="btn btn-link llas-left" data-id="'+result[i].id+'" onclick="sysManager.changePassword(this, \'changeUserPassword\');">修改密码</button>';
					tbodyHTML += '</td>';
					tbodyHTML += '</tr>';
				}
			}else{
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			tool.highlightingOperationRow(llas.currentRowId);				
		},
		createUser: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'userSearch');
				layer.closeAll('page');
			}else{
				layer.alert('创建失败，请检查您填写的信息是否合理!', {icon: 2});
			}		
		},
		changeUserStatus: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'userSearch');
				layer.closeAll('page');
			}else{
				layer.alert(result.errCode, {icon: 2});
			}			
		},
		changeUserPassword: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'userSearch');
				layer.closeAll('page');
			}else{
				layer.alert(result.errCode, {icon: 2});
			}	
		},
		registerSearch: function(result){

			var channelList = ['微信','前台'],
				registerModeList = ['报名','退课'],
				payModeList = ['微信支付','支付宝', 'POS机', '现金', '其他'];
			
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].id+'">';
				tbodyHTML += '<td>'+elements[key].classInfos[0].classId+'</td>';
				tbodyHTML += '<td class="className">'+elements[key].classInfos[0].className+'</td>';
				tbodyHTML += '<td class="startDate llas-nowrap">'+elements[key].classInfos[0].teacherName+'</td>';
				tbodyHTML += '<td class="endDate llas-nowrap">'+elements[key].studentInfos[0].name+'</td>';
				tbodyHTML += '<td class="classTime">'+elements[key].studentInfos[0].school+'</td>';
				tbodyHTML += '<td class="classCount">'+elements[key].studentInfos[0].attendYear+'年</td>';
				tbodyHTML += '<td class="teacherName">'+payModeList[elements[key].payMode]+'</td>';
				tbodyHTML += '<td class="classRoom">'+tool.formatDate(elements[key].registerDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td class="registerCount">¥'+elements[key].payFee+'</td>';
				tbodyHTML += '<td class="registerCount">'+channelList[elements[key].registerChannel]+'</td>';
				tbodyHTML += '<td class="classDescription" data-value="'+tool.html_encode(elements[key].note)+'"><div class="note-text-div"><span class="glyphicon glyphicon-eye-open" data-container="#date-body" data-toggle="tooltip" data-placement="left" title="'+tool.html_encode(elements[key].note)+'"></span></div></td>';
				tbodyHTML += '<td class="registerCount">'+registerModeList[elements[key].registerMode]+'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			$('span[data-toggle="tooltip"]').tooltip();
			tool.highlightingOperationRow(llas.currentRowId);
		},
		resourceSearch: function(result){

			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].id+'">';
				tbodyHTML += '<td class="registerCount">'+elements[key].id+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].menuUrl+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].createTime+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].modifyTime+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].action+'</td>';
				tbodyHTML += '<td class="registerCount">'+elements[key].description+'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);			
			
		},
		roleSearch: function(result){
			
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].id+'" data-enable="'+elements[key].enable+'">';
				tbodyHTML += '<td>'+elements[key].id+'</td>';
				tbodyHTML += '<td class="name">'+elements[key].name+'</td>';
				tbodyHTML += '<td class="createTime">'+elements[key].createTime+'</td>';
				tbodyHTML += '<td class="modifyTime">'+elements[key].modifyTime+'</td>';
				tbodyHTML += '<td class="resourceId">'
							+'<a role="button" data-toggle="collapse" href="#collapse-'+elements[key].id+'" aria-expanded="false" aria-controls="collapseExample">权限列表</a>'
							
					
					+'<div class="collapse" id="collapse-'+elements[key].id+'">'
					+'<div class="panel panel-success">'
					+'<div class="panel-body">';
					
					for(var i in elements[key].resource){
						tbodyHTML += '<div class="res-item" data-resid="'+elements[key].resource[i].id+'">'+elements[key].resource[i].description+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+elements[key].resource[i].menuUrl+'</div>';
						
					}
					tbodyHTML +='</div>'
							  +'</div>'
							  +'</div>'
							  +'</div></td>';
				var statusStr = '激活';
				if(elements[key].enable == 'false'){
					statusStr = '暂停';
				}
				tbodyHTML += '<td class="">'+statusStr+'</td>';
				tbodyHTML += '<td class="description">'+elements[key].description+'</td>';
				tbodyHTML += '<td><button type="button" class="btn btn-link llas-left" data-id="" onclick="editTableRowData(this, \'editRole\');">编辑</button></td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);			
		},
		resourceList: function(result){
			var $select = $('#c-role-resource'), optionHtml = '';
			
			for(var key in result) {
				optionHtml += '<li><input type="checkbox" value="'+result[key].id+'" onchange="changeResourceIds(this);"/>'+result[key].description+'&nbsp;&nbsp;&nbsp;&nbsp;'+result[key].menuUrl+'</li>';
			}
			if(optionHtml == ''){
				optionHtml = '<option value="" disabled>No data</option>';
			}
			$select.html(optionHtml);
		},
		roleList: function(result){
			var $select = $('#c-role-resource'), optionHtml = '';
			
			for(var key in result) {
				optionHtml += '<li><input type="checkbox" value="'+result[key].id+'" onchange="changeResourceIds(this);"/>&nbsp;&nbsp;'+result[key].name+'</li>';
			}
			if(optionHtml == ''){
				optionHtml = '<option value="" disabled>No data</option>';
			}
			$select.html(optionHtml);
		},		
		editRole: function(result){
			this.createRole(result);
		},
		createRole: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'roleSearch');
				layer.closeAll('page');
			}else{
				layer.alert('创建失败，请检查您填写的信息是否合理!', {icon: 2});
			}	
		},
		discountSearch: function(result){
			
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="'+elements[key].id+'">';
				tbodyHTML += '<td>'+elements[key].id+'</td>';
				tbodyHTML += '<td class="lowerFee">'+elements[key].lowerFee+'</td>';
				tbodyHTML += '<td class="upperFee">'+elements[key].upperFee+'</td>';
				tbodyHTML += '<td class="discountRate">'+elements[key].discountRate+'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
			tool.highlightingOperationRow(llas.currentRowId);			
		},
		createDiscount: function(result){
			if(result.result == 'success'){
				llas.currentRowId = result.errCode;
				formSubmitMethod(null, searchFrom, 'discountSearch');
				layer.closeAll('page');
			}else{
				layer.alert(result.errCode, {icon: 2});
			}	
		},
		teacherRoleList: function(result){
			var optionHTML = '';
			for(var i in result) {
				optionHTML += '<option data-phone="'+result[i].phone+'" value="'+result[i].name+'">'+result[i].name+'</option>'
			}
			$('#c-user-account').replaceWith('<select onchange="fillPhone(this)" class="form-control select-defaule-width required" name="account" id="c-user-account">'+optionHTML+'</select>');
			$('#change-teacher-list').attr('data-target','custom').text('自定义用户');
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
		}if(this.getAttribute('data-type') == 'MULTIPLE'){
			var arr = [], $ul = $('#c-role-resource');
			$target.find('.res-item').each(function(){
				arr.push(this.getAttribute('data-resid'));
				$ul.find('input[value="'+this.getAttribute('data-resid')+'"]').get(0).checked = true;
			});
			this.value = arr.join(',');
//			$('#c-role-resource').find('input[type="checkbox"]').each(function(){
//				
//			});
		}else{
			this.value = text;
		}
	});
	
}
function fillPhone(_this) {
	var phone = $(_this).find('option:selected').attr('data-phone');
	$('#c-user-phone').val(phone);
}
function changeUserRole(_this) {
	if(_this.getAttribute('data-target') == 'teacher') {
		ajaxGetDataMethod({},'/teacher/getall.json', 'get', 'teacherRoleList', null);
	}else{
		$('#c-user-account').replaceWith('<input type="text" class="form-control required" maxlength="32" name="account" id="c-user-account" placeholder="登录名称">	');
		$('#change-teacher-list').attr('data-target','teacher').text('教师列表');
	}
}
function changeTeacherId(_this, elemId) {
	var teacherId = $(_this).find('option:selected').data('id')
	$(elemId).val(teacherId);
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
	},
	systemLoginOut: function(){
		if(cookie.check('username')){
			cookie.del('username');
		}
	}
};
//课程管理模块
var classManager = {
	init: function(){
		//成绩等级
		ajaxGetDataMethod('', '/score/level.json', 'get', 'grade', null);
		//获取教师信息
		ajaxGetDataMethod({},'/teacher/getall.json', 'get', 'teacherList', null);
		//分页信息
		commonManager.page();
	},
    lookStudent: function(_this){
        ajaxGetDataMethod({classId:_this.getAttribute('data-id')},'/class/register.json', 'get', 'studentLook', null);
        
        
		$layerModel = $('#layer-modle-student-source');
		layer.open({
			type: 1,
			title: '查看已报名学生',
			shadeClose: false,
			scrollbar: false,
			zIndex: 110,
			skin: 'layui-layer-rim', 
			btn: ['确定', '取消'],
			btnAlign: 'c',
			btn1: function(index, layero){
					
			},
			btn2: function(index, layero){
				
			},
			area: '740px',
			content: $layerModel,
			end: function(){
				
			}
		 });        
        
        
	},
	changeStatus: function(_this, type){
		var title = '确认删除该课程吗？', icon = 2, classId = _this.getAttribute('data-id'), active = _this.getAttribute('data-value');
		if(!active || active == 'true'){
			title = '确认恢复该课程吗？';
			icon = 1;
		}
		layer.confirm(title, {
			title:'操作确认',
			icon: icon,
			btn: ['取消', '确认'],
			btn2: function(index, layero){
				ajaxGetDataMethod({classId:classId,active:active}, '/class/active.json', 'POST', type, _this);
			}
	    }, function(index, layero){
	    	layer.close(index);
		});	
	},
	enrollClass: function(_this, type){
		ajaxGetDataMethod({grade:_this.getAttribute('data-grade')},'/student/search.json', 'get', type, null);
		$('#enroll-class-id').val(_this.getAttribute('data-id'));
		var $layerModel = $('#layer-enroll-modle');
		layer.open({
			type: 1,
			title: '报名',
			shadeClose: false,
			scrollbar: false,
			zIndex: 110,
			skin: 'layui-layer-rim', 
			btn: ['确定', '取消'],
			btnAlign: 'c',
			btn1: function(index, layero){
				formSubmitMethod(_this, enrollForm, 'enrollClass');
			},
			btn2: function(index, layero){
				enrollForm.reset();
			},
			area: '740px',
			content: $layerModel,
			end: function(){
				enrollForm.reset();
			}
		 });
	},
	dropClassMethod: function (_this, type){

		ajaxGetDataMethod({classId: _this.getAttribute('data-id')}, '/class/register.json', 'get', type, null);
		
		$('#enroll-class-id').val(_this.getAttribute('data-id'));
		var $layerModel = $('#layer-drop-modle');
		layer.open({
			type: 1,
			title: '报名',
			shadeClose: false,
			scrollbar: false,
			zIndex: 110,
			skin: 'layui-layer-rim', 
			btn: ['确定', '取消'],
			btnAlign: 'c',
			btn1: function(index, layero){
				//formSubmitMethod(_this, enrollForm, 'enrollClass');
			},
			btn2: function(index, layero){
				enrollForm.reset();
			},
			area: '740px',
			content: $layerModel,
			end: function(){
				enrollForm.reset();
			}
		 });		
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
		ajaxGetDataMethod('', '/score/level.json', 'get', 'grade', null);
		ajaxGetDataMethod({curPage:1,pageSize: 10000}, '/class/search.json', 'get', 'classList', null);
	},
	lookSource: function(_this){
		var studentId = _this.getAttribute('data-id');
		$('#grade-evaluation-student').val(studentId);
		$('#enroll-class-table').attr('data-student', studentId);
		formSubmitMethod(_this, gradeEvaluationForm, 'enrollInfo');
		this.showDiagol();
//		ajaxGetDataMethod({studentId: studentId, curPage: curPage, pageSize: pageSize},'/wechat/search/enrollinfo.json', 'get', 'enrollInfo', null);
	},
	showDiagol: function(){
		//return false;
		$layerModel = $('#layer-modle-class');
		layer.open({
			type: 1,
			title: '查看已报课程',
			shadeClose: false,
			scrollbar: false,
			zIndex: 110,
			skin: 'layui-layer-rim', 
			btn: ['确定', '取消'],
			btnAlign: 'c',
			btn1: function(index, layero){
				var data = {}, $tbody = $('#enroll-class-table').find('tbody'), classIdArr = [], classScoresArr = [];
				$tbody.find('tr').each(function(){
					var $this = $(this), $scores = $this.find('input.classScores');
					if($scores.length > 0 && $scores.val() >= 0 ){
						classIdArr.push(this.id);
						classScoresArr.push($this.find('input.classScores').val());
					}
				});
				data.classId = classIdArr.join(',');
				data.classScores = classScoresArr.join(',');
				data.scoreLevel = $('#enroll-course-grade').val();
				data.studentId = $('#enroll-class-table').attr('data-student');;
				ajaxGetDataMethod( data, '/student/score.json', 'post', 'studentScore', null );
			},
			btn2: function(index, layero){
				
			},
			area: '740px',
			content: $layerModel,
			end: function(){
				
			}
		 });
	}
};
//打卡管理模块
var attendManager = {
	init: function(){
		commonManager.page();
		//获取教师信息
		ajaxGetDataMethod({},'/teacher/getall.json', 'get', 'teacherList', null);
	},
	checkIn: function(_this){
		var classText = $(_this).parents('tr').find('.className').text();
		
		layer.confirm('确认对&nbsp;&nbsp;"'+classText+'" 进行打卡吗？', {
			title:'打卡确认',
			icon: 1,
			btn: ['取消', '确认'],
			btn2: function(index, layero){
				ajaxGetDataMethod({classId:_this.getAttribute('data-id')}, '/attend/checkin.json', 'POST', 'checkInAfter', _this);
			}
	    }, function(index, layero){
	    	layer.close(index);
		});
		
		
		
	}
};
//打卡统计管理
var statisticManager = {
	init: function(){
		commonManager.page();
		//获取教师信息
		ajaxGetDataMethod({},'/teacher/getall.json', 'get', 'teacherList', null);
	},		
};
//系统管理模块
var sysManager = {
	init: function(){
		ajaxGetDataMethod({},'/role/rolelist.json', 'get', 'roleList', null);
	},
	changeStatus: function(_this, type){
		var title = '确认激活该用户吗？', icon = 1, userId = _this.getAttribute('data-id'), active = _this.getAttribute('data-value');
		if(!active || active == 'false'){
			title = '确认停用该用户吗？';
			icon = 2;
		}
		layer.confirm(title, {
			title:'操作确认',
			icon: icon,
			btn: ['取消', '确认'],
			btn2: function(index, layero){
				ajaxGetDataMethod({userId:userId,active:active}, '/manager/activeuser.json', 'POST', type, _this);
			}
	    }, function(index, layero){
	    	layer.close(index);
		});	
	},
	changePassword: function(_this, type){
		var userId = _this.getAttribute('data-id'), $layerModel = $('#layer-modle-change-password'), password = $('#l-user-change-password').val(), cof_password = '';
		layer.open({
			type: 1,
			title: '修改密码',
			shadeClose: false,
			scrollbar: false,
			zIndex: 110,
			skin: 'layui-layer-rim', 
			btn: ['确定', '取消'],
			btnAlign: 'c',
			btn1: function(index, layero){
				password = $('#user-change-password').val();
				cof_password = $('#l-user-change-password').val();
				if(password != cof_password){
					layer.alert('两次输入密码不一致', {icon: 2});
					return false;
				}
				ajaxGetDataMethod({userId:userId,password:password}, '/manager/changepassword.json', 'POST', type, _this);
			},
			btn2: function(index, layero){
				createForm.reset();
			},
			content: $layerModel,
			end: function(){
				createForm.reset();
			}
		 });
	}
	
};

var registerManager = {
	init: function(){
		commonManager.page();
	},				
};
var roleManager = {
	init: function(){
		commonManager.page();
		//获取资源列表
		ajaxGetDataMethod({},'/resource/resourcelist.json', 'get', 'resourceList', null);
	},				
};
function changeResourceIds(_this) {
	var $ul = $(_this).parents('ul'), $input = $('#resource-list-hidden'), idsArray = [];
	
	$ul.find('input[type="checkbox"]').each(function(){
		if(this.checked){
			idsArray.push(this.value);
		}
	});
	
	$input.val(idsArray.join(','));
}
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
        	var $this, $ul, pageData, type, value;
        	
        	$this    = $(this);
        	$ul      = $this.parents('ul.pagination');
        	pageData = $ul.data();
        	type = pageData.type;
        	value = $this.attr("page-data");
        	
        	if(pageData.target){
        		$('#'+pageData.target).find('.cur-page').val(value);
        		searchByCondition(this, document.getElementById(pageData.target), type);
        	}else{
            	document.getElementById('cur-page').value = value;
            	searchByCondition(this, searchFrom, type);
        	}
            page.setPageListCount(listCount, value, page.fun);
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
jQuery.validator.addMethod("cofirm-password", function(value, element) {
	var $form = $(element).parents('form'), password = $form.find('input[name="password"]').val();
	return this.optional(element) || password == value ;   
}, $.validator.format('两次输入的密码不一致'));
jQuery.validator.addMethod("phone", function(value, element) {
	var reg = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
	return this.optional(element) || reg.test($.trim(value));   
}, $.validator.format('您的密码太简单'));
function ss(_this){
	$(_this).popover('show')
}