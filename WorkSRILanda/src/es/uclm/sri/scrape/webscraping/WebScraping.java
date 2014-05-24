package es.uclm.sri.scrape.webscraping;

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

import es.uclm.sri.sis.utilidades.Utils;

/**
 * Para el scraping de cualquier de las web que siga la plantilla indicada.
 * Se utiliza mediante la l’nea de comando.
 * Necesita la librer’a Tidy, la cual nos proporciona el parseo de la web.
 * 
 * @author Sergio Navarro
 * */
public class WebScraping {

	/**
	 * Plantilla por defecto
	 */
	public static final String XSLT_TEMPLATE_URL = "WebScrapingTemplate.xsl";

	/**
	 * Procesando la l’nea de comandos.
	 * 
	 * @param args args[0]: URL con la web HTML. args[1] Localizaci—n de la plantilla XSLT
	 * @since 0.1
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		URL url = null;
		InputStream input = null;
		OutputStream out = null;
		StreamSource xsltSource = null;
		StreamSource source = null;
		// Se genera el fichero œnico por usuario
		File xmlOutFile = new File(System.getProperty("user.home")
				+ System.getProperty("file.separator")
				+ System.getProperty("user.name") + "-"
				+ WebScraping.class.getName() + "-"
				+ System.currentTimeMillis() + ".xml");
		try {
			if ((args != null) && (args.length == 1) && (args[0] != null)) {
				url = new java.net.URL(args[0]);
			} else {
				String strUrl = Utils.leerLineaComando("Introduce la url completa de la web");
				url = new java.net.URL(strUrl);
			}
			input = url.openStream();
			out = new FileOutputStream(xmlOutFile);
			// Recoge el HTML de la web y le aplica la plantilla XHTML
			Tidy tidy = new Tidy();
			/*
			 * Configuraci—n de los tags para convertir el documento y analizarlo
			 * con la plantilla XSLT
			 */
			tidy.setTidyMark(false);
			tidy.setDocType("omit");
			// Para secuencias de caracteres extra–os:
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
			// Recoge el XML y lo convierte take the XML y lo convierte usando el sylesheet
			source = new StreamSource(xmlOutFile);
			if ((args != null) && (args.length == 2) && (args[1] != null)) {
				xsltSource = new StreamSource(args[1]);
			} else {
				xsltSource = new StreamSource(Class.class
										.getResourceAsStream(XSLT_TEMPLATE_URL));
				
			}
			// El resultado se almacena
			StreamResult reportOut = new StreamResult(System.out); 
			TransformerFactory tFactory = TransformerFactory.newInstance();

 			Transformer trans = tFactory.newTransformer(xsltSource);

			// Transformaci—n
			trans.transform(source, reportOut);

		} catch (Exception exp) {
			throw exp;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignore) {

				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException ignore) {

				}
			}
			if ((source != null) && (source.getInputStream() != null)) {
				try {
					source.getInputStream().close();
				} catch (IOException ignore) {

				}
			}
			if ((xsltSource != null) && (xsltSource.getInputStream() != null)) {
				try {
					xsltSource.getInputStream().close();
				} catch (IOException ignore) {

				}
			}
			xmlOutFile.deleteOnExit();
		}
	}

}
