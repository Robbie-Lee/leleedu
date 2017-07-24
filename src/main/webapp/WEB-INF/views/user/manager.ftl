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
						  <li><a href="#">系统管理</a></li>
						  <li class="active">用户管理</li>
						</ol>
					</div>
				</div>
				<form class="form-inline" name="searchFrom" method="GET" action="/manager/searchuser.json">
					<div class="row">
						<div class="form-group">
							<label for="user-name">登录名称</label>
  							<input type="text" class="form-control" name="userName" id="user-name" placeholder="登录名称">	
						</div>
					</div>
					<div class="row llas-margin-b-20 llas-textright">
						 <button type="button" class="btn btn-primary" onclick="searchByCondition(this, searchFrom, 'userSearch');">查询</button>
						 <button type="reset" class="btn btn-primary">重置</button>
						 <button type="button" class="btn btn-primary" onclick="newTableRowData(this, 'createUser');">新增</button>
					</div>
				</form>
				
				<#assign status = {'true':'激活','false':'停用'}>
				<div class="row table-responsive llas-table-row">
					<table class="table table-bordered table-hover" id="search-table">
					        <thead>
					          <tr>
					            <th>登录名称</th>
					            <th>邮箱</th>
					            <th>用户名称</th>
					            <th>联系电话</th>
					            <th>创建时间</th>
					            <th>最后修改时间</th>
					            <th>状态</th>
					            <th style="width: 116px;">操作</th>
					          </tr>
					        </thead>
					        <tbody>
					        
							<#if (userlist?size > 0)>
			   					<#list userlist as user>
					          <tr id="user-${user['id']}">
					            <td class="account">${user['account']}</td>
					            <td class="email">${user['email']}</td>
					            <td class="name">${user['name']}</td>
					            <td class="phone">${user['phone']}</td>
					            <td class="llas-nowrap createTime">${user['createTime']}</td>
					            <td class="llas-nowrap modifyTime">${user['modifyTime']}</td>
					            <td class="enable" data-value="${user['enable']}">${user['enable']?string("激活","停用")}</td>
					            <td>
					            	<button type="button" class="btn btn-link llas-left" data-id="${user['id']}" data-value="${user['enable']?string("false","true")}" onclick="sysManager.changeStatus(this, 'changeUserStatus');">
					            	${user['enable']?string("停用","激活")}
									</button>				            	
					            	<span class="btn-link llas-left">|</span>
					            	<button type="button" class="btn btn-link llas-left" data-id="${user['id']}" onclick="sysManager.changePassword(this, 'changeUserPassword');">修改密码</button>
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
	<form class="container-fluid form-inline llas-valid-form error-info-div" name="createForm" method="POST" action="/manager/adduser.json">
		<div class="alert alert-danger contact-error">
			<span class="no-data-icon"></span>
			<span class="error-message"></span>
		</div>
		<div class="row">
			<div class="form-group">
				<label for="c-user-account">登录名称</label>
				<input type="text" class="form-control required" maxlength="32" name="account" id="c-user-account" placeholder="登录名称">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-user-password">登录密码</label>
				<input type="password" class="form-control required password" id="c-user-password" minlength="8" maxlength="32" name="password" placeholder="密码">
				<span class="llas-error-inco"></span>	
			</div>
			<div class="form-group">
				<label for="c-user-name">用户名称</label>
				<input type="text" name="name" class="form-control required" maxlength="32" id="c-user-name" placeholder="用户名称">	
				<span class="llas-error-inco"></span>
			</div>
			
			<div class="form-group">
				<label for="c-user-email">邮箱</label>
				<input type="text" name="email" class="form-control required email" maxlength="128" id="c-user-email" placeholder="邮箱">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-user-phone">联系电话</label>
				<input type="text" name="phone" class="form-control required phone" id="c-user-phone" placeholder="联系人电话">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="c-user-active">状态</label>
				<select id="c-user-active" name="active" class="form-control select-defaule-width required">
				  <option value="">请选择</option>
				  <option value="true">激活</option>
				  <option value="false">停用</option>
				</select>
				<span class="llas-error-inco"></span>
			</div>
		</div>
	</form>
</div>

<div id="layer-modle-change-password" class="layer-modle">
	<form class="container-fluid form-inline llas-valid-form error-info-div" name="changePasswordForm" method="POST" action="/manager/changepassword.json">
		<div class="alert alert-danger contact-error">
			<span class="no-data-icon"></span>
			<span class="error-message"></span>
		</div>
		<div class="row">
			<div class="form-group">
				<label for="user-change-password">登录密码</label>
				<input type="password" class="form-control required password" minlength="8" maxlength="32" name="password" id="user-change-password" placeholder="登录密码">	
				<span class="llas-error-inco"></span>
			</div>
			<div class="form-group">
				<label for="l-user-change-password">确认密码</label>
				<input type="password" class="form-control required cofirm-password" id="l-user-change-password"  minlength="8" maxlength="32"  placeholder="确认密码">
				<span class="llas-error-inco"></span>	
			</div>
		</div>
	</form>
</div>
<#include "/common/footer.ftl">
</body>
</html>
