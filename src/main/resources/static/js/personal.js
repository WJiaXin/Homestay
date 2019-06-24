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
    } else {
        $.ajax({
            type: 'post',        //数据提交的方式
            url: "/user/upPwd",//数据提交的路径
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data == '0') {
                    alert("验证码错误！");
                } else if (data == '1') {
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

//得到全部的订单
function getAllOrder(i) {
    var formData = new FormData();
    formData.append("page", i);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/getAllOrder",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            newData = eval("(" + data + ")");
            var html1 = '';
            var  listpage = "<div class=\"bg-white m-4 align-self-end\" style=\"font-size:16px;\">"+
                "<nav>" +
                "<ul id=\"allorderpagenum\" class=\"pagination\">"+
                "<li class=\"page-item\">"+
                "<a class=\"page-link\" href=\"#\"><</a>"+
                "</li>"
            for (var i in newData) {
                if (i < 5&& i!=newData[i].ordernum) {
                html1 = html1 +
                    "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                    "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                    "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                    "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                    newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                    "   </div>\n" +
                    "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                    "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                    "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                    "    <div class=\"pl-3 pr-5 ml-auto\">" + newData[i].state + "</div>\n" +
                    "    \n" +
                    "    </div>\n" +
                    "    <div>\n" +
                    "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>";
            }else{
                var num = newData[i].num;
                for (var j =0;j< num;j++){
                    listpage = listpage+
                       "<li class=\"page-item\">\n" +
                        "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getAllOrder("+(j+1)+")\" href=\"#quanbu2\">"+(j+1)+"</a>\n" +
                        "\t\t\t\t\t</li>"
                }
            }
            }
            html1 =html1+listpage +"<li class=\"page-item\">\n" +
                "\t\t\t\t\t\t<a class=\"page-link\" href=\"#\">></a>\n" +
                "\t\t\t\t\t</li>"+
                "</ul>\n" +
                "\t\t\t</nav>\n" +
                "\t\t\t</div>";
                $('#listallorder').html(html1);
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert("得到全部信息失败失败！");
        }
    });
}
//得到已下单或者已预订订单
function getBookingOrder(i) {
        var formData = new FormData();
        var  numberyang = i;
        formData.append("page", i);
        $.ajax({
            type: 'post',        //数据提交的方式
            url: "/order/getBookingOrder",//数据提交的路径
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            scriptCharset: 'utf-8',
            success: function (data) {
                newData = eval("(" + data + ")");
                var html1 = '';
                var  listpage = "<div class=\"bg-white m-4 align-self-end\" style=\"font-size:16px;\">"+
                    "<nav>" +
                    "<ul id=\"allorderpagenum\" class=\"pagination\">"+
                    "<li class=\"page-item\">"+
                    "<a class=\"page-link\" href=\"#\"><</a>"+
                    "</li>"
                for (var i in newData) {
                    if (i < 5&& i!=newData[i].ordernum) {
                        html1 = html1 +
                            "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                            "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                            "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                            "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                            newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                            "   </div>\n" +
                            "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                            "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                            "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                            "    <div class=\"pl-3 pr-5 ml-auto\">" + newData[i].state + "</div>\n" +
                            " <div class=\"pl-3 pr-5 ml-auto\"><button class=\"btn btn-default rounded pl-2 pr-2 pt-1 pb-1\"onclick=\"cancelOrder("+newData[i].o_id+")\" style=\"background-color:#FFF; color:#39F; border:#39F 1px solid; font-size:12px;\">取消</button><button class=\"btn btn-default rounded pl-2 pr-2 pt-1 pb-1\"onclick=\"querenOrder(" + newData[i].o_id + ")\" style=\"background-color:#FFF; color:#39F; border:#39F 1px solid; font-size:12px;\">确认</button></div>" +
                            "    \n" +
                            "    </div>\n" +
                            "    <div>\n" +
                            "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</div>";
                    }else{
                        var num = newData[i].num;
                        for (var j =0;j< num;j++){
                            listpage = listpage+
                                "<li class=\"page-item\">\n" +
                                "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getBookingOrder("+(j+1)+")\" href=\"#xiadan\">"+(j+1)+"</a>\n" +
                                "\t\t\t\t\t</li>"
                        }
                    }
                }
                html1 =html1+listpage +"<li class=\"page-item\">\n" +
                    "\t\t\t\t\t\t<a class=\"page-link\" href=\"#\">></a>\n" +
                    "\t\t\t\t\t</li>"+
                    "</ul>\n" +
                    "\t\t\t</nav>\n" +
                    "\t\t\t</div>";
                $('#listbookingorder').html(html1);
            },
            error: function (XMLHttpRequest, textStatus, error) {
                alert("得到已下单信息失败！");
            }
        });
    }
    //得到待评价订单
