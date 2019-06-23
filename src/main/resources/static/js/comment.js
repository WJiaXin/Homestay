function pf(id){
    $('#'+id+' i').bind('mouseenter',function(){

        var index=$('#'+id+' i').index(this);
        $('#'+id+' i:lt('+index+1+')').removeClass('icon-xingxing_empty');
        $('#'+id+' i:lt('+index+1+')').addClass('icon-xingxing');
        $('#'+id+' i:gt('+index+')').removeClass('icon-xingxing');
        $('#'+id+' i:gt('+index+')').addClass('icon-xingxing_empty');
    });
}
$("#shangchuan").click(function () {
    $("#file").click();
})
function shangchuan(){
    var formData = new FormData();
    formData.append("file",$('#file')[0].files[0]);
    $.ajax({
        type: 'post',
        url:"/img/pimg",
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            $("#img").prepend("<div class=\"ml-2 mr-2\" style=\"width:12em; height:12em;\"><img src=\"../img/space/"+data+"\" class=\"img-thumbnail\" style=\"max-height:100%; width:100%;\"></div>");
        },
        error: function (XMLHttpRequest, textStatus, error) {
            alert(error);
        }
    });
}
