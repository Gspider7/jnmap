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
    <form class="form-signin" action="${ctx}/user/sign/req" method="post">
-->
    <form id="sign-form" class="form-signin">
        <h2 class="form-signin-heading">请注册</h2>
        <label for="inputUsername" class="sr-only">用户名</label>
        <input type="text" id="inputUsername" name="username" class="form-control" placeholder="请输入用户名" required autofocus>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码" required onchange="onPasswordChange()">
        <label for="inputConfirmPassword" class="sr-only">确认密码</label>
        <input type="password" id="inputConfirmPassword" class="form-control" placeholder="请再次输入密码" required onchange="onConfirmPasswordChange()">
        <input type="hidden" id="hiddenPassword" name="password">
        <input type="hidden" id="hiddenConfirmPassword" name="confirmPassword">
<#--
        <button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
-->
        <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="register()">注册</button>
    </form>

</div> <!-- /container -->

<script src="${ctx}/static/js/jquery.min.js"></script>
<script src="${ctx}/static/js/jquery.md5.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function onPasswordChange() {
        const password = $("#inputPassword").val();
        $("#hiddenPassword").val($.md5(password));
    }

    function onConfirmPasswordChange() {
        const confirmPassword = $("#inputConfirmPassword").val();
        $("#hiddenConfirmPassword").val($.md5(confirmPassword));
    }

    function register() {
        // 执行表单校验
        let inputList = $("#sign-form").find("input");
        for (let i=0; i<inputList.length; i++) {
            if (!inputList[i].checkValidity()) return;
        }

        const username = $("#inputUsername").val();
        const password = $.md5($("#inputPassword").val());
        const confirmPassword = $.md5($("#inputConfirmPassword").val());
        $.ajax({
            method: "post",
            url: "${ctx}/user/sign/req",
            datatype: "json",
            async: false,
            data: {
                username: username,
                password: password,
                confirmPassword: confirmPassword
            },
            beforeSend: function() {

            },
            success: function (result) {
                const response = JSON.parse(result);
                const code = response.code;
                if (code === 0) {
                    window.location.href = "${ctx}/user/login";
                } else {
                    alert(response.message);
                }
            }
        });
    }
</script>

</body>
</html>
