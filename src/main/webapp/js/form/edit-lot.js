function editLotServerCall() {
    let selectEdit = $("#edit-lot-modal").data("selectEdit");
    let td = $(selectEdit).closest("tr").children();
    let lotId = $("#edit-lot-id-input").val();
    let lotName = $("#edit-lot-name-input").val();
    let lotDescription = $("#edit-lot-description-input").val();
    let lotCategory = $("#edit-lot-category-input").val();
    const isValid = validateEditLotForm(name, value);
    if (!isValid) {
        return;
    }
    $.post("edit-lot",
        {
            lotId: lotId,
            lotName: lotName,
            lotDescription: lotDescription,
            lotCategory: lotCategory
        },
        // data => editTableRow(data)
    );

    function validateEditLotForm(name, description, category) {
        let errorMessage = {};
        if (!name) {
            errorMessage.name = "Name is empty";
        }
        if (!description) {
            errorMessage.description = "Description is empty";
        }
        if (!category) {
            errorMessage.category = "Category is empty";
        }
        showEditLotErrors(errorMessage.name, errorMessage.description, errorMessage.category);
        return $.isEmptyObject(errorMessage);
    }

    function showEditLotErrors(nameMsg, descMsg, categMsg) {
        showOneError($("#edit-lot-name-input"), $("#edit-lot-name-error-small"), nameMsg);
        showOneError($("#edit-lot-description-input"), $("#edit-lot-description-error-small"), descMsg);
        showOneError($("#edit-lot-category-input"), $("#edit-lot-category-error-small"), categMsg);
    }

    function showOneError(input, error, msg) {
        if (msg) {
            input.addClass("is-invalid");
            error.text(msg)
        } else {
            input.removeClass("is-invalid");
            error.text("")
        }
    }
}