package es.uclm.sri.sis.util;

import java.io.FileInputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.parsers.DOMParser;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtil {

	private XMLUtil() {
	}

	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de un XML con SAX
	 * 
	 * @param nombre del XML a cargar
	 * @return Document
	 * */
	public Document loadFicheroBySAX(final String xmlFileName)
			throws DocumentException {
		Document docu = null;
		SAXReader reader = new SAXReader();

		try {
			docu = (Document) reader.read(xmlFileName);
		} catch (DocumentException e) {
			e.getStackTrace();
		}
		return docu;
	}

	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de un XML con DOM
	 * 
	 * @param nombre del XML a cargar
	 * @return Document
	 * */
	public Document loadFicheroByDOM(final String xmlFileName) {
		Document document = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(xmlFileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			document = (Document) documentBuilder.parse(fileInputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public void leerDocumentoXML(Document doc) {

	}

	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de una cadena
	 * 
	 * @param nombre de la cargar
	 * @return Document
	 * */
	public Document leerFicheroXML(final String cadenaXML) {
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
	 * Leer un tag del fichero XML
	 * 
	 * @param Documento XML (Document)
	 * @param Nombre del tag que buscamos
	 * @param Atributo del tag
	 * 
	 * @return Valor del atributo
	 * */
	public String readFicheroXML(Document xmlDocument, String strRoot, String strAttr) {
		String value = "";
		Element rootElement = xmlDocument.getDocumentElement();
		NodeList elements = rootElement
				.getElementsByTagName(strRoot);
		for (int i = 0; i < elements.getLength(); i++) {
			Element element = (Element) elements.item(i);
			String attribute = element.getAttribute(strAttr);
			value = element.getChildNodes().item(0).getNodeValue();
		}
		return value;
	}
	
	/**
	 * A–adir un nuevo elemento al documento XML
	 * 
	 * @param Documento XML (Document)
	 * @param Nuevo elemento
	 * 
	 * @return Elemento introducido (Element)
	 * */
	public Element addElementoFicheroXML(Document xmlDocument, String newElement) {
		Element rootElement = xmlDocument.getDocumentElement();
		Element element = xmlDocument.createElement(newElement);
		rootElement.appendChild(element);
		
		return element;
	}
	
	
	/**
	 * Cargar valor a un atributo del fichero XML
	 * 
	 * @param Documento XML
	 * @param Valor del atributo
	 * */
	public void loadValorAttrXML(Document xmlDocument, String attrName, String attrValue) {
		Element rootElement = xmlDocument.getDocumentElement();
		Element element = (Element) rootElement.getAttributeNode(attrName);
		element.setAttribute(attrName, attrValue);
	}

}
