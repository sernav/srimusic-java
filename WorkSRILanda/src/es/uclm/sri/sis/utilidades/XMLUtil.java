package es.uclm.sri.sis.utilidades;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.parsers.DOMParser;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * <code>XMLUtil</code> clase de utilidades para tratar XMLs
 * 
 * @author Sergio Navarro
 * */
public class XMLUtil {

	private XMLUtil() {
	}

	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de un XML con SAX
	 * 
	 * @param nombre del XML a cargar
	 * @return Document
	 * */
	public static Document loadFicheroBySAX(final String xmlFileName)
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
	public static Document loadFicheroByDOM(final String xmlFileName) {
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
	
	/**
	 * Elemento root de fichero XML
	 * 
	 *  @param Document
	 *  @param Root
	 *  
	 *  @return Element
	 **/
	public static Element getRootElement (Document xmlDocument, String strRoot) {
		return xmlDocument.getElementById(strRoot);
	}
	
	/**
	 * Elementos a partir del root del XML
	 * 
	 * @param Element
	 * @param Root
	 * 
	 * @return ArrayList de Elements
	 * */
	public static ArrayList<Element> getElements (Element rootElement, String strRoot) {
		ArrayList<Element> listElements = new ArrayList<Element>();
		NodeList elements = rootElement
				.getElementsByTagName(strRoot);
		for (int i = 0; i < elements.getLength(); i++) {
			Element element = (Element) elements.item(i);
			listElements.add(element);
		}
		return listElements;
	}
	
	/**
	 * Atributo de un elemento XML
	 * 
	 * @param Element
	 * @param Atributo
	 * 
	 * @return Cadena con el valor del atributo
	 * */
	public static String getAttribute (Element element, String strAttr) {
		return element.getAttribute(strAttr);
	}

	/**
	 * MŽtodo para devolver un archivo tipo Document apartir de una cadena
	 * 
	 * @param nombre de la cargar
	 * @return Document
	 * */
	public static Document getDocumentoXML(final String cadenaXML) {
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
	public static String getAttrDFicheroXML(Document xmlDocument, String strRoot, String strAttr) {
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
	public static Element addElementoFicheroXML(Document xmlDocument, String newElement) {
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
	public static void loadValorAttrXML(Document xmlDocument, String attrName, String attrValue) {
		Element rootElement = xmlDocument.getDocumentElement();
		Element element = (Element) rootElement.getAttributeNode(attrName);
		element.setAttribute(attrName, attrValue);
	}

}
