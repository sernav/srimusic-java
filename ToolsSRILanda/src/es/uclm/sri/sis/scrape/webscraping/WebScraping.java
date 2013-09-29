package es.uclm.sri.sis.scrape.webscraping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.tidy.Tidy;

public class WebScraping {

	/**
	 * URL para analizar
	 */
	public static final String URL_ROCKDELUXE_ALBUMS = "http://www.rockdelux.com/discos/albumes.html";

	/**
	 * Plantilla por defecto
	 */
	public static final String XSLT_TEMPLATE_URL = "WebScrapingTemplate.xsl";

	/**
	 * Command line processing
	 * 
	 * @param args args[0]: URL with the web HTML. args[1] Location of the XSLT
	 * @since 0.1
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		URL url = null;
		InputStream input = null;
		OutputStream out = null;
		StreamSource xsltSource = null;
		StreamSource source = null;
		// Create a file that is unique among runs (sort of)
		File xmlOutFile = new File(System.getProperty("user.home")
				+ System.getProperty("file.separator")
				+ System.getProperty("user.name") + "-"
				+ WebScraping.class.getName() + "-"
				+ System.currentTimeMillis() + ".xml");
		try {
			if ((args != null) && (args.length == 1) && (args[0] != null)) {
				url = new java.net.URL(args[0]);
			} else {
				url = new java.net.URL(URL_ROCKDELUXE_ALBUMS);
			}
			input = url.openStream();
			out = new FileOutputStream(xmlOutFile);
			// Get the HTML and convert it to XHTML
			Tidy tidy = new Tidy();
			/*
			 * Very important. Set the proper flags so we convert the document
			 * from HTML to XHTML. Then we can proceed to parse it with an XSLT
			 */
			tidy.setTidyMark(false);
			tidy.setDocType("omit");
			// Uncomment only if you know your XML has not weird escape sequences
			// tidy.setCharEncoding(Configuration.UTF8);
			tidy.setAltText("");
			tidy.setFixBackslash(true);
			tidy.setFixComments(true);
			tidy.setXmlPi(true);
			tidy.setQuoteAmpersand(true);
			tidy.setQuoteNbsp(true);
			tidy.setNumEntities(true);
			tidy.setXmlOut(true);
			tidy.setWraplen(999);
			tidy.setWriteback(true);
			tidy.setQuoteMarks(true);
			tidy.setLogicalEmphasis(true);
			tidy.setEncloseText(true);
			tidy.setHideEndTags(true);
			tidy.setShowWarnings(false);
			tidy.setQuiet(true);
			tidy.setXHTML(true);

			tidy.parse(input, out);
			out.close();
			// take the XML and convert it to a text report using the sylesheet
			source = new StreamSource(xmlOutFile);
			if ((args != null) && (args.length == 2) && (args[1] != null)) {
				xsltSource = new StreamSource(args[1]);
			} else {
				xsltSource = new StreamSource(Class.class
										.getResourceAsStream(XSLT_TEMPLATE_URL));
				
			}
			// The report  will go to STDOUT
			StreamResult reportOut = new StreamResult(System.out); 
			TransformerFactory tFactory = TransformerFactory.newInstance();

 			Transformer trans = tFactory.newTransformer(xsltSource);

			// Do the transformation
			trans.transform(source, reportOut);

		} catch (Exception exp) {
			throw exp;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignore) {
					// Ignore
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException ignore) {
					// Ignore
				}
			}
			if ((source != null) && (source.getInputStream() != null)) {
				try {
					source.getInputStream().close();
				} catch (IOException ignore) {
					// Ignore
				}
			}
			if ((xsltSource != null) && (xsltSource.getInputStream() != null)) {
				try {
					xsltSource.getInputStream().close();
				} catch (IOException ignore) {
					// Ignore
				}
			}
			xmlOutFile.deleteOnExit();
		}
	}

}
