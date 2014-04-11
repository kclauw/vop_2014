$(document).ready(function() {
//activate savebutton and saveallbutton on change dropdownlist
    $("#privacyOptions").change(function() {
        $("#btnSavePrivacy").removeAttr("disabled");
        $("#btnSaveAll").removeAttr("disabled");
    });
    $("#languageOptions").change(function() {
        $("#btnSaveLanguage").removeAttr("disabled");
        $("#btnSaveAll").removeAttr("disabled");
    });
});
function getValueID(element) {
    var id = $("#" + element + " option:selected").val();
    return id;
}

function savePrivacy() {
    var privacyID = getValueID("privacyOptions");
    $.ajax({
        type: "GET",
        url: "AccountServlet",
        data: "privacyID=" + privacyID,
        dataType: "json",
    }).success(function(data) {
        $("#btnSavePrivacy").prop("disabled", true);
    });
}

function saveLanguage() {
    var languageID = getValueID("languageOptions");
    $.ajax({
        type: "GET",
        url: "AccountServlet",
        data: "languageID=" + languageID,
        dataType: "json",
    }).success(function(data) {
        $("#btnSaveLanguage").prop("disabled", true);
    });
}

function saveAll() {
    savePrivacy();
    saveLanguage();
}