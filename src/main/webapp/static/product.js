function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/product";
}

function resetForm() {
    var element = document.getElementById("product-form");
    element.reset()
}

// function resetuploadform(){
//   var element = document.getElementById("product-edit-form");
//   element.reset()
// }

function toggleModal() {
    $('#add-product-item-modal').modal('toggle');
}

//BUTTON ACTIONS
function addProduct(event) {
    //Set the values to update
    var $form = $("#product-form");
    var json = toJson($form);
    var parsed = JSON.parse(json);
    console.log(parsed);
    if (parsed.barcode === "" || parsed.name === "" || parsed.mrp === "" || parsed.brand === "" || parsed.category === "")
        return frontendChecks("Fields are empty");
    if (json.mrp < 0)
        return frontendChecks("MRP can not be negative")
    var url = getProductUrl();

    $.ajax({
        url: url,
        type: "POST",
        data: json,
        headers: {
            "Content-Type": "application/json",
        },
        success: function (response) {
            $('#add-product-item-modal').modal('toggle');
            resetForm();
            SuccessMessage("Successfully added");
            getProductList();

        },
        error: handleAjaxError,
    });

    return false;
}

function updateProduct(event) {
    $("#edit-product-modal").modal("toggle");
    //Get the ID
    console.log("hello");
    var id = $("#product-edit-form input[name=id]").val();
    console.log(id);
    var url = getProductUrl() + "/" + id;

    //Set the values to update
    var $form = $("#product-edit-form");
    var json = toJson($form);
    console.log(json)

    var parsed = JSON.parse(json);
    console.log(parsed);
    if (parsed.name === "" || parsed.mrp === "")
        return frontendChecks("Fields are empty");
    if (json.mrp <= 0)
        return frontendChecks("MRP can not be negative or zero")

    $.ajax({
        url: url,
        type: "PUT",
        data: json,
        headers: {
            "Content-Type": "application/json",
        },
        success: function (response) {
            SuccessMessage("Successfully Updated");
            getProductList();
        },
        error: handleAjaxError,
    });

    return false;
}

function getProductList() {
    var url = getProductUrl();
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            displayProductList(data);
        },
        error: handleAjaxError,
    });
}

function deleteProduct(id) {
    var url = getProductUrl() + "/" + id;

    $.ajax({
        url: url,
        type: "DELETE",
        success: function (data) {
            getProductList();
        },
        error: handleAjaxError,
    });
}


//UI DISPLAY METHODS

function displayProductList(data) {
    var $tbody = $("#product-table").find("tbody");
    $tbody.empty();
    var index = 1;
    for (var i in data) {
        var e = data[i];
        // console.log(e);
        var mrp = e.mrp;
        var buttonHtml =
            // '<button type="button" class="btn btn-secondary" onclick="deleteProduct(' + e.id + ')">delete</button>';
            // buttonHtml +=
            ' <button type="button" class="btn btn-secondary" onclick="displayEditProduct(' + e.id + ')">edit</button>';
        var row =
            "<tr>" +
            "<td>" +
            index++ +
            "</td>" +
            "<td>" +
            e.barcode +
            "</td>" +
            "<td>" +
            e.brand +
            "</td>" +
            "<td>" +
            e.category +
            "</td>" +
            "<td>" +
            e.name +
            "</td>" +
            "<td>" +
            mrp.toFixed(2) +
            "</td>" +
            "<td>" +
            buttonHtml +
            "</td>" +
            "</tr>";
        $tbody.append(row);
    }
}

function displayEditProduct(id) {
    var url = getProductUrl() + "/" + id;
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            displayProduct(data);
        },
        error: handleAjaxError,
    });
}

function displayProduct(data) {
    // console.log(data);
    $("#product-edit-form input[name=name]").val(data.name);
    $("#product-edit-form input[name=brand]").val(data.brand);
    $("#product-edit-form input[name=barcode]").val(data.barcode);
    $("#product-edit-form input[name=mrp]").val(data.mrp);
    $("#product-edit-form input[name=category]").val(data.category);
    $("#product-edit-form input[name=id]").val(data.id);
    $("#edit-product-modal").modal("toggle");
}


//Fill dropdown Options
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
            // console.log(response);
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
        // console.log(e);
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


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
    var file = $("#productFile")[0].files[0];
    readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
    fileData = results.data;
    uploadRows();
}

function uploadRows() {
    //Update progress
    updateUploadDialog();
    //If everything processed then return
    // if (processCount == fileData.length) {
    //   return;
    // }
    //
    // //Process next row
    // var row = fileData[processCount];
    // processCount++;

    var json = JSON.stringify(fileData);
    var url = getProductUrl() + "-bulk";

    //Make ajax call
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        headers: {
            "Content-Type": "application/json",
        },
        // success: function (response) {
        //   uploadRows();
        // },
        // error: function (response) {
        //   row.error = response.responseText;
        //   errorData.push(row);
        //   uploadRows();
        // },
        success: function (response) {
            console.log(response);
            getProductList();
        },
        error: function (response) {
            // console.log(response);
            // for(var i in response)
            // {
            //     errorData.push(i);
            // }
            if (response.status === 403) {
                // toastr.error("403 Forbidden");
                frontendChecks("403")
            } else {
                var resp = JSON.parse(response.responseText);
                var jsonObj = JSON.parse(resp.message);
                console.log(jsonObj);
                errorData = jsonObj;
                console.log(response);
                toastr.error("Error in uploading TSV file, Download Error File");
                $("#download-errors").prop('disabled', false);
                // resetuploadform();
            }
        }
    });
}

function downloadErrors() {
    writeFileData(errorData);
}

function resetUploadDialog() {
    //Reset file name
    var $file = $("#productFile");
    $file.val("");
    $("#productFileName").html("Choose File");
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts
    updateUploadDialog();
}

function updateUploadDialog() {
    $("#rowCount").html("" + fileData.length);
    $("#processCount").html("" + processCount);
    $("#errorCount").html("" + errorData.length);
}

function updateFileName() {
    var $file = $("#productFile");
    var fileName = $file.val();
    $("#productFileName").html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $("#upload-product-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
    $("#add-product-modal").click(toggleModal);
    $("#add-product").click(addProduct);
    $("#update-product").click(updateProduct);
    $("#refresh-data").click(getProductList);
    $("#upload-data").click(displayUploadData);
    $("#process-data").click(processData);
    $("#download-errors").click(downloadErrors);
    $("#productFile").on("change", updateFileName);
    fillOptions();

}

$(document).ready(init);
$(document).ready(getProductList);
