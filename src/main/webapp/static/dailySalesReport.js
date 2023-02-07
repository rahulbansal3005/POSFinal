function getDaySalesReportUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/daySales-report";
}
function scheduler(){
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/schedule";

}

function getDaySalesList(){
    var url = getDaySalesReportUrl();
    // console.log(url)
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            console.log(data);
        },
        error: handleAjaxError
    });
}

function scheduleNow(){
    var url=scheduler();
    console.log(url);
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            console.log(data);
        },
        error: handleAjaxError
    });
}

function init(){
    $('#run-scheduler').click(scheduleNow);
    $(document).ready(getDaySalesList);
    $('#refresh-data').click(getDaySalesList);
}
$(document).ready(init);
