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
using System.Diagnostics;
namespace api.amazon
{

    public class FindItems
    {
        // Initializing class variables necessary for calling web service
        string AWS_ACCESS_KEY_ID_IDENTIFIER;
        string AWS_SECRET_KEY_IDENTIFIER;
        private const string NAMESPACE = "http://webservices.amazon.com/AWSECommerceService/2013-04-04";
        private const string DESTINATION = "ecs.amazonaws.com";
        private const string ENDPT = "http://ecs.amazonaws.com/onca/xml";
        private XmlNodeList items;
        
        /*
         * Function to return XmlDocument using GET method
         */
        public XElement getOutputXml(Page page, string keywords, string cat, string key1, string key2, string pgnum, string min, string max, string sort)
        {
            AWS_ACCESS_KEY_ID_IDENTIFIER = key1;
            AWS_SECRET_KEY_IDENTIFIER = key2;
            SignedRequestHelper helper = new SignedRequestHelper(AWS_ACCESS_KEY_ID_IDENTIFIER, AWS_SECRET_KEY_IDENTIFIER, DESTINATION);
            String reqUrl;
            XElement xmldoc = null;
            String[] Keywords = new String[] { keywords, };

            // Creating request string
            foreach (String keyword in Keywords)
            {
                String reqString = "Service=AWSECommerceService"
                    + "&Version=2013-04-04"
                    + "&Operation=ItemSearch"
                    + "&AssociateTag=net4ccsneue02-20"
                    + "&SearchIndex=" + cat
                    + "&ItemPage=" + pgnum
                    + "&MinimumPrice=" + min
                    + "&MaximumPrice=" + max
                    + "&Sort=" + sort
                    + "&ResponseGroup=ItemAttributes,Images,Variations,Offers"
                    + "&MerchantId=All"
                    + "&Keywords=" + keyword;

                // Calling sign method to sign the request url with signature
                reqUrl = helper.Sign(reqString);

                // Sending HTTP request to web service and returning response as Xml document
                try
                {
                    WebRequest req = HttpWebRequest.Create(reqUrl);
                    WebResponse resp = req.GetResponse();

                    XmlDocument doc = new XmlDocument();
                    Stream responseStream = resp.GetResponseStream();
                    doc.Load(responseStream);
                    XmlElement root = doc.DocumentElement;
                    string xml = root.InnerXml;
                    string fxml = "<root>" + xml + "</root>";
                    TextReader tr = new StringReader(fxml);
                    XElement lroot = XElement.Load(tr);
                    return lroot;
                }
                catch (Exception e)
                {
                    System.Console.WriteLine("Caught Exception: " + e.Message);
                    System.Console.WriteLine("Stack Trace: " + e.StackTrace);
                }
                return xmldoc;
            }
            return xmldoc;
        }

        public String getReqUrl(Page page, string keywords, string cat, string key1, string key2)
        {
            AWS_ACCESS_KEY_ID_IDENTIFIER = key1;
            AWS_SECRET_KEY_IDENTIFIER = key2;

            // Initializing variables
            SignedRequestHelper helper = new SignedRequestHelper(AWS_ACCESS_KEY_ID_IDENTIFIER, AWS_SECRET_KEY_IDENTIFIER, DESTINATION);
            String reqUrl;
            XElement xmldoc = null;
            byte[] bArray;

            //Store all the keywords to be searched in string array
            String[] Keywords = new String[] { keywords, };

            // Creating request string
            foreach (String keyword in Keywords)
            {
                String reqString = "Service=AWSECommerceService"
                    + "&Version=2013-04-04"
                    + "&Operation=ItemSearch"
                    + "&AssociateTag=net4ccsneue02-20"
                    + "&SearchIndex=" + cat
                    + "&MinimumPrice=10000"
                    + "&MaximumPrice=50000"
                    + "&Sort=relevancerank"
                    + "&ResponseGroup=ItemAttributes, Variations, Offers"
                    + "&MerchantId=All"
                    + "&Keywords=" + keyword;

                // Calling sign method to sign the request url with signature
                reqUrl = helper.Sign(reqString);

                // Create POST data and convert it to a byte array.
                int endptlen = ENDPT.Length;
                string cont = reqUrl.Substring(endptlen + 1);
                bArray = Encoding.UTF8.GetBytes(cont);

                // Sending HTTP POST request to web service and returning response will be an Xml document
                try
                {
                    // Create a request using a URL that can receive a post request.
                    WebRequest req = HttpWebRequest.Create(ENDPT);
                    req.Method = "POST";
                    req.ContentLength = bArray.Length;
                    req.ContentType = "application/x-www-form-urlencoded";

                    // Get the request stream.
                    Stream data_stream = req.GetRequestStream();
                    data_stream.Write(bArray, 0, bArray.Length);
                    data_stream.Close();

                    // get the web response
                    WebResponse resp = req.GetResponse();

                    XmlDocument doc = new XmlDocument();
                    Stream responseStream = resp.GetResponseStream();
                    doc.Load(responseStream);
                    XmlElement root = doc.DocumentElement;
                    string xml = root.InnerXml;
                    string fxml = "<root>" + xml + "</root>";
                    TextReader tr = new StringReader(fxml);
                    XElement lroot = XElement.Load(tr);
                    return fxml;
                }
                catch (Exception e)
                {
                    System.Console.WriteLine("Exception Found : " + e.Message);
                    System.Console.WriteLine("Stack Trace: " + e.StackTrace);
                }
                return reqUrl;
            }
            return "not init";
        }
    }
}