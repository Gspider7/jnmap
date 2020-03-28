<!DOCTYPE html>
<html lang="en">
<head>
    <#assign ctx=springMacroRequestContext.contextPath />

    <meta charset="utf-8" />
    <title>注册</title>

    <script src="${ctx}/static/js/jquery-3.3.1.min.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>

    <script>
        function register() {
            var username = $('#username').val();
            var password = $('#password').val();
            var confirmPassword = $('#confirmPassword').val();

            $.ajax({
                type: "post",
                url: "${ctx}/user/sign/req",
                async: false,
                data: {
                    "username": username,
                    "password": password,
                    "confirmPassword": confirmPassword
                },
                success: function (data) {
                    var response = JSON.parse(data);
                    var code = response.code;
                    if (code === 0) {
                        window.location.href = "${ctx}/user/login";
                        // $(window).attr("location", "${ctx}/user/login");
                    } else {
                        alert(response.message);
                    }
                },
                error: function () {

                }
            });
        }
    </script>
</head>

<body>
<!-- 注册表单 -->
    <div class="form-group">
        <label for="username" stype="display:inline;">用户名：</label>
        <input type="text" class="form-control" id="username" style="display:inline;width:200px;" autocomplete="off" />
    </div>
    <div class="form-group">
        <label for="password" style="display:inline;">密码：</label>
        <input type="password" class="form-control" id="password" style="display:inline;width:200px;" autocomplete="off" />
    </div>
    <div class="form-group">
        <label for="confirmPassword" style="display:inline;">确认密码：</label>
        <input type="password" class="form-control" id="confirmPassword" style="display:inline;width:200px;" autocomplete="off" />
    </div>
    <button class="btn btn-primary" onclick="register()">确认注册</button>
</body>
</html>