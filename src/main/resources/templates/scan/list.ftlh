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

    <div style="margin-top: 5%">
    <#if vulnUpdateTime??>
        <h4>
            <span class="label label-info">漏洞库已上传，最近更新时间为：${vulnUpdateTime?datetime}</span>
        </h4>
    <#else>
        <h4>
            <span class="label label-danger">漏洞库缺失，请上传漏洞库</span>
        </h4>
    </#if>
        <form action="${ctx}/vuln/upload/req" method="post" enctype="multipart/form-data"
              class="form-inline">
            <div class="form-group">
                <label for="uploadVuln" class="sr-only">漏洞库</label>
                <input type="file" id="uploadVuln" name="file" multiple style="display: inline-block">
                <button type="submit" class="btn btn-primary btn-sm" onclick="return checkFileSelected();">上传</button>
            </div>
        </form>
    </div>
    <div style="margin-top: 3%">
        <h3>扫描配置列表</h3>
        <table id="scanListTable" class="table table-bordered" style="margin-top: 1%">
            <thead>
            <tr>
                <td>扫描名称</td>
                <td>目标网段</td>
                <td>操作</td>
            </tr>
            </thead>
        <#list scanList as scan>
            <tr>
                <td style="vertical-align: middle">${scan.name}</td>
                <td style="vertical-align: middle">${scan.target}</td>
                <td style="vertical-align: middle">
                    <button class="btn btn-link" onclick="executeScan(this.value)" value="${scan.id}">执行</button>
                    <button class="btn btn-link" onclick="deleteScan(this.value)" value="${scan.id}">删除</button>
                    <button class="btn btn-link disabled" id="v${scan.id}" onclick="viewReport(this.value)"
                            value="${scan.id}" disabled="disabled">查看报告
                    </button>
                </td>
            </tr>
        </#list>
        </table>
    </div>
    <button class="btn btn-primary" data-toggle="modal" data-target="#addScanModal">
        添加
    </button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="addScanModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
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
                    <input type="text" id="inputScanName" class="form-control" placeholder="请输入自定义名称" required
                           autofocus>
                    <label for="inputScanTarget">目标网段</label>
                    <input type="text" id="inputScanTarget" class="form-control" placeholder="请输入目标网段" required
                           autofocus>
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

    $(document).ready(function () {
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
            var type = message.type;

            if ("VULN_UPLOAD" === type) {
                alert("漏洞库上传成功！");
                window.location.href = "${ctx}/scan/list";
            }
            else if ("" === type) {
                let id = message.id;
                let name = message.name;
                if ("success" === message.status) {
                    $("#v" + id).prop("disabled", false)
                            .removeClass("disabled");
                    alert("扫描'" + name + "'执行完成！");
                }
                if ("fail" === message.status) {
                    $("#v" + id).prop("disabled", true)
                            .addClass("disabled");
                    alert("扫描'" + name + "'执行失败！");
                }
            }
        };
        websocket.onclose = function () {
            // ...
        };
    }
    
    function checkFileSelected() {
        var fileInput = $('#uploadVuln').get(0).files[0];
        if (!fileInput) {
            alert("请选择需要上传的漏洞库文件");
            return false;
        }
        return true;
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
