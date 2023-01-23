function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/order";
}
function getOrderItemUrl(){
  var baseUrl = $("meta[name=baseUrl]").attr("content")
  return baseUrl + "/api/orderItem";
}

//BUTTON ACTIONS
function addCustomer(event) {
  //Set the values to update
  var $form = $("#customer-form");
  var json = toJson($form);
  var url = getOrderUrl();

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getCustomerList();
    },
    error: handleAjaxError,
  });

  return false;
}

function toggleModal(event)
{
  $("#add-customer-modal").modal("toggle");
}

function addOrderItem(){
  var $form = $("#customer-form");
  var json = toJson($form);
  var url = getOrderItemUrl();
  console.log(url);
  console.log(json);

  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      nextOrderItem();
    },
    error: handleAjaxError,
  });
}

function submitOrder()
{

  var $form = $("#customer-form");
  var json = toJson($form);
  var url = getOrderUrl();
  console.log(json);
}


function updateCustomer(event) {
  $("#edit-customer-modal").modal("toggle");
  //Get the ID
  var id = $("#customer-edit-form input[name=id]").val();
  var url = getOrderUrl() + "/" + id;

  //Set the values to update
  var $form = $("#customer-edit-form");
  var json = toJson($form);

  $.ajax({
    url: url,
    type: "PUT",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      getCustomerList();
    },
    error: handleAjaxError,
  });

  return false;
}

function getCustomerList() {
  var url = getOrderUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayCustomerList(data);
    },
    error: handleAjaxError,
  });
}

function deleteCustomer(id) {
  var url = getOrderUrl() + "/" + id;

  $.ajax({
    url: url,
    type: "DELETE",
    success: function (data) {
      getCustomerList();
    },
    error: handleAjaxError,
  });
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;

function processData() {
  var file = $("#customerFile")[0].files[0];
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
  if (processCount == fileData.length) {
    return;
  }

  //Process next row
  var row = fileData[processCount];
  processCount++;

  var json = JSON.stringify(row);
  var url = getOrderUrl();

  //Make ajax call
  $.ajax({
    url: url,
    type: "POST",
    data: json,
    headers: {
      "Content-Type": "application/json",
    },
    success: function (response) {
      uploadRows();
    },
    error: function (response) {
      row.error = response.responseText;
      errorData.push(row);
      uploadRows();
    },
  });
}

function downloadErrors() {
  writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayCustomerList(data) {
  var $tbody = $("#customer-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      '<button onclick="deleteCustomer(' + e.id + ')">delete</button>';
    buttonHtml +=
      ' <button onclick="displayEditCustomer(' + e.id + ')">edit</button>';
    var row =
      "<tr>" +
      "<td>" +
      e.id +
      "</td>" +
      "<td>" +
      e.barcode +
      "</td>" +
      "<td>" +
      e.quantity +
      "</td>" +
      "<td>" +
      e.mrp +
      "</td>" +
      "<td>" +
      buttonHtml +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function displayEditCustomer(id) {
  var url = getOrderUrl() + "/" + id;
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      displayCustomer(data);
    },
    error: handleAjaxError,
  });
}

function resetUploadDialog() {
  //Reset file name
  var $file = $("#customerFile");
  $file.val("");
  $("#customerFileName").html("Choose File");
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
  var $file = $("#customerFile");
  var fileName = $file.val();
  $("#customerFileName").html(fileName);
}

function displayUploadData() {
  resetUploadDialog();
  $("#upload-customer-modal").modal("toggle");
}

function displayCustomer(data) {
  $("#customer-edit-form input[name=barcode]").val(data.barcode);
  $("#customer-edit-form input[name=quantity]").val(data.quantity);
  $("#customer-edit-form input[name=mrp]").val(data.mrp);
  $("#customer-edit-form input[name=id]").val(data.id);
  $("#edit-customer-modal").modal("toggle");
}

//INITIALIZATION CODE
function init() {
  $("#add-Order").click(toggleModal);
  $("#add-orderItem").click(addOrderItem);
  $("#submit-order").click(submitOrder);
  $("#update-customer").click(updateCustomer);
  $("#refresh-Orderdata").click(getCustomerList);
  $("#upload-data").click(displayUploadData);
  $("#process-data").click(processData);
  $("#download-errors").click(downloadErrors);
  $("#customerFile").on("change", updateFileName);
}

$(document).ready(init);
$(document).ready(getCustomerList);
