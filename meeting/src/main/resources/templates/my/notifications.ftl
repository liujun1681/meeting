<!DOCTYPE html>
<html>
<head>
    <title>CoolMeeting会议管理系统</title>
    <link rel="stylesheet" href="/static/styles/common.css"/>
</head>
<body>
<#include '../common/top.ftl'>
<div class="page-body">
    <#include '../common/leftMenu.ftl'>
    <div class="page-content">
        <div class="content-nav">
            个人中心 > <a href="notifications">最新通知</a>
        </div>
        <table class="listtable">
            <caption>
                未来7天我要参加的会议:
            </caption>
            <tr class="listheader">
                <th style="width:300px">会议名称</th>
                <th>会议室</th>
                <th>起始时间</th>
                <th>结束时间</th>
                <th style="width:100px">操作</th>
            </tr>
            <#list nextSevenMeetings as nextSevenMeeting>
            <tr>
                <td>${nextSevenMeeting.meetingname!}</td>
                <td>${nextSevenMeeting.roomName!}</td>
                <td>${nextSevenMeeting.starttime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                <td>${nextSevenMeeting.endtime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                <td>
                    <a class="clickbutton" href="/employee/viewMeetins?meetingId=${nextSevenMeeting.meetingid}">查看详情</a>
                </td>
            </tr>
            </#list>
        </table>
        <table class="listtable">
            <caption>
                已取消的会议:
            </caption>
            <tr class="listheader">
                <th style="width:300px">会议名称</th>
                <th>会议室</th>
                <th>起始时间</th>
                <th>结束时间</th>
                <th>取消原因</th>
                <th style="width:100px">操作</th>
            </tr>
            <#list cancelMeetings as cancelMeeting>
            <tr>
                <td>${cancelMeeting.meetingname!}</td>
                <td>${cancelMeeting.roomName!}</td>
                <td>${cancelMeeting.starttime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                <td>${cancelMeeting.endtime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                <td>${cancelMeeting.canceledreason!}</td>
                <td>
                    <a class="clickbutton" href="/employee/viewMeetins?meetingId=${cancelMeeting.meetingid}">查看详情</a>
                </td>
            </tr>
            </#list>
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