function getSalesReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/sales-report";
}

function filterSalesReport() {
    var $form = $("#sales-form");
    var json = toJson($form);
    var url = getSalesReportUrl();

    console.log(json);
    var parsed=JSON.parse(json);
    console.log(parsed);
    if(parsed.startDate=="")
        return frontendChecks("Start Date is empty");

    if(parsed.startDate!="" && parsed.endDate=="")
        return frontendChecks("End date is empty");

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            console.log(response);
            displaySalesReport(response);
        },
        error: handleAjaxError
    });
}

function displaySalesReport(data) {
    let index = 1;
    var $tbody = $('#sales-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var b = data[i];
        console.log("b", b);
        var row = '<tr>'
            + '<td>' + index++ + '</td>'
            + '<td>' + b.brand + '</td>'
            + '<td>' + b.category + '</td>'
            + '<td>' + b.quantity + '</td>'
            + '<td class="text-right">' + parseFloat(b.revenue).toFixed(2) + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    $('thead').show();
}
const getBrandUrl = (brand = "", category = "") => {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand?brand=" + brand + "&category=" + category;
}
const fillOptions = () => {
    var url = getBrandUrl();

    $.ajax({
        url: url,
        type: 'GET',
        success: function (response) {
            console.log(response);
            populateBrand(response);
            populateCategory(response);
        },
        error: handleAjaxError
    });
}

const populateBrand = data => {
    let $selectBrand = $("#inputBrand");

    let brands = new Set();
    for (var i in data) {
        var e = data[i];
        console.log(e);
        brands.add(e.brand);
    }

    for (let brand of brands.values()) {
        var ele = '<option value="' + brand + '">' + brand + '</option>';
        $selectBrand.append(ele);
    }
}

const populateCategory = data => {
    let $selectCategory = $("#inputCategory");

    let categories = new Set();
    for (var i in data) {
        var e = data[i];
        categories.add(e.category);
    }

    for (let category of categories.values()) {
        var ele = '<option value="' + category + '">' + category + '</option>';
        $selectCategory.append(ele);
    }
}
function init() {
    $('#filter-sales-report').click(filterSalesReport);
    $('thead').hide();
    fillOptions();
}

$(document).ready(init);