<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../public/common/css/main.css">
<title>乐乐教育信息系统管理</title>
</head>
<body id="login" class="login-body">
	<div class="llas-header">
		<div class="topbar-head llas-left clearfix">
			<a class="topbar-btn topbar-logo llas-left" href="#" title="">LOGO</a>
			<h2 class="topbar-title llas-left">乐乐教育信息系统管理</h2>
		</div>
		<div class="topbar-head topbar-user llas-right clearfix">
				<a href="#" class="topbar-link-user">
					<span class="glyphicon glyphicon-user"></span>
				</a>
		</div>
	</div>
	
<div class="llas-login-panel">
<form class="form-horizontal" method="post" action="/login.json" name="llasLoginForm">
<h2 class="llas-login-title llas-textcenter">乐乐教育</h2>
  <div class="form-group">
    <!--  <label for="inputEmail3" class="col-sm-4 control-label">Email</label>-->
    <div class="col-sm-12">
      <input type="text" name="loginName" class="form-control input-lg" id="inputEmail3" required="required" placeholder="用户名">
    </div>
  </div>
  <div class="form-group">
    <!--<label for="inputPassword3" class="col-sm-4 control-label">Password</label>-->
    <div class="col-sm-12">
      <input type="password" name="password" class="form-control input-lg" id="inputPassword3" required="required" placeholder="密码">
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-12">
      <button type="button" class="btn btn-primary btn-lg btn-block input-lg" onclick="loginSystem(this, llasLoginForm);">进入</button>
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
<div class="alert alert-danger sr-only"  role="alert">
  <span> Better check yourself, you're not looking too good.</span>
</div>  
</form>
</div>
<script src="../public/common/js/jquery/jquery-1.11.1.min.js"></script>
<script src="../public/bootstrap/js/bootstrap.min.js"></script>
<script src="../public/common/js/jquery/jquery.validate.js"></script>
<script src="../public/common/js/custom.js"></script>

</body>
</html>
