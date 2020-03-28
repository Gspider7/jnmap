<!DOCTYPE html>
<html lang="en">
<head>
    <#assign ctx=springMacroRequestContext.contextPath />

    <meta charset="utf-8" />
    <title>登录</title>

    <script src="${ctx}/static/js/jquery-3.3.1.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>

    <script>
        function register() {
            window.location.href = "${ctx}/user/sign";
        }

        function login() {
            var username = $('#username').val();
            var password = $('#password').val();
            $.ajax({
                type: "post",
                url: "${ctx}/user/login/req",
                async:false,
                data: {
                    "username": username,
                    "password": password
                },
                success: function (data, textStatus) {
                    var response = JSON.parse(data);
                    var code = response.code;
                    if (code === 0) {
                        window.location.href = "${ctx}/vuln/test";
                    }
                    else {
                        alert(response.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("请求错误..\n" + errorThrown);
                }
            });
        }
    </script>
</head>
<body>
    <div class="form-group">
        <label for="username" stype="display:inline;">用户名：</label>
        <input type="text" class="form-control" id="username" style="display:inline;width:200px;" autocomplete="off" />
    </div>
    <div class="form-group">
        <label for="password" style="display:inline;">密码：</label>
        <input type="password" class="form-control" id="password" style="display:inline;width:200px;"autocomplete="off" />
    </div>
    <button class="btn btn-primary" onclick="login();">登录</button>
    <button class="btn btn-primary" onclick="register();">注册</button>
</body>
</html>