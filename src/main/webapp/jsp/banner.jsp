<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<script type="text/javascript">
    $(function () {
        // 初始化jgGrid插件
        $("#bannerTable").jqGrid({
            url: "${path}/banner/queryByPage",
            datatype: "json",
            colNames: ["ID", "标题", "网络路径", "href", "创建日期", "description", "status"],
            colModel: [
                {name: "id", align: "center", hidden: true},
                {name: "title", align: "center", editable: true, editrules: {required: true}},
                {name: 'url',align: "center",editable: true,
                    formatter: function (data) {
                        return "<img src='" + data + "' style='width:100px;height:60px'>";
                    },  edittype: "file", editoptions: {enctype: "multipart/form-data"}
                },
                {name: "href", align: "center", editable: true, editrules: {required: true}},
                {name: "createDate", align: "center"},
                {name: "description", align: "center", editable: true, editrules: {required: true}},
                {
                    name: "status", align: "center", editable: true, editrules: {required: true},
                    formatter: function (data) {
                        if (data == "1") {
                            return "展示";
                        } else return "冻结";
                    }, edittype: "select", editoptions: {value: "1:展示;2:冻结"}

                }
            ],

            rowNum: 5,
            rowList: [5, 10, 15, 20],
            pager: "#pager",
            sortname: 'id',
            mtype: "post",
            height: "45%",
            viewrecords: true,
            sortorder: "desc",
            caption: "轮播图",
            autowidth: true,
            multiselect: true,
            styleUI:"Bootstrap",
            editurl: "${path}/banner/save"
        });
        jQuery("#bannerTable").jqGrid('navGrid', '#pager', {
                edit: true,
                add: true,
                del: true,
                search: false,
                edittext: "编辑",
                addtext: "添加",
                deltext: "删除"
            },
            {
                closeAfterEdit: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                afterSubmit: function (response, postData) {
                    var editId = response.responseJSON.editId;
                    var urlId = response.responseJSON.urlId;
                    if (editId != null) {
                        $.ajaxFileUpload({
                            // 指定上传路径
                            url: "${path}/banner/uploadBanner",
                            type: "post",
                            datatype: "json",
                            data: {urlId: urlId},
                            // 指定上传的input框id
                            fileElementId: "url",
                            success: function (data) {
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                    }
                    // 防止页面报错
                    return postData;
                }
            }, {
                closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                afterSubmit: function (response, postData) {
                    var urlId = response.responseJSON.urlId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${path}/banner/uploadBanner",
                        type: "post",
                        datatype: "json",
                        data: {urlId: urlId},// 指定上传的input框id
                        fileElementId: "url",
                        success: function () {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            }, {
                closeAfterDel: true
            }
        );
    });
</script>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab" data-toggle="tab">轮播图管理</a></li>
    </ul>
    <div class="panel">
        <table id="bannerTable"></table>
        <div id="pager" style="height: 50px"></div>
    </div>
</div>