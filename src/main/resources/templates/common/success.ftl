<html>
<head>
    <meta charset="UTF-8">
    <title>卖家成功提示</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="col-md-12 column">
    <div class="alert alert-dismissable alert-success">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4>成功!</h4>
        <#--!""表示如果msg为空，则默认是空字符串-->
        <strong>${msg!""}</strong>
        <a href="${url}" class="alert-link">3s后自动跳转</a>
    </div>
</div>
</body>

<script>
    setTimeout('location.href="${url}"', 3000);
</script>

</html>