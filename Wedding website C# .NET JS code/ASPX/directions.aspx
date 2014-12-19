<%@ Page Language="C#" MasterPageFile="~/project/MasterPage.master" AutoEventWireup="true" %>
<%@ Import Namespace="System.Web" %>
<%@ Import Namespace="System.IO" %>
<%@ Import Namespace="System.Net" %>
<%@ Import Namespace="edu.neu.ccis.rasala" %>

<asp:Content ContentPlaceHolderID="head" runat="server">
    <link href='http://fonts.googleapis.com/css?family=Quintessential' rel='stylesheet' type='text/css'/>
    <link rel="stylesheet" type="text/css" href="css/mapsStyle.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" ></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
    
    <script type="text/javascript" src="http://j.maxmind.com/app/geoip.js"></script>
    <script type="text/javascript" src="javascript/directions.js"></script>
    
    <script type="text/javascript">
        var apiKey = '<%=KeyTools.GetKey(this, "googlePlaces")%>';

        function callfetchLL() {
            fetchLL(apiKey);
        }
    </script>    
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <h2 id="map_title">Find the perfect wedding shop around you</h2>
    <div class= "content-outer">
        
        <div id="map_container">
            <div class="map" id="map">
            </div>
        </div>
        <div id="places_container">
        <center>
        
        <div class="places_container_top">
            <i style="font-size: 18px; color: antiquewhite;">Click 'find shops' to find wedding shops around you</i><br/>
            <input type="button" id="Button" class="places" value="Find Shops" onclick="callfetchLL()" />
        </div>
          </center>
            <br />
            <center>
            <div class="container-listOfPlaces">
            <div class="listOfPlaces" id="listOfPlaces">
            </div>
            </div>
            </center>
        </div>
    </div>
</asp:Content>