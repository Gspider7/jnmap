<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<#assign ctx=springMacroRequestContext.contextPath />

<body>
<h1>上传文件测试</h1>
<form action="${ctx}/vuln/upload/req" method="post" enctype="multipart/form-data">
    <p><input type="file" name="file" multiple></p>
    <p><button type="submit">上传</button> </p>
    <img src="${ctx}/static/img/down.gif" />
</form>
</body>
</html>