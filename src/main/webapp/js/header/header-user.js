function goToBusinessProfile() {
    $("#business-profile-form").submit()
}

function goToMyAuctions() {
    $("#my-auction-form").submit()
}

function goToAddAuction() {
    $("#add-auction-form").submit()
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
    $.post("sign-out"
        // function () {
        //     $("#welcome-form").submit()
        // }
    )
}
