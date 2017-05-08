<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/lele/resources/bootstrap/css/bootstrap.min.css">
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
						<a href="#" class="sidebar-trans">
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
						<a href="#" class="sidebar-trans">
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
						  <li><a href="#">Home</a></li>
						  <li><a href="#">Library</a></li>
						  <li class="active">Data</li>
						</ol>
					</div>
				</div>
				<form class="form-inline">
					<div class="row">
						<div class="form-group">
							<label for="teacher-name">教师姓名</label>
  							<input type="text" class="form-control" name="" id="teacher-name" placeholder="教师姓名">	
						</div>
						<div class="form-group">
							<label for="teacher-number">教师编号</label>
  							<input type="text" class="form-control" id="teacher-number" placeholder="教师编号">	
						</div>
						<div class="form-group">
							<label for="teacher-gender">性别</label>
  							<select id="teacher-gender" class="form-control select-defaule-width">
							  <option value="">请选择</option>
							  <option value="1">男</option>
							  <option value="2">女</option>
							</select>
						</div>
						<div class="form-group">
							<label for="teacher-phone">联系电话</label>
  							<input type="text" class="form-control date-input" id="teacher-phone" placeholder="联系人电话">	
						</div>
						<div class="form-group">
							<label for="teacher-status">状态</label>
  							<select id="teacher-status" class="form-control select-defaule-width">
							  <option value="">请选择</option>
							</select>
						</div>
					</div>
					<div class="row llas-margin-b-20 llas-textright">
						 <button type="submit" class="btn btn-primary">查询</button>
						 <button type="reset" class="btn btn-primary">重置</button>
						 <button type="button" class="btn btn-primary" onclick="newTableRowData(this, 'course');">新增</button>
					</div>
				</form>
				<div class="row table-responsive llas-table-row">
					<table class="table table-bordered table-hover">
					        <thead>
					          <tr>
					            <th>学生姓名</th>
					            <th>学号</th>
					            <th>性别</th>
					            <th>就读学校</th>
					            <th>年级</th>
					            <th>联系人姓名</th>
					            <th>联系人关系</th>
					            <th>联系人电话</th>
					            <th>已报课程</th>
					            <th>成绩评定</th>
					            <th>折扣等级</th>
					            <th>说明</th>
					            <th>操作</th>
					          </tr>
					        </thead>
					        <tbody>
					          <tr>
					            <td scope="row">1</td>
					            <td>一年级奥数</td>
					            <td>2015/6/12</td>
					            <td>2015/6/12</td>
					            <td>2015/6/12</td>
					            <td>23</td>
					            <td>李老师</td>
					            <td>3000元</td>
					            <td>A级</td>
					            <td>否</td>
					            <td>63</td>
					            <td>
					            	<div class="note-text-div">
					            		<span class="glyphicon glyphicon-eye-open">
					            			<span class="note-text">这是一个说明</span>
					            		</span>
					            	 </div>
					            </td>
					            <td>
					            	<button type="button" class="btn btn-link" data-id="" onclick="editTableRowData(this, 'course');">编辑</button>
					            	<span class="btn-link">|</span>
					            	<button type="button" class="btn btn-link" data-id="1" onclick="deleteTableRowData(this, 'course');">删除</button>
					            </td>
					          </tr>
					        </tbody>
					      </table>
					<nav aria-label="Page navigation" class="llas-right">
					  <ul class="pagination">
					    <li>
					      <a href="#" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					    <li><a href="#">1</a></li>
					    <li><a href="#">2</a></li>
					    <li><a href="#">3</a></li>
					    <li><a href="#">4</a></li>
					    <li><a href="#">5</a></li>
					    <li>
					      <a href="#" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					  </ul>
					</nav>	      
				</div>
			</div>
		</div>
	</div>
</div>
<div id="layer-modle" class="layer-modle"></div>
<script src="/lele/resources/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="/lele/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="/lele/resources/layer/layer.js"></script>
<script src="/lele/resources/common/js/jquery/jquery.validate.js"></script>
<script src="/lele/resources/common/js/custom.js"></script>
</body>
</html>
