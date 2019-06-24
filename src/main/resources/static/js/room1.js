var formData = new FormData();
formData.append("qq", "qq");
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/order/getOneOrderInfo",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            document.getElementById("price").innerHTML = data.o_price;
            document.getElementById("o_id").innerHTML = data.o_id;
            document.getElementById("o_time").innerHTML = data.o_time;
            document.getElementById("h_name").innerHTML = data.roomname;
            document.getElementById("add").innerHTML = data.add1;
            document.getElementById("people").innerHTML = data.o_name;
            document.getElementById("tel1").innerHTML = data.o_phone;
            document.getElementById("stime").innerHTML = data.o_Stime;

        },
        error: function () {
            alert("查看失败!!!");
        }
    })

