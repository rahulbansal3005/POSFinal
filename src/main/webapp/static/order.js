var completeOrder = []
let processedItems = {};

function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}

function getInvoiceUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/pdf";
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
    completeOrder = Object.values(processedItems);
    var $tbody = $('#order-item-table').find('tbody');
    $tbody.empty();
    for (var i in completeOrder) {
        var e = completeOrder[i];
        var buttonHtml = '<button type="button" class="btn btn-secondary" onclick="deleteOrderItem(' + i + ')" class="btn">Delete</button>'
        buttonHtml += ' <button type="button" class="btn btn-secondary" onclick="editOrderItem(' + i + ')" class="btn"> Edit </button>'
        var row = '<tr>' +
            '<td>' + e.barCode + '</td>' +
            '<td>' + e.quantity + '</td>' +
            '<td>' + e.sellingPrice + '</td>' +
            '<td>' + buttonHtml + '</td>' +
            '</tr>';

        $tbody.append(row);
    }

}

function isInteger(str) {
    // Regular expression to match an integer
    var integerPattern = /^-?\d+$/;
    // Test if the string matches the integer pattern
    return integerPattern.test(str);
}
function addOrderItem(event) {
    var $form = $("#order-item-form");
    var json = (toJson($form));
    json = JSON.parse(json);
    console.log(json,"json");
    if(json.barCode==="" || json.quantity==="" || json.sellingPrice==="")
        return frontendChecks("Fields are empty");
    if(isInteger(json.quantity)===false)
        return frontendChecks("Quantity is not an integer");
    if(json.quantity<=0)
        return frontendChecks("Quantity is negative or zero")
    if(json.sellingPrice<0)
        return frontendChecks("Selling Price is negative");
    //
    // if(parsed.mrp<=0)
    //     return frontendChecks("MRP can not be negative")

    let str = json.quantity;
    let parts = str.split(".");
    let length = parts[0].length;

    if(length>10)
        return frontendChecks("Invalid Quantity");
    // console.log(json);
    str=json.sellingPrice
    parts = str.split(".");
    length = parts[0].length;
    if(length>10)
        return frontendChecks("Invalid Selling Price");

    if (processedItems[json.barCode]) {
        if (processedItems[json.barCode].sellingPrice !== json.sellingPrice) {
            alert("Error: MRP mismatch for item with Barcode: " + json.barCode);
        } else {
            let qty = parseInt(processedItems[json.barCode].quantity) + parseInt(json.quantity);
            processedItems[json.barCode].quantity = qty.toString();
        }
    } else {
        processedItems[json.barCode] = json;
    }
    resetForm();
    displayOrderItemList();
}

function displayCart() {
    $('#add-order-item-modal').modal('toggle');
    var $tbody = $('#order-item-table').find('tbody');
    $tbody.empty();
    processedItems = {};
}

function getOrderItemList() {
    var jsonObj = $.parseJSON('[' + completeOrder + ']');
    console.log(jsonObj);
}

function editOrderItem(id) {
    let keys = Object.keys(processedItems);
    let barCode = keys[id];
    let temp = processedItems[barCode];
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
            console.log("RESPONSE", response);
            $("#view-order-id").text(id);
            response['orderItemList'].forEach(element => {
                console.log("element", element);
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
    var url = getOrderUrl();
    completeOrder = Object.values(processedItems);
    if(completeOrder.length==0)
        return frontendChecks("No items in order to place");
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
            SuccessMessage("Successfully added");
            getOrderList();
        },
        error: handleAjaxError
    });

    return false;
}

function getOrderList() {
    var url = getOrderUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderList(data);
        },
        error: handleAjaxError
    });
}

function download(id) {
    var url = getInvoiceUrl() + "/download/" + id;

    window.location.href = url;

}

function displayOrderList(data) {
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();

    var index=1;
    for (var i in data) {
        var e = data[i];
        // console.log("E", e);
        var buttonHtml = ' <button type="button" class="btn btn-secondary" onclick="displayOrder(' + e.id + ')">View Order</button>'
        // console.log("isInvoiceGenerated",isInvoiceGenerated);
        if (e.status === true) {
            buttonHtml += ' <button type="button" class="btn btn-success" id="generateInvoice" onclick="GenerateInvoice(' + e.id + ')" disabled> Generate invoice</button>'
            buttonHtml += ' <button type="button" class="btn btn-info" id="download" onclick="download(' + e.id + ')" >Download Invoice</button>'
        } else {
            buttonHtml += ' <button type="button" class="btn btn-success" id="generateInvoice" onclick="GenerateInvoice(' + e.id + ')"> Generate invoice</button>'
            buttonHtml += ' <button type="button" class="btn btn-info" id="download" onclick="download(' + e.id + ')" disabled>Download Invoice</button>'
        }
        // var date = new Date(e.updated)
        var row = '<tr>'
            + '<td>' + index++ + '</td>'
            + '<td>' + e.dateTime + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function GenerateInvoice(id) {
    var status=false;
    var url = getInvoiceUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function () {
            // var url2 = getInvoiceUrl()+"/download/"+id;
            // window.location.href = url;
            // status=true;
            download(id);
            SuccessMessage("Invoice generated Successfully");
            getOrderList();
        },

        error: handleAjaxError
    });
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
    $('#generateInvoice').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(getOrderItemList);