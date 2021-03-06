<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../public/bootstrap/css/.min.css">
<link rel="stylesheet" type="text/css" href="../public/common/css/ion.calendar.css">
<link rel="stylesheet" type="text/css" href="../public/common/css/main.css">
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
		<div class="llas-content">
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
							<label for="course-number">课程号</label>
  							<input type="text" class="form-control" name="" id="course-number" placeholder="课程号">	
						</div>
						<div class="form-group">
							<label for="course-name">课程名称</label>
  							<input type="text" class="form-control" id="course-name" placeholder="课程名称">	
						</div>
						<div class="form-group">
							<label for="course-teacher">授课教师</label>
  							<select id="course-teacher" class="form-control select-defaule-width">
							  <option value="">请选择</option>
							  <option value="1">王老师</option>
							  <option value="2">李老师</option>
							  <option value="3">刘老师</option>
							  <option value="4">郭老师</option>
							</select>
						</div>
						<div class="form-group">
							<label for="course-address">上课地点</label>
  							<input type="text" class="form-control" id="course-address" placeholder="上课地点">	
						</div>	
						<div class="form-group">
							<label for="course-start">开始日期</label>
	  						<input type="text" class="form-control date-input" id="course-start" placeholder="开始日期">	
						</div>
						<div class="form-group">
							<label for="course-end">结束日期</label>
  							<input type="text" class="form-control date-input" id="course-end" placeholder="结束日期">	
						</div>
						<div class="form-group">
							<label for="course-time">上课时间</label>
	  						<input type="text" class="form-control" id="course-time" placeholder="上课时间">	
						</div>	
						<div class="form-group">
							<label for="course-grade">最低成绩</label>
  							<select id="course-grade" class="form-control select-defaule-width">
							  <option value="">A级（普通班）</option>
							  <option value="1">B级（普通班 +提高班）</option>
							  <option value="2">B级（普通班 +提高班＋尖子班）</option>
							</select>
						</div>

						<div class="form-group">
							<div class="checkbox">
							  <label for="course-discount">
							    <input id="course-discount" type="checkbox" value="">
							    	是否接受折扣
							  </label>
							</div>
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
					            <th>课程号</th>
					            <th>课程名称</th>
					            <th>开始时间</th>
					            <th>结束时间</th>
					            <th>上课时间</th>
					            <th>课次</th>
					            <th>上课地点</th>
					            <th>授课老师</th>
					            <th>价格</th>
					            <th>最低成绩</th>
					            <th>是否折扣</th>
					            <th>已报人数</th>
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
					            <td>北京</td>
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
<script src="../public/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="../public/bootstrap/js/bootstrap.min.js"></script>
<script src="../public/bootstrap/js/bootstrap-select.min.js"></script>
<script src="../public/layer/layer.js"></script>
<script src="../public/common/js/moment/moment.min.js"></script>
<script src="../public/common/js/moment/moment.zh-cn.js"></script>
<script src="../public/common/js/ion.calendar.min.js"></script>
<script src="../public/common/js/custom.js"></script>
</body>
</html>
