<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<#assign ctx=springMacroRequestContext.contextPath />

<body>
<h1>websocket测试</h1>
<div>
    <input id="inputMessage" type="text"/>
    <button type="button" onclick="sendWebsocketMessage()">发送</button>
</div>
<div>
    <textarea id="chatArea" style="width: 800px; height: 600px;">
    </textarea>
</div>
</body>

<script src="${ctx}/static/js/jquery.min.js"></script>
<script type="text/javascript">
    var websocket = null;

    function buildWebsocket() {
        if (websocket != null) return;

        if (!'WebSocket' in window) {
            alert("该浏览器不支持websocket");
        }

        var url = "ws://localhost:8090${ctx}/websocket";
        websocket = new WebSocket(url);

        websocket.onopen = function () {
            // ...
        };
        websocket.onmessage = function (event) {
            var message = event.data;

            $("#chatArea").append("server: ").append(message).append("\n");
        };
        websocket.onclose = function () {
            // ...
        };
    }

    function sendWebsocketMessage() {
        if (websocket == null) buildWebsocket();

        var message = $("#inputMessage").val();
        websocket.send(message);

        $("#chatArea").append("client: ").append(message).append("\n");
    }
</script>
</html>