function goToBusinessProfile() {
    $("#business-profile-form").submit()
}

function goToRunningAuction() {
    $("#running-auction-form").submit()
}

function goToProfile() {
    $("#profile-form").submit()
}

function goToSettings() {
    $("#settings-form").submit()
}

function goToRequestAuction() {
    $("#request-auction-form").submit()
}

function signOut() {
    $.post("sign-out",
        function () {
            $("#welcome-form").submit()
        }
    )
}
