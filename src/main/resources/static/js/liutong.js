var formData = new FormData();
formData.append("inputPhone","liutong");
$.ajax({
    type: 'post',        //数据提交的方式
    url: "/user/getInFo",//数据提交的路径
    data: formData,
    cache: false,
    processData: false,
    contentType: false,
    success: function (data) {
        if (data != "") {
            $('#user').attr('style', 'display:inline;')
            $('#user_login').html("<img src=\"../img/user/" + data.user_id + "/" + data.user_picture + "\"height=\"35px\"  width=\"35px\" style=\"border-radius:20px;\"/>" + data.user_name);
        }
        document.getElementById("user_name").innerHTML = data.user_name;
        document.getElementById("user_phone").innerHTML = data.user_id;
        document.getElementById("user_name1").innerHTML = data.user_name;
        document.getElementById("user_name_2").innerHTML = data.user_name;
        document.getElementById("user_IDcard").innerHTML = data.user_IDcard;
        if(user_sex=='0') {
            document.getElementById("user_sex").innerHTML = "女";
        }else{
            document.getElementById("user_sex").innerHTML = "男";
        }
        document.getElementById("user_address").innerHTML = data.user_address;
        $('#user_namepicture').html("<img src=\"../img/user/"+data.user_id+"/"+data.user_picture+"\"height=\"35px\"  width=\"35px\" style=\"border-radius:20px;\"/>"+data.user_name);
        $('#user_picture').html("<img src=\"../img/user/"+data.user_id+"/"+data.user_picture+"\" height=\"50px\"  width=\"50px\" class=\"rounded-circle\" id=\"img\"/>");
        $('#user_picture_2').html("<img src=\"../img/user/"+data.user_id+"/"+data.user_picture+"\" class=\"align-self-start m-3 rounded-circle\" style=\"width:60px\"/>");
        $('#myModal').modal('hide');
    },
    // }
    error: function (XMLHttpRequest, textStatus, error) {
        alert("错误！");

    }
})