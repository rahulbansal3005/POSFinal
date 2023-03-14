function getBrandUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/brand";
}

function resetForm() {
    var element = document.getElementById("brand-form");
    element.reset()
}


function toggleModal(){
    $('#add-brand-item-modal').modal('toggle');

}

//BUTTON ACTIONS
function addBrand(event) {
    //Set the values to update
    var $form = $("#brand-form");
    var json = toJson($form);
    var url = getBrandUrl();

    var parsed=JSON.parse(json);
    console.log(parsed);
    if(parsed.brand=="" || parsed.category=="")
        return frontendChecks("Fields are empty");

    $.ajax({
        url: url,
        type: "POST",
        data: json,
        headers: {
            "Content-Type": "application/json",
        },
        success: function (response) {
            $('#add-brand-item-modal').modal('toggle');
            resetForm();
            SuccessMessage("Successfully Added");
            getBrandList();

        },
        error: handleAjaxError,
    });

    return false;
}

function updateBrand(event) {
    $("#edit-brand-modal").modal("toggle");
    //Get the ID
    var id = $("#brand-edit-form input[name=id]").val();
    var url = getBrandUrl() + "/" + id;
    // console.log(id);
    //Set the values to update
    var $form = $("#brand-edit-form");
    var json = toJson($form);
    var parsed=JSON.parse(json);
    console.log(parsed);
    if(parsed.brand=="" || parsed.category=="")
        return frontendChecks("Fields are empty");
    $.ajax({
        url: url,
        type: "PUT",
        data: json,
        headers: {
            "Content-Type": "application/json",
        },
        success: function (response) {
            SuccessMessage("Successfully Updated")
            getBrandList();
        },
        error: handleAjaxError
    });

    return false;
}

function getBrandList() {
    var url = getBrandUrl();
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            displayBrandList(data);
        },
        error: handleAjaxError,
    });
}

function deleteBrand(id) {
    var url = getBrandUrl() + "/" + id;

    $.ajax({
        url: url,
        type: "DELETE",
        success: function (data) {
            getBrandList();
        },
        error: handleAjaxError,
    });
}


//UI DISPLAY METHODS

function displayBrandList(data) {
    var $tbody = $("#brand-table").find("tbody");
    $tbody.empty();
    var index=1;
    for (var i in data) {
        var e = data[i];
        var buttonHtml =
            // '<button type="button" class="btn btn-secondary" onclick="deleteBrand(' + e.id + ')">delete</button>';
            // buttonHtml +=
            ' <button type="button" class="btn btn-secondary" onclick="displayEditBrand(' + e.id + ')">Edit</button>';
        var row =
            "<tr>" +
            "<td>" +
            index++ +
            "</td>" +
            "<td>" +
            e.brand +
            "</td>" +
            "<td>" +
            e.category +
            "</td>" +
            "<td>" +
            buttonHtml +
            "</td>" +
            "</tr>";
        $tbody.append(row);
    }
}

function displayEditBrand(id) {
    var url = getBrandUrl() + "/" + id;
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            displayBrand(data);
        },
        error: handleAjaxError,
    });
}

function displayBrand(data) {
    $("#brand-edit-form input[name=brand]").val(data.brand);
    $("#brand-edit-form input[name=category]").val(data.category);
    $("#brand-edit-form input[name=id]").val(data.id);
    $("#edit-brand-modal").modal("toggle");
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
    var file = $("#brandFile")[0].files[0];
    readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
    fileData = results.data;
    var json = JSON.stringify(fileData);
    var headers = ["brand", "category"];
    jsonq = JSON.parse(json);
    console.log(jsonq[0]);
    len=Object.keys(jsonq).length;
    console.log(length);
    console.log(Object.keys(jsonq[0]));

    for(let i=0;i<len;i++)
    {
        if(Object.keys(jsonq[i]).length!==headers.length)
        {
            console.log(Object.keys(jsonq[i]).length);
            frontendChecks("Row is not correct "+ i );
            return;
        }
        let keys=Object.keys(jsonq[i]);
        console.log(keys);
        for(const key in keys)
        {
            if(jsonq[i][key]==="")
            {
                frontendChecks("error in this row ", i);
            }
        }

    }

    if(Object.keys(jsonq[0]).length !== headers.length){
        frontendChecks("File column number do not match. Please check the file and try again");
        return;
    }
    for(let i in headers){
        if(!jsonq[0].hasOwnProperty(headers[i])){
            frontendChecks('File columns do not match. Please check the file and try again');
            return;
        }
    }


    uploadRows(fileData);

}

function uploadRows(fileData) {
    updateUploadDialog();
    var json = JSON.stringify(fileData);
    var url = getBrandUrl() + "-bulk";
    console.log('inside ajax')
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        headers: {
            "Content-Type": "application/json",
        },
        // success: function (response) {
        //     uploadRows();
        // },
        // error: function (response) {
        //     row.error = response.responseText;
        //     errorData.push(row);
        //     uploadRows();
        // },
        success:function (response){
            console.log(response);
            getBrandList();
            SuccessMessage("SuccessFully added");

        },
        error: function (response){
            if(response.status === 403){
                frontendChecks("403")
            }
            else {
                var resp = JSON.parse(response.responseText);
                var jsonObj = JSON.parse(resp.message);
                console.log(jsonObj);
                const arr=[];
                for(let obj in jsonObj)
                {
                    // console.log(obj)
                    // console.log(jsonObj[obj]);
                    const temp={};
                    temp['brand']=jsonObj[obj].brand;
                    temp['category']=jsonObj[obj].category;
                    temp['message']=jsonObj[obj].message;

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
    var $file = $("#brandFile");
    $file.val("");
    $("#brandFileName").html("Choose File");
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
    var $file = $("#brandFile");
    var fileName = $file.val();
    $("#brandFileName").html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $("#upload-brand-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
    $("#add-brand-modal").click(toggleModal);
    $("#add-brand").click(addBrand);
    $("#update-brand").click(updateBrand);
    $("#refresh-data").click(getBrandList);
    $("#upload-data").click(displayUploadData);
    $("#process-data").click(processData);
    $("#download-errors").click(downloadErrors);
    $("#brandFile").on("change", updateFileName);
}

$(document).ready(init);
$(document).ready(getBrandList);
