function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/product";
}

function resetForm() {
    var element = document.getElementById("product-form");
    element.reset()
}


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
    // if (json.mrp < 0)
    //     return frontendChecks("MRP can not be negative")
    if (parsed.mrp <= 0)
        return frontendChecks("MRP can not be negative or Zero")

    let str = parsed.mrp;
    let parts = str.split(".");
    let length = parts[0].length;

    if (length > 10)
        return frontendChecks("Invalid MRP");
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

function isInteger(str) {
    // Regular expression to match an integer
    var integerPattern = /^-?\d+$/;
    // Test if the string matches the integer pattern
    return integerPattern.test(str);
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
    if (parsed.mrp <= 0)
        return frontendChecks("MRP can not be negative or Zero")

    let str = parsed.mrp;
    let parts = str.split(".");
    let length = parts[0].length;

    if (length > 10)
        return frontendChecks("Invalid MRP");

    // if (json.mrp <= 0)
    //     return frontendChecks("MRP can not be negative or zero")

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
            ' <button type="button" class="btn btn-secondary" onclick="displayEditProduct(' + e.id + ')">Edit</button>';
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

let brandCategoryData = [];
brandSet = new Set();
categorySet = new Set();

const fillOptions = () => {
    let url = getBrandUrl();

    $.ajax({
        url: url,
        type: 'GET',
        success: function (response) {
            brandCategoryData = response;
            // console.log(response);
            for (let i in response) {
                brandSet.add(response[i].brand);
                categorySet.add(response[i].category);
            }
            populateDropDowns(brandSet, categorySet);
        },
        error: handleAjaxError
    });
}

function populateDropDowns(brandSet, categorySet) {
    $('#inputBrand').empty()
    $('#inputCategory').empty()
    $('#inputBrand').append('<option selected="" value="">Select Brand</option>')
    $('#inputCategory').append('<option selected="" value="">Select Category</option>')
    brandSet.forEach(function (brand) {
        let brandOptionHTML = '<option value="' + brand + '">' + brand + '</option>'
        $('#inputBrand').append(brandOptionHTML)
    })
    categorySet.forEach(function (category) {
        let categoryOptionHTML = '<option value="' + category + '">' + category + '</option>'
        $('#inputCategory').append(categoryOptionHTML)
    })
}


let flag = "";

function brandChanged(event) {
    if (flag === "" || flag === "brand") {
        flag = "brand";
        let brand = event.target.value;
        if (brand === "") {
            populateDropDowns(brandSet, categorySet);
            flag = "";
            return;
        }
        $('#inputCategory').empty();
        $('#inputCategory').append('<option selected="" value="">Select Category</option>');
        for (let i in brandCategoryData) {
            if (brandCategoryData[i].brand === brand) {
                let categoryOptionHTML = '<option value="' + brandCategoryData[i].category + '">' + brandCategoryData[i].category + '</option>';
                $('#inputCategory').append(categoryOptionHTML);
            }
        }
    }
}

function categoryChanged(event) {
    if (flag === "" || flag === "category") {
        flag = "category";
        let category = event.target.value;
        if (category === "") {
            populateDropDowns(brandSet, categorySet);
            flag = "";
            return;
        }
        $('#inputBrand').empty();
        $('#inputBrand').append('<option selected="" value="">Select Brand</option>');
        for (let i in brandCategoryData) {
            if (brandCategoryData[i].category === category) {
                let brandOptionHTML = '<option value="' + brandCategoryData[i].brand + '">' + brandCategoryData[i].brand + '</option>';
                $('#inputBrand').append(brandOptionHTML);
            }
        }
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
    var json = JSON.stringify(fileData);
    var headers = ["barcode", "brand", "category", "name", "mrp"];
    jsonq = JSON.parse(json);
    console.log(jsonq[0]);
    len = Object.keys(jsonq).length;
    console.log(length);
    console.log(Object.keys(jsonq[0]));

    for (let i = 0; i < len; i++) {
        if (Object.keys(jsonq[i]).length !== headers.length) {
            console.log(Object.keys(jsonq[i]).length);
            frontendChecks("Row is not correct " + i);
            return;
        }
        let keys = Object.keys(jsonq[i]);
        console.log(keys);
        for (const key in keys) {
            if (jsonq[i][key] === "") {
                frontendChecks("error in this row ", i);
            }
        }

    }

    if (Object.keys(jsonq[0]).length !== headers.length) {
        frontendChecks("File column number do not match. Please check the file and try again");
        return;
    }
    for (let i in headers) {
        if (!jsonq[0].hasOwnProperty(headers[i])) {
            frontendChecks('File columns do not match. Please check the file and try again');
            return;
        }
    }
    uploadRows();
}

function uploadRows() {
    //Update progress
    updateUploadDialog();
    let flag=false;
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
    console.log(fileData);
    let index = 1;
    // for(let data in fileData)
    // {
    //     for(let prop in fileData[data])
    //     {
    //         if(fileData[prop]==="")
    //             frontendChecks("File data not properly added in row: "+index);
    //         console.log(prop);
    //         // console.log(data[prop]);
    //     }
    //     index++;
    // }

    for (let i = 0; i < fileData.length; i++) {
        if (fileData[i].barcode === "" || fileData[i].brand === "" || fileData[i].category === "" || fileData[i].mrp === "" || fileData[i].name === "") {
            flag=true;
            frontendChecks("File data not properly added in row: " + index);
        }
        index++;
    }

    if(flag)
        return;
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
            SuccessMessage("SuccessFully added");

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

                const arr = [];
                for (let obj in jsonObj) {
                    console.log(obj)
                    console.log(jsonObj[obj]);
                    const temp = {};
                    temp['barcode'] = jsonObj[obj].barcode;
                    temp['brand'] = jsonObj[obj].brand;
                    temp['category'] = jsonObj[obj].category;
                    temp['name'] = jsonObj[obj].name;
                    temp['mrp'] = jsonObj[obj].mrp;
                    temp['message'] = jsonObj[obj].message;

                    arr.push(temp);
                }
                console.log(arr);
                errorData = arr;
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
    $('#inputBrand').change(brandChanged);
    $('#inputCategory').change(categoryChanged);
    // fillOptions();

}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(fillOptions);