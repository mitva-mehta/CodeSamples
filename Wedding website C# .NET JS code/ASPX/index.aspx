<%@ Page Language="C#" MasterPageFile="~/project/MasterPage.master" AutoEventWireup="true"%>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" type="text/css" href="css/sliderStyle.css"/>
	<style type="text/css">a#vlb{display:none}</style>
	<script type="text/javascript" src="javascript/jquery.js"></script>
	<script type="text/javascript" src="javascript/slider.js"></script>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <div id="wowslider-container1">
	<div class="ws_images">
        <span><img src="images/beach-wedding.jpg" alt="beach-wedding" title="beach-wedding" id="wows0"/></span>
        <span><img src="images/bride.jpg" alt="bride" title="bride" id="wows1"/></span>
        <span><img src="images/disney_wedding.jpg" alt="disney_wedding" title="disney_wedding" id="wows2"/></span>
        <span><img src="images/honey-moon.jpg" alt="honey-moon" title="honey-moon" id="wows3"/></span>
        <span><img src="images/invitation.jpg" alt="invitation" title="invitation" id="wows4"/></span>
        <span><img src="images/traditions.jpg" alt="traditions" title="traditions" id="wows5"/></span>
        <span><img src="images/wedding-cake.jpg" alt="wedding-cake" title="wedding-cake" id="wows6"/></span>
        <span><img src="images/wedding-flowers.jpg" alt="wedding-flowers" title="wedding-flowers" id="wows7"/></span>
        <span><img src="images/wedding-photo.jpg" alt="wedding-photo" title="wedding-photo" id="wows8"/></span>
        <span><img src="images/wedding-ring.jpg" alt="wedding-ring" title="wedding-ring" id="wows9"/></span>
    </div>    <div class="ws_bullets">
        <div>
        <a href="#wows0" title="beach-wedding">1</a>
        <a href="#wows1" title="bride">2</a>
        <a href="#wows2" title="disney_wedding">3</a>
        <a href="#wows3" title="honey-moon">4</a>
        <a href="#wows4" title="invitation">5</a>
        <a href="#wows5" title="traditions">6</a>
        <a href="#wows6" title="wedding-cake">7</a>
        <a href="#wows7" title="wedding-flowers">8</a>
        <a href="#wows8" title="wedding-photo">9</a>
        <a href="#wows9" title="wedding-ring">10</a>
        </div>
    </div>

	</div>
	<script type="text/javascript" src="javascript/script.js"></script>
</asp:Content>
