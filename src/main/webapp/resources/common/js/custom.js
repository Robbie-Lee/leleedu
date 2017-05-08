/**
 * Prototype Mehtod to Clone new Node
 */
var defContainer = $("<div id='defContainer'></div>");
$.def = function (selector) {
    return defContainer.children(selector).clone().removeClass('def none');
};
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
	vaild: {
		username: /^[0-9a-zA-Z]+$/,
		password: [/[0-9]+/,/[a-zA-Z]+/,/((?=[\x21-\x7e]+)[^A-Za-z0-9])/],
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
	tableHandHTML: '<button type="button" class="btn btn-link llas-left" data-id="" onclick="editTableRowData(this, \'course\');">编辑</button><span class="btn-link llas-left">|</span><button type="button" class="btn btn-link llas-left" data-id="1" onclick="deleteTableRowData(this, \'course\');">删除</button>'
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

/*Jquery function*/
$(function(){
	tool.autoSwitchIcon2Active();
	tool.autoLogin();
	if($('.def').length > 0) $('.def').appendTo(defContainer);
	$('input.date-input').each(function(){
		$(this).ionDatePicker({
			lang: 'zh-cn',
			format: 'YYYY-MM-DD'
		});
	});
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
	
	//表单验证
	$(".llas-valid-form").validate({});
});
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
			var $gradeSelect = $('#course-grade'), optionsHtml = llas.initSelectStr;
			for(var key in result){
				optionsHtml += '<option value="'+result[key].scoreIndex+'">'+result[key].scoreDescription+'</option>';
			}
			$gradeSelect.html(optionsHtml);
		},
		classSearch:  function(result){
			var $tbody = $('#search-table').find('tbody'), tbodyHTML = '', elements = result.elements;
			for(var key in elements){
				tbodyHTML += '<tr id="class-'+elements[key].id+'">';
				tbodyHTML += '<td>'+elements[key].classId+'</td>';
				tbodyHTML += '<td>'+elements[key].className+'</td>';
				tbodyHTML += '<td>'+tool.formatDate(elements[key].startDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td>'+tool.formatDate(elements[key].endDate, 'YYYY-MM-DD')+'</td>';
				tbodyHTML += '<td>'+elements[key].classTime+'</td>';
				tbodyHTML += '<td>'+elements[key].classCount+'</td>';
				tbodyHTML += '<td>'+elements[key].classRoom+'</td>';
				tbodyHTML += '<td>'+elements[key].teacherName+'</td>';
				tbodyHTML += '<td>'+elements[key].classPrice+'</td>';
				tbodyHTML += '<td>'+elements[key].scoreLevel.scoreDescription+'</td>';
				tbodyHTML += '<td>'+tool.acceptDiscountStr(elements[key].acceptDiscount)+'</td>';
				tbodyHTML += '<td>'+elements[key].registerCount+'</td>';
				tbodyHTML += '<td><div class="note-text-div"><span class="glyphicon glyphicon-eye-open"><span class="note-text">'+elements[key].classDescription+'</span></span></div></td>';
				tbodyHTML += '<td>'+llas.tableHandHTML+'</td>';
				tbodyHTML += '</tr>';
			}
			if('' == tbodyHTML){
				tbodyHTML = llas.noDataTrHTML;
			}
			$tbody.html(tbodyHTML);
			page.init(result.totalElements, result.pageNumber, llas.page);
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
	if($form.hasClass('llas-valid-form') && !$form.valid()){
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
	//表单验证
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
//课程管理模块
var classManager = {
	init: function(){
		//成绩等级
		ajaxGetDataMethod('', '/lele/score/level.json', 'get', 'grade', null);
		//分页信息
		page.init(document.getElementById('total-items').value, document.getElementById('cur-page').value, llas.page);
	}
};


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
       // page.viewPage(currentPage,listCount,page.pagelistcount,page.data)
//      fun(currentPage);
    },
//    //页面显示功能
//     "viewPage":function (currentPage,listCount,pagelistcount,data){
//            var NUM=listCount%pagelistcount==0?listCount/pagelistcount:parseInt(listCount/pagelistcount)+1;
//            if(currentPage==NUM){
//                var result=data.slice((currentPage-1)* pagelistcount,data.length);
//            }
//            else{
//                var result=data.slice((currentPage-1)*pagelistcount,(currentPage-1)*pagelistcount+pagelistcount);
//            }
//            //options.callBack(result);
//    },
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