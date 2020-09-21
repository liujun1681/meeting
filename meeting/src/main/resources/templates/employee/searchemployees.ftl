<!DOCTYPE html>
<html>
<head>
    <title>CoolMeeting会议管理系统</title>
    <link rel="stylesheet" href="/static/styles/common.css"/>
    <style type="text/css">

    </style>
</head>
<body>
<#include '../common/top.ftl'>
<div class="page-body">
    <#include '../common/leftMenu.ftl'>
    <div class="page-content">
        <div class="content-nav">
            会议预定 > 搜索员工
        </div>
        <form action="/peopleManager/searchempByCondition" method="get">
            <fieldset>
                <legend>搜索会议</legend>
                <table class="formtable">
                    <tr>
                        <td>姓名：</td>
                        <td>
                            <input name="employeename" type="text" id="employeename"
                                   value="<#if condition??>${condition.employeename!''}</#if>" maxlength="20"/>
                        </td>
                        <td>账号名：</td>
                        <td>
                            <input name="username" type="text" id="accountname"
                                   value="<#if condition??>${condition.username!''}</#if>" maxlength="20"/>
                        </td>
                        <td>状态：</td>
                        <td>
                            <#if condition??>
                                <#if condition.status=='0'>
                                    <input type="radio" id="status" name="status" value="1"/><label>已批准</label>
                                    <input checked="checked" type="radio" id="status" name="status" value="0"/><label>待审批</label>
                                    <input type="radio" id="status" name="status" value="2"/><label>已关闭</label>
                                <#elseif condition.status=='1'>
                                    <input checked="checked" type="radio" id="status" name="status" value="1"/><label>已批准</label>
                                    <input type="radio" id="status" name="status" value="0"/><label>待审批</label>
                                    <input type="radio" id="status" name="status" value="2"/><label>已关闭</label>
                                <#elseif condition.status=='2'>
                                    <input type="radio" id="status" name="status" value="1"/><label>已批准</label>
                                    <input type="radio" id="status" name="status" value="0"/><label>待审批</label>
                                    <input checked="checked" type="radio" id="status" name="status" value="2"/><label>已关闭</label>
                                </#if>
                            <#else>
                                <input type="radio" id="status" name="status" value="1"
                                       checked="checked"/><label>已批准</label>
                                <input type="radio" id="status" name="status" value="0"/><label>待审批</label>
                                <input type="radio" id="status" name="status" value="2"/><label>已关闭</label>
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" class="command">
                            <input type="submit" class="clickbutton" value="查询"/>
                            <input type="reset" class="clickbutton" value="重置"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <div>
            <h3 style="text-align:center;color:black">查询结果</h3>
        </div>
        <#--    分页-->
        <div class="col-md-12 column">
            <ul class="pagination pull-right">
                <#if currentPage lte 1>
                    <li class="disabled" style="float: left;list-style: none"><a href="#">上一页</a></li>
                <#else>
                    <li style="float: left;list-style: none"><a href="/peopleManager/searchempByCondition?page=${currentPage - 1}&size=${size}&employeename=${condition.employeename!}&username=${condition.username!}&status=${condition.status!}">上一页</a></li>
                </#if>

                <#list 1..totalPage as index>
                    <#if currentPage == index>
                        <li class="disabled" style="float: left;list-style: none"><a href="#">${index}</a></li>
                    <#else>
                        <li style="float: left;list-style: none"><a href="/peopleManager/searchempByCondition?page=${index}&size=${size}&employeename=${condition.employeename!}&username=${condition.username!}&status=${condition.status!}">${index}</a></li>
                    </#if>
                </#list>

                <#if currentPage gte totalPage>
                    <li class="disabled" style="float: left;list-style: none"><a href="#">下一页</a></li>
                <#else>
                    <li style="float: left;list-style: none"><a href="/peopleManager/searchempByCondition?page=${currentPage + 1}&size=${size}&employeename=${condition.employeename!}&username=${condition.username!}&status=${condition.status!}">下一页</a></li>
                </#if>
            </ul>
            <div>【当前页】：${currentPage}</div>
        </div>
        <table class="listtable">
            <tr class="listheader">
                <th>姓名</th>
                <th>账号名</th>
                <th>联系电话</th>
                <th>电子邮件</th>
                <th>操作</th>
            </tr>
            <#if emps??>
                <#list emps as emp>
                    <tr>
                        <td>${emp.employeename}</td>
                        <td>${emp.username}</td>
                        <td>${emp.phone}</td>
                        <td>${emp.email}</td>
                        <td>
                            <a class="clickbutton" href="/admin/updateemp?id=${emp.employeeid}">关闭账号</a>
                        </td>
                    </tr>
                </#list>
            </#if>
        </table>
    </div>
</div>
<div class="page-footer">
    <hr/>
    更多问题，欢迎联系<a href="mailto:webmaster@eeg.com">管理员</a>
    <img src="/static/images/footer.png" alt="CoolMeeting"/>
</div>
</body>
</html>