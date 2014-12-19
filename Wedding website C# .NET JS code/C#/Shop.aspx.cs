using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;
using System.Xml;
using System.Xml.XPath;
using System.Linq;
using System.Xml.Linq;
using edu.neu.ccis.rasala;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Diagnostics;

namespace Shop 
{
    public class Amazon : System.Web.UI.Page
	{
        string totalpages;

        /*
         * This method collects all the parameters required to make a call to the
         * Amazon API, and sends them to the method that creates the URL to request
         * data from the Amazon API
         */
		protected void searchOnAmazon(Object sender, EventArgs e)
		{   
			// api key hiding
			var tildePath = Server.MapPath("~");
			var fullPath = Path.GetFullPath(Path.Combine(tildePath, "../keys/amznAccess.txt"));
			string ipstream;
			string ak = "";
			using (StreamReader sr = File.OpenText(fullPath))
			{
				ipstream = sr.ReadLine();
				while (ipstream != null)
				{
					ak += ipstream;
					ipstream = sr.ReadLine();
				}
			}
			tildePath = Server.MapPath("~");
			fullPath = Path.GetFullPath(Path.Combine(tildePath, "../keys/amznSecret.txt"));
			string sk = "";
			using (StreamReader sr = File.OpenText(fullPath))
			{
				ipstream = sr.ReadLine();
				while (ipstream != null)
				{
					sk += ipstream;
					ipstream = sr.ReadLine();
				}
			}

			// initializing other variables
			string keyWords = Request.QueryString["field1"];
			string index = Request.QueryString["field2"];
			string min = Request.QueryString["field3"];
			string max = Request.QueryString["field4"];
			string sort = Request.QueryString["field5"];
			string pg = "1";
			holder.InnerHtml = "";
			FindItems items = new FindItems();
			XElement xmldoc = items.getOutputXml(this, keyWords, index, ak, sk, pg, min, max, sort);

			countPages(xmldoc);
			parseXmlOnce(xmldoc);
		}

        /*
         * Method to request data from the API by providing the specific page number
         */
		protected void searchSpecificPage(object sender, CommandEventArgs e)
		{
			//api key hiding
			var tildePath = Server.MapPath("~");
			var fullPath = Path.GetFullPath(Path.Combine(tildePath, "../keys/amznAccess.txt"));
			string ipstream;
			string ak = "";
			using (StreamReader sr = File.OpenText(fullPath))
			{
				ipstream = sr.ReadLine();
				while (ipstream != null)
				{
					ak += ipstream;
					ipstream = sr.ReadLine();
				}
			}
			tildePath = Server.MapPath("~");
			fullPath = Path.GetFullPath(Path.Combine(tildePath, "../keys/amznSecret.txt"));
			string sk = "";
			using (StreamReader sr = File.OpenText(fullPath))
			{
				ipstream = sr.ReadLine();
				while (ipstream != null)
				{
					sk += ipstream;
					ipstream = sr.ReadLine();
				}
			}
			// initializing other variables
			string keyWords = Request.QueryString["field1"];
			string index = Request.QueryString["field2"];
			string min = Request.QueryString["field3"];
			string max = Request.QueryString["field4"];
			string sort = Request.QueryString["field5"];
			string pg;
			holder.InnerHtml = "";
			FindItems items = new FindItems();
			XElement xmldoc;
			xmldoc = items.getOutputXml(this, keyWords, index, ak, sk, e.CommandArgument.ToString(), min, max, sort); // page 1
			parseXmlOnce(xmldoc);            
		}

        /*
         * Method to count the total number of pages returned
         */
		protected void countPages(XElement xmldoc)
		{
			string amazonNamespace = "{http://webservices.amazon.com/AWSECommerceService/2011-08-01}";
			XElement _root = xmldoc;
			XElement errElement = null;
			string eMsg = null;
			errElement = _root.Element(amazonNamespace + "Items")
					.Element(amazonNamespace + "Request")
					.Element(amazonNamespace + "Errors");
			if (errElement != null)
			{
				eMsg = errElement.Element(amazonNamespace + "Error").Element(amazonNamespace + "Message").Value;
				renderonscreen(eMsg);
			}
			else
			{  
				foreach (XElement items in _root.Elements(amazonNamespace + "Items"))
				{
					totalpages = items.Element(amazonNamespace + "TotalPages").Value;
				}
			}
		}

