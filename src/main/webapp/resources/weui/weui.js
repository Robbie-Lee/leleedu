/*Jquery function*/
$(function(){
	if($('#class-list-div').length > 0){
		$(window).scroll(function () {
	        var scrollTop = $(this).scrollTop();
	        var scrollHeight = $(document).height();
	        var windowHeight = $(this).height();
	        if (scrollTop + windowHeight == scrollHeight) {
	        	loadMoreData();
	        }
	    });
	}
	if($("#weui-signup-form").length > 0){
    $.mvalidateExtend({
        phone:{
            required : true,   
            pattern : /^0?1[3|4|5|8][0-9]\d{8}$/,
            each:function(){
               
            },
            descriptions:{
                required : '<div class="field-invalidmsg">请输入手机号码</div>',
                pattern : '<div class="field-invalidmsg">您输入的手机号码格式不正确</div>',
                valid : '<div class="field-validmsg">正确</div>'
            }
        }
    });
    $("#weui-signup-form").mvalidate({
        type:1,
        onKeyup:true,
        sendForm: false,
        firstInvalidFocus:false,
        valid:function(event,options){
            //点击提交按钮时,表单通过验证触发函数
            //alert("验证通过！接下来可以做你想做的事情啦！");
             event.preventDefault();
             signup();
        },
        invalid:function(event, status, options){
            //点击提交按钮时,表单未通过验证触发函数
        },
        eachField:function(event,status,options){
            //点击提交按钮时,表单每个输入域触发这个函数 this 执向当前表单输入域，是jquery对象
        },
        eachValidField:function(val){},
        eachInvalidField:function(event, status, options){},
        conditional:{
            confirmpwd:function(){
                return $("#pwd").val()==$("#confirmpwd").val();
            }
        },
        descriptions:{
        	name:{
                required : '请输入姓名'
            },
            sex:{
                required : '请选择性别'
            },
            grade:{
                required : '请选择年级'
            },
            guarder:{
                required : '请选择监护人'
            },
            guarderName:{
                required : '请输入监护人姓名'
            }
        }
    });
	}
	$('#signup-button').on('click',function(){
		var $tooltips, $form, data, url, type;
		$form = $('#weui-signup-form');

		
		data = $form.serialize(); 
		url = $form.attr('action'); 
		type = $form.attr('method');
		
		$.ajax({
			url: url,
			type: type,
			data: data,
			dataType: 'json',
		})
		.done(function(result){
			if(result.result == 'success'){
				
			}else{
				
			}
		})
		.fail(function(){
			
		});
	});
});
function signup(){
	var $tooltips, $form, data, url, type;
	$form = $('#weui-signup-form');

	
	data = $form.serialize(); 
	url = $form.attr('action'); 
	type = $form.attr('method');
	
	$.ajax({
		url: url,
		type: type,
		data: data,
		dataType: 'json',
	})
	.done(function(result){
		if(result.result == 'success'){
			window.location.href= '/wechat/enroll.do?wechatid=' + $('#student-id').val();
		}else{
			dialogMethod(result.errCode);
		}
	})
	.fail(function(){
		dialogMethod('注册失败，请您稍后重新注册');
	});	
}
var showErrorMsg = function( msg ) {
	
};
function html_encode (str){
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
}

function loadingToastMethod(isLoading) {
	var $loadingToast = $('#loadingToast');
    if(isLoading){
    	 if ($loadingToast.css('display') != 'none') return;
    	$loadingToast.fadeIn(100);
    }else{
    	$loadingToast.fadeOut(100);
    }
}


