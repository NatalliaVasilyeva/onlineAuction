function quickAuctionView(auction) {
    let name = $("<p></p>").text(
        $.i18n("quickView-auction-name") + ": " + auction["name"]);
    let date = $("<p></p>").text(
        $.i18n("quickView-auction-date") + ": " + auction["eventDate"]);
    return $("<div></div>")
        .append(name)
        .append(date)
        .html();
}

function quickBuyerView(buyer) {
    let login = $("<p></p>").text(
        $.i18n("quickView-buyer-login") + ": " + buyer["login"]);
    let firstName = $("<p></p>").text(
        $.i18n("quickView-buyer-firstName") + ": " + buyer["firstName"]);
    let lastName = $("<p></p>").text(
        $.i18n("quickView-buyer-lastName") + ": " + buyer["lastName"]);
    let dob = $("<p></p>").text(
        $.i18n("quickView-buyer-dob") + ": " + buyer["dateOfBirth"]);
    return $("<div></div>")
        .append(login)
        .append(firstName)
        .append(lastName)
        .append(dob)
        .html();
}

function quickFlowerView(flower) {
    let ownerId = $("<p></p>").text(
        $.i18n("quickView-flower-ownerId") + ": " + flower["ownerId"]);
    let id = flower["flowerAccepted"] ? flower["auctionId"] : "";
    let auctionId = $("<p></p>").text(
        $.i18n("quickView-flower-auctionId") + ": " + id);
    let name = $("<p></p>").text(
        $.i18n("quickView-flower-name") + ": " + flower["name"]);
    let value = $("<p></p>").text(
        $.i18n("quickView-flower-value") + ": " + flower["value"]);
    return $("<div></div>")
        .append(ownerId)
        .append(auctionId)
        .append(name)
        .append(value)
        .html();
}
