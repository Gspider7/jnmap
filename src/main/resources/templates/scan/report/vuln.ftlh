<!DOCTYPE html>
<html lang="zh-CN">

<#assign ctx=springMacroRequestContext.contextPath />

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>漏洞扫描报告</title>
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/commont.css">
    <script src="${ctx}/static/js/jquery-3.3.1.min.js"></script>
    <script src="${ctx}/static/js/echarts.common.min.js"></script>
    <script src="${ctx}/static/js/html2canvas.min.js"></script>
    <script src="${ctx}/static/js/commont.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom,初始化echarts实例
        var app = echarts.init(document.getElementById('chart1'));
        app.title = '环形图';
        // 指定图表的配置项和数据
        var option = {
            title : {
                text: '操作系统统计',
                x:'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            color: ['#5f8ade','#a7cc67','#fbce76','#f58845','#ca2704', '#FF1493'],
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ['其他','WINDOWS','LINUX','ANDROID','UNIX','KYLIN']
            },
            series: [
                {
                    name: '操作系统',
                    type: 'pie',
                    radius: ['45%', '65%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}: {c}({d}%)',
                            fontSize: 14,
                        },
                        emphasis: { show: true, textStyle: { fontSize: '25', fontWeight: 'bold' } }
                    },
                    data: [
                        {value:${counter.totalFamilyOther?c} == 0 ? null : ${counter.totalFamilyOther?c}, name:'其他'},
                        {value: ${counter.totalFamilyWindows?c} == 0 ? null : ${counter.totalFamilyWindows?c}, name:'WINDOWS'},
                        {value: ${counter.totalFamilyLinux?c} == 0 ? null : ${counter.totalFamilyLinux?c}, name:'LINUX'},
                        {value: ${counter.totalFamilyAndroid?c} == 0 ? null : ${counter.totalFamilyAndroid?c}, name:'ANDROID'},
                        {value: ${counter.totalFamilyUnix?c} == 0 ? null : ${counter.totalFamilyUnix?c}, name:'UNIX'},
                        {value: ${counter.totalFamilyKylin?c} == 0 ? null : ${counter.totalFamilyKylin?c}, name:'KYLIN'}
                    ]
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        app.setOption(option);


        // 基于准备好的dom,初始化echarts实例
        var app1 = echarts.init(document.getElementById('chart2'));
        app1.title = '环形图';
        // 指定图表的配置项和数据
        var option1 = {
            title : {
                text: '安全漏洞统计',
                x:'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            color: ['#a7cc67','#fbce76','#f58845','#ca2704'],
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ['低','中','高','致命']
            },
            series: [
                {
                    name: '安全漏洞',
                    type: 'pie',
                    radius: ['45%', '65%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}: {c}({d}%)',
                            fontSize: 14,
                        },
                        emphasis: { show: true, textStyle: { fontSize: '25', fontWeight: 'bold' } }
                    },
                    data: [
                        {value: ${counter.totalVulnerabilityLow?c} == 0 ? null : ${counter.totalVulnerabilityLow?c}, name:'低'},
                        {value: ${counter.totalVulnerabilityMedium?c} == 0 ? null : ${counter.totalVulnerabilityMedium?c}, name:'中'},
                        {value: ${counter.totalVulnerabilityHigh?c} == 0 ? null : ${counter.totalVulnerabilityHigh?c}, name:'高'},
                        {value: ${counter.totalVulnerabilityCritical?c} == 0 ? null : ${counter.totalVulnerabilityCritical?c}, name:'致命'}
                    ]
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        app1.setOption(option1);

        $('#titlestylsjColor').click(function () {
            $(this).toggleClass('titleDivbckcolor ');
            $(this).children('button').toggleClass('buttonTopimg ');
            $('#div-stylesj').toggle();
            app.resize();
            app1.resize();
        });

        $('#tarlisj').click(function () {
            $(this).children('button').toggleClass('buttonTopimg ');
            $('#div-stylesj').toggle();
            $('#titlestylsjColor').toggleClass('titleDivbckcolor');
            app.resize();
            app1.resize();
        });

        $('.tbodytrClick tr').click(function () {
            $(this).next('.trNone').toggle();
            $(this).find('span').toggleClass('tdzs-Zkimg');
        });
    });
</script>
<body>
<div id="block-report-title" class="container jumbotron"
     style="display: flex; flex-direction: row; flex-wrap: nowrap; align-items: center;">
    <svg class="svg" xmlns="http://www.w3.org/2000/svg" version="1.1" fill="#2196F3"
         style="width: 160px; height: 40px; display: inline-flex;">
        <path d="M319.3,377.25v19.43s1.34,5.14,6.47,4.09l0.1,4.53s0.65,6.11,8.79,3.92v-7.8s-1.1-3.62-3-3.62h-3.44L328.16,381C328.14,376,321.38,377.42,319.3,377.25Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M334.06,377.25v19.43s1.34,5.14,6.47,4.09l0.09,4.53s0.65,6.11,8.79,3.92v-7.8s-1.1-3.62-3-3.62H343L342.93,381C342.91,376,336.15,377.42,334.06,377.25Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M348.83,377.25v19.43s1.34,5.14,6.47,4.09l0.09,4.53s0.65,6.11,8.79,3.92v-7.8s-1.1-3.62-3-3.62h-3.44L357.69,381C357.67,376,350.91,377.42,348.83,377.25Z"
              transform="translate(-319.3 -377.19)"/>
        <path
                d="M377.27,384.92a10.71,10.71,0,0,1-3.65,1.8l-0.35-.7a10.14,10.14,0,0,0,3.35-1.65,8.56,8.56,0,0,1-1.7-2,10.48,10.48,0,0,1-1.3,1.5l-0.6-.5a11.72,11.72,0,0,0,1.4-1.77,9.66,9.66,0,0,0,1.1-2.13l0.95,0.15q-0.25.55-.5,1h3.95l0.35,0.35a8.12,8.12,0,0,1-2.3,3.4,10.75,10.75,0,0,0,3.25,1.55l-0.45.8a10.33,10.33,0,0,1-3.5-1.8h0Zm0-1.1a8.07,8.07,0,0,0,1.8-2.4h-3.55l-0.1.15a6.47,6.47,0,0,0,1.85,2.25h0Zm-6.45,1.75,0-.2v6.15h0.95v-5.9a7.58,7.58,0,0,0,.8,1l0.55-.7q-0.9-1-1.35-1.55v-1.45H373v-0.8h-1.2v-2.55h-0.95v2.55h-1.75v0.8h1.7a16.19,16.19,0,0,1-.93,2.67,16.79,16.79,0,0,1-1.22,2.42l0.55,0.45a14,14,0,0,0,1.6-2.9h0Zm8.3,5.35v0.5h1v-4.8h-5.8v4.8h0.95v-0.5h3.9Zm-3.9-.8v-2.75h3.9v2.75h-3.9Zm9.73-9.85h7.15v9.6a0.38,0.38,0,0,0,.4.45h1.15a0.43,0.43,0,0,0,.45-0.45v-2.2l0.85,0.15v2.25a1,1,0,0,1-1.15,1.1h-1.55a1,1,0,0,1-1.1-1v-9h-5.25v3.3a14,14,0,0,1-.47,3.87,7.7,7.7,0,0,1-1.67,3l-0.75-.5a7.31,7.31,0,0,0,1.55-2.82,13.63,13.63,0,0,0,.4-3.52v-4.15h0Zm1.6,4.55v0.8h3.8v-0.8h-3.8Z"
                transform="translate(-319.3 -377.19)"/>
        <path
                d="M376,405.24H375.5l-0.36-.62a4.25,4.25,0,0,1-1.05.56,3.15,3.15,0,0,1-1.06.19,3,3,0,0,1-2.36-1,3.64,3.64,0,0,1-.88-2.57,4.06,4.06,0,0,1,.26-1.44,3.32,3.32,0,0,1,.72-1.16,2.88,2.88,0,0,1,1-.68,3.28,3.28,0,0,1,1.25-.23,2.84,2.84,0,0,1,1.09.21,3.2,3.2,0,0,1,1,.63l0.33-.64h0.46V401h-0.52a2.89,2.89,0,0,0-.83-1.54,2.06,2.06,0,0,0-1.44-.55,1.92,1.92,0,0,0-1.61.8,3.5,3.5,0,0,0-.6,2.16,3.4,3.4,0,0,0,.61,2.16,2,2,0,0,0,1.69.77,2.19,2.19,0,0,0,1.38-.41,1.32,1.32,0,0,0,.53-1.09v-0.41a0.58,0.58,0,0,0-.14-0.45,1.11,1.11,0,0,0-.63-0.11h-0.54v-0.53h3.34v0.53h-0.38a1,1,0,0,0-.59.1,0.65,0.65,0,0,0-.11.46v2.38h0Zm4.76-2.58-0.47.21-0.42.16-0.38.13a1.83,1.83,0,0,0-.71.37,0.7,0.7,0,0,0-.22.53,0.65,0.65,0,0,0,.21.51,0.81,0.81,0,0,0,.56.19,1.37,1.37,0,0,0,1.06-.41,1.64,1.64,0,0,0,.38-1.16v-0.53h0Zm0.06,1.91a2.79,2.79,0,0,1-.83.59,2.21,2.21,0,0,1-.91.2A1.56,1.56,0,0,1,378,405a1.15,1.15,0,0,1-.4-0.92,1.08,1.08,0,0,1,.36-0.84,3.28,3.28,0,0,1,1.25-.57l0.78-.22a1.35,1.35,0,0,0,.6-0.29,0.74,0.74,0,0,0,.15-0.51,0.78,0.78,0,0,0-.3-0.64,1.24,1.24,0,0,0-.8-0.24,1.66,1.66,0,0,0-.59.1,1.48,1.48,0,0,0-.5.32,0.59,0.59,0,0,1,.33.17,0.47,0.47,0,0,1,.12.33,0.45,0.45,0,0,1-.16.35,0.56,0.56,0,0,1-.39.14,0.59,0.59,0,0,1-.6-0.6,1.06,1.06,0,0,1,.54-0.9,2.36,2.36,0,0,1,1.35-.36,3,3,0,0,1,.78.1,1.57,1.57,0,0,1,.57.26,1.12,1.12,0,0,1,.38.45,2.07,2.07,0,0,1,.11.79v2.31a1,1,0,0,0,.07.45,0.24,0.24,0,0,0,.23.13,0.34,0.34,0,0,0,.33-0.26,3.4,3.4,0,0,0,.11-1h0.46v0.11a2.43,2.43,0,0,1-.25,1.26,0.88,0.88,0,0,1-.8.39,0.93,0.93,0,0,1-.61-0.19,1.07,1.07,0,0,1-.33-0.59h0Zm3.66-3.58v3.14a0.61,0.61,0,0,1-.12.46,0.83,0.83,0,0,1-.51.11h-0.29v0.48h2.86v-0.48H386a0.85,0.85,0,0,1-.52-0.11,0.6,0.6,0,0,1-.13-0.46V401h1.11V400.5h-1.11v-0.36a2.2,2.2,0,0,1,.18-1.05,0.66,0.66,0,0,1,.61-0.29,0.9,0.9,0,0,1,.66.26,0.52,0.52,0,0,0-.28.16,0.41,0.41,0,0,0-.1.29,0.45,0.45,0,0,0,.15.34,0.51,0.51,0,0,0,.37.14,0.52,0.52,0,0,0,.4-0.16,0.61,0.61,0,0,0,.15-0.43,0.88,0.88,0,0,0-.4-0.75,1.81,1.81,0,0,0-1.07-.29,1.4,1.4,0,0,0-1.12.44,2,2,0,0,0-.37,1.31v0.4h-0.92V401h0.92Zm4.17,0h-0.92V400.5h0.92v-0.4a2,2,0,0,1,.37-1.31,1.39,1.39,0,0,1,1.12-.44,1.81,1.81,0,0,1,1.07.29,0.88,0.88,0,0,1,.4.75,0.6,0.6,0,0,1-.15.43,0.52,0.52,0,0,1-.4.16,0.51,0.51,0,0,1-.37-0.14,0.45,0.45,0,0,1-.15-0.34,0.42,0.42,0,0,1,.1-0.29,0.53,0.53,0,0,1,.28-0.16,1,1,0,0,0-.3-0.19,1,1,0,0,0-.36-0.06,0.66,0.66,0,0,0-.61.29,2.18,2.18,0,0,0-.18,1.05v0.36h1.11V401h-1.11v3.14a0.6,0.6,0,0,0,.13.46,0.85,0.85,0,0,0,.52.11h0.45v0.48h-2.86v-0.48H388a0.84,0.84,0,0,0,.51-0.11,0.62,0.62,0,0,0,.12-0.46V401h0Z"
                transform="translate(-319.3 -377.19)"/>
        <path d="M399.32,387.15q0.37-.52,1.2-1.88h-3.4v-0.94h4q0.63-1.1,1.2-2.3l1,0.42a12.24,12.24,0,0,0-.73,1.26q-0.26.42-.37,0.63h7.48v0.94H407a7.07,7.07,0,0,1-2.2,3.29q0.42,0.16,1.2.52,0.94,0.42,2.82,1.2l-0.52.94a15.66,15.66,0,0,1-1.41-.68q-1.94-.89-3-1.31a18.56,18.56,0,0,1-5.75,2.09L398,391a6.51,6.51,0,0,1-.37-0.73,15.44,15.44,0,0,0,5.28-1.52q-0.84-.37-2.61-1.2Q399.63,387.31,399.32,387.15Zm3.14-7.84,0.89-.52a4.55,4.55,0,0,0,.37.58,9.58,9.58,0,0,1,.73,1.15l-0.52.31h5.18v2.41h-1v-1.52h-9.36v1.52h-1v-2.41h5.65A7.31,7.31,0,0,0,402.46,379.31Zm-1.78,7.53q0.42,0.21,1.41.58l1.67,0.73a6.1,6.1,0,0,0,2.14-2.88h-4.29Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M412.1,385.06a20.05,20.05,0,0,0,2.82-6.12l1,0.31q-0.05.16-.16,0.42-0.37,1.1-1,2.61v9h-0.94v-7.16a18.61,18.61,0,0,1-1.26,1.93Q412.31,385.53,412.1,385.06Zm7.16-5.75,0.84-.42,0.42,0.73q0.26,0.52.37,0.78l-0.58.26h4.39v0.89h-8.94v-0.89h4.18Q419.63,380,419.27,379.31Zm-2.77,3.4h7.43v0.89H416.5v-0.89Zm0,2h7.43v0.89H416.5v-0.89Zm0.1,2h7.22v4.55h-1v-0.73h-5.23v0.73h-1v-4.55Zm1,0.89v2.14h5.23v-2.14h-5.23Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M396.64,405V404.5H397a0.85,0.85,0,0,0,.49-0.11,1,1,0,0,0,.29-0.45l2.1-5.44h0.82l2.08,5.4a1,1,0,0,0,.31.49,0.88,0.88,0,0,0,.5.12h0.28V405h-3.09V404.5h0.53a1.16,1.16,0,0,0,.46-0.06,0.24,0.24,0,0,0,.11-0.23,0.43,0.43,0,0,0,0-.1l-0.05-.16-0.41-1.12h-2.49l-0.36.92a2.81,2.81,0,0,0-.11.32,0.78,0.78,0,0,0,0,.18,0.21,0.21,0,0,0,.11.21,1.45,1.45,0,0,0,.51.05h0.48V405h-2.82Zm2.4-2.7h2.08l-1.06-2.79Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M404.83,405.06v-1.92h0.5a1.83,1.83,0,0,0,.6,1.1,1.7,1.7,0,0,0,1.15.41,1.33,1.33,0,0,0,.78-0.2,0.64,0.64,0,0,0,.29-0.55,0.55,0.55,0,0,0-.21-0.44,1.45,1.45,0,0,0-.65-0.25l-0.87-.17a2.23,2.23,0,0,1-1.14-.51,1.22,1.22,0,0,1-.39-0.93,1.15,1.15,0,0,1,.47-1,2,2,0,0,1,1.25-.37,2.32,2.32,0,0,1,.76.13,2.62,2.62,0,0,1,.71.38l0.19-.42h0.44v1.79h-0.49a1.72,1.72,0,0,0-.53-1,1.56,1.56,0,0,0-1.06-.36,1.13,1.13,0,0,0-.68.19,0.57,0.57,0,0,0-.26.48,0.58,0.58,0,0,0,.2.46,1.3,1.3,0,0,0,.61.25l0.92,0.18a2.31,2.31,0,0,1,1.13.49,1.12,1.12,0,0,1,.39.87,1.31,1.31,0,0,1-.49,1.09,2.14,2.14,0,0,1-1.37.39,2.24,2.24,0,0,1-.83-0.15,2.39,2.39,0,0,1-.73-0.46l-0.2.49h-0.49Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M409.93,405.06v-1.92h0.5a1.83,1.83,0,0,0,.6,1.1,1.7,1.7,0,0,0,1.15.41,1.33,1.33,0,0,0,.78-0.2,0.64,0.64,0,0,0,.29-0.55,0.55,0.55,0,0,0-.21-0.44,1.45,1.45,0,0,0-.65-0.25l-0.87-.17a2.23,2.23,0,0,1-1.14-.51,1.22,1.22,0,0,1-.39-0.93,1.15,1.15,0,0,1,.47-1,2,2,0,0,1,1.25-.37,2.32,2.32,0,0,1,.76.13,2.62,2.62,0,0,1,.71.38l0.19-.42h0.44v1.79h-0.49a1.72,1.72,0,0,0-.53-1,1.56,1.56,0,0,0-1.06-.36,1.13,1.13,0,0,0-.68.19,0.6,0.6,0,0,0-.05.94,1.3,1.3,0,0,0,.61.25l0.92,0.18a2.31,2.31,0,0,1,1.13.49,1.12,1.12,0,0,1,.39.87,1.31,1.31,0,0,1-.49,1.09,2.14,2.14,0,0,1-1.37.39,2.24,2.24,0,0,1-.83-0.15,2.4,2.4,0,0,1-.73-0.46l-0.2.49h-0.49Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M418.52,403.52h0.57a2.06,2.06,0,0,1-.69,1.26,2.17,2.17,0,0,1-1.4.42,2.19,2.19,0,0,1-1.68-.68,2.84,2.84,0,0,1,0-3.58,2.19,2.19,0,0,1,1.68-.68,2.08,2.08,0,0,1,1.65.65,2.83,2.83,0,0,1,.58,1.89h-3.61v0.07a2.06,2.06,0,0,0,.37,1.32,1.28,1.28,0,0,0,1.06.45,1.47,1.47,0,0,0,.93-0.28A1.58,1.58,0,0,0,418.52,403.52Zm-0.24-1.23a1.84,1.84,0,0,0-.33-1.16,1.18,1.18,0,0,0-1-.38,1.16,1.16,0,0,0-.94.4,1.91,1.91,0,0,0-.39,1.14h2.61Z"
              transform="translate(-319.3 -377.19)"/>
        <path d="M419.82,405v-0.47h0.28a0.83,0.83,0,0,0,.51-0.11,0.6,0.6,0,0,0,.12-0.45v-2.53a0.6,0.6,0,0,0-.13-0.45,0.83,0.83,0,0,0-.51-0.11h-0.28v-0.47h1.74v0.79a1.88,1.88,0,0,1,.71-0.69,2,2,0,0,1,1-.23,1.84,1.84,0,0,1,.76.14,1.29,1.29,0,0,1,.75,1,5.47,5.47,0,0,1,.06,1V404a0.6,0.6,0,0,0,.12.45,0.83,0.83,0,0,0,.51.11h0.21V405H423v-0.47h0.32a0.82,0.82,0,0,0,.5-0.1A0.6,0.6,0,0,0,424,404v-1.72a2.1,2.1,0,0,0-.22-1.12,0.83,0.83,0,0,0-.74-0.32,1.39,1.39,0,0,0-1.06.43,1.61,1.61,0,0,0-.4,1.15V404a0.6,0.6,0,0,0,.12.45,0.82,0.82,0,0,0,.5.1h0.32V405h-2.69Z"
              transform="translate(-319.3 -377.19)"/>
    </svg>
    <h1>
        漏洞扫描报告
    </h1>
</div>
<div id="block-task-overview" class="container">
    <div class="page-header">
        <div id="titlestylmlColor" style="background: #fff;">目录
            <button id='buttonScriptrw-s'></button>
        </div>
    </div>
    <div id="div-styleml">
        <ul>
            <li><a id="tarlirw" href="#div-stylerw">1. 扫描任务</a></li>
            <li><a id="tarlisj" href="#div-stylesj">2. 数据统计</a></li>
            <li><a id="tarlizj" href="#div-stylezj">3. 主机列表</a></li>
            <li><a id="tarlild" href="#div-styleld">4. 漏洞列表</a></li>
            <#if kylinScanResultVos?? && (kylinScanResultVos?size > 0)>
                <li><a id="tarligc" href="#div-stylegc">5. 国产列表</a></li>
            </#if>
        </ul>
    </div>

    <div class="page-header">
        <div id="titlestylrwColor">1. 扫描任务
            <button id='buttonScriptrw-s'></button>
        </div>
    </div>
    <div id="div-stylerw" class="#buttonScript-z l-default">
        <table class="table table-no-border">
            <tr>
                <td>目标范围：${scan.target}</td>
                <td>扫描策略：${scan.strategy}</td>
                <td>扫描时间：${scan.scanTime}</td>
            </tr>
            <tr>
                <td>主机数量：${counter.totalHost}</td>
                <td>端口数量：${counter.totalPort}</td>
                <td>漏洞数量：${counter.totalVulnerability}</td>
            </tr>
        </table>
    </div>
    <div class="page-header">
        <div id="titlestylsjColor">2. 数据统计
            <button id='buttonScriptsj-s'></button>
            </button></div>
    </div>
    <div class="row" id="div-stylesj">
        <div class="col-xs-6 col-md-6">
            <div id="chart1" class="thumbnail" style="height:400px"></div>
        </div>
        <div class="col-xs-6 col-md-6">
            <div id="chart2" class="thumbnail" style="height:400px"></div>
        </div>
    </div>


    <div class="page-header">
        <div id="titlestylzjColor">3. 主机列表
            <button id='buttonScript-s'></button>
        </div>
    </div>
    <div id="div-stylezj" class="panel panel-default">
        <table class="table">
            <thead>
            <tr>
                <th rowspan="2">主机地址</th>
                <th rowspan="2">主机名</th>
                <th rowspan="2">操作系统</th>
                <th colspan="6" style="text-align: center;">漏洞风险(个)</th>
            </tr>
            <tr>
                <td>致命</td>
                <td>高</td>
                <td>中</td>
                <td>低</td>
                <td>未知</td>
                <td>总计</td>
            </tr>
            </thead>
            <tbody>
            <#list nmapScanResultVos as nmapScanResultVo>
            <tr>
                <td>
                    <#if resourceLocation == "">
                        <a href="host-${nmapScanResultVo.nmapScanResult.ipv4Address}.html" target="_Blank">${nmapScanResultVo.nmapScanResult.ipv4Address}</a>
                    <#else>
                        <a href="/scout-rest/v1/scanReport/hostVulnerability?nmapScanResultId=${nmapScanResultVo.nmapScanResult.id}" target="_Blank">${nmapScanResultVo.nmapScanResult.ipv4Address}</a>
                    </#if>
                </td>
                <td>${nmapScanResultVo.nmapScanResult.hostname!"-"}</td>
                <td>${nmapScanResultVo.nmapScanResult.systemInfo.family}</td>
                <td><span class="badge1">${nmapScanResultVo.vulnerabilityCriticalCount}</span></td>
                <td><span class="badge1">${nmapScanResultVo.vulnerabilityHighCount}</span></td>
                <td><span class="badge1">${nmapScanResultVo.vulnerabilityMediumCount}</span></td>
                <td><span class="badge1">${nmapScanResultVo.vulnerabilityLowCount}</span></td>
                <td><span class="badge1">${nmapScanResultVo.vulnerabilityUnknowCount}</span></td>
                <td><span class="badge1">${nmapScanResultVo.vulnerabilityCriticalCount + nmapScanResultVo.vulnerabilityHighCount + nmapScanResultVo.vulnerabilityMediumCount + nmapScanResultVo.vulnerabilityLowCount + nmapScanResultVo.vulnerabilityUnknowCount}</span></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <div class="page-header">
        <div id="titlestylldColor">4. 漏洞列表
            <button id='buttonScriptld-s'></button>
        </div>
    </div>
    <div id="div-styleld" class="panel panel-default">
        <table class="table">
            <tbody class="tbodytrClick">
            <tr>
                <th style="width: 15%">漏洞编号</th>
                <th style="width: 400px;">漏洞名称</th>
                <th>风险级别</th>
                <th>影响主机个数</th>
            </tr>
            <#list vulnerabilityVos as vulnerabilityVo>
            <tr>
                <td>${vulnerabilityVo.vulnerability.id}</td>
                <td><span class="tdzs-img"></span>${vulnerabilityVo.vulnerability.name}<#if vulnerabilityVo.vulnerability.cveId??>(${vulnerabilityVo.vulnerability.cveId})</#if></td>
                <td>
                    <#if vulnerabilityVo.vulnerability.severity == "低危">
                        <img src="${ctx}/static/img/5.gif" class="zjlbImg"/>低</td>
                    <#elseif vulnerabilityVo.vulnerability.severity == "中危">
                        <img src="${ctx}/static/img/4.gif" class="zjlbImg"/>中</td>
                    <#elseif vulnerabilityVo.vulnerability.severity == "高危">
                        <img src="${ctx}/static/img/3.gif" class="zjlbImg"/>高</td>
                    <#elseif vulnerabilityVo.vulnerability.severity == "超危">
                        <img src="${ctx}/static/img/2.gif" class="zjlbImg"/>致命</td>
                    <#else>
                        <img src="${ctx}/static/img/1.gif" class="zjlbImg"/>未知</td>
                    </#if>
                <td>${vulnerabilityVo.nmapScanResultVos?size}</td>
            </tr>
            <tr class="trNone">
                <td></td>
                <td colspan="4">
                    <table class="tableGfax">
                        <tbody>
                        <tr>
                            <th style="width: 180px;">受影响主机</th>
                            <td>
                                <#list vulnerabilityVo.nmapScanResultVos as nmapScanResultVo>
                                    <#if resourceLocation == "">
                                        <a href="host-${nmapScanResultVo.nmapScanResult.ipv4Address}.html" target="_Blank">${nmapScanResultVo.nmapScanResult.ipv4Address}; </a>
                                    <#else>
                                        <a href="/scout-rest/v1/scanReport/hostVulnerability?nmapScanResultId=${nmapScanResultVo.nmapScanResult.id}" target="_blank">${nmapScanResultVo.nmapScanResult.ipv4Address}; </a>
                                    </#if>
                                </#list>
                            </td>
                        </tr>
                        <tr>
                            <th>详细描述</th>
                            <td>${vulnerabilityVo.vulnerability.vulnerabilityDesc!"暂无"}</td>
                        </tr>
                        <tr>
                            <th>解决办法</th>
                            <td>${vulnerabilityVo.vulnerability.vulnerabilitySolution!"暂无"}</td>
                        </tr>
                        <tr>
                            <th>利用方式</th>
                            <td>${vulnerabilityVo.vulnerability.thrtype!"暂无"}</td>
                        </tr>
                        <tr>
                            <th>发现日期</th>
                            <td>${vulnerabilityVo.vulnerability.published}</td>
                        </tr>
                        <tr>
                            <th>更新日期</th>
                            <td>${vulnerabilityVo.vulnerability.modified}</td>
                        </tr>
                        <tr>
                            <th>CNNVD编号</th>
                            <td><a href="http://www.cnnvd.org.cn/web/xxk/ldxqById.tag?CNNVD=${vulnerabilityVo.vulnerability.id}" target="_blank">${vulnerabilityVo.vulnerability.id}</a></td>
                        </tr>
                        <#if vulnerabilityVo.vulnerability.cveId??>
                        <tr>
                            <th>CVE编号</th>
                            <td><a href="https://nvd.nist.gov/vuln/detail/${vulnerabilityVo.vulnerability.cveId}" target="_blank">${vulnerabilityVo.vulnerability.cveId}</a></td>
                        </tr>
                        <tr>
                            <th>CVSS评分*10</th>
                            <td>${vulnerabilityVo.vulnerability.cvssBaseScore!"暂无"}</td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <#if kylinScanResultVos?? && (kylinScanResultVos?size > 0)>
    <div class="page-header">
        <div id="titlestylgcColor">5. 国产列表
            <button id='buttonScript-s'></button>
        </div>
    </div>
    <div id="div-stylegc" class="panel panel-default">
        <table class="table">
            <thead>
            <tr>
                <th rowspan="2">主机地址</th>
                <th rowspan="2">主机名</th>
                <th rowspan="2">操作系统</th>
                <th colspan="6" style="text-align: center;">漏洞风险(个)</th>
            </tr>
            <tr>
                <td>致命</td>
                <td>高</td>
                <td>中</td>
                <td>低</td>
                <td>未知</td>
                <td>总计</td>
            </tr>
            </thead>
            <tbody>
            <#list kylinScanResultVos as nmapScanResultVo>
                <tr>
                    <td>
                        <#if resourceLocation == "">
                            <a href="host-${nmapScanResultVo.nmapScanResult.ipv4Address}.html" target="_Blank">${nmapScanResultVo.nmapScanResult.ipv4Address}</a>
                        <#else>
                            <a href="/scout-rest/v1/scanReport/hostVulnerability?nmapScanResultId=${nmapScanResultVo.nmapScanResult.id}" target="_Blank">${nmapScanResultVo.nmapScanResult.ipv4Address}</a>
                        </#if>
                    </td>
                    <td>${nmapScanResultVo.nmapScanResult.hostname!"-"}</td>
                    <td>${nmapScanResultVo.nmapScanResult.systemInfo.family}</td>
                    <td><span class="badge1">${nmapScanResultVo.vulnerabilityCriticalCount}</span></td>
                    <td><span class="badge1">${nmapScanResultVo.vulnerabilityHighCount}</span></td>
                    <td><span class="badge1">${nmapScanResultVo.vulnerabilityMediumCount}</span></td>
                    <td><span class="badge1">${nmapScanResultVo.vulnerabilityLowCount}</span></td>
                    <td><span class="badge1">${nmapScanResultVo.vulnerabilityUnknowCount}</span></td>
                    <td><span class="badge1">${nmapScanResultVo.vulnerabilityCriticalCount + nmapScanResultVo.vulnerabilityHighCount + nmapScanResultVo.vulnerabilityMediumCount + nmapScanResultVo.vulnerabilityLowCount + nmapScanResultVo.vulnerabilityUnknowCount}</span></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    </#if>

    <div class="container overBottom">
        <svg class="svg" xmlns="http://www.w3.org/2000/svg" version="1.1" fill="#2196F3"
             style="width: 160px; height: 40px; display: inline-flex;">
            <path d="M319.3,377.25v19.43s1.34,5.14,6.47,4.09l0.1,4.53s0.65,6.11,8.79,3.92v-7.8s-1.1-3.62-3-3.62h-3.44L328.16,381C328.14,376,321.38,377.42,319.3,377.25Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M334.06,377.25v19.43s1.34,5.14,6.47,4.09l0.09,4.53s0.65,6.11,8.79,3.92v-7.8s-1.1-3.62-3-3.62H343L342.93,381C342.91,376,336.15,377.42,334.06,377.25Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M348.83,377.25v19.43s1.34,5.14,6.47,4.09l0.09,4.53s0.65,6.11,8.79,3.92v-7.8s-1.1-3.62-3-3.62h-3.44L357.69,381C357.67,376,350.91,377.42,348.83,377.25Z"
                  transform="translate(-319.3 -377.19)"/>
            <path
                    d="M377.27,384.92a10.71,10.71,0,0,1-3.65,1.8l-0.35-.7a10.14,10.14,0,0,0,3.35-1.65,8.56,8.56,0,0,1-1.7-2,10.48,10.48,0,0,1-1.3,1.5l-0.6-.5a11.72,11.72,0,0,0,1.4-1.77,9.66,9.66,0,0,0,1.1-2.13l0.95,0.15q-0.25.55-.5,1h3.95l0.35,0.35a8.12,8.12,0,0,1-2.3,3.4,10.75,10.75,0,0,0,3.25,1.55l-0.45.8a10.33,10.33,0,0,1-3.5-1.8h0Zm0-1.1a8.07,8.07,0,0,0,1.8-2.4h-3.55l-0.1.15a6.47,6.47,0,0,0,1.85,2.25h0Zm-6.45,1.75,0-.2v6.15h0.95v-5.9a7.58,7.58,0,0,0,.8,1l0.55-.7q-0.9-1-1.35-1.55v-1.45H373v-0.8h-1.2v-2.55h-0.95v2.55h-1.75v0.8h1.7a16.19,16.19,0,0,1-.93,2.67,16.79,16.79,0,0,1-1.22,2.42l0.55,0.45a14,14,0,0,0,1.6-2.9h0Zm8.3,5.35v0.5h1v-4.8h-5.8v4.8h0.95v-0.5h3.9Zm-3.9-.8v-2.75h3.9v2.75h-3.9Zm9.73-9.85h7.15v9.6a0.38,0.38,0,0,0,.4.45h1.15a0.43,0.43,0,0,0,.45-0.45v-2.2l0.85,0.15v2.25a1,1,0,0,1-1.15,1.1h-1.55a1,1,0,0,1-1.1-1v-9h-5.25v3.3a14,14,0,0,1-.47,3.87,7.7,7.7,0,0,1-1.67,3l-0.75-.5a7.31,7.31,0,0,0,1.55-2.82,13.63,13.63,0,0,0,.4-3.52v-4.15h0Zm1.6,4.55v0.8h3.8v-0.8h-3.8Z"
                    transform="translate(-319.3 -377.19)"/>
            <path
                    d="M376,405.24H375.5l-0.36-.62a4.25,4.25,0,0,1-1.05.56,3.15,3.15,0,0,1-1.06.19,3,3,0,0,1-2.36-1,3.64,3.64,0,0,1-.88-2.57,4.06,4.06,0,0,1,.26-1.44,3.32,3.32,0,0,1,.72-1.16,2.88,2.88,0,0,1,1-.68,3.28,3.28,0,0,1,1.25-.23,2.84,2.84,0,0,1,1.09.21,3.2,3.2,0,0,1,1,.63l0.33-.64h0.46V401h-0.52a2.89,2.89,0,0,0-.83-1.54,2.06,2.06,0,0,0-1.44-.55,1.92,1.92,0,0,0-1.61.8,3.5,3.5,0,0,0-.6,2.16,3.4,3.4,0,0,0,.61,2.16,2,2,0,0,0,1.69.77,2.19,2.19,0,0,0,1.38-.41,1.32,1.32,0,0,0,.53-1.09v-0.41a0.58,0.58,0,0,0-.14-0.45,1.11,1.11,0,0,0-.63-0.11h-0.54v-0.53h3.34v0.53h-0.38a1,1,0,0,0-.59.1,0.65,0.65,0,0,0-.11.46v2.38h0Zm4.76-2.58-0.47.21-0.42.16-0.38.13a1.83,1.83,0,0,0-.71.37,0.7,0.7,0,0,0-.22.53,0.65,0.65,0,0,0,.21.51,0.81,0.81,0,0,0,.56.19,1.37,1.37,0,0,0,1.06-.41,1.64,1.64,0,0,0,.38-1.16v-0.53h0Zm0.06,1.91a2.79,2.79,0,0,1-.83.59,2.21,2.21,0,0,1-.91.2A1.56,1.56,0,0,1,378,405a1.15,1.15,0,0,1-.4-0.92,1.08,1.08,0,0,1,.36-0.84,3.28,3.28,0,0,1,1.25-.57l0.78-.22a1.35,1.35,0,0,0,.6-0.29,0.74,0.74,0,0,0,.15-0.51,0.78,0.78,0,0,0-.3-0.64,1.24,1.24,0,0,0-.8-0.24,1.66,1.66,0,0,0-.59.1,1.48,1.48,0,0,0-.5.32,0.59,0.59,0,0,1,.33.17,0.47,0.47,0,0,1,.12.33,0.45,0.45,0,0,1-.16.35,0.56,0.56,0,0,1-.39.14,0.59,0.59,0,0,1-.6-0.6,1.06,1.06,0,0,1,.54-0.9,2.36,2.36,0,0,1,1.35-.36,3,3,0,0,1,.78.1,1.57,1.57,0,0,1,.57.26,1.12,1.12,0,0,1,.38.45,2.07,2.07,0,0,1,.11.79v2.31a1,1,0,0,0,.07.45,0.24,0.24,0,0,0,.23.13,0.34,0.34,0,0,0,.33-0.26,3.4,3.4,0,0,0,.11-1h0.46v0.11a2.43,2.43,0,0,1-.25,1.26,0.88,0.88,0,0,1-.8.39,0.93,0.93,0,0,1-.61-0.19,1.07,1.07,0,0,1-.33-0.59h0Zm3.66-3.58v3.14a0.61,0.61,0,0,1-.12.46,0.83,0.83,0,0,1-.51.11h-0.29v0.48h2.86v-0.48H386a0.85,0.85,0,0,1-.52-0.11,0.6,0.6,0,0,1-.13-0.46V401h1.11V400.5h-1.11v-0.36a2.2,2.2,0,0,1,.18-1.05,0.66,0.66,0,0,1,.61-0.29,0.9,0.9,0,0,1,.66.26,0.52,0.52,0,0,0-.28.16,0.41,0.41,0,0,0-.1.29,0.45,0.45,0,0,0,.15.34,0.51,0.51,0,0,0,.37.14,0.52,0.52,0,0,0,.4-0.16,0.61,0.61,0,0,0,.15-0.43,0.88,0.88,0,0,0-.4-0.75,1.81,1.81,0,0,0-1.07-.29,1.4,1.4,0,0,0-1.12.44,2,2,0,0,0-.37,1.31v0.4h-0.92V401h0.92Zm4.17,0h-0.92V400.5h0.92v-0.4a2,2,0,0,1,.37-1.31,1.39,1.39,0,0,1,1.12-.44,1.81,1.81,0,0,1,1.07.29,0.88,0.88,0,0,1,.4.75,0.6,0.6,0,0,1-.15.43,0.52,0.52,0,0,1-.4.16,0.51,0.51,0,0,1-.37-0.14,0.45,0.45,0,0,1-.15-0.34,0.42,0.42,0,0,1,.1-0.29,0.53,0.53,0,0,1,.28-0.16,1,1,0,0,0-.3-0.19,1,1,0,0,0-.36-0.06,0.66,0.66,0,0,0-.61.29,2.18,2.18,0,0,0-.18,1.05v0.36h1.11V401h-1.11v3.14a0.6,0.6,0,0,0,.13.46,0.85,0.85,0,0,0,.52.11h0.45v0.48h-2.86v-0.48H388a0.84,0.84,0,0,0,.51-0.11,0.62,0.62,0,0,0,.12-0.46V401h0Z"
                    transform="translate(-319.3 -377.19)"/>
            <path d="M399.32,387.15q0.37-.52,1.2-1.88h-3.4v-0.94h4q0.63-1.1,1.2-2.3l1,0.42a12.24,12.24,0,0,0-.73,1.26q-0.26.42-.37,0.63h7.48v0.94H407a7.07,7.07,0,0,1-2.2,3.29q0.42,0.16,1.2.52,0.94,0.42,2.82,1.2l-0.52.94a15.66,15.66,0,0,1-1.41-.68q-1.94-.89-3-1.31a18.56,18.56,0,0,1-5.75,2.09L398,391a6.51,6.51,0,0,1-.37-0.73,15.44,15.44,0,0,0,5.28-1.52q-0.84-.37-2.61-1.2Q399.63,387.31,399.32,387.15Zm3.14-7.84,0.89-.52a4.55,4.55,0,0,0,.37.58,9.58,9.58,0,0,1,.73,1.15l-0.52.31h5.18v2.41h-1v-1.52h-9.36v1.52h-1v-2.41h5.65A7.31,7.31,0,0,0,402.46,379.31Zm-1.78,7.53q0.42,0.21,1.41.58l1.67,0.73a6.1,6.1,0,0,0,2.14-2.88h-4.29Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M412.1,385.06a20.05,20.05,0,0,0,2.82-6.12l1,0.31q-0.05.16-.16,0.42-0.37,1.1-1,2.61v9h-0.94v-7.16a18.61,18.61,0,0,1-1.26,1.93Q412.31,385.53,412.1,385.06Zm7.16-5.75,0.84-.42,0.42,0.73q0.26,0.52.37,0.78l-0.58.26h4.39v0.89h-8.94v-0.89h4.18Q419.63,380,419.27,379.31Zm-2.77,3.4h7.43v0.89H416.5v-0.89Zm0,2h7.43v0.89H416.5v-0.89Zm0.1,2h7.22v4.55h-1v-0.73h-5.23v0.73h-1v-4.55Zm1,0.89v2.14h5.23v-2.14h-5.23Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M396.64,405V404.5H397a0.85,0.85,0,0,0,.49-0.11,1,1,0,0,0,.29-0.45l2.1-5.44h0.82l2.08,5.4a1,1,0,0,0,.31.49,0.88,0.88,0,0,0,.5.12h0.28V405h-3.09V404.5h0.53a1.16,1.16,0,0,0,.46-0.06,0.24,0.24,0,0,0,.11-0.23,0.43,0.43,0,0,0,0-.1l-0.05-.16-0.41-1.12h-2.49l-0.36.92a2.81,2.81,0,0,0-.11.32,0.78,0.78,0,0,0,0,.18,0.21,0.21,0,0,0,.11.21,1.45,1.45,0,0,0,.51.05h0.48V405h-2.82Zm2.4-2.7h2.08l-1.06-2.79Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M404.83,405.06v-1.92h0.5a1.83,1.83,0,0,0,.6,1.1,1.7,1.7,0,0,0,1.15.41,1.33,1.33,0,0,0,.78-0.2,0.64,0.64,0,0,0,.29-0.55,0.55,0.55,0,0,0-.21-0.44,1.45,1.45,0,0,0-.65-0.25l-0.87-.17a2.23,2.23,0,0,1-1.14-.51,1.22,1.22,0,0,1-.39-0.93,1.15,1.15,0,0,1,.47-1,2,2,0,0,1,1.25-.37,2.32,2.32,0,0,1,.76.13,2.62,2.62,0,0,1,.71.38l0.19-.42h0.44v1.79h-0.49a1.72,1.72,0,0,0-.53-1,1.56,1.56,0,0,0-1.06-.36,1.13,1.13,0,0,0-.68.19,0.57,0.57,0,0,0-.26.48,0.58,0.58,0,0,0,.2.46,1.3,1.3,0,0,0,.61.25l0.92,0.18a2.31,2.31,0,0,1,1.13.49,1.12,1.12,0,0,1,.39.87,1.31,1.31,0,0,1-.49,1.09,2.14,2.14,0,0,1-1.37.39,2.24,2.24,0,0,1-.83-0.15,2.39,2.39,0,0,1-.73-0.46l-0.2.49h-0.49Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M409.93,405.06v-1.92h0.5a1.83,1.83,0,0,0,.6,1.1,1.7,1.7,0,0,0,1.15.41,1.33,1.33,0,0,0,.78-0.2,0.64,0.64,0,0,0,.29-0.55,0.55,0.55,0,0,0-.21-0.44,1.45,1.45,0,0,0-.65-0.25l-0.87-.17a2.23,2.23,0,0,1-1.14-.51,1.22,1.22,0,0,1-.39-0.93,1.15,1.15,0,0,1,.47-1,2,2,0,0,1,1.25-.37,2.32,2.32,0,0,1,.76.13,2.62,2.62,0,0,1,.71.38l0.19-.42h0.44v1.79h-0.49a1.72,1.72,0,0,0-.53-1,1.56,1.56,0,0,0-1.06-.36,1.13,1.13,0,0,0-.68.19,0.6,0.6,0,0,0-.05.94,1.3,1.3,0,0,0,.61.25l0.92,0.18a2.31,2.31,0,0,1,1.13.49,1.12,1.12,0,0,1,.39.87,1.31,1.31,0,0,1-.49,1.09,2.14,2.14,0,0,1-1.37.39,2.24,2.24,0,0,1-.83-0.15,2.4,2.4,0,0,1-.73-0.46l-0.2.49h-0.49Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M418.52,403.52h0.57a2.06,2.06,0,0,1-.69,1.26,2.17,2.17,0,0,1-1.4.42,2.19,2.19,0,0,1-1.68-.68,2.84,2.84,0,0,1,0-3.58,2.19,2.19,0,0,1,1.68-.68,2.08,2.08,0,0,1,1.65.65,2.83,2.83,0,0,1,.58,1.89h-3.61v0.07a2.06,2.06,0,0,0,.37,1.32,1.28,1.28,0,0,0,1.06.45,1.47,1.47,0,0,0,.93-0.28A1.58,1.58,0,0,0,418.52,403.52Zm-0.24-1.23a1.84,1.84,0,0,0-.33-1.16,1.18,1.18,0,0,0-1-.38,1.16,1.16,0,0,0-.94.4,1.91,1.91,0,0,0-.39,1.14h2.61Z"
                  transform="translate(-319.3 -377.19)"/>
            <path d="M419.82,405v-0.47h0.28a0.83,0.83,0,0,0,.51-0.11,0.6,0.6,0,0,0,.12-0.45v-2.53a0.6,0.6,0,0,0-.13-0.45,0.83,0.83,0,0,0-.51-0.11h-0.28v-0.47h1.74v0.79a1.88,1.88,0,0,1,.71-0.69,2,2,0,0,1,1-.23,1.84,1.84,0,0,1,.76.14,1.29,1.29,0,0,1,.75,1,5.47,5.47,0,0,1,.06,1V404a0.6,0.6,0,0,0,.12.45,0.83,0.83,0,0,0,.51.11h0.21V405H423v-0.47h0.32a0.82,0.82,0,0,0,.5-0.1A0.6,0.6,0,0,0,424,404v-1.72a2.1,2.1,0,0,0-.22-1.12,0.83,0.83,0,0,0-.74-0.32,1.39,1.39,0,0,0-1.06.43,1.61,1.61,0,0,0-.4,1.15V404a0.6,0.6,0,0,0,.12.45,0.82,0.82,0,0,0,.5.1h0.32V405h-2.69Z"
                  transform="translate(-319.3 -377.19)"/>
        </svg>
    </div>
</div>
</body>

</html>

