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
<div class="page__bd">
		<form>
        	<input type="hidden" value="${studentId}" name="studentId" id="student-id">
       		<input type="hidden" value="${enrollinfo.pageSize}" name="pageSize" id="page-size">
       		<input type="hidden" value="${enrollinfo.nextPageNumber}" name="curPage" id="cur-page">
       		<input type="hidden" value="${enrollinfo.lastPage}" id="last-page">
        </form>
	        
	    <div id="class1-list-div" class="list-class-info">
			<div class="weui-panel weui-panel_access">
				<div class="weui-panel__hd"><span>已报课程(${enrollinfo.elements[0].enrollClass?size})</span></div>
				<#if (enrollinfo.elements?size > 0)>
			   		<#list enrollinfo.elements[0].enrollClass as class>
				<div class="weui-panel__bd class-list">
					<div class="weui-media-box weui-media-box_text">
						<h4 class="weui-media-box__title">${class['className']}</h4>
						<p class="weui-media-box__desc"><label>开始日期:</label> <span>${class['startDate']}</span></p>
						<p class="weui-media-box__desc"><label>结束日期:</label> <span>${class['endDate']}</span></p>
						<p class="weui-media-box__desc"><label>上课时间:</label> <span>${class['classTime']}</span></p>
						<p class="weui-media-box__desc"><label>上地地点:</label> <span>${class['classRoom']}</span></p>
						<p class="weui-media-box__desc"><label>总课时:</label> <span>${class['classCount']}</span></p>
						<p class="weui-media-box__desc"><label>授课老师:</label> <span>${class['teacherName']}</span></p>
						<p class="weui-media-box__desc"><label>课程费用:</label> <span>${class['classPrice']}</span></p>
					</div>
				</div>
			</div>
	         	 	</#list>
			<#else>
				<div class="weui-loadmore weui-loadmore_line">
	            	<span class="weui-loadmore__tips">没有更多数据</span>
	        	</div>
			</#if>	    
        </div>
        			<div class="weui-loadmore weui-loadmore_line" id="nomore-div" style="display: none;">
            	<span class="weui-loadmore__tips">没有更多数据</span>
        	</div>
        	<div class="weui-loadmore" id="loadmore-div" style="display: none;">
            	<i class="weui-loading"></i>
            	<span class="weui-loadmore__tips">正在加载</span>
        	</div>	
    </div>
<script src="/resources/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="/resources/common/js/jquery/jquery.validate.js"></script>
<script src="/resources/weui/weui.js"></script>
<script>
$(function(){
	$(window).scroll(function () {
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(document).height();
        var windowHeight = $(this).height();
        if (scrollTop + windowHeight == scrollHeight) {
        	loadMoreData1();
        }
    });
});
function loadMoreData1() {
	var data = {}, lastPage = $('#last-page').val();
	data.studentId = GetRequest()['studentId'];
	data.pageSize = $('#page-size').val();
	data.curPage = $('#cur-page').val();
	if(lastPage != 'true'){
		ajaxSearch(data, true);
		$('#loadmore-div').show();
		$('#nomore-div').hide();
	}
}
function ajaxSearch(data, isMore){
	$.ajax({
		url: '/wechat/search/enrollinfo.json',
		type: 'GET',
		data: data,
		dataType: 'json',
	})
	.done(function(result){
		appendClassHtml(result, isMore);
	})
}

function appendClassHtml(result, isMore){

	var i, $c = $('#class1-list-div'), $list = $c.find('div.class-list'), classListHtml = ''
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
							'</div>';
	}
	if(isMore){
		$list.append(classListHtml);
	}else{
		$list.html(classListHtml);
		$c.show();
		$gradeSelect.hide();
	}
	changePage(result);
}

function changePage(result){
	if(result.lastPage || result.lastPage == 'true'){
		this.last_page = true;
		$('#loadmore-div').hide();
		$('#nomore-div').show();
	}else{
		$('#cur-page').val(result.nextPageNumber);
		$('#last-page').val(result.lastPage);
	}
}
</script>
</body>
</html>
