package testapps;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ModifyXML {
	
	String filePath = "C:\\Users\\ts-raphe.a.thongkham\\Documents\\SPDB_SystemWalker\\MyBK\\";
	static String fileName = "filePath + wf_simpleLoad_template.xml";
	
	public static void main(String args[]){
		
		try {
			
			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(fileName);
			
//			StringBuilder sb = new StringBuilder();
			
			// Get the root element
			Element root = document.getDocumentElement();
			System.out.println(root);
			
			if (root.hasAttributes()){
				// Attributes
				for (int j = 0; j < root.getAttributes().getLength(); j++) {
					System.out.println("\t:"
							+ root.getAttributes().item(j).getNodeName() + " = "
				            + root.getAttributes().item(j).getNodeValue());
				   }
			}
			
			// Current DT
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date date = new Date();
			System.out.println("current dt: " + dateFormat.format(date));
			
			// Update root's attr
			NamedNodeMap root_attr = root.getAttributes();
			Node nodeAttr = root_attr.getNamedItem("CREATION_DATE");
			nodeAttr.setTextContent(dateFormat.format(date));
			
			// Attributes
			for (int j = 0; j < root.getAttributes().getLength(); j++) {
				System.out.println("\t:"
						+ root.getAttributes().item(j).getNodeName() + " = "
			            + root.getAttributes().item(j).getNodeValue());
			   }
			
			// .... 
			
			
			// write the content into xml file
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(fileName));
			transformer.transform(source, result);
			
			System.out.println("Done");
			System.exit(0);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
