$(document).ready(function() {
    $('.popupboxbg').hide();
    $('.popupboxbg > div:last-child').click(function(args) {
        $('.popupboxbg').hide();
    });
    
    $('.popupbox img').error(function(){
        var def = "http://assets.vop.tiwi.be/team12/release/images/persons/DefaultMale.png";
        if ($('.popupbox img').attr("src") !== def) {
            $('.popupbox img').attr("src", def);
        }
    });
    
    $('.tree a:not(.unknown)').click(function(args) {
        $('.popupbox h1').text($(this).attr("data-firstname") + " " + $(this).attr("data-surname"));
        $('.popupbox #birthdate').text(lblBirthdate + ": " + $(this).attr("data-birthdate"));
        $('.popupbox #deathdate').text(lblDeathdate + ": " + $(this).attr("data-deathdate"));
        $('.popupbox #place').text(lblPlace + ": " + $(this).attr("data-zipcode") + " " + $(this).attr("data-placename"));
        $('.popupbox #country').text(lblCountry + ": " + $(this).attr("data-country"));
        $('.popupbox #refpersonid').val($(this).attr("data-id"));
        $('.popupbox img').attr("src", "http://assets.vop.tiwi.be/team12/release/images/persons/" + $(this).attr("data-tree-id") + "/" + $(this).attr("data-id") + ".jpg");
        
        $('.popupboxbg').show();
    });
});

$(window).load(function() {
    $('.tree').width($('.tree').width());
    $('body > .wrapper > div').css({'width':'auto'});
    
    $(document).scrollTo( '50%', {axis: 'x'} );
});