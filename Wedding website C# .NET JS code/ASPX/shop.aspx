<%@ Page Language="C#" MasterPageFile="~/project/MasterPage.master" CodeBehind="Shop.aspx.cs"
         Inherits = "Shop.Amazon" AutoEventWireup="true"%>
<%@ Import Namespace="api.amazon" %>
<%@ Import Namespace="System.Xml" %>
<%@ Import Namespace="System.Xml.XPath" %>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="System.Linq" %>
<%@ Import Namespace="System.Data.Linq" %>
<%@ Import Namespace="System" %>
<%@ Import Namespace="System.Web" %>
<%@ Import Namespace="System.Web.UI" %>
<%@ Import Namespace="System.Web.UI.WebControls" %>
<%@ Import Namespace="System.Net" %>
<%@ Import Namespace="System.IO" %>
<%@ Import Namespace="edu.neu.ccis.rasala" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" type="text/css" href="css/shopStyle.css"/>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <center>
    <div id="products_container">
        <asp:Label ID="runLabel" runat="server" OnLoad="searchOnAmazon"/>
        <br/>
        <asp:LinkButton ID="LinkButton1" CssClass='page_num' runat="server" CommandArgument="1" OnCommand="searchSpecificPage" Text="1"/>
        <asp:LinkButton ID="LinkButton2" CssClass='page_num' runat="server" CommandArgument="2" OnCommand="searchSpecificPage" Text="2"/>
        <asp:LinkButton ID="LinkButton3" CssClass='page_num' runat="server" CommandArgument="3" OnCommand="searchSpecificPage" Text="3"/>
        <asp:LinkButton ID="LinkButton4" CssClass='page_num' runat="server" CommandArgument="4" OnCommand="searchSpecificPage" Text="4"/>
        <asp:LinkButton ID="LinkButton5" CssClass='page_num' runat="server" CommandArgument="5" OnCommand="searchSpecificPage" Text="5"/>
        <asp:LinkButton ID="LinkButton6" CssClass='page_num' runat="server" CommandArgument="6" OnCommand="searchSpecificPage" Text="6"/>
        <asp:LinkButton ID="LinkButton7" CssClass='page_num'  runat="server" CommandArgument="7" OnCommand="searchSpecificPage" Text="7"/>
        <asp:LinkButton ID="LinkButton8" CssClass='page_num' runat="server" CommandArgument="8" OnCommand="searchSpecificPage" Text="8"/>
        <asp:LinkButton ID="LinkButton9" CssClass='page_num' runat="server" CommandArgument="9" OnCommand="searchSpecificPage" Text="9"/>
        <asp:LinkButton ID="LinkButton10" CssClass='page_num' runat="server" CommandArgument="10" OnCommand="searchSpecificPage" Text="10"/>
        <br/>

        <asp:Label ID="Label1" runat="server"></asp:Label>
        <hr />
        <label runat="server" id="holder">
        </label>
        
              <asp:LinkButton ID="btnOne" CssClass='page_num' runat="server" CommandArgument="1" OnCommand="searchSpecificPage" Text="1"/>
              <asp:LinkButton ID="btnTwo" CssClass='page_num'  runat="server" CommandArgument="2" OnCommand="searchSpecificPage" Text="2"/>
              <asp:LinkButton ID="btnThree" CssClass='page_num' runat="server" CommandArgument="3" OnCommand="searchSpecificPage" Text="3"/>
              <asp:LinkButton ID="btnFour" CssClass='page_num' runat="server" CommandArgument="4" OnCommand="searchSpecificPage" Text="4"/>
              <asp:LinkButton ID="btnFive" CssClass='page_num' runat="server" CommandArgument="5" OnCommand="searchSpecificPage" Text="5"/>
              <asp:LinkButton ID="btnSix" CssClass='page_num' runat="server" CommandArgument="6" OnCommand="searchSpecificPage" Text="6"/>
              <asp:LinkButton ID="btnSeven" CssClass='page_num'  runat="server" CommandArgument="7" OnCommand="searchSpecificPage" Text="7"/>
              <asp:LinkButton ID="btnEight" CssClass='page_num' runat="server" CommandArgument="8" OnCommand="searchSpecificPage" Text="8"/>
              <asp:LinkButton ID="btnNine" CssClass='page_num' runat="server" CommandArgument="9" OnCommand="searchSpecificPage" Text="9"/>
              <asp:LinkButton ID="btnTen" CssClass='page_num' runat="server" CommandArgument="10" OnCommand="searchSpecificPage" Text="10"/>
                      
        <br/>    
        <br />
        Disclaimer: All products that appear on this website are retrieved from amazon.com using amazon product advertising API."
        <hr />
    </div>
    </center>
</asp:Content>