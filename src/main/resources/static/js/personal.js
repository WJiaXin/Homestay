var ltop=$('#list').offset().top;

$(window).scroll(function(){
    if(ltop<$(document).scrollTop()){
        $('#list').addClass("list");
    }else{
        $('#list').removeClass("list");
    }

});
$("#shangchuan").click(function () {
    $("#file").click();
})
function pimg(id,img){
    var formData = new FormData();
    formData.append("file",$('#'+id+'')[0].files[0]);

    $.ajax({
        type: 'post',
        url:"/user/pimg",
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            $("#img").attr('src',"../img/user/"+data);
            location.reload(true);//刷新
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert('照片名含有非法字符，请重新上传！');
        }
    });
};//上传更换图
$('#cordZ').click(function () {
    $('#cordZ').attr('src',"/user/createImg?time="+ new Date().getTime());
})

function upName() {
    var formData = new FormData();
    formData.append("inputName", $('#inputName').val());
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/user/upName",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            location.reload(true);//刷新
        },
        // }
        error: function (XMLHttpRequest, textStatus, error) {
            alert("修改出错！");

        }
    })
}//修改用户名
function upPwd() {
    var formData = new FormData();
    formData.append("uppwd1", $('#newpwd').val());
    formData.append("uppwd2", $('#repwd').val());
    formData.append("inputCordZ", $('#inputCordZ').val());
    formData.append("pwd", $('#nowpwd').val());
    if (formData.get('uppwd1') != formData.get('uppwd2')) {
        alert("两次输入的密码必须相同！");
    }else {
        $.ajax({
            type: 'post',        //数据提交的方式
            url: "/user/upPwd",//数据提交的路径
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                if(data=='0'){
                    alert("验证码错误！");
                }else if(data=='1'){
                    alert("密码错误！");
                }
                else {
                    alert("修改成功！");
                    //location.reload(true);//刷新
                }
            },
            // }
            error: function (XMLHttpRequest, textStatus, error) {
                alert("修改出错！");

            }
        })
    }
}