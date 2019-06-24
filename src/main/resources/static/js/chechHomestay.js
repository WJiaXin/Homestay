var formData = new FormData();
formData.append("qq", "qq");
$.ajax({
    type: 'post',        //数据提交的方式
    url: "/room/getOneApplyHotel",//数据提交的路径
    data: formData,
    cache: false,
    processData: false,
    contentType: false,
    scriptCharset: 'utf-8',
    success: function (data) {
        document.getElementById("roomname").innerHTML = data.roomname;
        document.getElementById("shi").innerHTML = data.shi;
        document.getElementById("ting").innerHTML = data.ting;
        document.getElementById("wei").innerHTML = data.wei;
        document.getElementById("chu").innerHTML = data.chu;
        document.getElementById("yangtai").innerHTML = data.yangtai;
        document.getElementById("roommianji").innerHTML = data.roommianji;
        document.getElementById("bigbed2").innerHTML = data.bigbed2;
        document.getElementById("bigbed").innerHTML = data.bigbed;
        document.getElementById("bed").innerHTML = data.bed;
        document.getElementById("shangxia").innerHTML = data.shangxia;
        document.getElementById("shafa").innerHTML = data.shafa;
        document.getElementById("tatami").innerHTML = data.tatami;
        document.getElementById("qita").innerHTML = data.qita;
        document.getElementById("roomprice").innerHTML = data.roomprice;
        document.getElementById("someprice").innerHTML = data.someprice;
        document.getElementById("peoplenum").innerHTML = data.peoplenum;
        document.getElementById("hotelname").innerHTML = data.hotelname;
        document.getElementById("hotelbrief").innerHTML = data.hotelbrief;

        html1 = "";
        if (data.roomtype == "整套房间") {
            html1 = "  <div class=\"d-flex flex-row align-items-center p-2\"  tabindex=\"0\">\n" +
                "                            <div class=\"p-4 bg-body rounded czClick\" tabindex=\"0\"><i class=\"iconfont icon-zhengtaofangzi font-weight-bold\" style=\" font-size:2em;\"></i></div>\n" +
                "                            <div class=\"ml-3 text-gray6\" style=\"font-size:14px;\"><h5 style=\"color:#000;\">整套房间</h5>\n" +
                "                                客人可以预订整个空间，不与您或其它客人共住，如一整套三居室</div>\n" +
                "                        </div>"
        } else {
            html1 = "<div class=\"d-flex flex-row align-items-center p-2\"  tabindex=\"0\">\n" +
                "                            <div class=\"p-4 bg-body rounded czClick\" tabindex=\"0\"><i class=\"iconfont icon-danjian font-weight-bold\" style=\" font-size:2em;\"></i></div>\n" +
                "                            <div class=\"ml-3 text-gray6\" style=\"font-size:14px;\"><h5 style=\"color:#000;\">独立房间</h5>\n" +
                "                                客人预订的是一个或多个房间，公共空间与他人共用，如三居室中的主卧</div>\n" +
                "                        </div>"
        }
        $('#roomtype').html(html1);
        if(data.roomweiyu =="独立卫生间"){
            html1 = "<div class=\"d-flex flex-row align-items-center p-2\">\n" +
                "                            <div class=\"p-4 bg-body rounded czClick\"><i class=\"iconfont icon-_huabanfuben\" style=\" font-size:2em;\"></i></div>\n" +
                "                            <div class=\"ml-3\"><h5 style=\"color:#000;\">独立卫生间</h5></div>\n" +
                "                        </div>"
        }else {
            html1=" <div class=\"d-flex flex-row align-items-center p-2\">\n" +
                "                            <div class=\"p-4 bg-body rounded czClick\"><i class=\"iconfont icon-gonggongweishengjian1\" style=\"font-size:2em;\"></i></div>\n" +
                "                            <div class=\"ml-3\"><h5 style=\"color:#000;\">公共卫生间</h5></div>\n" +
                "                        </div>"
        }
        $('#roomweiyu').html(html1);
        if (data.img1=="无"){
            document.getElementById("img1").innerHTML = "无";
        } else{
            html1= "                                <img src=\"../img/"+data.img1+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img1').html(html1);
        }
        if (data.img2=="无"){
            document.getElementById("img2").innerHTML = "无";
        } else{
            html1= "                                <img src=\"../img/"+data.img2+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img2').html(html1);
        }
        if (data.img3=="无"){
            document.getElementById("img3").innerHTML = "无";
        } else{
            html1= "                                <img src=\"../img/"+data.img3+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img3').html(html1);
        }
        if (data.img4=="无"){
            document.getElementById("img4").innerHTML = "无";
        } else{
            html1= "                                <img src=\"../img/"+data.img4+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img4').html(html1);
        }

        if (data.img5=="无"){
            document.getElementById("img5").innerHTML = "无";
        } else{
            html1=
                "                                <img src=\"../img/"+data.img5+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img5').html(html1);
        }

        if (data.img6=="无"){
            document.getElementById("img6").innerHTML = "无";
        } else{
            html1=
                "                                <img src=\"../img/"+data.img6+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img6').html(html1);
        }

        if (data.img7=="无"){
            document.getElementById("img7").innerHTML = "无";
        } else{
            html1=
                "                                <img src=\"../img/"+data.img7+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img7').html(html1);
        }

        if (data.img8=="无"){
            document.getElementById("img8").innerHTML = "无";
        } else{
            html1=
                "                                <img src=\"../img/"+data.img8+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img8').html(html1);
        }

        if (data.img9=="无"){
            document.getElementById("img9").innerHTML = "无";
        } else{

            "                                <img src=\"../img/"+data.img9+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\" >\n"
            $('#img9').html(html1);
        }

    },
    error: function () {
        alert("查看失败!!!");
    }
})
function agreehotelApply() {
    var formData = new FormData();
    formData.append("qq", "qq");
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/room/agreehotelApply",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            alert("同意成功！！！");
            window.location.href="../html/admin.html";
        },
        error: function () {
            window.location.href="../html/admin.html";
        }
    })
}
function notAgreehotelApply() {
    var formData = new FormData();
    formData.append("qq", "qq");
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/room/notAgreehotelApply",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            alert("拒绝成功！！！");
            window.location.href="../html/admin.html";
        },
        error: function () {
            window.location.href="../html/admin.html";
        }
    })
}