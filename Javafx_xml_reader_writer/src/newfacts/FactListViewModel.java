package newfacts;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.w3c.dom.Element;

import javafx.beans.property.SimpleObjectProperty;

/**
 * ViewModel class for managing a list of facts and providing functionality
 * to load, save, search, and retrieve random facts.
 * This is the middle ground for the FactList and UI side. Which means, it handles the program logic,
 * and error/exception. 
 */
public class FactListViewModel {
	private final static String INPUT_FILE = "data/facts.xml";
    private FactList factList;
    private SimpleObjectProperty<String> factProperty;

    /**
     * Constructs a new FactListViewModel instance.
     * Initializes the fact list and fact property.
     */
    public FactListViewModel() {
        factList = new FactList();
        factProperty = new SimpleObjectProperty<>();
    }

    /**
     * Loads facts from an XML file specified by the given file path.
     * Parses the XML and adds the loaded facts to the existing fact list.
     *
     * @param filePath The path to the XML file containing facts.
     */
    public void loadFactsFromXML(String filePath) {
        Parser parser = new Parser(filePath);
        FactList loadedList = parser.getFactList();
        factList.addAll(loadedList);
        factProperty.set(factList.getRandom().toString());
    }

    /**
     * Loads facts from an XML file specified by the given path.
     * Replaces the existing fact list with the loaded facts from the file.
     *
     * @param path The path to the XML file containing facts.
     */
    public void loadFacts(String path) {
        Parser parser = new Parser(path);
        factList = parser.getFactList();
    }

    /**
     * Loads a random fact from the fact list and sets it as the current fact property.
     * If the fact list is empty, sets a message indicating that the list is empty.
     */
    public void loadRandomFact() {
        Fact randFact = factList.getRandom();
        if (randFact == null) {
            factProperty.set("Fact list is empty.");
            return;
        }
        factProperty.set(randFact.toString());
    }

    /**
     * Saves a new fact with the specified author, text, and type to an XML file.
     * The XML file location is predefined as "data/facts.xml".
     *
     * @param author The author of the new fact.
     * @param text   The text/content of the new fact.
     * @param type   The type/category of the new fact.
     * @return 
     */
    public NewFactStatus saveNewFact(String author, String text, String type) {
    	NewFactStatus status = validNewData(author, text, type);
    	if( status != NewFactStatus.VALID) return status; 
    	
        XMLWriter writer = new XMLWriter(INPUT_FILE);
        
        List<String> tags = Arrays.asList(XMLNode.FACT_AUTHOR.getNodeName(),
                XMLNode.FACT_TEXT.getNodeName(), XMLNode.FACT_TYPE.getNodeName());
        List<String> values = Arrays.asList(author, text, type);

        Element parent = writer.createElement(XMLNode.FACT.getNodeName());
        writer.addChildern(tags, values, parent);
        writer.addToRootNode(parent);
        writer.saveChanges();
        loadFacts(INPUT_FILE);
        return NewFactStatus.SAVED;
    }

	private NewFactStatus validNewData(String author, String type, String text) {
		if (author == null || author.isBlank()) {
            return NewFactStatus.AUTHOR_INVALID;
        }
        if (type == null || type.isBlank()) {
            return NewFactStatus.TYPE_INVALID;
        }
        if (text == null || text.isBlank()) {
            return NewFactStatus.TEXT_INVALID;
        }
        return NewFactStatus.VALID;
	}

    /**
     * Searches for a fact based on the given search string and search mode.
     * Sets the fact property to the found fact or a message if no matching fact is found.
     *
     * @param searchString The search string to match against fact attributes.
     * @param searchMode   The mode specifying which attribute to search (Author, Text, Type, or All).
     */
    public void searchFact(String searchString, FactSearchMode searchMode) {
        String searchLower = searchString.toLowerCase();
        Predicate<Fact> predicate = null;
        List<Fact> list = null;
        if (searchMode == FactSearchMode.ALL) {
            list = factList.searchAll(searchString);
        } else {
            if (searchMode == FactSearchMode.TEXT) {
                predicate = fact -> fact.getText().toLowerCase().contains(searchLower);
            } else if (searchMode == FactSearchMode.TYPE) {
                predicate = fact -> fact.getType().toLowerCase().contains(searchLower);
            } else if (searchMode == FactSearchMode.AUTHOR) {
                predicate = fact -> fact.getAuthor().toLowerCase().contains(searchLower);
            }
            list = this.factList.search(predicate);
        }
        if (list == null || list.isEmpty()) {
            factProperty.set("No results.");
            return;
        }
        final int index = new Random().nextInt(list.size());
        Fact foundFact = list.get(index);
        factProperty.set(foundFact.toString());
    }

    /**
     * Retrieves the fact property, which is a SimpleObjectProperty holding the current fact string.
     *
     * @return The fact property.
     */
    public SimpleObjectProperty<String> getFactProperty() {
        return factProperty;
    }
}

