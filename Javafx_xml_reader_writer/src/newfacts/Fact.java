package newfacts;

/**
 * The Fact class represents a piece of factual information with attributes such as author, type, and text.
 * It provides methods to get and set these attributes, as well as a custom toString method for string representation.
 */
public class Fact {
    private String author; // Author of the fact
    private String type; // Type of the fact
    private String text; // Content of the fact

    /**
     * Get the author of the fact.
     * @return The author of the fact.
     */
    public String getAuthor() {
        return author; 
    }

    /** 
     * Set the author of the fact.
     * @param author The author of the fact to be set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the type of the fact.
     * @return The type of the fact.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the fact.
     * @param type The type of the fact to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the text content of the fact.
     * @return The text content of the fact.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text content of the fact.
     * @param text The text content of the fact to be set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Default constructor for the Fact class.
     */
    public Fact() {
        super();
    }

    /**
     * Parameterized constructor for the Fact class.
     * @param author The author of the fact.
     * @param type The type of the fact.
     * @param text The content of the fact.
     */
    public Fact(String author, String type, String text) {
        super();
        this.author = author;
        this.type = type;
        this.text = text;
    }

    /**
     * Custom toString method to represent the Fact object as a formatted string.
     * @return A formatted string representing the Fact object.
     */
    @Override
    public String toString() {
        return "Author: " + author + ",\nType: " + type + ",\nFact: " + text + "\n";
    }

    /**
     * Set the author, type, or text of the fact based on the XMLNode type provided.
     * @param type The type of XML node (FACT_AUTHOR, FACT_TYPE, FACT_TEXT).
     * @param fact The value to be set for the corresponding type.
     */
    public void setFact(XMLNode type, String fact) {
        if (type == XMLNode.FACT_AUTHOR)
            setAuthor(fact);
        else if (type == XMLNode.FACT_TEXT)
            setText(fact);
        else if (type == XMLNode.FACT_TYPE)
            setType(fact);
    }
}
