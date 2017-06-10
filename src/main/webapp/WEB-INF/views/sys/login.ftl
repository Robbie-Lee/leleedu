<#include "/common/head.ftl">
<body id="login" class="login-body">
	<div class="llas-header">
		<div class="topbar-head llas-left clearfix">
			<a class="topbar-btn topbar-logo llas-left" href="/lele" title="">
				<img src="/lele/resources/common/images/logo.png" alt="Logo">
			</a>
			<!--<h2 class="topbar-title llas-left">乐乐教育信息系统管理</h2>-->
		</div>
		<div class="topbar-head topbar-user llas-right clearfix">
				<a href="#" class="topbar-link-user">
					<span class="glyphicon glyphicon-user"></span>
				</a>
		</div>
	</div>
	
<div class="llas-login-panel">
<form class="form-horizontal llas-valid-form" method="GET" action="/lele/login.json" name="llasLoginForm">
<h2 class="llas-login-title llas-textcenter">乐乐教育</h2>
  <div class="form-group">
    <div class="col-sm-12">
      <input type="text" name="loginName" class="form-control required username" autocomplete="off" id="inputEmail3" maxlength="32" placeholder="用户名">
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-12">
      <input type="password" name="password" class="form-control required" autocomplete="off" id="inputPassword3" minlength="6" maxlength="32" placeholder="密码">
    </div>
  </div>
<div class="form-group">
    <div class="col-sm-10">
      <div class="checkbox">
        <label>
          <input type="checkbox" name="rememberMe" value="true"> Remember me
        </label>
      </div>
    </div>
  </div>  
  <div class="form-group">
    <div class="col-sm-12">
      <button type="button" class="btn btn-primary btn-lg btn-block"  onclick="loginSystem(this, llasLoginForm);">进入</button>
    </div>
  </div>

<div id="login-error-info" class="alert alert-danger sr-only"  role="alert">
  <label id="login-error-text" class="margin0"></label>
</div>  
</form>
</div>
<script src="/lele/resources/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="/lele/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="/lele/resources/layer/layer.js"></script>
<script src="/lele/resources/common/js/jquery/jquery.validate.js"></script>
<script src="/lele/resources/common/js/custom.js"></script>

</body>
</html>
