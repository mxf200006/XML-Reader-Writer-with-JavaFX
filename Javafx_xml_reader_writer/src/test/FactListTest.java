package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import newfacts.Fact;
import newfacts.FactList;
/**
 * This class provides unit tests for the FactList class.
 * It tests various functionalities such as adding facts to the list,
 * retrieving facts from the list, searching for facts, and adding all facts from another list.
 */
class FactListTest {
	private FactList list;
	private static Random rand;
	private static List<Fact> TEST_LIST;

	/**
     * Sets up the test data by reading facts from a file and initializing a random generator.
     */
    @BeforeAll
    static void dataSetUp() {
        TEST_LIST = FactReader.readFactsFromFile("src/test/data.txt");
        rand = new Random();
    }

    /**
     * Initializes a new FactList instance before each test method.
     */
    @BeforeEach
    protected void setUp() {
        list = new FactList();
    }

    /**
     * Tests that the list size is zero when no facts are added.
     */
    @Test
    void testListSizeZero() {
        assertEquals(0, list.getSize());
    }

    /**
     * Tests that the list size is one after adding one fact to the list.
     */
    @Test
    void testListSizeOne() {
        list.add(getRandomFact());
        assertEquals(1, list.getSize());
    }

    /**
     * Tests that getting a random fact from an empty list returns null.
     */
    @Test
    void testRandomEmptyList() {
        assertNull(list.getRandom());
    }

    /**
     * Tests that getting a random fact from a list with one item returns that item.
     */
    @Test
    void testRandomOneItem() {
    	Fact f = getRandomFact();
        list.add(f);
        assertEquals(f, list.getRandom());
    }

    /**
     * Tests that getting a random fact from a list with multiple items returns a valid fact.
     */
    @Test
    void testRandomOnePlusItems() {
    	TEST_LIST.forEach(list::add);

        assertNotNull(list.getRandom());
    }


    /**
     * Tests that searching for a fact in an empty list returns null.
     */
    @Test
    void testGetEmptyList() {
        assertNull(list.get(0));
    }

    /**
     * Tests that getting a fact from a list with one item returns that item.
     */
    @Test
    void testGetListOneItem() {
        list.add(TEST_LIST.get(0));
        assertEquals(TEST_LIST.get(0), list.get(0));
    }

    /**
     * Tests that getting a fact from a list with multiple items returns the correct fact.
     */
    @Test
    void testGetListOnePlusItems() {
        final int index = 5;
        TEST_LIST.forEach(e -> list.add(e));
        assertEquals(TEST_LIST.get(index), list.get(index));
    }

    /**
     * Tests searching for all instances of a fact attribute in the list.
     * Positive test case where all facts should be found.
     */
    @Test
    void testSearchAllPositive() {
        TEST_LIST.forEach(list::add);

        for (Fact fact : TEST_LIST) {
            assertNotNull(list.searchAll(fact.getAuthor()));
            assertNotNull(list.searchAll(fact.getText()));
            assertNotNull(list.searchAll(fact.getType()));
        }
    }

    /**
     * Tests searching for all instances of a fact attribute in the list.
     * Negative test case where none of the facts should be found.
     */
    @Test
    void testSearchAllNegative() {
        for (Fact fact : TEST_LIST) {
            assertNull(list.searchAll(fact.getAuthor()));
            assertNull(list.searchAll(fact.getText()));
            assertNull(list.searchAll(fact.getType()));
        }
    }

    /**
     * Tests searching for a fact with non-existent attributes in the list.
     * The fact should not be found in any case.
     */
    @Test
    void testSearchNegative() {
        final String testText = "$$<--This text should not be found. -->$$";
        TEST_LIST.forEach(list::add);
        
        Predicate<Fact> AuthorPredicate = fact -> fact.getAuthor().equals(testText);
        assertNull(list.search(AuthorPredicate));

        Predicate<Fact> textPredicate = fact -> fact.getText().equals(testText);
        assertNull(list.search(textPredicate));

        Predicate<Fact> typePredicate = fact -> fact.getType().equals(testText);
        assertNull(list.search(typePredicate));
    }

    /**
     * Tests searching for a fact with existing attributes in the list.
     * The fact should be found in all cases.
     */
    @Test
    void testSearchPositive() {
    	TEST_LIST.forEach(list::add);
    	
        final Fact authorTest = getRandomFact();
        Predicate<Fact> AuthorPredicate = fact -> fact.getAuthor().equals(authorTest.getAuthor());
        assertNotNull(list.search(AuthorPredicate));


        final Fact textTest = getRandomFact();
        Predicate<Fact> textPredicate = fact -> fact.getText().equals(textTest.getText());
        assertNotNull(list.search(textPredicate));

        final Fact typeTest = getRandomFact();
        Predicate<Fact> typePredicate = fact -> fact.getType().equals(typeTest.getType());
        assertNotNull(list.search(typePredicate));
    }

    /**
     * Tests adding all facts from another list to the current list.
     * Verifies that the size of the current list increases by the number of facts added.
     */
    @Test
    void testAddAll() {
    	TEST_LIST.forEach(list::add);
        final int initialListSize = list.getSize();

        FactList newList = new FactList();
        Fact fact1 = new Fact("Author1", "Text1", "Type1");
        Fact fact2 = new Fact("Author2", "Text2", "Type2");
        newList.add(fact1);
        newList.add(fact2);

        list.addAll(newList);
        assertEquals(initialListSize + 2, list.getSize());
    }
	
	private static Fact getRandomFact() {
		return TEST_LIST.get(rand.nextInt(TEST_LIST.size()));
	}
}