function getEndOrder(i) {
            var formData = new FormData();
            formData.append("page", i);
            $.ajax({
                type: 'post',        //数据提交的方式
                url: "/order/getEndOrder",//数据提交的路径
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                scriptCharset: 'utf-8',
                success: function (data) {
                    newData = eval("(" + data + ")");
                    var html1 = '';
                    var  listpage = "<div class=\"bg-white m-4 align-self-end\" style=\"font-size:16px;\">"+
                        "<nav>" +
                        "<ul id=\"allorderpagenum\" class=\"pagination\">"+
                        "<li class=\"page-item\">"+
                        "<a class=\"page-link\" href=\"#\"><</a>"+
                        "</li>"
                    for (var i in newData) {
                        if (i < 5&& i!=newData[i].ordernum) {
                            html1 = html1 +
                                "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                                "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                                "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                                "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                                newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                                "   </div>\n" +
                                "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                                "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                                "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                                "    <div class=\"pl-3 pr-5 ml-auto\">待评价</div>\n" +
                                "    \n" +
                                "    </div>\n" +
                                "    <div>\n" +
                                "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                                "    </div>\n" +
                                "  </div>\n" +
                                "</div>";
                        }else{
                            var num = newData[i].num;
                            for (var j =0;j< num;j++){
                                listpage = listpage+
                                    "<li class=\"page-item\">\n" +
                                    "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getEndOrder("+(j+1)+")\" href=\"#pingjia\">"+(j+1)+"</a>\n" +
                                    "\t\t\t\t\t</li>"
                            }
                        }
                    }
                    html1 =html1+listpage +"<li class=\"page-item\">\n" +
                        "\t\t\t\t\t\t<a class=\"page-link\" href=\"#\">></a>\n" +
                        "\t\t\t\t\t</li>"+
                        "</ul>\n" +
                        "\t\t\t</nav>\n" +
                        "\t\t\t</div>";
                    $('#listendorder').html(html1);
                },
                error: function (XMLHttpRequest, textStatus, error) {
                    alert("得到待评价信息失败！");
                }
            })
        }
        //得到售后订单与以完成订单
