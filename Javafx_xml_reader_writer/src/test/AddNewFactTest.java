/**
 * This class is used to test the functionality of adding new facts to a FactListViewModel.
 * It contains several test methods to ensure that the addition of facts works correctly under different conditions.
 */
package test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import newfacts.Fact;
import newfacts.FactList;
import newfacts.FactListViewModel;
import newfacts.NewFactStatus;
import newfacts.Parser;

public class AddNewFactTest {
    // Path to the input file containing initial facts
    private final static String INPUT_FILE = "data/facts.xml";
    private static FactListViewModel viewModel; // ViewModel instance for testing
    private static Random rand; // Random instance for generating test data
    private static List<Fact> TEST_LIST; // List of test facts
    
    /**
     * Sets up the necessary objects and data before running any test methods.
     */
    @BeforeAll
    static void setUp() {
        viewModel = new FactListViewModel();
        viewModel.loadFacts(INPUT_FILE); // Load facts from XML file
        TEST_LIST = FactReader.readFactsFromFile("src/test/data.txt"); // Read test facts from file
        rand = new Random();
    }

    /**
     * Test case to validate adding a null fact to the ViewModel.
     */
    @Test
    void testAddFactNull() {
        NewFactStatus st;
        Fact fact = getRandomFact(); // Get a random fact from the test list
        
        // Attempt to save a null fact
        st = viewModel.saveNewFact(null, null, null);
        assertEquals(NewFactStatus.AUTHOR_INVALID, st); // Expect author invalid status
        
        // Attempt to save a fact with null type and text
        st = viewModel.saveNewFact(fact.getAuthor(), null, null);
        assertEquals(NewFactStatus.TYPE_INVALID, st); // Expect type invalid status
        
        // Attempt to save a fact with null text
        st = viewModel.saveNewFact(fact.getAuthor(), fact.getText(), null);
        assertEquals(NewFactStatus.TEXT_INVALID, st); // Expect text invalid status
    }
    
    /**
     * Test case to validate adding an empty fact to the ViewModel.
     */
    @Test
    void testAddFactEmpty() {
        NewFactStatus st;
        Fact fact = getRandomFact(); // Get a random fact from the test list
        
        // Attempt to save an empty fact with empty author, type, and text
        st = viewModel.saveNewFact("", "", "");
        assertEquals(NewFactStatus.AUTHOR_INVALID, st); // Expect author invalid status
        
        // Attempt to save a fact with empty type and text
        st = viewModel.saveNewFact(fact.getAuthor(), "", "");
        assertEquals(NewFactStatus.TYPE_INVALID, st); // Expect type invalid status
        
        // Attempt to save a fact with empty text
        st = viewModel.saveNewFact(fact.getAuthor(), fact.getText(), "");
        assertEquals(NewFactStatus.TEXT_INVALID, st); // Expect text invalid status
    }
    
    /**
     * Test case to validate adding a valid fact to the ViewModel.
     */
    @Test
    void testAddFactPositive() {
        NewFactStatus st;
        Fact fact = getRandomFact(); // Get a random fact from the test list
        
        // Attempt to save a valid fact
        st = viewModel.saveNewFact(fact.getAuthor(), fact.getText(), fact.getType());
        assertEquals(NewFactStatus.SAVED, st); // Expect saved status
    }
    
    /**
     * Test case to ensure that an added fact is reflected in the XML file.
     */
    @Test
    void testAddedFactInXMLFile() {
        Fact fact = getRandomFact(); // Get a random fact from the test list
        
        // Save the fact to the ViewModel
        viewModel.saveNewFact(fact.getAuthor(), fact.getText(), fact.getType());
        
        // Read the XML file using a parser and check if the added fact exists
        Parser parser = new Parser(INPUT_FILE);
        FactList loadedList = parser.getFactList();
        Predicate<Fact> authorPredicate = fc -> fc.getAuthor().equals(fact.getAuthor());
        assertNotNull(loadedList.search(authorPredicate)); // Expect the added fact to exist in the XML file
    }
    
    /**
     * Helper method to get a random fact from the test list.
     * @return A random fact from the test list.
     */
    public static Fact getRandomFact() {
        final int index = rand.nextInt(TEST_LIST.size());
        return TEST_LIST.get(index);
    }
}
