$(document).ready(function() {
    $('.popupboxbg').hide();
    $('.popupboxbg div:last-child').click(function() {
        $('.popupboxbg').hide();
    });
    
    $('.tree a').click(function(args) {
        $('.popupbox h1').text($(this).attr("data-firstname") + " " + $(this).attr("data-surname"));
        $('.popupbox #birthdate').text("Birtdate: " + $(this).attr("data-birthdate"));
        $('.popupbox #deathdate').text("Deathdate: " + $(this).attr("data-deathdate"));
        $('.popupbox #place').text("Place: " + $(this).attr("data-zipcode") + " " + $(this).attr("data-placename"));
        $('.popupbox #country').text("Country: " + $(this).attr("data-country"));
        
        $('.popupboxbg').show();
    });
});