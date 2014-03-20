/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var map;
var markers = {};

$(document).ready(function() {
    init();
});

function init() {
    var mindate = new Date("1992-05-01" + " 00:00:00");
    var maxdate = new Date();
    maxdate.setHours(0,0,0,0);
    var diff = maxdate - mindate;
    diff = Math.round(diff/1000/60/60/24);
    
    $( "#datepicker" ).datepicker({
        changeMonth: true,
        changeYear: true,
        minDate: mindate,
        maxDate: maxdate,
        dateFormat: "yy-mm-dd"
    });
    $( "#datepicker" ).datepicker("setDate", mindate);
    
    $( "#slider" ).slider({
        min: 0,
        max: diff,
        slide: function( event, ui ) {
            var date = new Date(mindate);
            date.setDate(date.getDate() + ui.value);
            $( "#datepicker" ).datepicker("setDate", date);
            setMarkers(date);
        }
    });
    
    var mapOptions = {
        center: new google.maps.LatLng(50.562852, 4.391394),
        zoom: 8
    };
    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
}

function setMarkers(date) {
    var today = new Date();
    today.setHours(0,0,0,0);
    if (date >= new Date("1992-05-09") && date <= new Date())
    {
        constructMarker(50.8868997, 3.3494059, 1, 1, "Lowie", "Huyghe", "8792", "Desselgem", "België", "1992-05-09", "");
        constructMarker(50.562852, 4.391394, 1, 2, "Sander", "Huyghe", "8792", "Desselgem", "België", "1992-05-09", "");
    }
    else
    {
        deconstructMarker(23740);
        deconstructMarker(23741);
    }
}

function constructMarker(latitude, longitude, treeid, id, firstname, lastname, zipcode, placename, country, birthdate, deathdate) {
    if (markers[id] !== undefined) return;
    
    var myLatlng = new google.maps.LatLng(latitude, longitude);
    
    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        title:firstname + " " + lastname
    });
    markers[id] = marker;
    
    var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h1 id="firstHeading" class="firstHeading">' + firstname + " " + lastname + '</h1>'+
      '<div id="bodyContent">'+
      '<img src="http://assets.vop.tiwi.be/team12/staging/images/persons/' + treeid + "/" + id + '.jpg" height="200" />' +
      '<p>Place: ' + zipcode + ' ' + placename + '</p>' +
      '<p>Country: ' + country + '</p>' +
      '<p>Birthdate: ' + birthdate + '</p>' +
      '<p>Deathdate: ' + deathdate + '</p>' +
      '</div>'+
      '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: contentString
    });
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map,marker);
    });
    
}

function deconstructMarker(id) {
    if (markers[id] !== undefined)
    {
        markers[id].setMap(null);
        markers[id] = undefined;
    }
}