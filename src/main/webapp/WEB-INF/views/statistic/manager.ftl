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
						  <li><a href="#">信息管理</a></li>
						  <li class="active">课程管理</li>
						</ol>
					</div>
				</div>
				<form class="form-inline" name="searchFrom" method="GET" action="/statistic/search.json">
					<div class="row">
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
					<input type="hidden" value="${classStatistic.pageSize}" name="pageSize" id="page-size"/>
					<input type="hidden" value="${classStatistic.pageNumber}" name="curPage" id="cur-page"/>
					<input type="hidden" value="${classStatistic.totalElements}" id="total-items"/>
					<div class="row llas-margin-b-20 llas-textright">
						 <button type="button" class="btn btn-primary" onclick="searchByCondition(this, searchFrom, 'statisticSearch');">查询</button>
						 <button type="reset" class="btn btn-primary">重置</button>
					</div>
				</form>
				<div class="row table-responsive llas-table-row">
					<table class="table table-bordered table-hover" id="search-table">
					        <thead>
					          <tr>
					          	<th>课程号</th>
					          	<th>课程名称</th>
					            <th>授课教师</th>
					            <th>统计开始日期</th>
					            <th>统计结束日期</th>
					            <th>上课次数</th>
					            <th>上课人次</th>
					            <th>每人次课时费</th>
					            <th>保底课时费</th>
					            <th>比例课时费</th>
					            <th>应付课时费</th>
					          </tr>
					        </thead>
					        <tbody>
					        
							<#if (classStatistic.elements?size > 0)>
			   					<#list classStatistic.elements as statistic>
					          <tr id="${statistic['classId']}">
					          	<td>${statistic['classId']}</td>
					          	<td>${statistic['className']}</td>
					            <td class="teacherName">${statistic['teacherName']}</td>
					            <td class="startDate">${statistic['startDate']}</td>
					            <td class="endDate llas-nowrap">${statistic['endDate']}</td>
					            <td class="attendCount">${statistic['attendCount']}</td>
					            <td class="studentCount">${statistic['studentCount']}</td>
					            <td>¥<span class="teacherSalary">${statistic['teacherSalary']}</span></td>
					            <td>¥<span class="teacherMinFee">${statistic['teacherMinFee']}</span></td>
					            <td>¥<span class="teacherRateFee">${statistic['teacherRateFee']}</span></td>
					            <td>¥<span class="totalFee">${statistic['totalFee']}</span></td>
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
										  <ul class="pagination" id="pagination" data-type="statisticSearch"></ul>
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
statisticManager.init();
</script>
</body>
</html>
