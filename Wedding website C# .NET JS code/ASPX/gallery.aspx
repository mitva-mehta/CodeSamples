<%@ Page Language="C#" MasterPageFile="~/project/MasterPage.master" AutoEventWireup="true"%>
<%@ Import Namespace="System.Web" %>
<%@ Import Namespace="System.IO" %>
<%@ Import Namespace="System.Net" %>
<%@ Import Namespace="edu.neu.ccis.rasala" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/galleryStyle.css"/>
    <script type="text/javascript" src="javascript/gallery.js"></script>
    
    <script type="text/javascript">
        function callMyFunction() {
            var apikey = '<%=KeyTools.GetKey(this, "flickrkey")%>';
            myFunction(apikey);
        }
    </script>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <script type="text/javascript">
        callMyFunction()
    </script>
    <div id="containerOuter">
        <!-- Place another div within this div and set the height of inner div in order to properly 
            position references and source code -->
    </div>
</asp:Content>