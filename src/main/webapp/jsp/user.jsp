<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<script type="text/javascript">
    $(function () {
        // 初始化jgGrid插件
        $("#userTable").jqGrid({
            url: "${path}/user/queryUserByPage",
            datatype: "json",
            colNames: ["ID", "密码", "手机", "头像", "姓名", "签名", "性别", "法号", "地区", "注册时间", "最后登陆", "状态", "操作"],
            colModel: [
                {name: "id", align: "center", hidden: true},
                {name: "password", align: "center", hidden: true},
                {name: "phone", align: "center"},
                {name: "photo", align: "center", formatter: function (data) {
                    if(data!=""){return "<img src='" + data + "' style='width:100px;height:60px'>";}
                    else return "无头像";
                    }
                },
                {name: "name", align: "center"},
                {name: "nickName", align: "center"},
                {name: "sex",align: "center",
                    formatter: function (data) {
                        if (data == "1") {
                            return "男";
                        } else {
                            return "女";
                        }
                    }
                },
                {name: "sign", align: "center"},
                {name: "location", align: "center"},
                {name: "rigestDate", align: "center"},
                {name: "lastLogin", align: "center"},
                {name: 'status', align: "center",
                    formatter: function (data) {
                        if (data == "1") {
                            return "展示";
                        } else {
                            return "冻结";
                        }
                    }
                },
                {name: "", align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + rowObject.id + "')\">修改状态</button>";
                    }
                }
            ],
            rowNum: 5,
            rowList: [5, 10, 15, 20],
            pager: "#pager",
            sortname: 'id',
            height: "45%",
            mtype: "post",
            caption: "用户信息管理",
            autowidth: true,
            styleUI: "Bootstrap"
        });
        jQuery("#userTable").jqGrid('navGrid', '#pager', {edit: false, add: false, del: false, search: false}
        );
    });
    //修改按钮事件
    function update(id) {
        var data = $("#userTable").jqGrid("getRowData", id);
        $.ajax({
            url: "${path}/user/updateUser",
            datatype: "json",
            type: "post",
            data: {"id": data.id},
            success: function (data) {
                $("#userTable").trigger("reloadGrid");
            }
    })
    }
</script>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">员工列表</a></li>
    </ul>
    <div class="panel">
        <table id="userTable"></table>
        <div id="pager" style="height: 50px"></div>
    </div>
</div>


