<!DOCTYPE html>
<html lang="en">

<#assign ctx=springMacroRequestContext.contextPath />

<head>
    <meta charset="utf-8">
    <title>用户注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" media="screen" href="${ctx}/static/css/login.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/reset.css" />
</head>

<body>
    <div id="particles-js">
        <div class="login">
            <div class="login-top">
                注册
            </div>
            <div class="login-center clearfix">
                <div class="login-center-img"><img src="${ctx}/static/img/name.png" /></div>
                <div class="login-center-input">
                    <input type="text" id="inputUsername" placeholder="请输入您的用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的用户名'" />
                    <div class="login-center-input-text">用户名</div>
                </div>
            </div>
            <div class="login-center clearfix">
                <div class="login-center-img"><img src="${ctx}/static/img/password.png" /></div>
                <div class="login-center-input">
                    <input type="password" id="inputPassword" placeholder="请输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'" />
                    <div class="login-center-input-text">密码</div>
                </div>
            </div>
            <div class="login-center clearfix">
                <div class="login-center-img"><img src="${ctx}/static/img/password.png" /></div>
                <div class="login-center-input">
                    <input type="password" id="inputConfirmPassword" placeholder="请再次输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'" />
                    <div class="login-center-input-text">请再次输入您的密码</div>
                </div>
            </div>
            <div class="register-button" onclick="register()">
                注册
            </div>
        </div>
        <div class="sk-rotating-plane"></div>
    </div>
    <!-- scripts -->
    <script src="${ctx}/static/js/particles.min.js"></script>
    <script src="${ctx}/static/js/login.js"></script>
    <script src="${ctx}/static/js/jquery.min.js"></script>
    <script src="${ctx}/static/js/jquery.md5.js"></script>
    <script type="text/javascript">
        function register() {
            var username = $("#inputUsername").val();
            var password = $.md5($("#inputPassword").val());
            var confirmPassword = $.md5($("#inputConfirmPassword").val());
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
                    var response = JSON.parse(result);
                    var code = response.code;
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