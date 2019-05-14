function signUpServerCall() {
    let login = $("#sign-up-login-input").val();
    let firstname = $("#sign-up-first-name-input").val();
    let lastname = $("#sign-up-last-name-input").val();
    let email = $("#sign-up-email-input").val();
    let phone = $("#sign-up-phone-input").val();
    let password = $("#sign-up-password-input").val();
    let repeat_password = $("#sign-up-confirm-password-input").val();
    const isValid = validateSignUpForm(login, firstname, lastname, email, phone, password, repeat_password);
    if (!isValid) {
        return;
    }
    $.post("sign-up",
        {
            login: login,
            firstname: firstname,
            lastname: lastname,
            email: email,
            phone: phone,
            password: password,
            repeat_password: repeat_password
        },
        $("#welcome-form").submit());

}

function validateSignUpForm(login, firstname, lastname, email, phone, password, repeat_password) {
    let errorMessage = {};
    if (!login) {
        errorMessage.login = "Login is empty";
    }
    if (!firstname) {
        errorMessage.firstname = "First name is empty";
    }
    if (!lastname) {
        errorMessage.lastname = "Last name is empty";
    }
    if (!email) {
        errorMessage.email = "Email is empty";
    }
    if (!phone) {
        errorMessage.phone = "Phone number is empty";
    }
    if (!password) {
        errorMessage.password = "Password is empty";
    }
    if (!repeat_password) {
        errorMessage.repeat_password = "Confirm Password is empty";
    }
    if (password !== repeat_password) {
        errorMessage.password = "Password and Confirm Password do not match";
        errorMessage.repeat_password = "Password and Confirm Password do not match";
    }
    showSignUpErrors(errorMessage.login, errorMessage.firstname, errorMessage.lastname, errorMessage.email, errorMessage.phone, errorMessage.password, errorMessage.repeat_password);
    return $.isEmptyObject(errorMessage);
}

function showSignUpErrors(loginMsg, firstMsg, lastMsg, emailMsg, phoneMsg, passwordMsg, confirmPasswordMsg) {
    showOneError($("#sign-up-login-input"), $("#sign-up-login-error-small"), loginMsg);
    showOneError($("#sign-up-first-name-input"), $("#sign-up-first-name-error-small"), firstMsg);
    showOneError($("#sign-up-last-name-input"), $("#sign-up-last-name-error-small"), lastMsg);
    showOneError($("#sign-up-email-input"), $("#sign-up-email-error-small"), emailMsg);
    showOneError($("#sign-up-phone-input"), $("#sign-up-last-phone-error-small"), phoneMsg);
    showOneError($("#sign-up-password-input"), $("#sign-up-password-error-small"), passwordMsg);
    showOneError($("#sign-up-confirm-password-input"), $("#sign-up-confirm-password-error-small"), confirmPasswordMsg);
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

function clearSignUpInput() {
    let logInput = $("#sign-up-login-input");
    let firstInput = $("#sign-up-first-name-input");
    let lastInput = $("#sign-up-last-name-input");
    let emailInput = $("#sign-up-email-input");
    let phoneInput = $("#sign-up-phone-input");
    let pasInput = $("#sign-up-password-input");
    let conInput = $("#sign-up-confirm-password-input");
    let logError = $("#sign-up-login-error-small");
    let firstError = $("#sign-up-first-name-error-small");
    let lastError = $("#sign-up-last-name-error-small");
    let emailError = $("#sign-up-email-error-small");
    let phoneError = $("#sign-up-last-phone-error-small");
    let pasError = $("#sign-up-password-error-small");
    let conError = $("#sign-up-confirm-password-error-small");
    logInput.val("");
    firstInput.val("");
    lastInput.val("");
    emailInput.val("");
    phoneInput.val("");
    pasInput.val("");
    conInput.val("");
    showOneError(logInput, logError, null);
    showOneError(firstInput, firstError, null);
    showOneError(lastInput, lastError, null);
    showOneError(emailInput, emailError, null);
    showOneError(phoneInput, phoneError, null);
    showOneError(pasInput, pasError, null);
    showOneError(conInput, conError, null);
}