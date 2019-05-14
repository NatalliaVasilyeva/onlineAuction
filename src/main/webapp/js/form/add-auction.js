function addAuctionServerCall() {
    let startTime = $("#add-auction-start-time-input").val();
    let finishTime = $("#add-auction-finish-time-input").val();
    let description = $("#add-auction-description-input").val();
    let type = $("#add-auction-type-input").val();
    const isValid = validateAddAuctionForm(startTime, finishTime, description, type);
    if (!isValid) {
        return;
    }
    $.post('add-auction',
        {
            startTime: startTime,
            finishTime: finishTime,
            description: description,
            type: type
        },

    $("#welcome-form").submit());
        //,
  //      () => location.reload());
}

function validateAddAuctionForm(startTime, finishTime, description, type) {
    let errorMessage = {};
    if (!startTime) {
        errorMessage.startTime = "Start time is empty";
    }
    if (!finishTime) {
        errorMessage.finishTime = "Finish time is empty";
    }
    if (!description) {
        errorMessage.description = "Description is empty";
    }
    if (!type) {
        errorMessage.type = "Type is empty";
    }
    showAddAuctionErrors(errorMessage.startTime, errorMessage.finishTime, errorMessage.description, errorMessage.type);
    return $.isEmptyObject(errorMessage);
}

function showAddAuctionErrors(startMsg, finishdMsg, descMsg, typeMsg) {
    showOneError($("#add-auction-start-time-input"), $("#add-auction-start-time-error-small"), startMsg);
    showOneError($("#add-auction-finish-time-input"), $("#add-auction-finish-time-error-small"), finishdMsg);
    showOneError($("#add-auction-description-input"), $("#add-auction-description-error-small"), descMsg);
    showOneError($("#add-auction-type-input"), $("#add-auction-type-error-small"), typeMsg);
}

function showOneError(input, error, msg) {
    if (msg) {
        input.addClass("is-invalid");
        error.text(msg);
    } else {
        input.removeClass("is-invalid");
        error.text("");
    }
}
