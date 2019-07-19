<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品列表</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/bootstrap.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/3.css"/>
</head>
<body>
<div class="page-header">
    <h1>商品列表</h1>
</div>
<form id="form" action="${pageContext.request.contextPath}/item/list" method="get">
    <input type="hidden" id="cp" name="page" value="1" />
    <input type="hidden" name="size" value="5" />
    <table class="table table-hover">
        <tr>
            <th class="active">
                <span style="font-size: 26px;">商品名称:</span>
            </th>
            <td class="warning">
                <input type="text" id="name" name="name" value="${name}" class="form-control" placeholder="商品名称" />
            </td>
            <td class="info">
                <button type="submit" class="btn btn-success">查询</button>
            </td>
            <td class="danger">
                <button type="button" onclick="location.href='${pageContext.request.contextPath}/item/add-ui'" class="btn btn-info">添加商品</button>
            </td>
        </tr>
    </table>
</form>
<table class="table">
	<tr class="info">
		<td colspan="7">商品列表:</td>
	</tr>
	<tr class="active">
		<th>商品名称</th>
		<th>商品价格</th>
		<th>生产日期</th>
		<th>商品描述</th>
		<th>展示图片</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${pageInfo.list}" var="item">
		<tr class="success">
			<td style="width:20%;">
				${item.name }
			</td>
			<td>
				${item.price }
			</td>
			<td>
                <fmt:formatDate value="${item.productionDate }" pattern="yyyy-MM-dd"/>
			</td>
			<td style="width:40%;">${item.description }</td>
			<td>
				<img src="${item.pic }" style="width:80px;height:80px;" />
			</td>
			<td>
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/item/update/${item.id}'" class="btn btn-warning">修改</button>
				<button type="button" onclick="del(this,${item.id})" class="btn btn-warning">删除</button>
			</td>
		</tr>
	</c:forEach>
</table>
<div id="page">
    <span style="font-size: 20px;">当前第 ${pageInfo.page} 页 / 共 ${pageInfo.pages} 页  (${pageInfo.total}条)</span>
    <div style="float: right;">
        <c:if test="${pageInfo.page>1}">
            <button type="button" class="btn btn-warning" onclick="page(1)">首页</button>
            <button type="button" class="btn btn-warning" onclick="page(${pageInfo.page-1})">上一页</button>
        </c:if>

        <c:if test="${pageInfo.page<pageInfo.pages}">
            <button type="button" class="btn btn-warning" onclick="page(${pageInfo.page+1})">下一页</button>
            <button type="button" class="btn btn-warning" onclick="page(${pageInfo.pages})">尾页</button>
        </c:if>

    </div>
</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript">
    
    //删除商品信息 obj是按钮的js对象, id是商品的id
    function del(obj, id) {
        if (confirm("是否确定删除该商品??")) {
            $.ajax({
                url:"${pageContext.request.contextPath}/item/del/"+id,
                type: "DELETE",
                dataType:"json",
                success:function (result) {
                    //动态删除指定行
                    if (result.code == 0) {
                        $(obj).parent().parent().remove();
                    }else {
                        alert(result.msg)
                    }
                },
                error:function (result) {
                    alert("服务器异常")
                }
            })

        }
    }
    
    
    //分页
    function page(page) {
        //1.选中隐藏域page,重新赋值
        $("#cp").val(page)
        //2.选中form表单,提交
        $("#form").submit();
    }
</script>
</html>