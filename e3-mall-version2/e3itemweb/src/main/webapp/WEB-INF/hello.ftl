<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>freemarker</title>
</head>
<body>
   学生信息</br>
   姓名:${student.name}&nbsp&nbsp&nbsp学号:${student.id}&nbsp&nbsp&nbsp年龄:${student.age}&nbsp&nbsp&nbsp地址:${student.address}
   <a href="http://www.baidu.com">${hello}</a>
    <#--取列表中的数据-->
    <table border="1">
        <tr>
            <th>学生姓名</th>
            <th>学号</th>
            <th>年龄</th>
            <th>籍贯</th>
        </tr>
        <#list stulist as stu>
            <#if stu_index%2==0>
                <tr bgcolor="#7fff00">
                    <td>${stu.name}</td>
                    <td>${stu.id}</td>
                    <td>${stu.age}</td>
                    <td>${stu.address}</td>
                </tr>
                <#else>
                    <tr bgcolor="#ffe4c4">
                        <td>${stu.name}</td>
                        <td>${stu.id}</td>
                        <td>${stu.age}</td>
                        <td>${stu.address}</td>
                    </tr>
            </#if>
        </#list>

        <br>
        当前时间:${date?date}<br>
        当前时间:${date?time}<br>
        当前日期和时间:${date?datetime}<br>
        <#--其中SS表示显示毫秒 ss显示秒-->
        自定义日期格式:${date?string("yyyy-MM-dd/HH:mm:ss")}

        <br>
        NULL值的处理:${myval!"myval值为null"}
        NULL值的处理:<#if myval??>
            myval不为空时。。。
        <#else >
            myval为空时。。。
        </#if>
        <#include "include.ftl"/>
    </table>
</body>
</html>