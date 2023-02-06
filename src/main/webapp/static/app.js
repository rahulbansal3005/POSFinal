//HELPER METHOD
function toJson($form) {
    var serialized = $form.serializeArray();
    // console.log(serialized);
    var s = '';
    var data = {};
    for (s in serialized) {
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}

function handleAjaxError(response) {
    console.log(response);
    var response = JSON.parse(response.responseText);
    console.log(response);
    toastr.error(response.message, "Error: ", {
        "closeButton": true,
        "timeOut": "0",
        "extendedTimeOut": "0"
    });
}

function frontendChecks(response){
    console.log(response);
    toastr.error(response, "Error: ", {
        "closeButton": true,
        "timeOut": "0",
        "extendedTimeOut": "0"
    });
}

// function fillBrandCategoryOptions(){

// }


function SuccessMessage(res)
{
    toastr.success(res,"Success: ",{
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    } )
}

function readFileData(file, callback) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results);
        }
    }
    Papa.parse(file, config);
}


function writeFileData(arr) {
    var config = {
        quoteChar: '',
        escapeChar: '',
        delimiter: "\t"
    };

    var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl = null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click();
}