		/*
         * Method to parse the xml of first 10 items returned by default
         */
		protected void parseXmlOnce(XElement xdoc)
		{
			string amazonNamespace = "{http://webservices.amazon.com/AWSECommerceService/2011-08-01}";
			XElement _root = xdoc;
			string mediumImageUrl = "testurl";
			string title = "testTitle";
			string amount = "testAmount";
			string FullPageUrl = "testurl";
			string brand = "testbrand";
			string totalPages = "0";

			// Check if any error message is present in response
			XElement errElement = null;
			string eMsg = null;
			errElement = _root.Element(amazonNamespace + "Items")
					.Element(amazonNamespace + "Request")
					.Element(amazonNamespace + "Errors");

            // if error, display error message to user
			if (errElement != null)
			{
				eMsg = errElement.Element(amazonNamespace + "Error").Element(amazonNamespace + "Message").Value;
				renderonscreen(eMsg);
			}
			else
			{   // else, render the information retrieved
				foreach (XElement items in _root.Elements(amazonNamespace + "Items"))
				{
					totalPages = items.Element(amazonNamespace + "TotalPages").Value;
					foreach (XElement item in items.Elements(amazonNamespace + "Item"))
					{
						if (item.Element(amazonNamespace + "DetailPageURL") != null)
							FullPageUrl = item.Element(amazonNamespace + "DetailPageURL").Value;
						if (item.Element(amazonNamespace + "MediumImage") != null)
						{
							if (item.Element(amazonNamespace + "MediumImage").Element(amazonNamespace + "URL") != null)
								mediumImageUrl =
                                item.Element(amazonNamespace + "MediumImage").Element(amazonNamespace + "URL").Value;
						}
						if (item.Element(amazonNamespace + "VariationSummary") != null)
						{
							XElement varSumm = item.Element(amazonNamespace + "VariationSummary");
							if (varSumm.Element(amazonNamespace + "LowestPrice") != null)
							{
								XElement varLowest = varSumm.Element(amazonNamespace + "LowestPrice");
								if (varLowest.Element(amazonNamespace + "FormattedPrice") != null)
								{
									amount = varLowest.Element(amazonNamespace + "FormattedPrice").Value;
								}
							}
						}
						if (item.Element(amazonNamespace + "Offers") != null)
						{
							XElement itemOffers = item.Element(amazonNamespace + "Offers");
							if (itemOffers.Element(amazonNamespace + "Offer") != null)
							{
								XElement itemOffr = itemOffers.Element(amazonNamespace + "Offer");
								if (itemOffr.Element(amazonNamespace + "OfferListing") != null)
								{
									XElement offerlist = itemOffr.Element(amazonNamespace + "OfferListing");
									if (offerlist.Element(amazonNamespace + "Price") != null)
									{
										XElement pricelist = offerlist.Element(amazonNamespace + "Price");
										if (pricelist.Element(amazonNamespace + "FormattedPrice") != null)
										{
											amount = pricelist.Element(amazonNamespace + "FormattedPrice").Value;
										}
									}
								}
							}
						}
						if (item.Element(amazonNamespace + "ItemAttributes") != null)
						{
							XElement itemAttr = item.Element(amazonNamespace + "ItemAttributes");
							if (itemAttr.Element(amazonNamespace + "Title") != null)
								title = itemAttr.Element(amazonNamespace + "Title").Value;
							if (itemAttr.Element(amazonNamespace + "ListPrice") != null)
							{
								XElement listPrice = itemAttr.Element(amazonNamespace + "ListPrice");
								if (listPrice.Element(amazonNamespace + "FormattedPrice") != null)
									amount = listPrice.Element(amazonNamespace + "FormattedPrice").Value;
							}
							if (itemAttr.Element(amazonNamespace + "Brand") != null)
								brand = itemAttr.Element(amazonNamespace + "Brand").Value;
						}
						renderonscreen(FullPageUrl, mediumImageUrl, title, amount, brand);  
					}
				}

			}
		}


		/*
         * This method displays error when no results are returned
         */
		public void renderonscreen(string msg)
		{
			holder.InnerHtml = holder.InnerHtml +
					"<h3>No Matching result found</h3>" + msg;
		}


		/*
         * This method will display product information on HTML page
         */
		public void renderonscreen(string pageurl, string imageurl, string title, string amount, string brand)
		{
			string imageTag, pageTag, FURL = null;
			if (imageurl == "testurl")
				imageTag = "<label>No Image available</label></br>";
			else
				imageTag = "<img class='shopImg' src='" + imageurl + "' alt='Image' /></br></br>";

			if (pageurl == "testurl")
				pageTag = "<label>Amazon Page URL not available</label></br>";
			else
				pageTag = "<a href='" + pageurl +
                          "' target='_blank'><img src=images/amazon-logo.gif alt='click to shop on amazon website' " +
                          "title='Click to shop on amazon website' id='buy_on_amazon'/></a>";

			if (title == "testTitle")
				title = "No Title found";
			else
				FURL = "title=" + title;

			if (amount == "testAmount")
				amount = "No price found";
			else
				FURL += "&amp;amount=" + amount;

			if (brand == "testbrand")
				brand = "Brand not found";
			else
				FURL += "&amp;Manfacturer=" + brand;

			holder.InnerHtml = holder.InnerHtml +
					"<div id='product_listing'>" +
					"<label id='prod_title'><b>" + title + "</b></label></br><br/>" +
					imageTag +
					"<label id='prod_price'><b>Price: " + amount + "</b></label></br></br>" +
					"<label id='prod_brand'><b>Brand: " + brand + "</b></label></br></br>" +
					"<label id='prod_pageTag'><b>Click to shop on " +pageTag + "</b></label><br/><br/><br/>" +
					"<hr/>" +
					"</div>";
		}

	}
}

