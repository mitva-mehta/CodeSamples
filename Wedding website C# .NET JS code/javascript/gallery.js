/*
 * Javascript file containing all the methods required for the 'Gallery'
 * feature on the project website
 */

        /*
         * Put the jquery code inside a function that can be called on onload of
         * dynamically created script tag
         */
        function onLoad() {
            $(document).ready(function ($) {

                $('.lightbox_trigger').click(function (e) {

                    //prevent default action (hyperlink)
                    e.preventDefault();

                    //Get clicked link href
                    var image_href = $(this).attr("src");

                    /*
                    If the lightbox window HTML already exists in document,
                    change the img src to to match the href of whatever link was clicked
    
                    If the lightbox window HTML doesn't exists, create it and insert it.
                    (This will only happen the first time around)
                    */

                    if ($('#lightbox').length > 0) { // #lightbox exists

                        //place href as img src value
                        $('#content').html('<img src="' + image_href + '" />');

                        //show lightbox window - you could use .show('fast') for a transition
                        $('#lightbox').show();
                    }

                    else { //#lightbox does not exist - create and insert (runs 1st time only)

                        //create HTML markup for lightbox window
                        var lightbox =
                        '<div id="lightbox">' +
                            '<p style="text-align: center; font-size: 20px;">Click to close</p>' +
                            '<div id="content">' + //insert clicked link's href into img src
                                '<img src="' + image_href + '" />' +
                            '</div>' +
                        '</div>';

                        //insert lightbox HTML into page
                        $('body').append(lightbox);
                    }

                });

                //Click anywhere on the page to get rid of lightbox window
                $('#lightbox').live('click', function () { //must use live, as the lightbox element is inserted into the DOM
                    $('#lightbox').hide();
                });

            });
        }

        /*
         * This function extracts the search keyword passed in the query string
         * from the master page
         */
        function querySt(Key) {
            var url = window.location.href;
            KeysValues = url.split(/[\?&]+/);
            for (i = 0; i < KeysValues.length; i++) {
                KeyValue = KeysValues[i].split("=");
                if (KeyValue[0] == Key) {
                    return KeyValue[1];
                }
            }
        }

        /*
         * This function creates a URL using the API key and search keyword, along with
         * other required parameters to call the Flickr API, and appends this URL to a 
         * script tag on page load
         */
        function myFunction(apikey) {
            
            var value = querySt("field1").split("%20");
            var str = value[0] + " " + value[1];
            
            //using a regexp to validate the search keyword
            var patt = new RegExp("([-A-Za-z0-9 ,.].*?)");

            //if keyword matches regex, then build the url, else display error message          
            if (str.match(patt)) {
                var url = "http://api.flickr.com/services/rest/";
                url += "?method=flickr.photos.search";
                url += "&api_key=" + apikey;
                url += "&text=" + str;
                url += "&sort=relevance";
                url += "&per_page=50";
                url += "&format=json";
                s = document.createElement('script'); // create script element
                s.onload = onLoad;
                s.src = url;
                document.body.appendChild(s);
            } else {
                //display error message
                window.alert("Please enter a valid search keyword");
            }
        }

        /*
         * Function for parsing JSON response from the FLickr API, and creating HTML from the
         * parsed response
         */
        function jsonFlickrApi(rsp) {
            window.rsp = rsp;
            var p = "";
            p += '<div id="container"><p><i>Click on the images to view them using lightbox effect</i></p>';
            p += '<ul class="album">';
            for (var i = 0; i < 12; i++) {
                photo = rsp.photos.photo[i];
                img_url = "http://farm" + photo.farm + ".static.flickr.com/" + photo.server + "/"
                        + photo.id + "_" + photo.secret + ".jpg";

                p += '<li>';
                p += '<a href="#image-' + (i + 1) + '">';
                p += '<img src="' + img_url + '" class="lightbox_trigger" alt="image'
                        + (i + 1) + '" width=200px height=200px>';
                p += '<span></span>';
                p += '</a>';
                p += '</li>';
            }
            p += '</ul></div>';
    
            document.getElementById("containerOuter").innerHTML = p;
        }