function getSalesReportUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/sales-report";
}

function filterSalesReport() {
    var $form = $("#sales-form");
    var json = toJson($form);
    var url = getSalesReportUrl();

    console.log(json);
    var parsed = JSON.parse(json);
    console.log(parsed);
    if (parsed.startDate == "")
        return frontendChecks("Start Date is empty");

    if (parsed.startDate != "" && parsed.endDate == "")
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

// const getBrandUrl = (brand = "", category = "") => {
//     var baseUrl = $("meta[name=baseUrl]").attr("content")
//     return baseUrl + "/api/brand?brand=" + brand + "&category=" + category;
// }
// const fillOptions = () => {
//     var url = getBrandUrl();
//
//     $.ajax({
//         url: url,
//         type: 'GET',
//         success: function (response) {
//             console.log(response);
//             populateBrand(response);
//             populateCategory(response);
//         },
//         error: handleAjaxError
//     });
// }
//
// const populateBrand = data => {
//     let $selectBrand = $("#inputBrand");
//
//     let brands = new Set();
//     for (var i in data) {
//         var e = data[i];
//         console.log(e);
//         brands.add(e.brand);
//     }
//
//     for (let brand of brands.values()) {
//         var ele = '<option value="' + brand + '">' + brand + '</option>';
//         $selectBrand.append(ele);
//     }
// }
//
// const populateCategory = data => {
//     let $selectCategory = $("#inputCategory");
//
//     let categories = new Set();
//     for (var i in data) {
//         var e = data[i];
//         categories.add(e.category);
//     }
//
//     for (let category of categories.values()) {
//         var ele = '<option value="' + category + '">' + category + '</option>';
//         $selectCategory.append(ele);
//     }
// }

//Fill dropdown Options
const getBrandCategoryUrl = (brand = "", category = "") => {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand?brand=" + brand + "&category=" + category;
}

let brandCategoryData = [];
brandSet = new Set();
categorySet = new Set();

const fillOptions = () => {
    let url = getBrandCategoryUrl();

    $.ajax({
        url: url,
        type: 'GET',
        success: function (response) {
            brandCategoryData = response;
            // console.log(response);
            for(let i in response){
                brandSet.add(response[i].brand);
                categorySet.add(response[i].category);
            }
            populateDropDowns(brandSet,categorySet);
        },
        error: handleAjaxError
    });
}

function populateDropDowns(brandSet,categorySet) {
    $('#inputBrand').empty()
    $('#inputCategory').empty()
    $('#inputBrand').append('<option selected="" value="">Select Brand</option>')
    $('#inputCategory').append('<option selected="" value="">Select Category</option>')
    brandSet.forEach(function(brand){
        let brandOptionHTML = '<option value="'+ brand +'">'+ brand+'</option>'
        $('#inputBrand').append(brandOptionHTML)
    })
    categorySet.forEach(function(category){
        let categoryOptionHTML = '<option value="'+ category +'">'+ category+'</option>'
        $('#inputCategory').append(categoryOptionHTML)
    })
}


let flag="";
function brandChanged(event){
    if(flag==="" || flag==="brand"){
        flag="brand";
        let brand = event.target.value;
        if(brand===""){
            populateDropDowns(brandSet,categorySet);
            flag="";
            return;
        }
        $('#inputCategory').empty();
        $('#inputCategory').append('<option selected="" value="">Select Category</option>');
        for(let i in brandCategoryData){
            if(brandCategoryData[i].brand===brand){
                let categoryOptionHTML = '<option value="'+ brandCategoryData[i].category +'">'+ brandCategoryData[i].category+'</option>';
                $('#inputCategory').append(categoryOptionHTML);
            }
        }
    }
}
function categoryChanged(event){
    if(flag==="" || flag==="category"){
        flag= "category";
        let category = event.target.value;
        if(category===""){
            populateDropDowns(brandSet,categorySet);
            flag="";
            return;
        }
        $('#inputBrand').empty();
        $('#inputBrand').append('<option selected="" value="">Select Brand</option>');
        for(let i in brandCategoryData){
            if(brandCategoryData[i].category===category){
                let brandOptionHTML = '<option value="'+ brandCategoryData[i].brand +'">'+ brandCategoryData[i].brand+'</option>';
                $('#inputBrand').append(brandOptionHTML);
            }
        }
    }
}

function readyDates() {
    // Get references to the startDate and endDate inputs
    const startDateInput = document.getElementById("inputStartDate");
    const endDateInput = document.getElementById("inputEndDate");


    //SETTING END DATE TO TODAY'S DATE
    let today = new Date();
    var year = today.getFullYear();
    var month = (today.getMonth() + 1).toString().padStart(2, '0');
    var day = today.getDate().toString().padStart(2, '0');
    document.getElementById("inputEndDate").value = year + "-" + month + "-" + day;


    //SETTING START DATE TO 30 DAYS AGO
    today.setDate(today.getDate() - 30);
    var year = today.getFullYear();
    var month = (today.getMonth() + 1).toString().padStart(2, '0');
    var day = today.getDate().toString().padStart(2, '0');
    var thirtybefore = year + "-" + month + "-" + day;
    document.getElementById("inputStartDate").value = thirtybefore;


    // Get today's date
    // const today = new Date();

    // Set the minimum value for the startDate to be 30 days from today's date
    startDateInput.min = new Date(today.getFullYear(), today.getMonth(), today.getDate()).toISOString().split("T")[0];
    startDateInput.max = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 30).toISOString().split("T")[0];
    // Update the endDate's min and max values whenever the startDate value changes
    startDateInput.addEventListener("input", function () {
        // endDateInput.min = startDateInput.value;
        // endDateInput.max = today.toISOString().split("T")[0];
        // if (startDateInput.value) {
        //     endDateInput.disabled = false;

        endDateInput.min = startDateInput.value;
        console.log(endDateInput.min);
        today=new Date();
        endDateInput.max = today.toISOString().split("T")[0];
        // } else {
        //     endDateInput.disabled = true;
        // }
    });

}

function init() {
    readyDates();
    $('#filter-sales-report').click(filterSalesReport);
    $('thead').hide();
    // fillOptions();
    $('#inputBrand').change(brandChanged);
    $('#inputCategory').change(categoryChanged);
}

$(document).ready(init);
$(document).ready(fillOptions);