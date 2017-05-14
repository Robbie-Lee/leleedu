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
			<div class="sidebar-nav sidebar-nav-active">
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
						<a href="#" class="sidebar-trans">
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
			<div class="sidebar-nav">
				<div class="sidebar-title sidebar-trans">
					<div class="sidebar-title-inner">
						<span class="glyphicon glyphicon-triangle-right"></span>
						<span class="sidebar-title-text">授课管理</span>
					</div>
				</div>
				<ul class="sidebar-trans max-none">
					<li class="nav-item">
						<a href="#" class="sidebar-trans">
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
							<span class="icon iconfont icon-yonghuguanli"></span>
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
						  <li class="active">教师管理</li>
						</ol>
					</div>
				</div>
				<form class="form-inline" name="searchFrom" method="GET" action="/lele/teacher/search.json">
					<div class="row">
						<div class="form-group">
							<label for="teacher-name">教师姓名</label>
  							<input type="text" class="form-control" name="teacherName" id="teacher-name" placeholder="教师姓名">	
						</div>
						<div class="form-group">
							<label for="teacher-number">教师编号</label>
  							<input type="text" class="form-control" id="teacher-number" name="teacherId" placeholder="教师编号">	
						</div>
						<div class="form-group">
							<label for="teacher-gender">性别</label>
  							<select id="teacher-gender" class="form-control select-defaule-width" name="sex">
							  <option value="">请选择</option>
							  <option value="男">男</option>
							  <option value="女">女</option>
							</select>
						</div>
						<div class="form-group">
							<label for="teacher-phone">联系电话</label>
  							<input type="text" name="phone" class="form-control" id="teacher-phone" placeholder="联系人电话">	
						</div>
						<div class="form-group">
							<label for="teacher-status">状态</label>
  							<select id="teacher-status" name="status" class="form-control select-defaule-width">
							  <option value="">请选择</option>
							  <option value="0">正常</option>
							  <option value="1">休假</option>
							  <option value="2">离职</option>
							</select>
						</div>
					</div>
					<input type="hidden" value="${classInfo.pageSize}" name="pageSize" id="page-size"/>
					<input type="hidden" value="${classInfo.pageNumber}" name="curPage" id="cur-page"/>
					<input type="hidden" value="${classInfo.totalElements}" id="total-items"/>
					<div class="row llas-margin-b-20 llas-textright">
						 <button type="button" class="btn btn-primary" onclick="searchByCondition(this, searchFrom, 'teacherSearch');">查询</button>
						 <button type="reset" class="btn btn-primary">重置</button>
						 <button type="button" class="btn btn-primary" onclick="newTableRowData(this, 'createTeacher');">新增</button>
					</div>
				</form>
				
				<#assign status = ['正常','休假','离职']>
				<div class="row table-responsive llas-table-row">
					<table class="table table-bordered table-hover" id="search-table">
					        <thead>
					          <tr>
					            <th>教师姓名</th>
					            <th>教师编号</th>
					            <th>性别</th>
					            <th>学历</th>
					            <th>专业</th>
					            <th>毕业院校</th>
					            <th>年龄</th>
					            <th>教龄</th>
					            <th>课时费比例</th>
					            <th>保底课时费</th>
					            <th>任职时间</th>
					            <th>联系电话</th>
					            <th>状态</th>
					            <th style="width: 50px;">操作</th>
					          </tr>
					        </thead>
					        <tbody>
					        
							<#if (teacherInfo.elements?size > 0)>
			   					<#list teacherInfo.elements as tearch>
					          <tr id="tearch-${tearch['id']}" data-birthday="${tearch['birthDay']}">
					            <td class="teacherName">${tearch['name']}</td>
					            <td class="teacherId">${tearch['teacherId']}</td>
					            <td class="llas-nowrap sex">${tearch['sex']}</td>
					            <td class="llas-nowrap degree">${tearch['degree']}</td>
					            <td class="major">${tearch['major']}</td>
					            <td class="college">${tearch['college']}</td>
					            <td class="age">${tearch['age']}</td>
					            <td class="teachAge">${tearch['teachAge']}</td>
					            <td class="classFeeRate" tearch="llas-nowrap">${tearch['classFeeRate']?string("0.##")}</td>
					            <td>¥<span class="minClassFee">${tearch['minClassFee']}</span></td>
					            <td class="checkInTime">${tearch['checkInTime']}</td>
					            <td class="phone">${tearch['phone']}</td>
					            <td class="status" data-value="${tearch['status']}">${status[tearch['status']]}</td>
					            <td>
					            	<button type="button" class="btn btn-link llas-left" data-id="" onclick="editTableRowData(this, 'editClass');">编辑</button>
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
<div id="layer-modle" class="layer-modle">
	<form class="container-fluid form-inline llas-valid-form error-info-div" name="createForm" method="POST" action="/lele/teacher/create.json">
		<div class="alert alert-danger contact-error">
			<span class="no-data-icon"></span>
			<span class="error-message"></span>
		</div>
		<div class="row">
			<div class="form-group">
				<label for="c-teacher-name">教师姓名</label>
				<input type="text" class="form-control username required" maxlength="16" name="teacherName" id="c-teacher-name" placeholder="教师姓名">	
			</div>
			<div class="form-group">
				<label for="c-teacher-number">教师编号</label>
				<input type="text" class="form-control required" id="c-teacher-number" maxlength="16" name="teacherId" placeholder="教师编号">	
			</div>
			<div class="form-group">
				<label for="c-teacher-gender">性别</label>
				<select id="c-teacher-gender" class="form-control select-defaule-width required" name="sex">
				  <option value="">请选择</option>
				  <option value="男">男</option>
				  <option value="女">女</option>
				</select>
			</div>
			<div class="form-group">
				<label for="c-teacher-degree-create">学历</label>
				<select id="c-teacher-degree-create" name="degree" class="form-control select-defaule-width">
				  <option value="">请选择</option>
				  <option value="0">本科</option>
				  <option value="1">硕士</option>
				  <option value="2">博士</option>
				</select>	
			</div>			
			<div class="form-group">
				<label for="c-teacher-major">专业</label>
				<input type="text" name="major" class="form-control" id="c-teacher-major" placeholder="专业">	
			</div>
			
			<div class="form-group">
				<label for="c-teacher-college">毕业院校</label>
				<input type="text" name="college" class="form-control" id="c-teacher-college" placeholder="毕业院校">	
			</div>
			<div class="form-group">
				<label for="c-teacher-birth">出生年月</label>
				<input type="text" name="birthDay" class="form-control required date-input dateISO" id="c-teacher-birth" placeholder="出生年月">	
			</div>		
			<div class="form-group">
				<label for="c-teacher-teachage">教龄</label>
				<input type="text" name="teachAge" class="form-control required digits" id="c-teacher-teachage" placeholder="教龄">	
			</div>		
			<div class="form-group">
				<label for="c-teacher-check">任职时间</label>
				<input type="text" name="checkInTime" class="form-control required date-input dateISO" id="c-teacher-check" placeholder="任职时间">	
			</div>				
			<div class="form-group">
				<label for="c-teacher-phone">联系电话</label>
				<input type="text" name="phone" class="form-control required phone" id="c-teacher-phone" placeholder="联系人电话">	
			</div>
			<div class="form-group">
				<label for="c-teacher-status">状态</label>
				<select id="c-teacher-status" name="status" class="form-control select-defaule-width required">
				  <option value="">请选择</option>
				  <option value="0">正常</option>
				  <option value="1">休假</option>
				  <option value="2">离职</option>
				</select>
			</div>
			<div class="form-group">
				<label for="c-teacher-rate">课时费比例</label>
				<input type="text" name="classFeeRate" class="form-control required" max=1 id="c-teacher-rate" placeholder="课时费比例">	
			</div>
			<div class="form-group">
				<label for="c-teacher-min">保底课时费</label>
				<input type="text" name="minClassFee" class="form-control required number" id="c-teacher-min" placeholder="保底课时费">	
			</div>
		</div>
	</form>
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
teacherManager.init();
</script>
</body>
</html>
