package newfacts;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is a SAX handler that extends DefaultHandler and is used to parse XML data
 * containing facts. It implements the necessary methods to handle XML parsing events
 * such as startElement, endElement, and characters. It stores the parsed facts in a FactList
 * object.
 */
public class Handler extends DefaultHandler {
    private FactList list = new FactList(); // FactList to store parsed facts
    private Fact temp = null; // Temporary Fact object to hold the current fact being parsed
    private String currentElement = null; // Name of the current XML element being parsed

    /**
     * Constructor for Handler class.
     */
    public Handler() {
        super();
    }

    /**
     * Returns the FactList containing the parsed facts.
     *
     * @return FactList object
     */
    public FactList getList() {
        return list;
    }

    /**
     * Called at the start of the XML document parsing.
     */
    @Override
    public void startDocument() {
        // No implementation needed for this handler
    }

    /**
     * Called at the end of the XML document parsing.
     */
    @Override
    public void endDocument() {
        // No implementation needed for this handler
    }

    /**
     * Called when the parser encounters the start of an XML element.
     *
     * @param uri       The Namespace URI, or the empty string if the element has no Namespace URI
     * @param name      The local name (without prefix), or the empty string if Namespace processing is not being performed
     * @param qName     The qualified name (with prefix), or the empty string if qualified names are not available
     * @param atts      The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object
     */
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {
        currentElement = XMLNode.getValueForNode(qName); // Get the value corresponding to the XML node
        if (XMLNode.FACT.name().equalsIgnoreCase(qName)) // Check if the current element is a FACT node
            temp = new Fact(); // Create a new Fact object
    }

    /**
     * Called when the parser encounters the end of an XML element.
     *
     * @param uri       The Namespace URI, or the empty string if the element has no Namespace URI
     * @param name      The local name (without prefix), or the empty string if Namespace processing is not being performed
     * @param qName     The qualified name (with prefix), or the empty string if qualified names are not available
     */
    @Override
    public void endElement(String uri, String name, String qName) {
        if (XMLNode.FACT.name().equalsIgnoreCase(qName)) { // Check if the current element is a FACT node
            list.add(temp); // Add the parsed Fact to the FactList
            temp = null; // Reset the temporary Fact object
        }
    }

    /**
     * Called with character data (between start and end tags) within an XML element.
     *
     * @param ch        The characters from the XML document
     * @param start     The start position in the character array
     * @param length    The number of characters to use from the character array
     */
    @Override
    public void characters(char ch[], int start, int length) {
        String value = new String(ch, start, length); // Get the character data as a string
        if (!value.trim().equals("")) { // Check if the string value is not empty or just whitespace
            for (XMLNode node : XMLNode.values()) { // Iterate through all XMLNode values
                if (currentElement.equalsIgnoreCase(node.getNodeName())) { // Check if the currentElement matches the XMLNode
                    temp.setFact(node, value); // Set the fact attribute in the temporary Fact object
                }
            }
        }
    }
}

