<%@ Page Language="C#" MasterPageFile="~/project/MasterPage.master" AutoEventWireup="true"%>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
    <link rel="stylesheet" type="text/css" href="css/contact.css"/>
    <link href='http://fonts.googleapis.com/css?family=Tangerine' rel='stylesheet' type='text/css'/>
    <link href='http://fonts.googleapis.com/css?family=Sevillana' rel='stylesheet' type='text/css'/>
    <link href='http://fonts.googleapis.com/css?family=Fjord One' rel='stylesheet' type='text/css'/>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
     <div class="contact-container">
        <div id="image-container">
           <center> 
            <h3>Get directions to our wedding store</h3>
            <script src="//www.gmodules.com/ig/ifr?url=http://hosting.gmodules.com/ig/gadgets/file/114281111391296844949/driving-directions.xml&amp;up_fromLocation=&amp;up_myLocations=Happily%20Ever%20After%2C%2075%20Saint%20Alphonsus%20Street%2C%20Boston%2C%20MA&amp;up_defaultDirectionsType=&amp;up_autoExpand=&amp;synd=open&amp;w=320&amp;h=55&amp;title=Directions+by+Google+Maps&amp;lang=en&amp;country=US&amp;border=%23ffffff%7C0px%2C1px+solid+%23004488%7C0px%2C1px+solid+%23005599%7C0px%2C1px+solid+%230077BB%7C0px%2C1px+solid+%230088CC&amp;output=js"></script>
            </center>
        </div>
        <div id="text-container">
          <h2 id="contact-title">Contact Us</h2>
          
          <h3>Address:</h3>
          <p>Happily Ever After, <br/>
             75 Saint Alphonsus Street,<br/>
             Boston, MA 02120
          </p>
          <hr/>
          <h3>Phone:</h3>
          <p>+1 (857) 123 456</p>
          <hr/>
          <h3>Email:</h3>
          <p>support@happilyeverafter.com</p>
                 
        </div>
    </div>
</asp:Content>