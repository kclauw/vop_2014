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
    var mindate;
    var maxdate;
    var today = new Date();
    today.setHours(0,0,0,0);
    
    if (typeof persons !== "undefined") {
        
        for (i = 0; i < persons.length; i++) {
            var person = persons[i];
            
            person[9] = (person[9] === "")?today:new Date(person[9] + " 00:00:00");
            person[10] = (person[10] === "")?today:new Date(person[10] + " 00:00:00");
            
            
            if (typeof mindate === "undefined")
            {
                mindate = person[9];
            }
            else if (person[9] < mindate)
            {
                mindate = person[9];
            }
            if (typeof maxdate === "undefined")
            {
                maxdate = person[10];
            }
            else if (person[10] > maxdate)
            {
                maxdate = person[10];
            }
        }
    }
    
    var diff = maxdate - mindate;
    diff = Math.round(diff/1000/60/60/24);
    
    $( "#datepicker" ).datepicker({
        changeMonth: true,
        changeYear: true,
        minDate: mindate,
        maxDate: maxdate,
        dateFormat: "yy-mm-dd",
        onSelect: function(dateText) {
            var date = new Date(dateText + " 00:00:00");
            setMarkers(date);
            updateSlider(mindate, date);
        }
    });
    $( "#datepicker" ).datepicker("setDate", mindate);
    
    $( ".wrapper > .slider" ).slider({
        min: 0,
        max: diff,
        slide: function( event, ui ) {
            var date = new Date(mindate);
            date.setDate(date.getDate() + ui.value);
            $( "#datepicker" ).datepicker("setDate", date);
            setMarkers(date);
        }
    });
    
    $( "#timecontrol > .slider" ).slider({
        min: 1,
        max: 365 * 3
    });
    $( "#timecontrol > .slider" ).slider({
        value: $( "#timecontrol > .slider" ).slider("option", "min")
    });
    
    var intervalID = -1;
    $('#timecontrol #play').click(function() {
        if (intervalID < 0)
        {
            //Start timer
            $(this).attr("src", "./images/pause.png");
            intervalID = window.setInterval(function() {
                var date = new Date($('#datepicker').datepicker('getDate'));
                
                if (date >= maxdate) {
                    console.log("zdzdzqdzd");
                    $('#timecontrol #play').attr("src", "./images/play.png");
                    window.clearInterval(intervalID);
                    intervalID = -1;
                } else {
                    date.setDate(date.getDate() + $( "#timecontrol > .slider" ).slider("option", "value"));
                    if (date > maxdate)
                        date = maxdate;

                    $( "#datepicker" ).datepicker("setDate", date);
                    setMarkers(date);
                }
                updateSlider(mindate, date);
            }, 50);
        } else {
            //Stop timer
            $('#timecontrol #play').attr("src", "./images/play.png");
            window.clearInterval(intervalID);
            intervalID = -1;
        }
    });
    
    var mapOptions = {
        center: new google.maps.LatLng(50.562852, 4.391394),
        zoom: 8
    };
    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
    
    setMarkers(mindate);
}

function setMarkers(date) {
    
    for (i = 0; i < persons.length; i++) {
        var person = persons[i];
        if (person[9] <= date && date <= person[10]) {
            constructMarker(person[0], person[1], person[2], person[3], person[4], person[5], person[6], person[7], person[8], person[9], person[10]);
        } else {
            deconstructMarker(person[3]);
        }
    }
    
}

function constructMarker(latitude, longitude, treeid, id, firstname, lastname, zipcode, placename, country, birthdate, deathdate) {
    if (markers[id] !== undefined) return;
    
    var myLatlng = new google.maps.LatLng(longitude, latitude);
    
    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        title:firstname + " " + lastname
    });
    markers[id] = marker;
    
    var image = 'http://assets.vop.tiwi.be/team12/staging/images/persons/' + treeid + "/" + id + '.jpg';
    var image_found = false;
    $.get(image).done(function() { 
        image = '<img src="' + image + '" height="200" />';
        image_found = true;
    });
    if (!image_found)
        image = '';
    
    var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h1 id="firstHeading" class="firstHeading">' + firstname + " " + lastname + '</h1>'+
      '<div id="bodyContent">'+
      image +
      '<p>Place: ' + zipcode + ' ' + placename + '</p>' +
      '<p>Country: ' + country + '</p>' +
      '<p>Birthdate: ' + dateToString(birthdate) + '</p>' +
      '<p>Deathdate: ' + dateToString(deathdate) + '</p>' +
      '</div>'+
      '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: contentString
    });
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map,marker);
    });
    
}

function dateToString(date) {
    var year = '' + date.getFullYear();
    var month = '' + date.getMonth();
    var day = '' + date.getDate();
    
    if (month.length === 1)
        month = '0' + month;
    if (day.length === 1)
        day = '0' + day;
    
    return year + '-' + month + '-' + day;
}

function deconstructMarker(id) {
    if (markers[id] !== undefined)
    {
        markers[id].setMap(null);
        markers[id] = undefined;
    }
}

function updateSlider(mindate, date) {
    var diff = date - mindate;
    diff = Math.round(diff/1000/60/60/24);
    
    $( ".wrapper > .slider" ).slider({
        value: diff
    });
}