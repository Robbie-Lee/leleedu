<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/lele/resources/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/lele/resources/common/css/ion.calendar.css">
<link rel="stylesheet" type="text/css" href="/lele/resources/common/css/main.css">
<title>乐乐教育信息系统管理</title>
</head>
<body>
<div class="">
	<!-- Header start-->
	<div class="llas-header">
		<div class="topbar-head llas-left clearfix">
			<a class="topbar-btn topbar-logo llas-left" href="#" title="">LOGO</a>
			<h2 class="topbar-title llas-left">乐乐教育信息系统管理</h2>
		</div>
		<div class="topbar-head topbar-user llas-right clearfix">
				<a href="#" class="topbar-link-user">
					<span class="glyphicon glyphicon-user"></span>
				</a>
				<div class="dropdown llas-left">
				  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    刘老师
				    <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu2">
				    <li><a href="#">个人中心</a></li>
				    <li><a href="#">退出</a></li>
				  </ul>
				</div>
		</div>
	</div>
	<!-- header end -->
	<div class="llas-body llas-sidebar-full">
		<!-- sidebar start -->
		<div class="llas-sidebar">
			<div class="sidebar-switch scope">
				<div class="sidebar-fold"><span class="glyphicon glyphicon-menu-left"></span></div>
			</div>
			<div class="sidebar-nav">
				<div class="sidebar-title sidebar-trans">
					<div class="sidebar-title-inner">
						<span class="glyphicon glyphicon-triangle-right"></span>
						<span class="sidebar-title-text">信息管理</span>
					</div>
				</div>
				<ul class="sidebar-trans max-none">
					<li class="nav-item">
						<a href="/lele/class/manager.do" class="sidebar-trans">
							<span class="icon iconfont icon-course"></span>
							<span class="nav-title">课程管理</span>
						</a>
					</li>
					<li class="nav-item">
						<a href="/lele/student/manager.do" class="sidebar-trans">
							<span class="icon iconfont icon-xueshengguanli"></span>
							<span class="nav-title">学生管理</span>
						</a>
					</li>
					<li class="nav-item">
						<a href="/lele/teacher/manager.do" class="sidebar-trans">
							<span class="icon iconfont icon-tubiaofuben81"></span>
							<span class="nav-title">教师管理</span>
						</a>
					</li>
				</ul>
			</div>
			<div class="sidebar-nav sidebar-nav-active">
				<div class="sidebar-title sidebar-trans">
					<div class="sidebar-title-inner">
						<span class="glyphicon glyphicon-triangle-right"></span>
						<span class="sidebar-title-text">授课管理</span>
					</div>
				</div>
				<ul class="sidebar-trans max-none">
					<li class="nav-item">
						<a href="/lele/attend/manager.do" class="sidebar-trans">
							<span class="icon iconfont icon-daqia"></span>
							<span class="nav-title">授课打卡</span>
						</a>
					</li>
					<li class="nav-item">
						<a href="#" class="sidebar-trans">
							<span class="icon iconfont icon-tongji"></span>
							<span class="nav-title">授课统计</span>
						</a>
					</li>					
				</ul>
			</div>
			<div class="sidebar-nav">
				<div class="sidebar-title sidebar-trans">
					<div class="sidebar-title-inner">
						<span class="glyphicon glyphicon-triangle-right"></span>
						<span class="sidebar-title-text">系统管理</span>
					</div>
				</div>
				<ul class="sidebar-trans max-none">
					<li class="nav-item">
						<a href="#" class="sidebar-trans">
							<span class="icon iconfont icon-tubiaofuben81"></span>
							<span class="nav-title">用户管理</span>
						</a>
					</li>
					
				</ul>
			</div>			
		</div>
		<!-- sidebar end -->
		<div class="llas-content" id="date-body">
			<div class="container-fluid">
				<div class="row">
					<div class="">
						<ol class="breadcrumb">
						  <li><a href="#">授课管理</a></li>
						  <li class="active">授课打卡</li>
						</ol>
					</div>
				</div>
				<form class="form-inline" name="searchFrom" method="GET" action="/lele/attend/search.json">
					<div class="row">
						<div class="form-group">
							<label for="course-number">课程号</label>
  							<input type="text" class="form-control" name="classId" id="course-number" placeholder="课程号">	
						</div>
						<div class="form-group">
							<label for="course-name">课程名称</label>
  							<input type="text" class="form-control" name="className" id="course-name" placeholder="课程名称">	
						</div>
						<div class="form-group">
							<label for="course-teacher">授课教师</label>
  							<select id="course-teacher" class="form-control select-defaule-width teacher-list" name="teacherName"></select>
						</div>
						<div class="form-group">
							<label for="course-start">开始日期</label>
	  						<input type="text" class="form-control date-input" data-per="date-body" name="startDate" id="course-start" placeholder="开始日期">	
						</div>
						<div class="form-group">
							<label for="course-end">结束日期</label>
  							<input type="text" class="form-control date-input" data-per="date-body" name="endDate" id="course-end" placeholder="结束日期">	
						</div>
					</div>
					<input type="hidden" value="${classAttend.pageSize}" name="pageSize" id="page-size"/>
					<input type="hidden" value="${classAttend.pageNumber}" name="curPage" id="cur-page"/>
					<input type="hidden" value="${classAttend.totalElements}" id="total-items"/>
					<div class="row llas-margin-b-20 llas-textright">
						 <button type="button" class="btn btn-primary" onclick="searchByCondition(this, searchFrom, 'attendSearch');">查询</button>
						 <button type="reset" class="btn btn-primary">重置</button>
					</div>
				</form>
				<div class="row table-responsive llas-table-row">
					<table class="table table-bordered table-hover" id="search-table">
					        <thead>
					          <tr>
					            <th>课程号</th>
					            <th>课程名称</th>
					            <th>开始时间</th>
					            <th>结束时间</th>
					            <th>上课时间</th>
					            <th>课次</th>
					            <th>上课地点</th>
					            <th>授课老师</th>
					            <th>当前课次</th>
					            <th style="width: 50px;">操作</th>
					          </tr>
					        </thead>
					        <tbody>
					        
							<#if (classAttend.elements?size > 0)>
			   					<#list classAttend.elements as attend>
					          <tr id="class-${class['id']}">
					            <td class="classId">${attend['classId']}</td>
					            <td class="className">${attend['className']}</td>
					            <td class="startDate llas-nowrap">${attend['startDate']}</td>
					            <td class="endDate llas-nowrap">${attend['endDate']}</td>
					            <td class="classTime">${attend['classTime']}</td>
					            <td class="classCount">${attend['classCount']}</td>
					            <td class="classRoom">${attend['classRoom']}</td>
					            <td class="teacherName">${attend['teacherName']}</td>
				            	<td class="registerCount">${attend['checkinCount']}</td>
					            <td>
					            	<#if attend['checkinCount'] < attend['classCount'] >
						            	<button type="button" class="btn btn-link llas-left" data-id="${attend['classId']}" onclick="attendManager.checkIn(this);">打卡</button>
				            		<#else>
					            		<span class="llas-success-text">结束</span>
				            		</#if>
					            </td>
					          </tr>
					         	 </#list>
							<#else>
							   <tr class="no-data-tr">
							  		<td colspan="100"><span class="no-data-icon">您还没有数据，赶紧去创建吧！</span></td>
							   </tr>
							</#if>
					        </tbody>
					        <tfoot>
					        	<tr>
					        		<td colspan="100">
										<nav aria-label="Page navigation" class="llas-right">
										  <ul class="pagination" id="pagination"></ul>
										</nav>						        		
					        		</td>
					        	</tr>
					        </tfoot>
					      </table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="/lele/resources/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="/lele/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="/lele/resources/layer/layer.js"></script>
<script src="/lele/resources/common/js/moment/moment.min.js"></script>
<script src="/lele/resources/common/js/moment/moment.zh-cn.js"></script>
<script src="/lele/resources/common/js/ion.calendar.min.js"></script>
<script src="/lele/resources/common/js/jquery/jquery.validate.js"></script>
<script src="/lele/resources/common/js/custom.js"></script>
<script>
attendManager.init();
</script>
</body>
</html>
