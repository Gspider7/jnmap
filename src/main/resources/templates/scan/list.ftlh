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

<div class="container" style="width: 80%">

    <table id="scanListTable" class="table table-striped">
        <thead>
            <td>扫描名称</td>
            <td>目标网段</td>
            <td>操作</td>
        </thead>
        <#list scanList as scan>
            <tr>
                <td>${scan.name}</td>
                <td>${scan.target}</td>
                <td>
                    <button onclick="executeScan(this.value)" value="${scan.id}">执行</button>
                    <button onclick="deleteScan(this.value)" value="${scan.id}">删除</button>
                    <button id="v${scan.id}" onclick="viewReport(this.value)" value="${scan.id}" disabled="disabled">查看报告</button>
                </td>
            </tr>
        </#list>
    </table>
    <button class="btn btn-primary" data-toggle="modal" data-target="#addScanModal">
        添加
    </button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="addScanModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        添加扫描配置
                    </h4>
                </div>
                <div class="modal-body">
                    <label for="inputScanName">名称</label>
                    <input type="text" id="inputScanName" class="form-control" placeholder="请输入自定义名称" required autofocus>
                    <label for="inputScanTarget">目标网段</label>
                    <input type="text" id="inputScanTarget" class="form-control" placeholder="请输入目标网段" required autofocus>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="addScan()">确认</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

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
            var message = JSON.parse(event.data);
            var id = message.id;
            var name = message.name;
            if ("success" === message.status) {
                $("#v" + id).prop("disabled", false);
                alert("扫描'" + name + "'执行完成！");
            }
            if ("fail" === message.status) {
                alert("扫描'" + name + "'执行失败！");
            }
            // todo： 停止动画
        };
        websocket.onclose = function () {
            // ...
        };
    }

    function addScan() {
        var name = $("#inputScanName").val();
        var target = $("#inputScanTarget").val();

        $.ajax({
            method: "post",
            url: "${ctx}/scan/add",
            datatype: "json",
            async: false,
            data: {
                name: name,
                target: target
            },
            success: function (result) {
                const response = JSON.parse(result);
                const code = response.code;
                if (code === 0) {
                    window.location.href = "${ctx}/scan/list";
                } else {
                    alert(response.message);
                }
            }
        });
    }

    function deleteScan(id) {
        $.ajax({
            method: "post",
            url: "${ctx}/scan/delete",
            datatype: "json",
            async: false,
            data: {
                id: id
            },
            success: function (result) {
                const response = JSON.parse(result);
                const code = response.code;
                if (code === 0) {
                    window.location.href = "${ctx}/scan/list";
                } else {
                    alert(response.message);
                }
            }
        });
    }

    function executeScan(id) {
        $.ajax({
            method: "post",
            url: "${ctx}/scan/execute",
            datatype: "json",
            async: false,
            data: {
                id: id
            },
            success: function (result) {
                const response = JSON.parse(result);
                const code = response.code;
                if (code === 0) {
                    // todo: 添加动画效果
                } else {
                    alert(response.message);
                }
            }
        });
    }

    function viewReport(id) {
        window.open("${ctx}/scan/vuln-report?scanId=" + id);
    }
</script>

</body>
</html>
