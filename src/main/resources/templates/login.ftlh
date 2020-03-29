<!DOCTYPE html>
<html lang="zh-CN">

<#assign ctx=springMacroRequestContext.contextPath />

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${ctx}/static/icon/favicon.ico">

    <title>登录</title>

    <!-- Bootstrap core CSS -->
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="${ctx}/static/css/login.css" rel="stylesheet">
</head>

<body>

<div class="container">

<#--
    <form class="form-signin">
-->
        <h2 class="form-signin-heading">请登录</h2>
        <label for="inputUsername" class="sr-only">用户名</label>
        <input type="text" id="inputUsername" class="form-control" placeholder="请输入用户名" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" onclick="login()">登录</button>
        <button class="btn btn-lg btn-primary btn-block" onclick="register()">注册</button>
<#--
    </form>
-->

</div> <!-- /container -->

<script src="${ctx}/static/js/jquery.min.js"></script>
<script src="${ctx}/static/js/jquery.md5.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function register() {
        window.location.href = "${ctx}/user/sign";
    }

    function login() {
        const username = $("#inputUsername").val();
        const password = $.md5($("#inputPassword").val());
        $.ajax({
            method: "post",
            url: "${ctx}/user/login/req",
            datatype: "json",
            async: false,
            data: {
                username: username,
                password: password
            },
            success: function (result) {
                const response = JSON.parse(result);
                const code = response.code;
                if (code === 0) {
                    window.location.href = "${ctx}/vuln/test";
                } else {
                    alert(response.message);
                }
            }
        });
    }
</script>

</body>
</html>
