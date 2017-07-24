<#include "/common/head.ftl">
<body>
<div class="">
	<#include "/common/topbar.ftl">
	<div class="llas-body llas-sidebar-full">
		<#include "/common/sidebar.ftl">
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
				<form class="form-inline" name="searchFrom" method="GET" action="/attend/search.json">
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
										  <ul class="pagination" id="pagination" data-type="attendSearch"></ul>
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
<#include "/common/footer.ftl">
<script>
attendManager.init();
</script>
</body>
</html>
