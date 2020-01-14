<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<script type="text/javascript">
    $(function () {
        // 初始化jgGrid插件
        $("#adminTable").jqGrid({
            url: "${path}/admin/queryAdminByPage",
            datatype: "json",
            colNames: ["ID", "用户名", "盐","权限"],
            colModel: [
                {name: "id", align: "center"},
                {name: "username", align: "center"},
                {name: "salt", align: "center"},
                {name: "resources", align: "center"},
            ],
            rowNum: 5,
            rowList: [5, 10, 15, 20],
            pager: "#pager",
            sortname: 'id',
            height: "45%",
            mtype: "post",
            caption: "后台用户管理",
            autowidth: true,
            styleUI: "Bootstrap"
        });
        jQuery("#adminTable").jqGrid('navGrid', '#pager', {edit: true, add: true, del: true, search: true}
        );
    });
    //修改按钮事件
</script>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab" data-toggle="tab">管理员列表</a></li>
    </ul>
    <div class="panel">
        <table id="adminTable"></table>
        <div id="pager" style="height: 50px"></div>
    </div>
</div>


