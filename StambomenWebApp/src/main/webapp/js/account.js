$(document).ready(function() {
    $("#privacySetting").change(function() {
        $("#btnSavePrivacy").removeAttr("disabled");
    });
});

function getPrivacyID() {
    var privacyID = $("option selected").val();

    return privacyID;
}

function savePrivacy() {
    var privacyID = getPrivacyID();

    $.ajax({
        type: "GET",
        url: "AccountServlet",
        data: "privacyID=" + privacyID,
        dataType: "json",
    }).success(function(data) {
        $("#btnSavePrivacy").prop("disabled", true);
    });
}