<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<!doctype html>
<html lang="en">
<head>
    <!-- 引入 echarts.js -->
    <script src="${path}/bootstrap/js/echarts.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>

    <script type="text/javascript">
        $(function(){

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            $.get('${pageContext.request.contextPath}/echarts/getUserData',function(data){

                // 指定图表的配置项和数据
                var option = {
                    //标题
                    title: {
                        text: '每月注册用户统计图'
                    },
                    tooltip: {},  //鼠标提示
                    legend: {     //选项卡
                        data:['柱状图','折线图']
                    },
                    xAxis: {   //横坐标
                        data: data.month
                    },
                    yAxis: {},   //纵坐标
                    series: [{   //数据系列
                        name: '柱状图',   //选项卡名字
                        type: 'bar',  //柱状图
                        data: data.count
                    },{   //数据系列
                        name: '折线图',
                        type: 'line',  //折线图
                        data: data.count
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);

            },"json");

        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>