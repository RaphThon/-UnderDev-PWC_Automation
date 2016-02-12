import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParser {

	static String mybk = "C:\\Users\\ts-raphe.a.thongkham\\Documents\\SPDB_SystemWalker\\MyBK";
	
	private static File inputXML = new File(mybk+"\\XML data\\wf_m_XML_Source_Processing.xml");
	private static String OutputPath = mybk+"\\wf_m_XML_Source_Processing.txt";
	
	public static void main(String[] args) {
		
		try {
			
			// Create a DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			factory.setFeature("http://xml.org/sax/features/namespaces", false);
			factory.setFeature("http://xml.org/sax/features/validation", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder builder = factory.newDocumentBuilder();
//			StringBuilder sb = new StringBuilder();
			
			// Create a Document from a file
			Document document = builder.parse(inputXML);
			
			// Normalize the XML Structure
			document.getDocumentElement().normalize();
			
			// Extract root element
			Element root = document.getDocumentElement();
			System.out.println(root.getNodeName());
//			sb.append(root.getNodeName());
//			sb.append("\n");
			
			if (root.hasAttributes()){
				// Attributes
				for (int j = 0; j < root.getAttributes().getLength(); j++) {
//					sb.append("\t:"
//							+ root.getAttributes().item(j).getNodeName() + " = "
//				            + root.getAttributes().item(j).getNodeValue());
//					sb.append("\n");
					System.out.println("\t:"
							+ root.getAttributes().item(j).getNodeName() + " = "
				            + root.getAttributes().item(j).getNodeValue());
				   }
				
			}
			
			int level = 1;
			if (root.hasChildNodes()) {
				NodeList nList = document.getElementsByTagName("REPOSITORY");
//				visitChildNodes(nList, level, sb);
				visitChildNodes(nList, level);
			}

//			writeFile(OutputPath, sb);
		
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void visitChildNodes(NodeList nList, int level) {
		int olevel=level;
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			Node node = nList.item(temp);
			
			if (node.getNodeType() == 1) {
				StringBuilder sbtab = new StringBuilder();
				for (int t=0;t<level;t++)
					sbtab.append("\t");
					
				System.out.println(sbtab.toString() + node.getNodeName());
			
	            //Check all attributes
	            if (node.hasAttributes()) {
	            	
	            	// get attributes names and values
	            	NamedNodeMap nodeMap = node.getAttributes();
	            	for (int i = 0; i < nodeMap.getLength(); i++) {
	            		Node tempNode = nodeMap.item(i);
	            		System.out.println(sbtab.toString() +"\t:" 
	            				+ tempNode.getNodeName()+ " = " 
	            				+ tempNode.getNodeValue());
	            	}
	            	if (node.hasChildNodes()) {
	            		level++;
	            		//We got more childs
	            		visitChildNodes(node.getChildNodes(), level);
	            	} 
	            } 
	         } else {
	            	level=olevel;
	         }
	    }
	
	}

	//This function is called recursively
//	private static void visitChildNodes(NodeList nList, int level, StringBuilder sb) {
//		
//		int olevel=level;
//		for (int temp = 0; temp < nList.getLength(); temp++) {
//			
//			Node node = nList.item(temp);
//			
//			if (node.getNodeType() == 1) {
//				StringBuilder sbtab = new StringBuilder();
//				for (int t=0;t<level;t++)
//					sbtab.append("\t");
//					
////				System.out.println(sbtab.toString() + node.getNodeName());
//				sb.append(sbtab.toString() + node.getNodeName());
//				sb.append("\n");
//				
//	            //Check all attributes
//	            if (node.hasAttributes()) {
//	            	
//	            	// get attributes names and values
//	            	NamedNodeMap nodeMap = node.getAttributes();
//	            	for (int i = 0; i < nodeMap.getLength(); i++) {
//	            		Node tempNode = nodeMap.item(i);
//	            		sb.append(sbtab.toString() +"\t:" 
//	            				+ tempNode.getNodeName()+ " = " 
//	            				+ tempNode.getNodeValue());
//	            		sb.append("\n");
////	            		System.out.println(sbtab.toString() +"\t:" 
////	            				+ tempNode.getNodeName()+ " = " 
////	            				+ tempNode.getNodeValue());
//	            	}
//	            	if (node.hasChildNodes()) {
//	            		level++;
//	            		//We got more childs
//	            		visitChildNodes(node.getChildNodes(), level, sb);
//	            	} 
//	            } 
//	         } else {
//	            	level=olevel;
//	         }
//	    }
//	   
//	
//	}
	
	public static void writeFile(String filepath, StringBuilder sb) throws FileNotFoundException, IOException {
		File file = new File(filepath);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		    writer.write(sb.toString());
		} finally {
		    if (writer != null) writer.close();
		}
    }

}
