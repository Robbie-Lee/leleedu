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
						  <li class="active">学生管理</li>
						</ol>
					</div>
				</div>
				<#assign gradeList = ['请选择','一年级','二年级','三年级','四年级','五年级','六年级']>
				<#assign guarderList = ['请选择','爸爸','妈妈','爷爷','奶奶','姥爷','姥姥','其他']>
				<form class="form-inline" name="searchFrom" method="GET" action="/student/search.json">
					<div class="row">
						<div class="form-group">
							<label for="student-name">学生姓名</label>
  							<input type="text" class="form-control" name="studentName" id="student-name" placeholder="学生姓名">	
						</div>
						<div class="form-group">
							<label for="student-number">学号</label>
  							<input type="text" class="form-control" id="student-number" name="studentId" placeholder="学号">	
						</div>
						<div class="form-group">
							<label for="student-gender">性别</label>
  							<select id="student-gender" class="form-control select-defaule-width" name="sex">
							  <option value="">请选择</option>
							  <option value="男">男</option>
							  <option value="女">女</option>
							</select>
						</div>
						<div class="form-group">
							<label for="student-grade">入学年份</label>
                            <input type="text" class="form-control" id="student-grade" name="attendYear" placeholder="入学年份">
						</div>	
						<div class="form-group">
							<label for="student-contacts-name">联系人姓名</label>
	  						<input type="text" class="form-control" id="student-contacts-name" name="guarderName" placeholder="联系人姓名">	
						</div>
						<div class="form-group">
							<label for="student-contacts-name">联系人电话</label>
  							<input type="text" class="form-control" id="student-contacts-name" name="guarderPhone" placeholder="联系人电话">	
						</div>
					</div>
					<input type="hidden" value="${studentInfo.pageSize}" name="pageSize" id="page-size"/>
					<input type="hidden" value="${studentInfo.pageNumber}" name="curPage" id="cur-page"/>
					<input type="hidden" value="${studentInfo.totalElements}" id="total-items"/>
					<div class="row llas-margin-b-20 llas-textright">
						 <button type="button" class="btn btn-primary" onclick="searchByCondition(this, searchFrom, 'studentSearch');">查询</button>
						 <button type="reset" class="btn btn-primary">重置</button>
						 <button type="button" class="btn btn-primary" onclick="newTableRowData(this, 'createStudent');">新增</button>
					</div>
				</form>
				<div class="row table-responsive llas-table-row">
					<table class="table table-bordered table-hover" id="search-table">
					        <thead>
					          <tr>
					            <th>学生姓名</th>
					            <!--<th>学号</th>-->
					            <th>性别</th>
					            <th>就读学校</th>
					            <th>入学年份</th>
					            <th>联系人姓名</th>
					            <th>联系人关系</th>
					            <th>联系人电话</th>
					            <th>已报课程</th>
					            <th>累计学费</th>
					            <th>成绩评定</th>
					            <th>折扣等级</th>
					            <th>说明</th>
					            <th>操作</th>
					          </tr>
					        </thead>
					        <tbody>
							<#if (studentInfo.elements?size > 0)>
			   					<#list studentInfo.elements as student>
					          <tr id="${student['studentId']}" data-studentid="${student['studentId']}">
					            <td class="name">${student['name']}</td>
					            <!--<td class="studentId">${student['studentId']}</td>-->
					            <td class="llas-nowrap sex">${student['sex']}</td>
					            <td class="llas-nowrap school">${student['school']}</td>
					            <td class="attendYear" data-value="${student['attendYear']}">${student['attendYear']}年</td>
					            <td class="guarderName">${student['guarderName']}</td>
					            <td class="guarder" data-value="${student['guarder']}">${guarderList[student['guarder']]}</td>
					            <td class="guarderPhone">${student['guarderPhone']}</td>
					            <td class="examine"><a href="javascript:;" data-id="${student['studentId']}" onclick="studentManager.lookSource(this);">查看</a></td>
					            <td>${student['totalFee']}</td>
					            <td class="scoreLevel llas-nowrap" data-value="${class['scoreLevel'].scoreIndex}">${student['scoreLevel'].scoreDescription}</td>
					            <td class="discountRate">${student['discountRate']?string("0.##")}</td>
					            <td class="note" data-value="${student['note']?html}">
					            	<div class="note-text-div">
					            		<span class="glyphicon glyphicon-eye-open" data-container="#date-body" data-toggle="tooltip" data-placement="left" title="${student['note']?html}">
					            		</span>
					            	 </div>
					            </td>
					            <td>
					            	<button type="button" class="btn btn-link llas-left" data-id="" onclick="editTableRowData(this, 'editStudent');">编辑</button>
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
										  <ul class="pagination" id="pagination" data-type="studentSearch"></ul>
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
	<form class="container-fluid form-inline llas-valid-form error-info-div" name="createForm" method="POST" action="/student/create.json">
		<div class="alert alert-danger contact-error">
			<span class="no-data-icon"></span>
			<span class="error-message"></span>
		</div>
		<div class="row">
			<div class="form-group">
				<label for="c-student-name">学生姓名</label>
				<input type="text" class="form-control required" name="name" id="c-student-name" placeholder="学生姓名" maxlength="16">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-student-id">学号</label>
				<input type="text" class="form-control required" name="studentId" id="c-student-id" placeholder="学号" maxlength="32">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-student-sex">性别</label>
  				<select id="c-student-sex" class="form-control select-defaule-width required" name="sex">
					<option value="">请选择</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-student-grade">入学年份</label>
				<input type="text" class="form-control required" id="c-student-grade" name="attendYear" maxlength="4" placeholder="入学年份">
				<span class="llas-error-inco"></span>
			</div>	
			<div class="form-group">
				<label for="c-student-school">就读学校</label>
				<input type="text" class="form-control" name="school" id="c-student-school" placeholder="就读学校" maxlength="64">	
				<span class="llas-error-inco"></span>
			</div>	
			<div class="form-group">
				<label for="c-student-guarder">联系人</label>
				<select id="c-student-guarder" class="form-control select-defaule-width required" name="guarder">
				<#list guarderList as guarder>
					<#if guarder_index == 0>
					<option value="">${guarder}</option>
					<#else>
					<option value="${guarder_index}">${guarder}</option>
					</#if>
				</#list>
				</select>
				<span class="llas-error-inco"></span>
			</div>				
			<div class="form-group">
				<label for="c-student-contacts-name">联系人姓名</label>
				<input type="text" class="form-control required" id="c-student-contacts-name" name="guarderName" placeholder="联系人姓名" maxlength="16">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-student-contacts-name">联系人电话</label>
				<input type="text" class="form-control phone required" id="c-student-contacts-name" name="guarderPhone" placeholder="联系人电话" maxlength="11">
				<span class="llas-error-inco"></span>	
			</div>
			<div class="form-group">
				<label for="c-student-note">备注说明</label>
				<textarea id="c-student-note" placeholder="备注说明" name="note" class="form-control note" rows="3" maxlength="512"></textarea>
				<span class="llas-error-inco"></span>
			</div>	
		</div>
	</form>
