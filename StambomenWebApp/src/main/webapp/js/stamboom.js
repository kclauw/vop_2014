$(document).ready(function() {
    $('.popupboxbg').hide();
    $('.popupboxbg > div:last-child').click(function(args) {
        $('.popupboxbg').hide();
    });
    
    $('.tree a:not(.unknown)').click(function(args) {
        $('.popupbox h1').text($(this).attr("data-firstname") + " " + $(this).attr("data-surname"));
        $('.popupbox #birthdate').text(lblBirthdate + ": " + $(this).attr("data-birthdate"));
        $('.popupbox #deathdate').text("Deathdate: " + $(this).attr("data-deathdate"));
        $('.popupbox #place').text("Place: " + $(this).attr("data-zipcode") + " " + $(this).attr("data-placename"));
        $('.popupbox #country').text("Country: " + $(this).attr("data-country"));
        $('.popupbox #refpersonid').val($(this).attr("data-id"));
        $('.popupbox img').val($(this).attr("data-id"));
        
        $('.popupboxbg').show();
    });
});

$(window).load(function() {
    $('.tree').width($('.tree').width());
    $('body > .wrapper > div').css({'width':'auto'});
    
    $(document).scrollTo( '50%', {axis: 'x'} );
});