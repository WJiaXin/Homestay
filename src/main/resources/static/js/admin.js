var formData = new FormData();
formData.append("page", 1);
$.ajax({
    type: 'post',        //数据提交的方式
    url: "/room/getAllHotelApply",//数据提交的路径
    data: formData,
    cache: false,
    processData: false,
    contentType: false,
    scriptCharset: 'utf-8',
    success: function (data) {
        newData = eval("(" + data + ")");
        var html1 = '';
        for (var i in newData) {
            if (i < 5 && i != newData[1].getHotelNum) {
                html1 = html1 +
                    "\n" +
                    "                               <div class=\"media bg-white mt-3 mb-3 p-3 border-bottom\">\n" +
                    "                                   <img src=\"../img/\"+newData[i].picture+\"\\\" alt=\"John Doe\" class=\"mr-3 rounded\" style=\"width:210px; height:140px;\">\n" +
                    "                                   <div class=\"media-body d-flex flex-column\" >\n" +
                    "                                       <div class=\"d-flex align-items-center\">\n" +
                    "                                           <div><h5 style=\"color:rgb(19, 209, 190)\">" + newData[i].name + "</h5></div>\n" +
                    "                                           <div class=\"ml-5\" style=\"color:#F90; \">￥<span style=\"font-size:28px;\">" + newData[i].price + "</span>起</div>\n" +
                    "                                       </div>\n" +
                    "                                       <div class=\"d-flex flex-row mt-4\" style=\"font-size:13px;\">\n" +
                    "                                           <div class=\"d-flex flex-column flex-fill\">\n" +
                    "                                               <div class=\"p-1\">" + newData[i].hoteladdress + "</div>\n" +
                    "                                               <div class=\"pt-3\" style=\"font-size:13px;\">" + newData[i].hotelrule + "</div>\n" +
                    "                                           </div>\n" +
                    "                                           <div class=\"pl-2 pr-2 ml-auto\" style=\"font-size:13px;\"> <button class=\"btn btn-default rounded border-orange border\" style=\"background-color:#FFF; color: #F90;\"><a href='/room/getApplyHotelId?id="+newData[i].hotelid+"'> 查看详情</a></button></div>\n" +
                    "                                         </div>\n" +
                    "                                   </div>\n" +
                    "                               </div>"
            }
        }
        html1 =html1+"</div>"
        $('#homestay').html(html1);
    },
    error: function (XMLHttpRequest, textStatus, error) {
        alert("得到全部信息失败失败！");
    }
});
function getroomapplt(i) {
    var formData = new FormData();
    formData.append("page", i);
    $.ajax({
        type: 'post',        //数据提交的方式
        url: "/room/getApplyRoom",//数据提交的路径
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        scriptCharset: 'utf-8',
        success: function (data) {
            newData = eval("(" + data + ")");
            var html1 = '';
            var listpage =" <div class=\"bg-white m-4\" style=\"font-size:16px;\">\n" +
                "                               <nav>\n" +
                "                                   <ul class=\"pagination\">\n" +
                "                                       <li class=\"page-item\">\n" +
                "                                           <a class=\"page-link\" href=\"#\"><</a>\n" +
                "                                       </li>";
            for (var i in newData){
                if(i<5&& i!=newData[i].allapplyroomnum){
                    html1 = html1+
                      " <div  class=\"media border-bottom bg-white p-3 m-4\">\n" +
                        "                               <img src=\"../img/"+newData[i].picture+"\" alt=\"John Doe\" class=\"mr-3 mt-3 rounded\" style=\"width:130px; height:90px;\">\n" +
                        "                               <div class=\"media-body bg-white pt-3 d-flex flex-column-reverse\">\n" +
                        "                                   <div class=\"d-flex mt-2\" style=\"font-size:12px;\">\n" +
                        "                                       <div class=\"pl-0 pr-3\"><i class=\"fas fa-concierge-bell pr-1\" style=\"color:#999;\"></i>"+newData[i].cook+"</div><div class=\"pl-2 pr-3\"><i class=\"iconfont icon-jinzhi pr-1\" style=\"color:#999;\"></i>"+newData[i].pet+"</div><div class=\"pl-2 pr-3\"><i class=\"iconfont icon-jinzhi pr-1\" style=\"color:#999;\"></i>"+newData[i].meeting+"</div>\n" +
                        "                                       <div class=\"mr-3 ml-auto\"> <button class=\"btn btn-default rounded border-orange border\"  style=\"background-color:#FFF; color: #F90;\"><a href='/room/getApplyRoomId?id="+newData[i].roomid+"'> 查看详情</a></button></div>\n" +
                        "                                   </div>\n" +
                        "                                   <div class=\"d-flex flex-row mt-3\" style=\"font-size:12px;\">\n" +
                        "                                       <div class=\"pl-0 pr-3  bg-white border-right\"><span class=\"pr-1\" style=\"color:#999;\">类型:</span> "+newData[i].roomtype+"</div>\n" +
                        "                                       <div class=\"pl-3 pr-3  border-right\"><span class=\"pr-1\" style=\"color:#999;\">卫浴:</span> "+newData[i].roomweiyu+"</div>\n" +
                        "                                       <div class=\"pl-3 pr-3  border-right\"><span class=\"pr-1\" style=\"color:#999;\">户型:</span> "+newData[i].huxing+"</div>\n" +
                        "                                       <div class=\"pl-3 pr-3 \"><span class=\"pr-1\" style=\"color:#CCC;\">面积:</span> "+newData[i].roommianji+"</div>\n" +
                        "\n" +
                        "                                   </div>\n" +
                        "                                   <div class=\"d-flex\">\n" +
                        "                                       <div><h5>"+newData[i].roomname+"</h5></div>\n" +
                        "                                       <div class=\"ml-5\" style=\"color:#F60; font-size:24px;\"><span style=\" font-size:8px;\">￥</span>"+newData[i].roomprice+"</div>\n" +
                        "                                   </div>\n" +
                        "                               </div></div>"
                }else{
                    var num = newData[i].num;
                    for (var j =0;j< num;j++){
                        listpage = listpage+
                            "<li class=\"page-item\">\n" +
                            "\t\t\t\t\t\t<a class=\"page-link\" onclick=\"getroomapplt("+(j+1)+")\" href=\"#room\">"+(j+1)+"</a>\n" +
                            "\t\t\t\t\t</li>"
                    }
                }
            }
            html1 =html1+listpage +" <li class=\"page-item\">\n" +
                "                                           <a class=\"page-link\" href=\"#\">></a>\n" +
                "                                       </li>\n" +
                "                                   </ul>\n" +
                "                               </nav>\n" +
                "                           </div>\n" ;
            $('#room').html(html1);
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert("得到全部信息失败失败！");
              }
    });
}