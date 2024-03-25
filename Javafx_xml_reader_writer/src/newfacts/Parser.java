package newfacts;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * The Parser class is responsible for parsing XML files containing facts
 * using a SAX parser. It utilizes a Handler object to handle the parsing events
 * and store the parsed facts in a FactList.
 */
public class Parser {
    private Handler handler; // Handler object to handle XML parsing

    /**
     * Constructor for the Parser class.
     *
     * @param fileName The name of the XML file to be parsed
     */
    public Parser(String fileName) {
        try {
            File file = new File(fileName); // Create a File object from the file name
            handler = new Handler(); // Initialize the Handler object
            SAXParserFactory factory = SAXParserFactory.newInstance(); // Get a SAXParserFactory instance
            SAXParser saxParser = factory.newSAXParser(); // Create a SAXParser instance
            saxParser.parse(file, handler); // Parse the XML file using the SAXParser and the Handler
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
    }

    /**
     * Retrieves the FactList containing the parsed facts.
     *
     * @return FactList object containing parsed facts
     */
    public FactList getFactList() {
        return handler.getList(); // Return the FactList from the Handler object
    }
}
