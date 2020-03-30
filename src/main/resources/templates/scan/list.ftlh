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

    <title>扫描配置</title>

    <!-- Bootstrap core CSS -->
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="${ctx}/static/css/login.css" rel="stylesheet">
</head>

<body>

<div class="container">



</div> <!-- /container -->

<script src="${ctx}/static/js/jquery.min.js"></script>
<script src="${ctx}/static/js/jquery.md5.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var websocket = null;

    $(document).ready(function() {
        // dom加载完毕时，初始化websocket连接
        buildWebsocket();


    });

    function buildWebsocket() {
        if (websocket != null) return;
        // if (!'WebSocket' in window) {
        //     alert("该浏览器不支持websocket");
        // }

        var url = "wss://localhost:8443${ctx}/websocket";
        websocket = new WebSocket(url);

        websocket.onopen = function () {
            // ...
        };
        websocket.onmessage = function (event) {
            var message = event.data;
        };
        websocket.onclose = function () {
            // ...
        };
    }
</script>

</body>
</html>
