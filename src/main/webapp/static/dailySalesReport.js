function getDaySalesReportUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/daySales-report";
}

function getDaySalesList(){
    var url = getDaySalesReportUrl();
    // call api
    console.log(url)
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            // display data
            console.log(data);
            // displayDaySalesList(data);
        },
        error: handleAjaxError
    });
}

$(document).ready(getDaySalesList);
