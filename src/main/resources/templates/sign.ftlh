<!DOCTYPE html>
<html lang="zh-CN">

<#assign ctx=springMacroRequestContext.contextPath />

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${ctx}/static/icon/favicon.ico">

    <title>注册</title>

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
        <h2 class="form-signin-heading">请注册</h2>
        <label for="inputUsername" class="sr-only">用户名</label>
        <input type="text" id="inputUsername" class="form-control" placeholder="请输入用户名" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码" required>
        <label for="inputConfirmPassword" class="sr-only">确认密码</label>
        <input type="password" id="inputConfirmPassword" class="form-control" placeholder="请再次输入密码" required>
        <button class="btn btn-lg btn-primary btn-block" onclick="register()">注册</button>
<#--
    </form>
-->

</div> <!-- /container -->

<script src="${ctx}/static/js/jquery.min.js"></script>
<script src="${ctx}/static/js/jquery.md5.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var ctx = "${ctx}";

    function register() {
        const username = $("#inputUsername").val();
        const password = $("#inputPassword").val();
        const confirmPassword = $("#inputConfirmPassword").val();

        $.ajax({
            method: "post",
            url: ctx + "/user/sign/req",
            datatype: "json",
            async: false,
            data: {
                username: username,
                password: password,
                confirmPassword: confirmPassword
            },
            success: function (result) {
                const response = JSON.parse(result);
                const code = response.code;
                if (code === 0) {
                    window.location.href = ctx + "/user/login";
                } else {
                    alert(response.message);
                }
            }
        });
    }
</script>

</body>
</html>
