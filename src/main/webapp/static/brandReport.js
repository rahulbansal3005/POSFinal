function getBrandUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand-report";
}

// const getBrandCategoryUrl = (brand="", category="") => {
//     var baseUrl = $("meta[name=baseUrl]").attr("content")
//     return baseUrl + "/api/brand?brand="+brand+"&category="+category;
// }

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

// const fillOptions = () => {
//     var url = getBrandCategoryUrl();
//
//     $.ajax({
//         url: url,
//         type: 'GET',
//         success: function(response) {
//             // console.log("from brandCategoryURL",response);
//             populateBrand(response);
//             populateCategory(response);
//         },
//         error: handleAjaxError
//     });
// }
// const populateBrand = data => {
//     let $selectBrand = $("#inputBrand");
//
//     let brands = new Set();
//     for(var i in data){
//         var e = data[i];
//         brands.add(e.brand);
//     }
//
//     for(let brand of brands.values()) {
//         let ele = '<option value="'+brand+'">' + brand + '</option>';
//         $selectBrand.append(ele);
//     }
// }
//
// const populateCategory = data => {
//     let $selectCategory = $("#inputCategory");
//
//     let categories = new Set();
//     for(let i in data){
//         let e = data[i];
//         categories.add(e.category);
//     }
//
//     for(let category of categories.values()) {
//         let ele = '<option value="'+category+'">' + category + '</option>';
//         $selectCategory.append(ele);
//     }
// }


function init(){
    $('#filter-report').click(filterReport);
    $('thead').hide();
    // fillOptions();
    $('#inputBrand').change(brandChanged);
    $('#inputCategory').change(categoryChanged);
}
$(document).ready(init);
$(document).ready(fillOptions);
