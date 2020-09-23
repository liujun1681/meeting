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
                    会议预定 > 搜索会议
                </div>
                <form action="/meeting/doSearchmeetings" method="get">
                    <fieldset>
                        <legend>搜索会议</legend>
                        <table class="formtable">
                            <tr>
                                <td>会议名称：</td>
                                <td>
                                    <input type="text" id="meetingname" name="meetingname" maxlength="20"/>
                                </td>
                                <td>会议室名称：</td>
                                <td>
                                    <input type="text" id="roomname" name="roomname" maxlength="20"/>
                                </td>
                                <td>预定者姓名：</td>
                                <td>
                                    <input type="text" id="reservername" name="reservername" maxlength="20"/>
                                </td>
                            </tr>
                            <tr>
                                <td>预定日期：</td>
                                <td colspan="5">
                                    从&nbsp;<input type="date" id="reservefromdate" name="reservefromdate" placeholder="例如：2013-10-20"/>
                                    到&nbsp;<input type="date" id="reservetodate" name="reservetodate" placeholder="例如：2013-10-22"/>
                                </td>
                            </tr>
                            <tr>
                                <td>会议日期：</td>
                                <td colspan="5">
                                    从&nbsp;<input type="date" id="meetingfromdate" name="meetingfromdate" placeholder="例如：2013-10-20"/>
                                    到&nbsp;<input type="date" id="meetingtodate" name="meetingtodate" placeholder="例如：2013-10-22"/>
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
                <#--    分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled" style="float: left;list-style: none"><a href="#">上一页</a></li>
                        <#else>
                            <li style="float: left;list-style: none"><a href="/meeting/doSearchmeetings?page=${currentPage - 1}&size=${size}&meetingname=${meetingConditionVo.meetingname!}&roomname=${meetingConditionVo.roomname!}&reservername=${meetingConditionVo.reservername!}&reservefromdate=${meetingConditionVo.reservefromdate!}&reservetodate=${meetingConditionVo.reservetodate!}&meetingfromdate=${meetingConditionVo.meetingfromdate!}&meetingtodate=${meetingConditionVo.meetingtodate!}">上一页</a></li>
                        </#if>

                        <#list 1..totalPage as index>
                            <#if currentPage == index>
                                <li class="disabled" style="float: left;list-style: none"><a href="#" >${index}</a></li>
                            <#else>
                                <li style="float: left;list-style: none"><a href="/meeting/doSearchmeetings?page=${index}&size=${size}&meetingname=${meetingConditionVo.meetingname!}&roomname=${meetingConditionVo.roomname!}&reservername=${meetingConditionVo.reservername!}&reservefromdate=${meetingConditionVo.reservefromdate!}&reservetodate=${meetingConditionVo.reservetodate!}&meetingfromdate=${meetingConditionVo.meetingfromdate!}&meetingtodate=${meetingConditionVo.meetingtodate!}">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte totalPage>
                            <li class="disabled" style="float: left;list-style: none"><a href="#" >下一页</a></li>
                        <#else>
                            <li style="float: left;list-style: none"><a href="/meeting/doSearchmeetings?page=${currentPage + 1}&size=${size}&meetingname=${meetingConditionVo.meetingname!}&roomname=${meetingConditionVo.roomname!}&reservername=${meetingConditionVo.reservername!}&reservefromdate=${meetingConditionVo.reservefromdate!}&reservetodate=${meetingConditionVo.reservetodate!}&meetingfromdate=${meetingConditionVo.meetingfromdate!}&meetingtodate=${meetingConditionVo.meetingtodate!}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
                <div>【当前页】：${currentPage}</div>
                <table class="listtable">
                    <tr class="listheader">
                        <th>会议名称</th>
                        <th>会议室名称</th>
                        <th>会议开始时间</th>
                        <th>会议结束时间</th>
                        <th>会议预定时间</th>
                        <th>预定者</th>
                        <th>操作</th>
                    </tr>
                    <#if meetings??>
                    <#list meetings as meeting>
                    <tr>
                        <td>${meeting.meetingname!}</td>
                        <td>${meeting.roomName!}</td>
                        <td>${meeting.starttime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                        <td>${meeting.endtime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                        <td>${meeting.reservationtime?string('yyyy-MM-dd HH:mm:ss')!}</td>
                        <td>${meeting.reservationistName!}</td>
                        <td>
                            <a class="clickbutton" href="/employee/viewMeetins?meetingId=${meeting.meetingid}">查看详情</a>
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