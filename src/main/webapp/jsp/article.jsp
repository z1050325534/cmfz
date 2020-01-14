<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url: "${path}/article/queryByPage",
                datatype: "json",
                colNames: ['id', '标题', '封面', '内容', '创建时间', '出版时间', '状态', '所属上师', '操作'],
                colModel: [
                    {name: 'id', align: "center", hidden: true},
                    {name: 'title', align: "center"},
                    {
                        name: 'image', formatter: function (cellvalue, options, rowObject) {
                            return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                        },
                        align: "center"
                    },
                    {name: 'content', align: "center"},
                    {name: "createDate", align: "center"},
                    {name: 'publishDate', align: "center"},
                    {
                        name: 'status', align: "center", formatter: function (data) {
                            if (data == "1") return "展示";
                            else return "冻结";
                        }
                    },
                    {
                        name: 'guruId',
                        align: "center",
                    },
                    {
                        name: '', formatter: function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + rowObject.id + "')\">修改</button>&nbsp;&nbsp;";
                            button += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('" + rowObject.id + "')\">删除</button>";
                            return button;
                        }
                    }
                ],
                rowNum: 5,
                rowList: [5,10,15,20],
                pager: '#articlePage',
                autowidth: true,
                height: "45%",
                sortname: 'id',
                mtype: "post",
                viewrecords: true,
                caption: "文章展示",
                styleUI: "Bootstrap"
            });
        $("#articleTable").jqGrid('navGrid', '#articlePage', {edit: false, add: false, del: false, search: false});
    });
    $.ajax({
        url: "${path}/guru/selectAllGuru",
        datatype: "json",
        type: "post",
        success: function (data) {
            // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
            // 一定将局部遍历声明在外部
            var option = "";
            data.forEach(function (guru) {
                option += "<option value=" + guru.id + ">" + guru.name + "</option>"
            })
            $("#guru_list").html(option);
        }
    });

    // 添加文章模态框展示
    function showArticle() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id", "");
        $.ajax({
            url: "${path}/guru/selectAllGuru",
            datatype: "json",
            type: "post",
            success: function (data) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option = "";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }
        });
        $("#myModal").modal("show");
    }
    //提交按钮事件
    function sub() {
        $.ajaxFileUpload({
            url: "${path}/article/add",
            type: "post",
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "cover",
            success: function (data) {
                $("#myModal").modal("hide");
                $("#articleTable").trigger("reloadGrid");
            }
        });
    }
    //修改按钮事件
    function update(id) {
        $("#kindForm")[0].reset();
        //只读属性，最后选择行的id
        var data = $("#articleTable").jqGrid("getRowData", id);
        //给input框设添加数据
        $("#id").val(data.id);
        $("#title").val(data.title);
        KindEditor.html("#editor_id", data.content);
        var option = "";
        if (data.status == "展示") {
            option += "<option selected value=\"1\">展示</option>";
            option += "<option value=\"2\">冻结</option>";
        } else {
            option += "<option value=\"1\">展示</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);
        $.ajax({
            url: "${path}/guru/selectAllGuru",
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option2 = "";
                gurulist.forEach(function (guru) {
                    if (guru.id == data.guruId) {
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    } else {
                        option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                    }
                });
                $("#guru_list").html(option2);
            }
        });
        //展示模态框
        $("#myModal").modal("show");
        //给富文本编辑器添加内容
        KindEditor.html("#editor_id", row.content);
    }
    //删除按钮事件
    function del(id) {
        var data = $("#articleTable").jqGrid("getRowData", id);
        $.ajax({
            url: "${path}/article/delete",
            data: {"id": data.id},
            type: "post",
            dataType: "json",
            success: function () {
                //刷新页面
                $("#articleTable").trigger("reloadGrid");
            }
        });
    }
</script>
</head>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab" data-toggle="tab">文章管理</a></li>
        <li><a class="btn btn-default" onclick="showArticle()">添加文章</a></li>
    </ul>
    <div class="panel">
        <table id="articleTable"></table>
        <div id="articlePage" style="height:50px"></div>
    </div>
</div>


