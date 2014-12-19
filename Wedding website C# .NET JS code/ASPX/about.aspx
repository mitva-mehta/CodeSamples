<%@ Page Language="C#" MasterPageFile="~/project/MasterPage.master" AutoEventWireup="true"%>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" type="text/css" href="css/aboutUs.css"/>
    <link href='http://fonts.googleapis.com/css?family=Tangerine' rel='stylesheet' type='text/css'/>
    <link href='http://fonts.googleapis.com/css?family=Sevillana' rel='stylesheet' type='text/css'/>
    <link href='http://fonts.googleapis.com/css?family=Fjord One' rel='stylesheet' type='text/css'/>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
     <div class="about-container">
        <div id="image-container">
            <img src="images/happilyEverAfter.jpg" id="HEA"/>
        </div>
        <div id="text-container">
            <h3 id="about-title">About Us</h3>
            <p style="text-align: center">Marriages are made in heaven ...<br/>
                ... but the shopping happens on earth</p>

            <p>Extraordinary weddings don't just happen, they are planned. We know how stressful planning a wedding can be. 
              The amount of thought that needs to be put behind the smallest of details can leave you exhausted before a wedding.
            <p>

            <p>We strive to offer you a place where you can browse all things related to a wedding
            in the comfort of your home. 
            </p>

            <p>Because in your dreams, every detail matters. And we want you to live 
                <i style="font-family: 'Sevillana'; font-size: 22px; font-weight: bold;">Happily Ever After</i>
            </p>
        </div>
    </div>
</asp:Content>



