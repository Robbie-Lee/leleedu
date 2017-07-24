<!-- Header start-->
<div class="llas-header">
	<div class="topbar-head llas-left clearfix">
		<a class="topbar-btn topbar-logo llas-left" href="/lele">
			<img src="/resources/common/images/logo.png" alt="Logo">
		</a>
		<!--<h2 class="topbar-title llas-left">乐乐教育信息系统管理</h2>-->
	</div>
	<div class="topbar-head topbar-user llas-right clearfix">
			<a href="#" class="topbar-link-user">
				<span class="glyphicon glyphicon-user"></span>
			</a>
			<div class="dropdown llas-left">
			  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    ${_cur_user}
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu2">
			   <!-- <li><a href="#">个人中心</a></li> -->
			    <li><a href="/logout.do" onclick="commonManager.systemLoginOut();">退出</a></li>
			  </ul>
			</div>
	</div>
</div>
<!-- header end -->