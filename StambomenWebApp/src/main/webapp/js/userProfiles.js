$(document).ready(function() {
    $('.popupboxbg').hide();
    $('.popupboxbg > div:last-child').click(function(args) {
        closePopUpBox();
    });

    $("#publicUserProfile li").click(function() {
        getUserProfileInPopUpBox(this.id);
    });
});

function closePopUpBox() {
    $('.popupboxbg').hide();
}

function getUserProfileInPopUpBox(userID) {
    $.ajax({
        type: "GET",
        url: "UserProfileServlet",
        data: "userID=" + userID,
        dataType: "json",
    }).success(function(data) {
        addContentToPopUpBox(data);
    });
}

function addContentToPopUpBox(data) {
    var content = "<h2>" + data.username + "</h2>";

    $(".popupboxContent").empty();
    $(".popupboxContent").append(content);
    $('.popupboxbg').show();
}