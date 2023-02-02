function getBrandUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand-report";
}

const getBrandCategoryUrl = (brand="", category="") => {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand?brand="+brand+"&category="+category;
}

function displayBrandList(data){
    let index=1;
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
    for(const i in data){
        const e = data[i];
        // console.log("e from brand report",e);
        const row = '<tr>'
            + '<td>' + index++ + '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    $('thead').show();
}

function filterReport() {
    let $form = $("#brand-form");
    let json = toJson($form);
    let url = getBrandUrl();
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(response) {
            displayBrandList(response);
        },
        error: handleAjaxError
    });
}
const fillOptions = () => {
    var url = getBrandCategoryUrl();

    $.ajax({
        url: url,
        type: 'GET',
        success: function(response) {
            // console.log("from brandCategoryURL",response);
            populateBrand(response);
            populateCategory(response);
        },
        error: handleAjaxError
    });
}
const populateBrand = data => {
    let $selectBrand = $("#inputBrand");

    let brands = new Set();
    for(var i in data){
        var e = data[i];
        brands.add(e.brand);
    }

    for(let brand of brands.values()) {
        let ele = '<option value="'+brand+'">' + brand + '</option>';
        $selectBrand.append(ele);
    }
}

const populateCategory = data => {
    let $selectCategory = $("#inputCategory");

    let categories = new Set();
    for(let i in data){
        let e = data[i];
        categories.add(e.category);
    }

    for(let category of categories.values()) {
        let ele = '<option value="'+category+'">' + category + '</option>';
        $selectCategory.append(ele);
    }
}


function init(){
    $('#filter-report').click(filterReport);
    $('thead').hide();
    fillOptions();
}

$(document).ready(init);