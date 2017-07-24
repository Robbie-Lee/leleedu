<#assign leve1Menu={"class":"infoManager","student":"infoManager","teacher":"infoManager","attend":"teachManager","statistic":"teachManager","manager":"sysManager"}>

<!-- sidebar start -->
<div class="llas-sidebar">
	<div class="sidebar-switch scope">
		<div class="sidebar-fold"><span class="glyphicon glyphicon-menu-left"></span></div>
	</div>
	<div class="sidebar-nav <#if leve1Menu[_cur_menu.level1] == "infoManager"> sidebar-nav-active</#if>">
		<div class="sidebar-title sidebar-trans">
			<div class="sidebar-title-inner">
				<span class="glyphicon glyphicon-triangle-right"></span>
				<span class="sidebar-title-text">信息管理</span>
			</div>
		</div>
		<ul class="sidebar-trans max-none">
			<li class="nav-item">
				<a href="/class/manager.do" class="sidebar-trans <#if _cur_menu.level1 == "class">active</#if>">
					<span class="icon iconfont icon-course"></span>
					<span class="nav-title">课程管理</span>
				</a>
			</li>
			<li class="nav-item">
				<a href="/student/manager.do" class="sidebar-trans <#if _cur_menu.level1 == "student">active</#if>">
					<span class="icon iconfont icon-xueshengguanli"></span>
					<span class="nav-title">学生管理</span>
				</a>
			</li>
			<li class="nav-item">
				<a href="/teacher/manager.do" class="sidebar-trans <#if _cur_menu.level1 == "teacher">active</#if>">
					<span class="icon iconfont icon-tubiaofuben81"></span>
					<span class="nav-title">教师管理</span>
				</a>
			</li>
		</ul>
	</div>
	<div class="sidebar-nav <#if leve1Menu[_cur_menu.level1] == "teachManager"> sidebar-nav-active</#if>">
		<div class="sidebar-title sidebar-trans">
			<div class="sidebar-title-inner">
				<span class="glyphicon glyphicon-triangle-right"></span>
				<span class="sidebar-title-text">授课管理</span>
			</div>
		</div>
		<ul class="sidebar-trans max-none">
			<li class="nav-item">
				<a href="/attend/manager.do" class="sidebar-trans <#if _cur_menu.level1 == "attend">active</#if>">
					<span class="icon iconfont icon-daqia"></span>
					<span class="nav-title">授课打卡</span>
				</a>
			</li>
			<li class="nav-item">
				<a href="/statistic/manager.do" class="sidebar-trans <#if _cur_menu.level1 == "statistic">active</#if>">
					<span class="icon iconfont icon-tongji"></span>
					<span class="nav-title">授课统计</span>
				</a>
			</li>					
		</ul>
	</div>
	<div class="sidebar-nav <#if leve1Menu[_cur_menu.level1] == "sysManager"> sidebar-nav-active</#if>">
		<div class="sidebar-title sidebar-trans">
			<div class="sidebar-title-inner">
				<span class="glyphicon glyphicon-triangle-right"></span>
				<span class="sidebar-title-text">系统管理</span>
			</div>
		</div>
		<ul class="sidebar-trans max-none">
			<li class="nav-item">
				<a href="/manager/user.do" class="sidebar-trans <#if _cur_menu.level1 == "manager">active</#if>">
					<span class="icon iconfont icon-tubiaofuben81"></span>
					<span class="nav-title">用户管理</span>
				</a>
			</li>
		</ul>
	</div>			
</div>
<!-- sidebar end -->