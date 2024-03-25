package newfacts;

/**
 * The XMLNode enum represents different XML nodes that are used in parsing XML files
 * containing facts. Each enum value corresponds to a specific XML node name.
 */
public enum XMLNode {
    FACT_LIST("fact-list"), 	// Represents the root element in the XML file
    FACT("fact"), 				// Represents a fact element within the XML file
    FACT_AUTHOR("author"), 		// Represents the author element within a fact
    FACT_TEXT("fact-text"), 	// Represents the text content of a fact
    FACT_TYPE("fact-type"); 	// Represents the type of a fact

    private final String nodeName; // The name of the XML node

    /**
     * Constructor for XMLNode enum. 
     *
     * @param nodeName The name of the XML node
     */
    XMLNode(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * Gets the name of the XML node.
     *
     * @return The name of the XML node
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Gets the value corresponding to a specific XML node name.
     *
     * @param nodeName The name of the XML node
     * @return The value corresponding to the XML node name, or an empty string if not found
     */
    public static String getValueForNode(String nodeName) {
        for (XMLNode node : XMLNode.values()) { // Iterate through all XMLNode values
            if (node.getNodeName().equalsIgnoreCase(nodeName)) { // Check if the node name matches
                return node.getNodeName(); // Return the node name as the value
            }
        }
        return ""; // Return an empty string if node name not found
    }
}
