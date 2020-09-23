<div class="page-header">
    <div class="header-banner">
        <img src="/static/images/header.png" alt="CoolMeeting"/>
    </div>
    <div class="header-title">
        欢迎访问Cool-Meeting会议管理系统
    </div>
    <div class="header-quicklink">
        欢迎您，
        <#if employee??>
            <strong>${employee.employeename!''}</strong>
        </#if>
        <a href="/changepassword">[修改密码]</a>
        <a href="/logout">[注销]</a>
    </div>
</div>