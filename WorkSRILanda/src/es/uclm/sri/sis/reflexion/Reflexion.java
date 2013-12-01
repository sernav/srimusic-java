package es.uclm.sri.sis.reflexion;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.uclm.sri.sis.utilidades.XMLUtil;

/**
 * @author Sergio Navarro
 * 
 * */
public class Reflexion {

	private Reflexion() {
	}
	
	public Class reflexionCache(final String classNane) {
		Class crawlerClass = null;
		try {
			crawlerClass = Class.forName(classNane);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return crawlerClass;
	}
	
	/**
	 * Reflexi�n a partir de fichero XML donde tengo las clases.
	 * 
	 * */
	public Class reflexionCacheByXML() {
		Class classReflection = null;
		try{
			Document xmlDocument = XMLUtil.loadFicheroByDOM(KCtesReflexion.Reflexion.pathXmlClasesReflexion);
			Element rootElement = XMLUtil.getRootElement(xmlDocument, KCtesReflexion.Reflexion.rootElement);
			ArrayList<Element> elements = XMLUtil.getElements(rootElement, KCtesReflexion.Reflexion.rootElement);
			for (Element element : elements) {
				boolean isEnabled = Boolean.parseBoolean(element.getAttribute(KCtesReflexion.Reflexion.attrbIsEnable));
				if (isEnabled) {
					classReflection = Class.forName(element.getAttribute(KCtesReflexion.Reflexion.attrbClass));
					
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return classReflection;
	}

}