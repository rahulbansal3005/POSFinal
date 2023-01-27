// let completeOrder = [];
//
// // function getOrderItemUrl() {
// //   var baseUrl = $("meta[name=baseUrl]").attr("content")
// //   return baseUrl + "/api/orders";
// // }
//
// function getOrderUrl() {
//   var baseUrl = $("meta[name=baseUrl]").attr("content")
//   return baseUrl + "/api/order";
// }
//
// // function getProductUrl() {
// //   var baseUrl = $("meta[name=baseUrl]").attr("content")
// //   return baseUrl + "/api/products";
// // }
// //
// // function getInventoryUrl() {
// //   var baseUrl = $("meta[name=baseUrl]").attr("content")
// //   return baseUrl + "/api/inventory";
// // }
//
// function resetForm() {
//   var element = document.getElementById("order-item-form");
//   element.reset()
// }
//
//
// function deleteOrderItem(id) {
//   completeOrder.splice(id, 1);
//   displayOrderItemList();
// }
//
// function displayOrderItemList(data) {
//   var $tbody = $('#order-item-table').find('tbody');
//   $tbody.empty();
//
//
//   for (var i in completeOrder) {
//     var e = completeOrder[i];
//     var buttonHtml = '<button onclick="deleteOrderItem(' + i + ')" class="btn">delete</button>';
//     var row = '<tr>' +
//         '<td>' + completeOrder[i].barCode + '</td>' +
//         '<td>' + completeOrder[i].quantity + '</td>' +
//         '<td>' + completeOrder[i].sellingPrice + '</td>' +
//         '<td>' + buttonHtml + '</td>' +
//         '</tr>';
//
//     $tbody.append(row);
//   }
//
// }
//
// function addOrderItem(event) {
//   var $form = $("#order-item-form");
//   var json = JSON.parse(toJson($form));
//   console.log("check:")
//   console.log(completeOrder);
//   completeOrder.push(json)
//   resetForm();
//   displayOrderItemList();
// }
//
// function displayCart() {
//   $('#add-order-item-modal').modal('toggle');
//   var $tbody = $('#order-item-table').find('tbody');
//   $tbody.empty();
// }
//
// function getOrderItemList() {
//   var jsonObj = $.parseJSON('[' + completeOrder + ']');
//   console.log(jsonObj);
// }
//
//
// function displayOrder(id) {
//
//   $('#view-order-item-modal').modal('toggle');
//
//   var url = getOrderUrl() + "/" + id + "/items";
//
//   var $tbody = $('#view-order-item-table').find('tbody');
//   $tbody.empty();
//
//   $.ajax({
//     url: url,
//     type: 'GET',
//     headers: {
//       'Content-Type': 'application/json'
//     },
//     success: function (response) {
//       $("#view-order-id").text(id);
//       response.forEach(element => {
//         var row = '<tr>' +
//             '<td>' + element.barCode + '</td>' +
//             '<td>' + element.quantity + '</td>' +
//             '<td>' + element.sellingPrice + '</td>' +
//             '</tr>';
//
//         $tbody.append(row);
//       });
//     },
//     error: function (error) {
//       alert(error.responseJSON.message);
//     }
//   });
//
// }
//
//
// function placeOrder() {
//   console.log(completeOrder);
//   var url = getOrderUrl();
//   $.ajax({
//     url: url,
//     type: 'POST',
//     data: JSON.stringify(completeOrder),
//     headers: {
//       'Content-Type': 'application/json'
//     },
//     success: function (response) {
//       $('#add-order-item-modal').modal('toggle');
//       completeOrder = []
//       $("#Order-table").getOrderList();
//     },
//     error: handleAjaxError
//   });
//
//   return false;
// }
//
// function getOrderList(){
//   var url = getOrderUrl();
//   $.ajax({
//     url: url,
//     type: 'GET',
//     success: function(data) {
//       displayOrderList(data);
//     },
//     error: handleAjaxError
//   });
// }
//
// function displayOrderList(data){
//   var $tbody = $('#order-table').find('tbody');
//   $tbody.empty();
//   for(var i in data){
//
//     var e = data[i];
//     console.log("printing e");
//     console.log(e);
//     var buttonHtml = ' <button type="button" class="btn btn-secondary" onclick="displayOrder(' + e.id + ')">View Order</button>'
//     var date = new Date(e.updated)
//     var row = '<tr>'
//         + '<td>' + e.id + '</td>'
//         + '<td>' + e.time("en-US") + '</td>'
//         + '<td>' + buttonHtml + '</td>'
//         + '</tr>';
//     $tbody.append(row);
//   }
// }
//
// function init() {
//   $("#add-order-item-modal").on('shown', function () {
//     completeOrder = [];
//   });
//   $('#add-order').click(displayCart);
//   $('#add-order-item').click(addOrderItem);
//   $('#place-order').click(placeOrder);
//   $('#refresh-data').click(getOrderList);
// }
//
// $(document).ready(init);
// $(document).ready(getOrderList);
// $(document).ready(getOrderItemList);




var completeOrder = []
let processedItems = {};
function getOrderItemUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content")
  return baseUrl + "/api/order";
}

function getOrderUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content")
  return baseUrl + "/api/order";
}

function getProductUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content")
  return baseUrl + "/api/product";
}

function getInventoryUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content")
  return baseUrl + "/api/inventory";
}

function resetForm() {
  var element = document.getElementById("order-item-form");
  element.reset()
}


function deleteOrderItem(id) {
  let keys = Object.keys(processedItems);
  let barCode = keys[id];
  console.log("delete:");
  console.log(processedItems[barCode]);
  delete processedItems[barCode];
  displayOrderItemList();
}

function displayOrderItemList(data) {
  completeOrder=Object.values(processedItems);
  var $tbody = $('#order-item-table').find('tbody');
  $tbody.empty();
  for (var i in completeOrder) {
    var e = completeOrder[i];
    var buttonHtml = '<button type="button" class="btn btn-secondary" onclick="deleteOrderItem(' + i + ')" class="btn">Delete</button>'
    buttonHtml +=' <button type="button" class="btn btn-secondary" onclick="editOrderItem(' + i + ')" class="btn"> Edit </button>'
    var row = '<tr>' +
        '<td>' + e.barCode + '</td>' +
        '<td>' + e.quantity + '</td>' +
        '<td>' + e.sellingPrice + '</td>' +
        '<td>' + buttonHtml + '</td>' +
        '</tr>';

    $tbody.append(row);
  }

}

function addOrderItem(event) {
  var $form = $("#order-item-form");
  var json = JSON.parse(toJson($form));
  console.log("check:")
  console.log(json);
  if (processedItems[json.barCode]) {
    if (processedItems[json.barCode].sellingPrice !== json.sellingPrice) {
      alert("Error: MRP mismatch for item with Barcode: "+ json.barCode);
    }else{
      let qty= parseInt(processedItems[json.barCode].quantity) + parseInt(json.quantity);
      processedItems[json.barCode].quantity=qty.toString();
    }
  }
  else {
    processedItems[json.barCode] = json;
  }
  resetForm();
  displayOrderItemList();
}

function displayCart() {
  $('#add-order-item-modal').modal('toggle');
  var $tbody = $('#order-item-table').find('tbody');
  $tbody.empty();
  processedItems={};
}

function getOrderItemList() {
  var jsonObj = $.parseJSON('[' + completeOrder + ']');
  console.log(jsonObj);
}

function editOrderItem(id) {
  let keys = Object.keys(processedItems);
  let barCode = keys[id];
  let temp=processedItems[barCode];
  $("#order-item-form input[name=barCode]").val(temp.barCode);
  $("#order-item-form input[name=quantity]").val(temp.quantity);
  $("#order-item-form input[name=sellingPrice]").val(temp.sellingPrice);
  deleteOrderItem(id);
}


function displayOrder(id) {
  $('#view-order-item-modal').modal('toggle');

  var url = getOrderUrl() + "/" + id;

  var $tbody = $('#view-order-item-table').find('tbody');
  $tbody.empty();

  $.ajax({
    url: url,
    type: 'GET',
    headers: {
      'Content-Type': 'application/json'
    },
    success: function (response) {
      console.log("RESPONSE",response);
      $("#view-order-id").text(id);
      response['c'].forEach(element => {
        console.log("element",element);
          var row = '<tr>' +
              '<td>' + element.barCode + '</td>' +
              '<td>' + element.quantity + '</td>' +
              '<td>' + element.sellingPrice + '</td>' +
              '</tr>';

          $tbody.append(row);
      });
    },
    error: function (error) {
      alert(error.responseJSON.message);
    }
  });
}


function placeOrder() {
  var url = getOrderItemUrl();
  completeOrder=Object.values(processedItems);
  $.ajax({
    url: url,
    type: 'POST',
    data: JSON.stringify(completeOrder),
    headers: {
      'Content-Type': 'application/json'
    },
    success: function (response) {
      $('#add-order-item-modal').modal('toggle');
      completeOrder = []
      processedItems = Object.assign({});
      getOrderList();
    },
    error: handleAjaxError
  });

  return false;
}

function getOrderList(){
  var url = getOrderUrl();
  $.ajax({
    url: url,
    type: 'GET',
    success: function(data) {
      displayOrderList(data);
    },
    error: handleAjaxError
  });
}

function displayOrderList(data){
  var $tbody = $('#order-table').find('tbody');
  $tbody.empty();

  for(var i in data){
    var e = data[i];
    console.log("E",e);
    var buttonHtml = ' <button type="button" class="btn btn-secondary" onclick="displayOrder(' + e.id + ')">View Order</button>'
    var date = new Date(e.updated)
    var row = '<tr>'
        + '<td>' + e.id + '</td>'
        + '<td>' + e.dateTime +'</td>'
        + '<td>' + buttonHtml + '</td>'
        + '</tr>';
    $tbody.append(row);
  }
}

function init() {
  $("#add-order-item-modal").on('shown', function () {
    completeOrder = [];
    processedItems = Object.assign({});
  });
  $('#add-order').click(displayCart);
  $('#add-order-item').click(addOrderItem);
  $('#place-order').click(placeOrder);
  $('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(getOrderItemList);