function getAfterOrder(i) {
    var formData = new FormData();
    formData.append("page", i);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/getAfterOrder",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            newData = eval("(" + data + ")");
            var html1 = '';
            var  listpage = "<div class=\"bg-white m-4 align-self-end\" style=\"font-size:16px;\">"+
                "<nav>" +
                "<ul id=\"allorderpagenum\" class=\"pagination\">"+
                "<li class=\"page-item\">"+
                "<a class=\"page-link\" href=\"#\"><</a>"+
                "</li>"
            for (var i in newData) {
                if (i < 5&& i!=newData[i].ordernum) {
                    html1 = html1 +
                        "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                        "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                        "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                        "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                        newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                        "   </div>\n" +
                        "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                        "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                        "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                        "    <div class=\"pl-3 pr-5 ml-auto\">" + newData[i].state + "</div>\n" +
                        "    \n" +
                        "    </div>\n" +
                        "    <div>\n" +
                        "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>";
                }else{
                    var num = newData[i].num;
                    for (var j =0;j< num;j++){
                        listpage = listpage+
                            "<li class=\"page-item\">\n" +
                            "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getAfterOrder("+(j+1)+")\" href=\"#shouhou\">"+(j+1)+"</a>\n" +
                            "\t\t\t\t\t</li>"
                    }
                }
            }
            html1 =html1+listpage +"<li class=\"page-item\">\n" +
                "\t\t\t\t\t\t<a class=\"page-link\" href=\"#\">></a>\n" +
                "\t\t\t\t\t</li>"+
                "</ul>\n" +
                "\t\t\t</nav>\n" +
                "\t\t\t</div>";
            $('#listafterorder').html(html1);
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert("得到售后信息失败！");
        }
    })
}
function cancelOrder(id) {
    var formData = new FormData();
    formData.append("cancelid", id);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/cancelOrderById",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function () {
            alert("取消成功！！！");
            getBookingOrder(1);
        },
        error: function () {
            alert("取消失败!!!");
        }
    })
}
function getAllHOrder(i) {
    var formData = new FormData();
    formData.append("page", i);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/getHOrder",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            newData = eval("(" + data + ")");
            var html1 = '';
            var  listpage = "<div class=\"bg-white m-4 align-self-end\" style=\"font-size:16px;\">"+
                "<nav>" +
                "<ul id=\"allorderpagenum\" class=\"pagination\">"+
                "<li class=\"page-item\">"+
                "<a class=\"page-link\" href=\"#\"><</a>"+
                "</li>"
            for (var i in newData) {
                if (i < 5&& i!=newData[i].ordernum) {
                    html1 = html1 +
                        "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                        "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                        "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                        "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                        newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                        "   </div>\n" +
                        "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                        "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                        "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                        "    <div class=\"pl-3 pr-5 ml-auto\">" + newData[i].state + "</div>\n" +
                        "    \n" +
                        "    </div>\n" +
                        "    <div>\n" +
                        "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>";
                }else{
                    var num = newData[i].num;
                    for (var j =0;j< num;j++){
                        listpage = listpage+
                            "<li class=\"page-item\">\n" +
                            "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getAllHOrder("+(j+1)+")\" href=\"#quanbu\">"+(j+1)+"</a>\n" +
                            "\t\t\t\t\t</li>"
                    }
                }
            }
            html1 =html1+listpage +"<li class=\"page-item\">\n" +
                "\t\t\t\t\t\t<a class=\"page-link\" href=\"#\">></a>\n" +
                "\t\t\t\t\t</li>"+
                "</ul>\n" +
                "\t\t\t</nav>\n" +
                "\t\t\t</div>";
            $('#listallhorder').html(html1);
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert("得到全部数据失败！");
        }
    });
}
function getOneTypeHOrder(i,state,id) {               //得到一种状态订单的值
    var formData = new FormData();
    formData.append("page", i);
    formData.append("state", state);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/getOneTypeOrder",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            newData = eval("(" + data + ")");
            var html1 = '';
            var  listpage = "<div class=\"bg-white m-4 align-self-end\" style=\"font-size:16px;\">"+
                "<nav>" +
                "<ul id=\"allorderpagenum\" class=\"pagination\">"+
                "<li class=\"page-item\">"+
                "<a class=\"page-link\" href=\"#\"><</a>"+
                "</li>"
            for (var i in newData) {
                if (i < 5&& i!=newData[i].ordernum) {
                    if(state=="已下单") {
                        html1 = html1 +
                            "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                            "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                            "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                            "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                            newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                            "   </div>\n" +
                            "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                            "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                            "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                            "    <div class=\"pl-3 pr-5 ml-auto\">" + "<button class=\"btn btn-default rounded pl-2 pr-2 pt-1 pb-1\"onclick=\"refuseOrder(" + newData[i].o_id + ")\" style=\"background-color:#FFF; color:#39F; border:#39F 1px solid; font-size:12px;\">拒绝</button><button class=\"btn btn-default rounded pl-2 pr-2 pt-1 pb-1\"onclick=\"agreeOrder(" + newData[i].o_id + ")\" style=\"background-color:#FFF; color:#39F; border:#39F 1px solid; font-size:12px;\">确认</button></div>" +
                            "    \n" +
                            "    </div>\n" +
                            "    <div>\n" +
                            "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</div>";
                    }else{
                        html1 = html1 +
                            "<div    class='media bg-white mt-3 mb-3 p-3 border rounded'>" +
                            "<img src=\"../img/room3.jpg\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                            "  <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\" >\n" +
                            "   <div class=\"mt-2\" style=\"font-size:12px;\">\n" +
                            newData[i].o_Stime + " 至 " + newData[i].o_Etime + "\n" +
                            "   </div>\n" +
                            "   <div class=\"d-flex flex-row mt-2\" style=\"font-size:14px; color:#666;\">\n" +
                            "   <div class=\"pl-0 pr-3 pt-2 bg-white\">单间</div>\n" +
                            "    <div class=\"pl-3 pr-3 ml-auto\">房价：￥" + newData[i].o_price + "</div>\n" +
                            "    <div class=\"pl-3 pr-5 ml-auto\">" + state + "</div>\n" +
                            "    \n" +
                            "    </div>\n" +
                            "    <div>\n" +
                            "    <h5><a href='/order/transit?id="+newData[i].o_id+"'>" + newData[i].hotelname + "</a></h5>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</div>";
                    }
                }else{
                    var num = newData[i].num;
                    for (var j =0;j< num;j++){
                        listpage = listpage+
                            "<li class=\"page-item\">\n" +
                            "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getOneTypeHOrder("+(j+1)+",'"+state+"','"+id+"')\" href=\"#pingjia\">"+(j+1)+"</a>\n" +
                            "\t\t\t\t\t</li>"
                    }
                }
            }
            html1 =html1+listpage +"<li class=\"page-item\">\n" +
                "\t\t\t\t\t\t<a class=\"page-link\" href=\"#\">></a>\n" +
                "\t\t\t\t\t</li>"+
                "</ul>\n" +
                "\t\t\t</nav>\n" +
                "\t\t\t</div>";
            $('#'+id+'').html(html1);                   //列出待入住列表
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert("得到数据失败！"+state);
        }
    })
}
function refuseOrder(id) {
    var formData = new FormData();
    formData.append("refuseOrderid", id);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/refuseOrderById",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function () {
            alert("拒绝成功！！！");
            getOneTypeHOrder(1,"已下单","horder1");
        },
        error: function () {
            alert("拒绝失败!!!");
        }
    })
}
function agreeOrder(id) {
    var formData = new FormData();
    formData.append("agreeOrderid", id);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/agreeOrderById",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function () {
            alert("同意成功！！！");
            getOneTypeHOrder(1,"已下单","horder1");
        },
        error: function () {
            alert("同意失败!!!");
        }
    })
}
function querenOrder(id) {
    var formData = new FormData();
    formData.append("querenorderid", id);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/querenOrderById",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function () {
            alert("确认成功！！！");
            getBookingOrder(1);
        },
        error: function () {
            alert("确认失败!!!");
        }
    })
}