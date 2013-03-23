package es.uclm.sri.sis.arqtecn.reflexion;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.uclm.sri.sis.arqtecn.KConstantes;
import es.uclm.sri.sis.util.XMLUtil;

/**
 * @author sernav
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
			Document xmlDocument = XMLUtil.loadFicheroByDOM(KConstantes.Reflexion.pathXmlClasesReflexion);
			Element rootElement = XMLUtil.getRootElement(xmlDocument, KConstantes.Reflexion.rootElement);
			ArrayList<Element> elements = XMLUtil.getElements(rootElement, KConstantes.Reflexion.rootElement);
			for (Element element : elements) {
				boolean isEnabled = Boolean.parseBoolean(element.getAttribute(KConstantes.Reflexion.attrbIsEnable));
				if (isEnabled) {
					classReflection = Class.forName(element.getAttribute(KConstantes.Reflexion.attrbClass));
					
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
