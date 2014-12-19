/* 
 * Javascript file containing all the methods required for the 'Directions'
 * feature on the project website
 */

    /*
     * This function is used to create a map and display the generated data 
     * on the map i.e display wedding related shops around the user's location
     * on the map
     */
    function ShowOnMap(locations, nameOfPlaces, locationPlace) {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 10,
            center: new google.maps.LatLng(locations[0][0], locations[0][1]),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });
        var styles = [
          {
              stylers: [
              { hue: "#b09572" },
              { saturation: -20 }
              ]
          }, {
              featureType: "road",
              elementType: "geometry",
              stylers: [
              { lightness: 100 },
              { visibility: "simplified" }
              ]
          }, {
              featureType: "road",
              elementType: "labels",
              stylers: [
              { visibility: "off" }
              ]
          }
        ];
            
        //add the style defined above to the map
        map.setOptions({ styles: styles });
        var infowindow = new google.maps.InfoWindow();

        var marker, i;
        for (i = 0; i < locations.length; i++) {
            marker = new google.maps.Marker({
                position: new google.maps.LatLng(locations[i][0], locations[i][1]),
                map: map,
                title: "welcome",
            });
                
            //show information when marker is clicked
            google.maps.event.addListener(marker, 'click', (function (marker, i) {
                return function () {
                    infowindow.setContent(nameOfPlaces[i] + '<br/>' + locationPlace[i]);
                    infowindow.open(map, marker);
                }
            })(marker, i));
        }
    }


    /*
     * This function fetches the latitude and longitude of the user's location
     */
    function fetchLL(apikey) {
        var lat = geoip_latitude();
        var long = geoip_longitude();
        createURL(lat, long, apikey);
    }

    /*
     * This function constructs a URL containing the url of the proxy, url of google places
     * API, the latitude, longitude, the keyword for search, and the API key. The constructed
     * URL will be used to make a call to the Google Places API
     */
    function createURL(lat, lon, apikey) {

        var proxyUrl = 'http://net4.ccs.neu.edu/home/rasala/simpleproxy.aspx?url="';
        var base = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=';
        var latitude = lat;
        var longitude = lon;
        var param1 = '&keyword=Wedding&radius=30000&types=store&sensor=false&key=';
           
        var finalUrl = proxyUrl + base + latitude + ',' + longitude + param1 + apiKey + '"';
        getJsonData(finalUrl);
    }

    /*
     * Function for fetching JSON response from Google Places API using the constructed URL
     */
    function getJsonData(finalUrl) {
        $.ajax({
            url: finalUrl,
            dataType: "json",
            success: getPlaces,
            error: function () { alert(final) }
        });
    }

    /*
     * Function for parsing the JSON data returned in response from the API
     */
    function getPlaces(data) {
        $("div.listOfPlaces").empty();
        var latitude = [];
        var longitude = [];
        var namePlace = [];
        var locationPlace = [];
        var places = data.results;
        
        $.each(places, function (index, temp) {
            var name = temp.name;
            var location = temp.vicinity;
            var geometry = temp.geometry;
            var displayPlaces = '<div class="storeDetails">' + name + '<br/>' + location
                                + '</div>' + '<br/> <br/>';
            $("div.listOfPlaces").append(displayPlaces);
            $.each(geometry, function (index, temp1) {
                var lat = temp1.lat;
                var lon = temp1.lng;
                   
                latitude.push(lat);
                longitude.push(lon);
            });
            namePlace.push(name);
            locationPlace.push(location);
        });
        
        var LatLon = new Array();
        for (var i = 0; i < latitude.length; i++) {
            LatLon[i] = [latitude[i], longitude[i]];
        }
        ShowOnMap(LatLon, namePlace, locationPlace);
    }

    /*
     * Creating default location on the map
     */
    $(document).ready(function () {

        var LatLon = new Array();
        LatLon[0] = [42.334483, -71.100829];
        var nameOfPlaces = new Array();
        nameOfPlaces[0] = "Happily Ever After";
        var locationPlace = new Array();
        locationPlace[0] = "75 Saint Alphonsus Street, Boston, MA 02120";
   
        ShowOnMap(LatLon, nameOfPlaces, locationPlace);
    });