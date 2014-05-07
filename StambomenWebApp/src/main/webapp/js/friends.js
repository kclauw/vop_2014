$(document).ready(function() {
    $('.popupboxbg').hide();
    $('.popupboxbg > div:last-child').click(function(args) {
        $('.popupboxbg').hide();
    });
    
    $('#adduser').click(function(args) {
        $('.popupboxbg').show();
    });
    
    if ($('.friendrequestlist ul').children().length === 0) {
        $('.friendrequestlist').hide();
    }
    
});