
function init() {
    //定义locale汉化插件
    var locale = {
        "format": 'YYYY-MM-DD',
        "separator": " 至 ",
        "applyLabel": "确定",
        "cancelLabel": "取消",
        "fromLabel": "起始时间",
        "toLabel": "结束时间'",
        "weekLabel": "W",
        "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
        "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        "firstDay": 1
    };
    $('#daterangeC').html(moment().format('YYYY-MM-DD') + ' 至 ' + moment().add(1,'day').format('YYYY-MM-DD'));
    //日期控件初始化
    $('#daterange').daterangepicker(
        {
            'locale': locale,
            startDate: moment().subtract(29, 'days'),
            endDate: moment(),
            autoUpdateInput: false
        },
        function (start, end) {
            $('#daterangeC').html(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
        }
    );
};
$(document).ready(function() {
    init();
});
$('#select div').bind("click",function(){

    var index=$('#select div').index(this);
    $('#select>div:eq('+index+')').attr('style','border-bottom:#69F 2px solid;color:#69F;');
    $('#select>div:gt('+index+')').attr('style','border-bottom:#FFF 2px solid;');
    $('#select>div:lt('+index+')').attr('style','border-bottom:#FFF 2px solid;');
});

var ptop=$('#pay').offset().top;

$(window).scroll(function(){
    if(ptop<$(document).scrollTop()){
        $('#pay').addClass("pay");
    }else{
        $('#pay').removeClass("pay");
    }

});

