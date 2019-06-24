$(function () {

    'use strict';


    var $citypicker1 = $('#city-picker1');

    $citypicker1.citypicker();

    var $citypicker2 = $('#city-picker2');

    $citypicker2.citypicker({
        province: '江苏省',
        city: '常州市',
        district: '溧阳市'
    });

    var $citypicker3 = $('#city-picker3');

    $('#reset').click(function () {
        $citypicker3.citypicker('reset');
    });

    $('#destroy').click(function () {
        $citypicker3.citypicker('destroy');
    });
    //
    //$('#distpicker1').distpicker();
    //
    //$('#distpicker2').distpicker({
    //  province: '---- 所在省 ----',
    //  city: '---- 所在市 ----',
    //  district: '---- 所在区 ----'
    //});
    //
    //$('#distpicker3').distpicker({
    //  province: '浙江省',
    //  city: '杭州市',
    //  district: '西湖区'
    //});
    //
    //$('#distpicker4').distpicker({
    //  placeholder: false
    //});
    //
    //$('#distpicker5').distpicker({
    //  autoSelect: false
    //});

});
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
    //日期控件初始化
    $('#daterange').daterangepicker(
        {
            'locale': locale,
            startDate: moment().subtract(29, 'days'),
            endDate: moment(),
            autoUpdateInput: false
        },
        function (start, end) {
            $('#daterangeC').val(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
        }
    );
};
$(document).ready(function() {
    init();
});
$('#cord').click(function () {
    $('#cord').attr('src', "/user/createImg?time=" + new Date().getTime());
})
$('#cordZ').click(function () {
    $('#cordZ').attr('src',"/user/createImg?time="+ new Date().getTime());
})
function register() {
    var formData1 = new FormData();
    formData1.append("user_id", $('#user_id').val());
    formData1.append("user_name", $('#user_name').val());
    formData1.append("user_pwd1", $('#user_pwd1').val());
    formData1.append("user_pwd2", $('#user_pwd2').val());
    formData1.append("inputCordZ", $('#inputCordZ').val());
   if (formData1.get('user_pwd1') != formData1.get('user_pwd2')) {
     alert("两次密码必须相同！");
    } else {
        $.ajax({
            type: 'post',        //数据提交的方式
            url: "/user/register",//数据提交的路径
            data: formData1,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                if(data=='9'){
                    alert("注册成功！");
                    location.reload(true);
               }else if(data=='1'){
                    alert("验证码输入错误！");
                }else{
                    alert("用户已经注册！");
                }
            },
            error: function (XMLHttpRequest, textStatus, error) {
                alert("注册失败，请检查是否有选项未填，两次密码是否一致，验证码是否填写正确！");
            }
        })
   }
}//注册处理
function login() {
    var formData = new FormData();
    formData.append("inputPhone", $('#inputPhone').val());
    formData.append("inputPassword", $('#inputPassword').val());
    formData.append("inputCord1", $('#inputCord1').val());

   //if(formData1.get('inputPhone')==""){
    //    alert("用户账号不能为空！");
    //} else {
        $.ajax({
            type: 'post',        //数据提交的方式
            url: "/user/login",//数据提交的路径
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                var json = eval('(' + data + ')');
          //      if(data==="0"){
        //            alert("验证码错误！");
        //        }else if(date==="1"){
       //             alert("该用户未注册！");
     //           }else if(data==="2"){
     //               alert("密码不正确！");
     //           }else{
                    $('#user .dropdown-toggle').html("<img src=\"../img/user/"+json.user_id+"/"+json.user_picture+"\" height=\"35px\"  width=\"35px\" style=\"border-radius:20px;\"/> "+json.user_name);
                    $('#myModal').modal('hide');
                    $('#user_name').html(json.user_name);
                  //  location.reload(true);
                    $('#user').attr('style','display:inline;')

                },
           // }
            error: function (XMLHttpRequest, textStatus, error) {
                alert("登录失败,请检查用户是否注册，或者密码是否输入正确！");

            }
        })
   // }

}//登陆处理


$(function () {

    $.ajax({
        type: 'post',
        url:"/homestay/home",
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            for(i in data){
                var str="";
                str+=" <div class=\"col-md-3\">\n" +
                    "                 <div class=\"thumbnail\">\n" +
                    "           <img class=\"img-responsive\" src=\"../img/space/"+$.parseJSON(data[i].picture).封面+"\" />\n" +
                    "            <div class=\"caption\"><h5>"+data[i].h_name+"</h5>";
                for(j in data[i].roomType){
                    if(data[i].roomType[j]==0){str+=" <span class=\"label label-default\">单间</span> ";}
                    else{str+=" <span class=\"label label-default\">整套</span>";}
                }
                str+=" <h6>"+$.parseJSON(data[i].h_address).addressName+"</h6>\n" +
                    "                             <h4 style=\"color:#F00;\">￥"+data[i].price+"起</h4></div></div></div>";
                alert(str);
                $('#content').append(str);

            }
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert(error);
        }
    })
})

function search() {
    var search={};
    search.city=$('#city').val();
    search.daterangeC=$('#daterangeC').val();
    $.ajax({
        type: 'post',
        url:"/homestay/setHomestay",
        data:JSON.stringify(search),
        cache: false,
        processData: false,
        contentType : 'application/json;charset=utf-8',
        success: function (data) {
           window.location.href="/homestay/toHomestay";
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert(error);
        }
    })
}

