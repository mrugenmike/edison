
var map;
var markers=[];
var serverUrl = "http://localhost:8080/api/v1/nearby";


// Add a marker to the map and push to the array.
function addMarker(location,icon) {
    var image = {
        url: '/images/parking.gif',
        // This marker is 20 pixels wide by 32 pixels tall.
        size: new google.maps.Size(20, 32),
        // The origin for this image is 0,0.
        origin: new google.maps.Point(0,0),
        // The anchor for this image is the base of the flagpole at 0,32.
        anchor: new google.maps.Point(0, 32)
    };
    var infowindow = new google.maps.InfoWindow({
        content: "Total Vacant Spot:"+icon
    });
    var marker = new google.maps.Marker({
    position: location,
    map: map,
    title:"Total Vacant Spot:"+icon,
    icon:"/images/parking.png"
  });
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map,marker);
    });
  markers.push(marker);
}

// Sets the map on all markers in the array.
function setAllMap(map) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setAllMap(null);
}

// Shows any markers currently in the array.
function showMarkers() {
  setAllMap(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}
function initialize() {
  var mapOptions = {
    zoom: 14,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
  // try HTML5 geolocation
  if(navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = new google.maps.LatLng(position.coords.latitude,
          position.coords.longitude);
/*
*  {
 "coordinates": [
 -121.882644,
 37.332257
 ],
 "type": "Point",
 "vac": 12
 }
* */
    fetchNearestParkingLots(position.coords.latitude,position.coords.longitude).then(function(data){
      if(data){
        data.forEach(function(spot){
          var parkingMarker = new google.maps.LatLng(spot.coordinates[1],
              spot.coordinates[0]);
          addMarker(parkingMarker,spot.vac.toString())
        });

        var infowindow = new google.maps.InfoWindow({
          map: map,
          position: pos,
          content: 'Parking spots found'
        });

        map.setCenter(pos);
      }
    });

    }, function() {
      handleNoGeolocation(true);
    });
  } else {
    // browser doesn't support geolocation
    handleNoGeolocation(false);
  }
}

function handleNoGeolocation(errorFlag) {
  if (errorFlag) {
    var content = 'Error: The Geolocation service failed.';
  } else {
    var content = 'Error: Your browser doesn\'t support geolocation.';
  }

  var options = {
    map: map,
    position: new google.maps.LatLng(60, 105),
    content: content
  };

  var infowindow = new google.maps.InfoWindow(options);
  map.setCenter(options.position);
}

function fetchNearestParkingLots(lat,long){
  return $.ajax({
    url: serverUrl+"/"+long+"/"+lat,
  });
}
google.maps.visualRefresh = true;
google.maps.event.addDomListener(window, 'load', initialize);


