<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<script type="text/javascript">
    $(function () {
        // 初始化jgGrid插件
        $("#logTable").jqGrid({
            url: "${path}/admin/queryLogByPage",
            datatype: "json",
            colNames: ["ID", "用户", "操作信息", "操作时间", "结果"],
            colModel: [
                {name: "id", align: "center", hidden: true},
                {name: "name", align: "center"},
                {name: 'annotation', align: "center"},
                {name: "time", align: "center"},
                {
                    name: "flag", align: "center",
                    formatter: function (data) {
                        if (data == "1") {
                            return "成功";
                        } else return "失败";
                    }
                }
            ],
            rowNum: 7,
            rowList: [7, 15, 20],
            pager: "#pager",
            sortname: 'id',
            mtype: "post",
            height: "45%",
            caption: "日志信息",
            autowidth: true,
            styleUI:"Bootstrap",
        });
        jQuery("#logTable").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false, search: false}
        );
    });
    function moveLog() {
        $.ajax({
            url: "${path}/user/moveShuJu",
            type: "post",
            dataType: "json",
            success: function (data) {
                alert(data.message);
            }
        });
    }
    //提交按钮事件
    function subLog() {
        $.ajaxFileUpload({
            url: "${path}/user/addShuJu",
            type: "post",
            datatype: "json",
            fileElementId: "log",
            success: function (data) {
                alert("导入成功");
                $("#myModa2").modal("hide");
                $("#logTable").trigger("reloadGrid");
            }
        });
    }
    // 添加文章模态框展示
    function showArticle() {
        $("#LogForm")[0].reset();
        $("#myModa2").modal("show");
    }
</script>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab" data-toggle="tab">用户日志管理</a></li>
        <li><a href="javascript:void(0)" onclick="moveLog()">导出日志信息</a></li>
        <li><a href="javascript:void(0)" onclick="showArticle()">导入日志信息</a></li>
        <li><a>Excel模板下载</a></li>
    </ul>
    <div class="panel">
        <table id="logTable"></table>
        <div id="pager" style="height: 50px"></div>
    </div>
</div>

