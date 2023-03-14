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
            // console.log(data);
            displayScheduler(data);
        },
        error: handleAjaxError
    });
}

function displayScheduler(data)
{
    let index=1;
    var $tbody = $('#daily-table').find('tbody');
    $tbody.empty();
    for(const i in data){
        const e = data[i];
        console.log("e from daily report",e);
        const row = '<tr>'
            + '<td>' + index++ + '</td>'
            + '<td>' + e.date + '</td>'
            + '<td>' + e.invoicedOrderCount + '</td>'
            + '<td>' + e.invoicedItemcount + '</td>'
            + '<td>' + e.totalRevenue + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    $('thead').show();
}

function scheduleNow(){
    var url=scheduler();
    console.log(url);
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            getDaySalesList();
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
