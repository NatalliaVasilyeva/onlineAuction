function editProfileServerCall() {
    $.post('edit-profile',
        {
            firstName: $('#profile-first-name-input').val(),
            lastName: $('#profile-last-name-input').val(),
            login: $('#profile-user-name-input').val(),
            email: $('#profile-email-input').val(),
            phone: $('#profile-phone-input').val(),
            role: $('#profile-role-input').val()
        },
        () => onSuccessfulEdit()
    );
}

function makeEditable() {
    setInputsReadOnly(false);
    $('#edit-profile-button-div').hide();
    $('#edit-manage-buttons-div').show();
}

function makeUneditable() {
    // revertInputs();
    setInputsReadOnly(true);
    $('#edit-profile-button-div').show();
    $('#edit-manage-buttons-div').hide();
}

function onSuccessfulEdit() {
    saveInputs();
    makeUneditable();
}

function saveInputs() {
    $(document).data('first-name', $('#profile-first-name-input').val());
    $(document).data('last-name', $('#profile-last-name-input').val());
    $(document).data('user-name', $('#profile-user-name-input').val());
    $(document).data('email', $('#profile-email-input').val());
    $(document).data('phone', $('#profile-phone-input').val());
    $(document).data('role', $('#profile-role-input').val());
    $(document).data('balance', $('#profile-money-input').val());
    $(document).data('frozen-money', $('#profile-frozen-money-input').val());
}

function revertInputs() {
    $('#profile-first-name-input').val($(document).data('first-name'));
    $('#profile-last-name-input').val($(document).data('last-name'));
    $('#profile-user-name-input').val($(document).data('user-name'));
    $('#profile-email-input').val($(document).data('email'));
    $('#profile-phone-input').val($(document).data('phone'));
    $('#profile-role-input').val($(document).data('role'));
    $('#profile-money-input').val($(document).data('balance'));
    $('#profile-frozen-money-input').val($(document).data('frozen-money'));
}

function setInputsReadOnly(isReadOnly) {
    $('#profile-first-name-input').prop('readonly', isReadOnly);
    $('#profile-last-name-input').prop('readonly', isReadOnly);
    $('#profile-email-input').prop('readonly', isReadOnly);
    $('#profile-phone-input').prop('readonly', isReadOnly);

}