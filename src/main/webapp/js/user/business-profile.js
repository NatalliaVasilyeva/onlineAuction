function editLot(button) {
    let td = $(button).closest("tr").children();
    $("#edit-lot-id-input").val($(td[1]).text());
    $("#edit-lot-name-input").val($(td[2]).text());
    $("#edit-lot-description-input").val($(td[3]).text());
    $("#edit-lot-category-input").val($(td[4]).text());

    let form = $("#edit-lot-modal");
    form.data("selectEdit", button);
    form.modal('show')
}

function removeLot(button) {
    let tr = $(button).closest("tr");
    let lotId = $(tr.children()[1]).text();
    $.post("remove-lot",
        {
            lotId: lotId
        },
        () => tr.remove()
    )
}
