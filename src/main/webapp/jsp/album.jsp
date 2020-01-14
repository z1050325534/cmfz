<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<script type="text/javascript">
    $(function () {
        $("#albumTable").jqGrid(
            {
                url: '${path}/album/queryByPage',
                datatype: "json",
                height: 300,
                colNames: ['ID', '专辑标题', '封面', '集数', '作者', '简介', '评分', '播音', '创建日期'],
                colModel: [
                    {name: 'id', align: "center", hidden: true},
                    {name: 'title', align: "center", editable: true, editrules: {required: true}},
                    {
                        name: 'url', align: "center", editable: true, editrules: {required: false},
                        formatter: function (data) {
                            return "<img src='" + data + "' style='width:100px;height:60px'>";
                        }, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                    },
                    {name: 'counts', align: "center"},
                    {name: 'author',align: "center",editable: true,
                        edittype: 'select',
                        editoptions: {value:gettypes()}
                    },
                    {name: 'description', align: "center", editable: true, editrules: {required: true}},
                    {name: 'score', align: "center", editable: true, editrules: {required: true}},
                    {name: 'broadcast', align: "center", editable: true, editrules: {required: true}},
                    {name: 'createDate', align: "center", edittype: "date"}
                ],
                rowNum: 5,
                rowList: [5, 10, 15, 20],
                pager: '#albumPage',
                sortname: 'id',
                viewrecords: true,//是否要显示总记录数
                sortorder: "desc",//排序方式
                mtype: "post",
                autowidth: true,//自动列宽
                height: '45%',//设置表格高度
                hidegrid: true,
                // 开启子表格支持
                subGrid: true,
                caption: "专辑列表",
                styleUI: "Bootstrap",
                editurl: "${path}/album/save",
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded: function (subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id, row_id);
                },
                // 删除表格的方法
                subGridRowColapsed: function (subgrid_id, row_id) {
                    $("#albumTable").trigger("reloadGrid");
                }
            });
        $("#albumTable").jqGrid('navGrid', '#albumPage', {
                add: true,
                edit: true,
                del: true,
                search: false,
                edittext: "修改",
                addtext: "添加",
                deltext: "删除"
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (response, postData) {
                    var editId = response.responseJSON.editId;
                    var urlId = response.responseJSON.urlId;
                    if (editId != null) {
                        $.ajaxFileUpload({
                            // 指定上传路径
                            url: "${path}/album/uploadAlbum",
                            type: "post",
                            datatype: "json",
                            data: {urlId: urlId},// 指定上传的input框id
                            fileElementId: "url",
                            success: function () {
                                $("#albumTable").trigger("reloadGrid");
                            }
                        });
                    }
                    // 防止页面报错
                    return postData;
                }
            },
            {
                closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                afterSubmit: function (response, postData) {
                    var urlId = response.responseJSON.urlId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${path}/album/uploadAlbum",
                        type: "post",
                        datatype: "json",
                        data: {urlId: urlId},// 指定上传的input框id
                        fileElementId: "url",
                        success: function () {
                            $("#albumTable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },
            {closeAfterDel: true});
    });

    // subgrid_id 父行级id
    function addSubgrid(subgrid_id, row_id) {
        // 声明子表格Id
        var sid = subgrid_id + "table";
        // 声明子表格工具栏id
        var spage = subgrid_id + "page";
        $("#" + subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='" + spage + "' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url: "${pageContext.request.contextPath}/chapter/queryByPage?album=" + row_id,
                datatype: "json",
                colNames: ['id', '名称', '音频', '大小', '时长', '创建时间'],
                colModel: [
                    {name: "id", key: true, align: "center", hidden: true},
                    {name: "title", align: "center", editable: true, editrules: {required: true}},
                    {
                        name: "url", align: "center", editable: true,
                        formatter: function (value, option, rows) {
                            return "<audio controls loop preload='auto'>\n" +
                                "<source src='" + value + "' type='audio/mpeg'>\n" +
                                "<source src='" + value + "' type='audio/ogg'>\n" +
                                "</audio>";
                        }, width: 300, edittype: 'file'
                    },
                    {
                        name: "size", align: "center",
                        formatter: function (value, options, row) {
                            return value + 'MB';
                        }
                    },
                    {name: "time", align: "center"},
                    {name: "createTime", align: "center"}
                ],
                rowNum: 5,
                rowList: [5, 10, 15, 20],
                pager: spage,
                mtype: "post",
                multiselect: true,//多选框
                sortname: 'num',
                viewrecords: true,//是否显示总记录数
                sortorder: "asc",//排序方式
                height: '100%',
                styleUI: "Bootstrap",
                autowidth: true,//自动列宽
                editurl: "${path}/chapter/save?album=" + row_id
            });
        $("#" + sid).jqGrid('navGrid',
            "#" + spage, {
                edit: true,
                add: true,
                del: true,
                search: false,
                edittext: "修改",
                addtext: "添加",
                deltext: "删除"
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var editId = response.responseJSON.editId;
                    var urlId = response.responseJSON.urlId;
                    if (editId != null) {
                        $.ajaxFileUpload({
                            url: "${path}/chapter/uploadChapter",//用于文件上传的服务器端请求地址
                            fileElementId: 'url', //文件上传空间的id属性  <input type="file" id="file" name="file" />
                            // dataType : 'json', //返回值类型 一般设置为json,但是前台如果返回的不是json格式，而是void或者字符串类型，则这个一定要注掉
                            data: {urlId: urlId},
                            success: function () {
                                $("#" + sid).trigger("reloadGrid");
                            }
                        });
                    }
                    return "332211";
                }

            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    //alert("上传");
                    var urlId = response.responseJSON.urlId;
                    $.ajaxFileUpload({
                        url: "${path}/chapter/uploadChapter",//用于文件上传的服务器端请求地址
                        fileElementId: 'url', //文件上传空间的id属性  <input type="file" id="file" name="file" />
                        // dataType : 'json', //返回值类型 一般设置为json,但是前台如果返回的不是json格式，而是void或者字符串类型，则这个一定要注掉
                        data: {urlId: urlId},
                        success: function () {
                            $("#" + sid).trigger("reloadGrid");
                        }
                    });
                    return "332211";
                }
            }, {closeAfterDel: true});
    };
    function gettypes() {
        var str="";
        $.ajax({
            type : "post",
            async : false,
            url : "${path}/guru/selectAllGuru",
            success : function(data){
                var jsonobj=eval(data);
                var length=jsonobj.length;
                for(var i=0;i<length;i++) {
                    if (i != length - 1) {
                        str += jsonobj[i].name +":"+jsonobj[i].name+";";
                    }else{
                        str += jsonobj[i].name +":"+jsonobj[i].name;
                    }
                }
            }
        });
        return str;		//必须有此返回值
    }
</script>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab"
                                                  data-toggle="tab">专辑管理</a></li>
    </ul>
    <div class="panel">
        <table id="albumTable"></table>
        <div id="albumPage" style="height:50px"></div>
    </div>
</div>
