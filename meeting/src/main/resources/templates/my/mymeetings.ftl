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
                    个人中心 > 我的会议
                </div>
                <table class="listtable">
                    <caption>我将参加的会议：</caption>
                    <tr class="listheader">
                        <th>会议名称</th>
                        <th>会议室名称</th>
                        <th>会议开始时间</th>
                        <th>会议结束时间</th>
                        <th>会议预定时间</th>
                        <th>预定者</th>
                        <th>操作</th>
                    </tr>
                    <#list meetings.records as meeting>
                    <tr>
                        <td>${meeting.meetingname!}</td>
                        <td>${meeting.roomName!}</td>
                        <td>${meeting.starttime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                        <td>${meeting.endtime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                        <td>${meeting.reservationtime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                        <td>${meeting.reservationistName!}</td>
                        <td>
                            <a class="clickbutton" href="/employee/viewMeetins?meetingId=${meeting.meetingid}">查看/撤销</a>
                        </td>
                    </tr>
                    </#list>
                </table>
            </div>
        </div>
    <#--    分页-->
    <div class="col-md-12 column">
        <ul>
            <#if currentPage lte 1>
                <li class="disabled"  style="float: left;list-style: none"><a href="#">上一页</a></li>
            <#else>
                <li  style="float: left;list-style: none"><a href="/employee/myParticipateIn?page=${currentPage - 1}&size=${size}">上一页</a></li>
            </#if>

            <#list 1..totalPage as index>
                <#if currentPage == index>
                    <li class="disabled"  style="float: left;list-style: none"><a href="#">${index}</a></li>
                <#else>
                    <li  style="float: left;list-style: none"><a href="/employee/myParticipateIn?page=${index}&size=${size}">${index}</a></li>
                </#if>
            </#list>

            <#if currentPage gte totalPage>
                <li class="disabled"  style="float: left;list-style: none"><a href="#">下一页</a></li>
            <#else>
                <li  style="float: left;list-style: none"><a href="/employee/myParticipateIn?page=${currentPage + 1}&size=${size}">下一页</a></li>
            </#if>
        </ul>
    </div>
    <div>【当前页】：${currentPage}</div>
    <div class="page-footer">
            <hr/>
            更多问题，欢迎联系<a href="mailto:webmaster@eeg.com">管理员</a>
            <img src="/static/images/footer.png" alt="CoolMeeting"/>
        </div>
    </body>
</html>