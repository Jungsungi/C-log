function nullCheck(value) {
    if (value == null || value == '') {
        return true;
    }
    return false;
}

 $(document).ready(function(){
    $("#bookMenu").on({
        "mouseover" : function () {
            $("#bookNav").css("display", "block");
        },
        "mouseout" : function () {
            $("#bookNav").css("display", "none");
        }
    })
 });
