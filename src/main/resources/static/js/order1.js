/*$.get("/room/getRoomById",
    function (data) {
    alert(data.toString());
    var roominfo = JSON.stringify(data);
    roominfo = eval("("+roominfo+")");
        alert("OK"+"$"+roominfo.R_price);
    });*/
function setOrderUser() {
    var name = document.getElementById("order_name").value;
    var phone = document.getElementById("order_phone").value;
    $.ajax({
        type:"post",
        url:"/order/addOrder",
        data:{name:name,phone:phone},
        success:function () {
            alert("订单添加成功！！！");
            window.location.href="../html/personal.html";
        },
        error:function(){
           alert("订单添加错误！！！")
        }
    })
};
function setOrderRoom() {
    // var daterangeC = document.getElementById("daterangeC").value;
    //alert($('#daterangeC').value);
    var price = document.getElementById("price").innerHTML;
    var datec = document.getElementById("daterangeC").innerHTML;
    $.ajax({
        type:"post",
        url:"/order/order_getinfo",
        data:{price:price,datec:datec},
        success:function (data) {
            window.location.href="../html/P_order.html";
        },
        error:function(){
           alert("数据传输错误！！！");
        }
    })
};
