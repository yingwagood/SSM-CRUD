<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2021/9/16
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <title>员工列表</title>
    <% pageContext.setAttribute("APP_PATH", request.getContextPath());%>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-1.12.4.min.js"></script>
    <link rel="stylesheet" href="${APP_PATH}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="${APP_PATH}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
</head>
<body>


<%--员工添加的界面--%>
<div class="modal fade " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog " role="document">
        <div class="modal-content ">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="emp_model">
                    <div class="form-group">
                        <label for="empAdd_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="empAdd_input" name="empName"
                                   placeholder="empName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="emailAdd_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-5">
                            <input type="email" class="form-control" id="emailAdd_input" name="email"
                                   placeholder="email@qq.com">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1Add_input" value="1" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2Add_input" value="0"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deptName_select" class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="dId" id="deptName_select">
                            </select>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer ">
                <button type="button" class="btn btn-default " data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_button">保存修改</button>
            </div>
        </div>
    </div>
</div>


<div class="container">
    <%--    标题--%>
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8 ">
            <button type="button" class="btn btn-primary" id="add_button">新增</button>
            <button type="button" class="btn btn-danger">删除</button>
        </div>
    </div>
    <br/>


    <%--    显示表格数据--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <%--    显示分页信息--%>
    <div class="row">
        <div class="col-md-6" id="page_info">
            <%--            当前页码:${page.pageNum},共${page.pages}页，总${page.total}记录--%>
        </div>
        <%--        分页--%>
        <div class="col-md-6" id="pages">
        </div>
    </div>
</div>
<script type="text/javascript">
    var totalpages;
    $(function () {
        to_page(1);
    });

    // 显示第几页的数据
    function to_page(pageNum) {
        $.ajax({
                url: "${APP_PATH}/emps",
                data: "pageNum=" + pageNum,
                type: "GET",
                success: function (result) {
                    console.log(result);
                    result_emps(result);
                    result_pages(result);
                }
            }
        );
    }

    // 显示类数据
    function result_emps(result) {
        $("#emps_table tbody").empty();
        $("#pages").empty();
        var emps = result.extend.pages.list;
        $.each(emps, function (index, item) {
            var empId = $("<td></td>").append(item.empId);
            var empName = $("<td></td>").append(item.empName);
            if (item.gender == 0) {
                var gender = $("<td></td>").append("女");
            }
            if (item.gender == 1) {
                var gender = $("<td></td>").append("男");
            }
            var email = $("<td></td>").append(item.email);
            var dName = $("<td></td>").append(item.department.deptName);
            var addBut = $("<button aria-label='Left Align' class='btn btn-primary btn-sm'></button>").append($("<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>")).append("新增");
            var delBut = $("<button aria-label='Left Align'></button>").addClass("btn btn-danger btn-sm").append($("<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>")).append("删除");
            var totalBut = $("<td></td>").append(addBut).append(delBut);
            $("<tr></tr>").append(empId).append(empName).append(gender).append(email).append(dName).append(totalBut).appendTo("#emps_table tbody");
        });
    }

    // 显示页码选择，比如首页等
    function result_pages(result) {
        $("#page_info").empty();
        $("#page_info").append("当前页码:" + result.extend.pages.pageNum + ",共" + result.extend.pages.pages + "页，总共" + result.extend.pages.total + "记录");
        totalpages = result.extend.pages.pages + 1;
        var firstpage = $("<li></li>").append($("<a></a>").append("首页"));
        firstpage.click(function () {
            to_page(1);
        });
        var lastpage = $("<li></li>").append($("<a></a>").append("末页"));
        lastpage.click(function () {
            to_page(result.extend.pages.pages);
        });
        var leftpage = $("<li></li>").append($("<a aria-label='Previous'></a>").append($("<span aria-hidden='true'></span>").append("&laquo;")));
        leftpage.click(function () {
            to_page(result.extend.pages.pageNum - 1);
        });
        var rightpage = $("<li></li>").append($("<a aria-label='Next'></a>").append($("<span aria-hidden='true'></span>").append("&raquo;")));
        rightpage.click(function () {
            to_page(result.extend.pages.pageNum + 1);
        });
        if (result.extend.pages.isFirstPage) {
            firstpage.addClass("disabled");
            leftpage.addClass("disabled");
        }
        if (result.extend.pages.isLastPage) {
            lastpage.addClass("disabled");
            rightpage.addClass("disabled");
        }
        var ul = $("<ul class='pagination'></ul>").append(firstpage).append(leftpage);
        $.each(result.extend.pages.navigatepageNums, function (index, item) {
            var numpage = $("<li></li>").append($("<a></a>").append(item));
            numpage.click(function () {
                to_page(item);
            });
            if (result.extend.pages.pageNum == item) {
                numpage.addClass("active");
            }
            ul.append(numpage);
        });
        ul.append(rightpage).append(lastpage);
        $("#pages").append($("<nav aria-label='Page navigation'></nav>").append(ul));
    }

    // 显示添加的悬浮窗
    $("#add_button").click(function () {
        $("#deptName_select").empty();
        getDept();
        $('#myModal').modal({
            backdrop: "static"
        });
    });

    function getDept() {
        $.ajax({
            url: "${APP_PATH}/depts",
            type: "GET",
            success: function (result) {
                console.log(result);
                var depts = result.extend.depts;
                $.each(depts, function () {
                    var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                    optionEle.appendTo("#deptName_select");
                });
            }
        });
    }

    $("#emp_save_button").click(function () {
        console.log($("#emp_model").serialize());
        $.ajax({
            url: "${APP_PATH}/emp",
            type: "POST",
            data: $("#emp_model").serialize(),
            success: function (result) {
                console.log(result);
                $('#myModal').modal("hide");
                to_page(totalpages);
            }
        });
    });
</script>

</body>
</html>
