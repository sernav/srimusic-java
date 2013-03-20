package es.uclm.sri.sis.util;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.parsers.DOMParser;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class XMLUtil {

	private XMLUtil() {
	}

	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de un XML
	 * con SAX
	 * 
	 * @param nombre del XML a cargar
	 * @return Document
	 * */
	public Document leerDocumentoBySAX(final String xmlFileName)
			throws DocumentException {
		Document docu = null;
		SAXReader reader = new SAXReader();

		try {
			docu = reader.read(xmlFileName);
		} catch (DocumentException e) {
			e.getStackTrace();
		}
		return docu;
	}
	
	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de un XML
	 * con DOM
	 * 
	 * @param nombre del XML a cargar
	 * @return Document
	 * */
	public Document leerDocumentoByDOM(final String xmlFileName) {
		Document document = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(xmlFileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			document = (Document) documentBuilder
					.parse(fileInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de una cadena
	 * 
	 * @param nombre de la cargar
	 * @return Document
	 * */
	public Document leerCadenaXML(final String cadenaXML) {
		Document document = null;
		try {
			 DOMParser domParser = new DOMParser(); 
			 domParser.parse(new InputSource(new StringReader(cadenaXML)));
			 document = (Document) domParser.getDocument();
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return document;
	}

	/**
	 * Recogemos los atributos de un nodo
	 * 
	 * @param nodo referencia
	 * @return lista de atributos del nodo
	 * */
	public List<Attribute> getAtributosDNodo(Node node) {
		Element e = (Element) node;
		List<Attribute> list = e.attributes();

		return list;
	}

}
