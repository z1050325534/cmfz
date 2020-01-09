<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${path}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/boot/css/back.css">
    <link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${path}/jqgrid/css/jquery-ui.css">
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${path}/boot/js/bootstrap.min.js"></script>
    <script src="${path}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${path}/boot/js/ajaxfileupload.js"></script>
    <script src="${path}/echarts/echarts.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="../echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <%--页面添加以下脚本--%>
    <script charset="utf-8" src="${path}/kindeditor-4.1.11-zh-CN/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${path}/kindeditor-4.1.11-zh-CN/kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript">
        // KindEditor初始化时必须放在head标签中,不然会出现无法初始化的情况
        KindEditor.ready(function (K) {
            // K.create("textarea的Id")
            // 如需自定义配置 在id后使用,{}的形式声明
            window.editor = K.create('#editor_id', {
                width: '500px',
                uploadJson: '${path}/article/uploadArticleImg',
                allowFileManager: true,
                filePostName: "image",
                fileManagerJson: '${path}/article/queryArticleImg',
                // 失去焦点后 触发的事件
                afterBlur: function () {
                    // 同步数据方法
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
<!-- 导航栏 -->
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持名法洲后台管理系统</a>
        </div>
        <div>
            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
                <li><a>来了${sessionScope.admin.username}老弟</a></li>
                <li><a>退出登陆</a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- 栅格系统 -->
<div class="container-fluid">
    <div class="row">
        <!-- 手风琴 -->
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                用户模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li>
                                    <a href="javascript:$('#centerLay').load('${path}/jsp/user.jsp')">用户管理</a>
                                </li>
                                <li><a href="javascript:$('#centerLay').load('${path}/jsp/echarts.jsp')">用户活跃度分析</a>
                                </li>
                                <li><a href="javascript:$('#centerLay').load('${path}/jsp/usersMap.jsp')">用户地区分布</a>
                                </li>
                                <li>
                                    <a href="javascript:$('#centerLay').load('${path}/jsp/log.jsp')">用户日志记录</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                专辑模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li>
                                    <a href="javascript:$('#centerLay').load('${path}/jsp/album.jsp')">专辑管理</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                文章模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li>
                                    <a href="javascript:$('#centerLay').load('${path}/jsp/article.jsp')">文章管理</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                上师模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#centerLay').load('${path}/jsp/guru.jsp')">上师管理</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                轮播图模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li>
                                    <a href="javascript:$('#centerLay').load('${path}/jsp/banner.jsp')">轮播图管理</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseSix">
                                聊天室模块
                            </a>
                        </h4>
                    </div>
                    <div id="collapseSix" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li>
                                    <a href="javascript:$('#centerLay').load('${path}/jsp/goeasy.jsp')">进入聊天室</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-10" id="centerLay">
            <div class="jumbotron">
                <div class="container">
                    <h3>欢迎使用持名法洲系统！</h3>
                </div>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img src="../image/baise.jpg" alt="First slide">
                            <div class="carousel-caption">标题 1</div>
                        </div>
                        <div class="item">
                            <img src="../image/daima.jpeg" alt="Second slide">
                            <div class="carousel-caption">标题 2</div>
                        </div>
                        <div class="item">
                            <img src="../image/ditie.jpg" alt="Third slide">
                            <div class="carousel-caption">标题 3</div>
                        </div>
                        <div class="item">
                            <img src="../image/tianye.png" alt="Fourth slide">
                            <div class="carousel-caption">标题 4</div>
                        </div>
                        <div class="item">
                            <img src="../image/lingdao.jpg" alt="Fifth slide">
                            <div class="carousel-caption">标题 5</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h4 style="text-align: center">@百知教育 @baizhiedu.com.cn</h4>
</div>
<!-- 添加文章模态框（Modal） -->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:730px;">
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加文章信息 </h4>
            </div>
            <%--身体--%>
            <div class="modal-body" align="center">
                <form role="form" id="kindForm">
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="id" name="id">
                    </div>
                    <div class="form-group">
                        <label for="title">标题</label>
                        <input type="text" class="form-control" name="title" id="title" placeholder="请输入名称">
                    </div>
                    <div class="form-group">
                        <label for="cover">封面</label>
                        <!-- name不能起名和实体类一致 会出现使用String类型接受二进制文件的情况 -->
                        <input type="file" id="cover" name="cover">
                    </div>
                    <div class="form-group">
                        <label for="editor_id">内容</label>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="status">状态</label>
                        <select class="form-control" id="status" name="status">
                            <option value="1" selected>展示</option>
                            <option value="2">冻结</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="guru_list">上师列表</label>
                        <select class="form-control" id="guru_list" name="guruId">
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sub()">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 添加文章模态框（Modal） -->
<div class="modal fade" id="myModa2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:730px;">
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">选择导入路径 </h4>
            </div>
            <%--身体--%>
            <div class="modal-body" align="center">
                <form role="form" id="LogForm">
                    <div class="form-group">
                        <label for="log">封面</label>
                        <!-- name不能起名和实体类一致 会出现使用String类型接受二进制文件的情况 -->
                        <input type="file" id="log" name="log">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="subLog()">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>