$(function () {

    var app = echarts.init(document.getElementById('chart1'));
    app.title = '环形图';
    option = {
        tooltip: {trigger: 'item', formatter: "{a} <br/>{b}: {c} ({d}%)"},
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ]
            }
        ]
    };
    app.setOption(option);


    var app1 = echarts.init(document.getElementById('chart2'));
    app1.title = '环形图';
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ]
            }
        ]
    };
    ;
// 使用刚指定的配置项和数据显示图表。
    app1.setOption(option);

    $('#titlestylsjColor').click(function () {
        $(this).toggleClass('titleDivbckcolor ')
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylesj').toggle();
        app.resize();
        app1.resize();
    })

    $('#tarlisj').click(function () {
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylesj').toggle()
        $('#titlestylsjColor').toggleClass('titleDivbckcolor')
        app.resize();
        app1.resize();
    })
    $('#tbodytrClick tr:odd').click(function () {
        $(this).next().toggle()
        $(this).find('span').toggleClass('tdzs-Zkimg')
    })

})