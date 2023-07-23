package xml_parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {
	static Logger logger = Logger.getLogger(XmlParser.class.getName());

	public static void main(String[] args) {

		// Create a String Builder for CSV file
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ID").append(",").append("Target").append("\n");

		// Create a document builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Given Xml
			Document doc = builder.parse("sma_gentext.xml");

			// Normalize the xml
			doc.getDocumentElement().normalize();

			// Get the <trans-unit> elements
			NodeList temp = doc.getElementsByTagName("trans-unit");

			// Iterate over each element
			for (int i = 0; i < temp.getLength(); i++) {
				Node n = temp.item(i);

				// Check if the node is an element node
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) n;

					// Check if the id attribute is 42007
					if (element.getAttribute("id").equals("42007")) {

						// Get the value of the target element
						String targetVal = element.getElementsByTagName("target").item(0).getTextContent();

						// Get the value of the id
						String id = element.getAttribute("id");

						logger.info("Target value is " + targetVal);
						logger.info("Id value is " + id);
						stringBuilder.append(id).append(",").append(targetVal).append("\n");

						// Write the value into a CSV file
						FileWriter fileWriter = new FileWriter("target.csv");
						fileWriter.write(stringBuilder.toString());
						
						//Close the file
						fileWriter.close();

						break;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
