<!DOCTYPE html>
<html>
<head>
    <#assign ctx=springMacroRequestContext.contextPath />

    <meta charset="utf-8" />
    <title>登录</title>

    <script src="${ctx}/static/js/jquery-3.3.1.min.js"></script>
    <script src="${ctx}/static/js/bootstrap4.min.js"></script>
    <script src="${ctx}/static/js/popper.min.js"></script>
    <script src="${ctx}/static/css/bootstrap4.min.css"></script>

    <#--
    <!-- 新 Bootstrap4 核心 CSS 文件 &ndash;&gt;
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <!-- popper.min.js 用于弹窗、提示、下拉菜单 &ndash;&gt;
    <script src="https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min.js"></script>
    <!-- 最新的 Bootstrap4 核心 JavaScript 文件 &ndash;&gt;
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    -->

    <script>
        function register() {
            //跳转到注册界面register.html进行注册
            window.open("register.html", "_blank");  //_self,_parent,_top,_blank
        }
        function login() {
            //登录逻辑
            //jQuery写法
            var user = $('#user').val();
            var password = $('#password').val();
            //JavaScript原生写法
            //var user = document.getElementById('user').value;
            //var password = document.getElementById('password').value;
            $.ajax({
                type: "post",  //post put get 等等
                url: "login.ashx",
                //编写登录功能时，要将异步设置为false（缺省为true）
                //如果async是ture,对于FireFox浏览器，会刷新掉alert()弹出框的内容
                //对于Chrome浏览器，第一次注册时会执行error的回调函数，输出“请求在连接过程中出现错误..”
                async:false,
                data: {  //要传入ashx文件的数据
                    "user": user,
                    "password": password
                },
                success: function (data, textStatus) {
                    //连接至ashx文件成功时，执行函数
                    //data是从ashx文件返回来的信息，可以是字符串也可以是一个对象
                    //如果data是字符串，则可以输出data的值
                    //如果data是对象，则可以将这个对象的各属性值赋给其他变量
                    //textStatus是表示状态的字符串，这里textStatus的值是"success"

                    if (data == "admin") {
                        window.open("index.html", "_blank");
                    }
                    else {
                        alert(data);  //这里data是从ashx文件返回的“账户名或密码不存在..
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {  //连接至ashx文件失败时，执行函数
                    //XMLHttpRequest在这个例子里没有用到
                    //textStatus是表示状态的字符串，这里textStatus的值是"error"
                    //errorThrown包含连接失败的信息，可以输出查看
                    alert("请求在连接过程中出现错误..\n" + errorThrown);
                }
            });
        }
    </script>
</head>
<body>
<!-- 登录表单 -->
<form style="margin-left:500px;margin-top:200px;">
    <div class="form-group">
        <label for="user" stype="display:inline;">账户：</label>
        <input type="text" class="form-control" id="user" style="display:inline;width:200px;"autocomplete="off" />
    </div>
    <div class="form-group">
        <label for="password" style="display:inline;">密码：</label>
        <input type="text" class="form-control" id="password" style="display:inline;width:200px;"autocomplete="off" />
    </div>
    <button type="submit" class="btn btn-primary" onclick="login()">登录</button>
    <button type="submit" class="btn btn-primary" onclick="register()">注册</button>
</form>
</body>
</html>