function dialogMethod (msg){
	var $iosDialog2 = $('#iosDialog2');
	$iosDialog2.find('.dialog-notice').text(msg);
	$iosDialog2.fadeIn(200);
}
function hideDialogMethod(_this){
	$(_this).parents('.js_dialog').fadeOut(200);
}
function toDecimal2(x) { 
	var f = parseFloat(x); 
	if (isNaN(f)) { 
		return false; 
	}
	var f = Math.round(x*100)/100; 
	var s = f.toString(); 
	var rs = s.indexOf('.'); 
	if (rs < 0) { 
		rs = s.length; 
		s += '.'; 
	}
	while (s.length <= rs + 2) { 
		s += '0'; 
	} 
	return s; 
}
function loadMoreData() {
	var data = {};
	data.classGrade = classManager.cur_grade;
	data.pageSize = $('#page-size').val();
	data.curPage = $('#cur-page').val();
	if(!classManager.last_page){
		classManager.ajaxSearch(data, true);
		$('#loadmore-div').show();
		$('#nomore-div').hide();
	}
}
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
function cancel(_this){
	var $this, $prev, $cur, data;
	$this  = $(_this);
	data   = $this.data();
	$prev  = $('#' + data.prev);
	$cur   = $('#' + data.cur);
	$cur.fadeOut('fast', function(){
		$prev.fadeIn('fast');
	});
}
var classManager = {
	cur_grade: 0,
	last_page: false,
	searchClass: function(_this){
		var data = {}, _t = this;
		loadingToastMethod(true);
		this.cur_grade = _this.getAttribute('data-grade');
		data.classGrade = this.cur_grade;
		data.pageSize = 5;
		data.curPage = 1;
		this.ajaxSearch(data, false);
	},
	ajaxSearch: function(data, isMore){
		var _t = this;
		$.ajax({
			url: 'search/class.json',
			type: 'GET',
			data: data,
			dataType: 'json',
		})
		.done(function(result){
			_t.appendClassHtml(result, isMore);
			loadingToastMethod(false);
		})
		.fail(function(){
			loadingToastMethod(false);
		});		
	},
	appendClassHtml: function(result, isMore){
		var i, $c = $('#class-list-div'), $list = $c.find('div.class-list'), classListHtml = ''
			$gradeSelect = $('#grade-select-div'), studentID = $('#student-id').val();
		for( i in result.elements ) {
			classListHtml +=    '<div class="weui-media-box weui-media-box_text">'+
								'	<h4 class="weui-media-box__title">'+result.elements[i].className+'</h4>'+
								'	<p class="weui-media-box__desc"><label>开始日期:</label> <span>'+result.elements[i].startDate+'</span></p>'+
								'	<p class="weui-media-box__desc"><label>结束日期:</label> <span>'+result.elements[i].endDate+'</span></p>'+
								'	<p class="weui-media-box__desc"><label>上课时间:</label> <span>'+result.elements[i].classTime+'</span></p>'+
								'	<p class="weui-media-box__desc"><label>上地地点:</label> <span>'+result.elements[i].classRoom+'</span></p>'+
								'	<p class="weui-media-box__desc"><label>总课时:</label> <span>'+result.elements[i].classCount+'</span></p>'+
								'	<p class="weui-media-box__desc"><label>授课老师:</label> <span>'+result.elements[i].teacherName+'</span></p>'+
								'	<p class="weui-media-box__desc"><label>课程费用:</label> <span>'+result.elements[i].classPrice+'</span></p>'+
								'	<a href="javascript:;" onclick="classManager.getFee(this)" class="weui-btn weui-btn_primary" data-title="'+html_encode(result.elements[i].className)+'" data-note="'+html_encode(result.elements[i].classDescription)+'" data-classid="'+result.elements[i].classId+'" data-student="'+studentID+'">我要报名</a>'+
								'</div>';
		}
		if(isMore){
			$list.append(classListHtml);
		}else{
			$list.html(classListHtml);
			$c.show();
			$gradeSelect.hide();
		}
		this.changePage(result);
	},
	changePage: function(result){
		if(result.lastPage || result.lastPage == 'true'){
			this.last_page = true;
			$('#loadmore-div').hide();
			$('#nomore-div').show();
		}else{
			$('#cur-page').val(result.nextPageNumber);
		}
	},
	cur_class: null,
	cur_student: null,
	cur_classInfo: null,
	getFee: function(_this){
		var $this = $(_this), data = {}, _t = this, param = $this.data();
		loadingToastMethod(true);
		data.classId = param.classid;
		data.studentId = param.student;
		this.cur_class = param.classid;
		this.cur_student = param.student;
		
		this.cur_classInfo = param;
		
		$.ajax({
			url: 'search/discount.json',
			type: 'GET',
			data: data,
			dataType: 'json',
		})
		.done(function(result){
			if(result.discountFee && result.discountFee > 0){
				_t.classEnroll(result.discountFee, param);
				_t.cur_classInfo.fee = result.discountFee;
			}else{
				dialogMethod(result.errCode);
			}
			loadingToastMethod(false);
		})
		.fail(function(){
			loadingToastMethod(false);
			dialogMethod('请求失败，请您稍后再试');
		});			
	},
	classEnroll: function(fee, param){
		loadingToastMethod(true);
		var data = {},_t = this;
		
		data.classId   = param.classid;
		data.studentId = param.student;
		data.fee       = fee;
		data.payMode   = 0;
		
		$.ajax({
			url: 'enroll.json',
			type: 'POST',
			data: data,
			dataType: 'json',
		})
		.done(function(result){
			loadingToastMethod(false);
			
			if(result.result == 'success'){
				$('#class-list-div').hide();
				var $pay = $('#class-pay-div');
				$pay.find('.pay-price').text('¥' + toDecimal2(fee));
				$pay.find('.class-title').text(param.title);
				$pay.find('.class-note').text(param.note);
				$pay.show();

			}else{
				dialogMethod(result.errCode);
			}
			
		})
		.fail(function(){
			loadingToastMethod(false);
			dialogMethod('请求失败，请您稍后再试');
		});	
	},
	requestData: null,
	getWechatPrepay: function(){
		var data = {}, _t = this;
		data.clientIp = returnCitySN["cip"];
		data.payFee = _t.cur_classInfo.fee;
		data.studentId = _t.cur_classInfo.student;
		data.classId = _t.cur_classInfo.classid;
		console.log('in');
		$.ajax({
			url: '/wechatPay/prepay.json',
			type: 'GET',
			data: data,
			dataType: 'json',
		})
		.done(function(result){
			if(result.status == 0){
				_t.requestData = result;
				console.log(JSON.stringify(result));
				_t.getBrandPayRequest();
			}else{
				dialogMethod(result.errMsg);	
			}
		})
		.fail(function(){
			loadingToastMethod(false);
			dialogMethod('请求失败，请您稍后再试');
		});	
	},
	
	getBrandPayRequest: function(){
//		var TestData = {
//			"appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入     
//	        "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数     
//	        "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串     
//	        "package":"prepay_id=u802345jgfjsdfgsdg888",     
//	        "signType":"MD5",         //微信签名方式：     
//	        "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
//		};
		var _t = this, reD = _t.requestData;
		var data = {
			"appId": reD.appId,
			"timeStamp": reD.timeStamp,
			"signType": reD.signType,
			"nonceStr": reD.nonceStr,
			"package": reD.prepayId,
			"paySign": reD.paySign
		};

		WeixinJSBridge.invoke('getBrandWCPayRequest', data, function(res){
			    console.log(res.errMsg);
			    if(res.errMsg == 'get_brand_wcpay_request:ok'){
			    	_t.clientpayback('true');
			    }else{
			    	_t.clientpayback('false');
			    }
	   });

	},
	clientpayback: function(flag){
		var data = {};
		data.result = flag;
		data.prepayId = this.requestData.prepayId || '';
		var _t = this;
		console.log(JSON.stringify(data));
		$.ajax({
			url: '/wechatPay/clientpayback.json',
			type: 'GET',
			data: data,
			dataType: 'json',
		})
		.done(function(result){
			$('#class-pay-div').hide();
			if(result == 1){
				$('#enroll-success-div').find('.weui-msg__title').text(_t.cur_classInfo.title + '报名成功');
				$('#enroll-success-div').show();
			}else{
				$('#enroll-fail-div').find('.weui-msg__title').text(_t.cur_classInfo.title + '报名失败');
				$('#enroll-fail-div').show();
			}
		})
//		.fail(function(){
//			dialogMethod('报名失败');
//		});	
	},
};