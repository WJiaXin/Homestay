var formData = new FormData();
formData.append("who", "qiuqiu");
$.ajax({
    type: 'post',        //数据提交的方式
    url: "/room/getRoomInfoById",//数据提交的路径
    data: formData,
    cache: false,
    processData: false,
    contentType: false,
    success: function (data) {
        newData = eval("("+data+")");
        document.getElementById("price").innerHTML = newData.r_price;
        document.getElementById("people").innerHTML = newData.r_numPeople;
    },
    error: function (XMLHttpRequest, textStatus, error) {
        alert("RoomInfo数据传输失败！");
    }
})
