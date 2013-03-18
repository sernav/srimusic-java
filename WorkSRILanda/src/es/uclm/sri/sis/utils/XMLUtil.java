package es.uclm.sri.sis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil {

	public Document getDocumento(String filePath) throws DocumentException {
		File file = new File(filePath);
		SAXReader reader = new SAXReader();

		return reader.read(file);
	}

	@SuppressWarnings("deprecation")
	public void updateElement(Document document, String elementName,
			String attributeName, String newValue) {
		Element root = document.getRootElement();
		Element updatedNode = root.element(elementName);

		updatedNode.setAttributeValue(attributeName, newValue);
	}

	public void addElement(Document document, List<String> columns) {
		Element root = document.getRootElement();
		Element updatedNode = root.element("report_content"); // parent element.

		for (int i = 0; i < columns.size(); i++) {
			String column = columns.get(i);
			Element newElement = updatedNode.addElement("property");// child
																	// element.
			newElement.addAttribute("row_name", column);// the first property.
			newElement.addAttribute("row_id", String.valueOf(i));// the second
																	// property.
		}
	}

	public void writeXml(Document document, String filePath)
			throws IOException {
		File file = new File(filePath);
		XMLWriter writer = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			writer = new XMLWriter(new FileOutputStream(file));
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	

}