</div>

<div id="layer-modle-class" class="layer-modle">
	<form class="container-fluid form-inline llas-valid-form error-info-div" id="grade-evaluation-form" name="gradeEvaluationForm" method="GET" action="/wechat/search/enrollinfo.json">
		<input type="hidden" value="5" name="pageSize" class="page-size"/>
		<input type="hidden" value="1" name="curPage" class="cur-page"/>
		<input type="hidden" value="" id="total-items"/>
		<input type="hidden" name="studentId" id="grade-evaluation-student"/>
		
		<div class="alert alert-danger contact-error">
			<span class="no-data-icon"></span>
			<span class="error-message"></span>
		</div>
		<div class="row">
				<table class="table table-bordered table-hover" id="enroll-class-table">
			        <thead>
			          <tr>
			            <th>课程名称</th>
			            <th>授课教师</th>
			            <th>状态</th>
			            <th>课程成绩</th>
			          </tr>
			        </thead>
			        <tbody>
					
			        </tbody>
			        <tfoot>
			        	<tr>
			        		<td colspan="100">
								<nav aria-label="Page navigation" class="llas-right">
								  <ul class="pagination" id="pagination-class" data-type="enrollInfo" data-target="grade-evaluation-form"></ul>
								</nav>						        		
			        		</td>
			        	</tr>
			        </tfoot>
			  </table>
				<div class="form-group llas-right" style="margin-top: 15px;">
					<label for="enroll-course-grade">成绩评定</label>
					<select id="enroll-course-grade" class="form-control select-defaule-width course-grade">
					</select>
				</div>
		</div>
	</form>
</div>
<#include "/common/footer.ftl">
<script>
classManager.init();
</script>
</body>
</html>
