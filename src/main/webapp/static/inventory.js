function getInventoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/inventory";
}
function resetForm() {
  var element = document.getElementById("inventory-form");
  element.reset()
}
function toggleModal(){
  $('#add-inventory-item-modal').modal('toggle');
}
function addInventory(event) {
  var $form = $("#inventory-form");
  var json = toJson($form);
  console.log(json)
  var parsed=JSON.parse(json);
  console.log(parsed);

  if(parsed.barcode==="" || parsed.quantity==="")
    return frontendChecks("Fields are empty");
  if(Number.isInteger(parsed.quantity)==false)
    return frontendChecks("Quantity is not an integer");

  if(parsed.quantity<0)
    return frontendChecks("Quantity can not be negative")

  var url = getInventoryUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      $('#add-inventory-item-modal').modal('toggle');
      resetForm();
      SuccessMessage("Successfully added");
      getInventoryList();
    },
    error: handleAjaxError,
  });
  return false;
}

function updateInventory(event) {
  $("#edit-inventory-modal").modal("toggle");
  var id = $("#inventory-edit-form input[name=id]").val();
  var url = getInventoryUrl() + "/" + id;

  var $form = $("#inventory-edit-form");
  // console.log($form);

  var json = toJson($form);

  var parsed=JSON.parse(json);
  console.log(parsed);
  if(parsed.quantity=="")
    return frontendChecks("Fields are empty");
  if(parsed.quantity<0)
    return frontendChecks("Quantity can not be negative")

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      SuccessMessage('Updated successfully');
      getInventoryList();
    },
    error: handleAjaxError,
  });
  return false;
}

function getInventoryList() {
  var url = getInventoryUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {

      displayInventoryList(data);
    },
    error: handleAjaxError,
  });
}

function deleteInventory(id) {
  var url = getInventoryUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getInventoryList();
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS

function displayInventoryList(data) {
  var $tbody = $("#inventory-table").find("tbody");
  $tbody.empty();
  let index=1;
  for (var i in data) {
    var e = data[i];
    // console.log(e);
    var buttonHtml =
        // '<button type="button" class="btn btn-secondary" onclick="deleteInventory(' + e.id + ')">Delete</button>';
        // buttonHtml +=
        ' <button type="button" class="btn btn-secondary" onclick="displayEditInventory(' + e.id + ')">Edit</button>';
    var row =
        "<tr>" +
        "<td>" +
        index++ +
        "</td>" +
        "<td>" +
        e.barcode +
        "</td>" +
        "<td>" +
        e.quantity +
        "</td>" +
        "<td>" +
        buttonHtml +
        "</td>" +
        "</tr>";
    $tbody.append(row);
  }
}

function displayEditInventory(id) {
  var url = getInventoryUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayInventory(data);
    },
    error: handleAjaxError,
  });
}




// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#inventoryFile")[0].files[0];
  readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
  fileData = results.data;
  var json = JSON.stringify(fileData);
  var headers = ["barcode", "quantity"];
  jsonq = JSON.parse(json);
  console.log(jsonq[0]);
  len=Object.keys(jsonq).length;
  console.log(length);
  console.log(Object.keys(jsonq[0]));

  for(let i=0;i<len;i++)
  {
    if(Object.keys(jsonq[i]).length!=headers.length)
    {
      console.log(Object.keys(jsonq[i]).length);
      frontendChecks("Row is not correct "+ i );
      return;
    }
    let keys=Object.keys(jsonq[i]);
    console.log(keys);
    for(const key in keys)
    {
      if(jsonq[i][key]=="")
      {
        frontendChecks("error in this row ", i);
      }
    }

  }

  if(Object.keys(jsonq[0]).length != headers.length){
    frontendChecks("File column number do not match. Please check the file and try again");
    return;
  }
  for(var i in headers){
    if(!jsonq[0].hasOwnProperty(headers[i])){
      frontendChecks('File columns do not match. Please check the file and try again');
      return;
    }
  }
  uploadRows();
}

function uploadRows() {
  //Update progress
  updateUploadDialog();
  //If everything processed then return
  // if (processCount == fileData.length) {
  //   return;
  // }

  //Process next row
  // var row = fileData[processCount];
  // processCount++;

  var json = JSON.stringify(fileData);
  var url = getInventoryUrl()+ "-bulk";

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
    success:function (response){
      console.log(response);
      getInventoryList();
    },
    error: function (response){
      // console.log(response);
      // for(var i in response)
      // {
      //     errorData.push(i);
      // }
      if(response.status == 403){
        // toastr.error("403 Forbidden");
        frontendChecks("403")
      }
      else {
        var resp = JSON.parse(response.responseText);
        var jsonObj = JSON.parse(resp.message);
        console.log(jsonObj);
        errorData = jsonObj;
        console.log(response);
        toastr.error("Error in uploading TSV file, Download Error File");
        $("#download-errors").prop('disabled', false);
      }
    }
  });
}

function downloadErrors() {
  writeFileData(errorData);
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#inventoryFile");
  $file.val("");
  $("#inventoryFileName").html("Choose File");
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
  var $file = $("#inventoryFile");
  var fileName = $file.val();
  $("#inventoryFileName").html(fileName);
}

function displayUploadData() {
  resetUploadDialog();
  $("#upload-inventory-modal").modal("toggle");
}

function displayInventory(data) {
  console.log(data);
  $("#inventory-edit-form input[name=barcode]").val(data.barcode);
  $("#inventory-edit-form input[name=category]").val(data.category);
  $("#inventory-edit-form input[name=id]").val(data.id);
  $("#edit-inventory-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#add-inventory-modal").click(toggleModal);
  $("#add-inventory").click(addInventory);
  $("#update-inventory").click(updateInventory);
  $("#refresh-data").click(getInventoryList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#inventoryFile").on("change", updateFileName);
}

$(document).ready(init);
$(document).ready(getInventoryList);
