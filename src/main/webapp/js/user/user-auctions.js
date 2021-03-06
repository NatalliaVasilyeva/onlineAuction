function showMyLastAuction() {
    $('#show-my-last-auction').toggle();
}

function MakeEditable() {
    $('#my-last-auction-start-time').prop('readonly', false);
    $('#my-last-auction-finish-time').prop('readonly', false);
    $('#my-last-auction-description').prop('readonly', false);
    $("#footer-button").hide();
    $("#footer-edit-button").show();
}

function CancelEditAuction() {
    $('#my-last-auction-start-time').prop('readonly', true);
    $('#my-last-auction-finish-time').prop('readonly', true);
    $('#my-last-auction-description').prop('readonly', true);
    $("#footer-button").show();
    $("#footer-edit-button").hide();
}


function EditAuctionServerCall() {
    let auctionId = $("#my-last-auction-id").val();
    let startTime = $("#my-last-auction-start-time").val();
    let finishTime = $("#my-last-auction-finish-time").val();
    let description = $("#my-last-auction-description").val();
    // const isValid = validateEditAuctionForm(startTime, finishTime, description);
    // if (!isValid) {
    //     return;
    // }
    $.post("edit-auction",
        {
            auctionId: auctionId,
            startTime: startTime,
            finishTime: finishTime,
            description: description
        },
        () => onSuccessfulEdit()
    );
}

function DeleteAuctionServerCall() {
    let auctionId = $("#my-last-auction-id").val();

    $.post("delete-auction",
        {
            auctionId: auctionId,
        },
    );
}


// function validateEditAuctionForm(startTime, finishTime, description) {
//     let errorMessage = {};
//     if (!name) {
//         errorMessage.name = "Name is empty";
//     }
//     if (!description) {
//         errorMessage.description = "Description is empty";
//     }
//     if (!category) {
//         errorMessage.category = "Category is empty";
//     }
//     showEditLotErrors(errorMessage.name, errorMessage.description, errorMessage.category);
//     return $.isEmptyObject(errorMessage);
// }
//
// function showEditLotErrors(nameMsg, descMsg, categMsg) {
//     showOneError($("#edit-lot-name-input"), $("#edit-lot-name-error-small"), nameMsg);
//     showOneError($("#edit-lot-description-input"), $("#edit-lot-description-error-small"), descMsg);
//     showOneError($("#edit-lot-category-input"), $("#edit-lot-category-error-small"), categMsg);
// }
//
// function showOneError(input, error, msg) {
//     if (msg) {
//         input.addClass("is-invalid");
//         error.text(msg)
//     } else {
//         input.removeClass("is-invalid");
//         error.text("")
//     }
// }
// }

function onSuccessfulEdit() {
    saveInputs();
    CancelEditAuction();
}

function saveInputs() {
    $(document).data('auction-start-time', $('#my-last-auction-start-time').val());
    $(document).data('auction-finish-time', $('#my-last-auction-finish-time').val());
    $(document).data('auction-description', $('#my-last-auction-description').val());

}