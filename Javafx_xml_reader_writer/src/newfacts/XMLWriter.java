package newfacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**
 * The XMLWriter class provides functionality to manipulate and write XML documents.
 * It allows creating, modifying, and saving XML files.
 */
public class XMLWriter {
    private String inputFilePath = "data/facts.xml"; // Default input file path
    public Document document; // XML document object
    private Transformer transformer; // Transformer for saving changes

    /**
     * Constructor to initialize XMLWriter with a custom input file path.
     * @param filePath The path to the XML file to be manipulated.
     */
    public XMLWriter(String filePath) {
        this.inputFilePath = filePath;
        OpenXMLFile(inputFilePath); // Open the XML file
    }

    /**
     * Get the XML document object.
     * @return The XML document object.
     */
    public Document getDocument() {
        return this.document;
    }

    /**
     * Save changes made to the XML document back to the file.
     * This method updates the XML file with any modifications made.
     */
    public void saveChanges() {
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(inputFilePath);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a child element to the root node of the XML document.
     * @param child The child element to be added to the root node.
     */
    public void addToRootNode(Element child) {
        document.getDocumentElement().appendChild(child);
    }

    /**
     * Create a new XML element with the specified tag name.
     * @param tagName The tag name for the new XML element.
     * @return The newly created XML element.
     */
    public Element createElement(String tagName) {
        return document.createElement(tagName);
    }

    /**
     * Open and parse the XML file specified by the input file path.
     * @param inputFilePath The path to the XML file to be opened and parsed.
     */
    private void OpenXMLFile(String inputFilePath) {
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputFilePath);
            document.setXmlStandalone(true);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add multiple child elements to a parent element in the XML document.
     * @param elements The list of element tag names to be created and added as children.
     * @param values The list of values to be added as text content to the corresponding elements.
     * @param parent The parent element to which the children will be added.
     */
    public void addChildern(List<String> elements, List<String> values, Element parent) {
        List<Element> list = createElements(elements, values);
        for(Element el: list) {
            parent.appendChild(el);
        }
    }

    /**
     * Create a list of XML elements based on the provided element tag names and values.
     * @param elements The list of element tag names to be created.
     * @param values The list of values to be added as text content to the corresponding elements.
     * @return A list of newly created XML elements.
     */
    public List<Element> createElements(List<String> elements, List<String> values) {
        List<Element> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Element el = document.createElement(elements.get(i));
            el.appendChild(document.createTextNode(values.get(i)));
            list.add(el);
        }
        return list;
    }
}