package newfacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class representing a list of facts and providing operations to manage and
 * search the facts.
 */
public class FactList {
	private ObservableList<Fact> factList;

	/**
	 * Constructs a new FactList instance. Initializes the fact list as an
	 * observable array list and the random generator.
	 */
	public FactList() {
		this.factList = FXCollections.observableArrayList();
	}

	/**
	 * Adds a new fact to the fact list.
	 * 
	 * @param temp The fact to be added to the list.
	 */
	public void add(Fact temp) {
		factList.add(temp);
	}

	/**
	 * Retrieves the size of the fact list.
	 * 
	 * @return The number of facts in the list.
	 */
	public int getSize() {
		return factList.size();
	}

	/**
	 * Retrieves the fact at the specified index in the fact list.
	 * 
	 * @param i The index of the fact to retrieve.
	 * @return The fact at the specified index.
	 */
	public Fact get(int i) {
		//System.out.println("This is i: " + i +"->" + this.factList.size());
		if (factList.isEmpty() || i < 0 || i > factList.size())
			return null;
		return factList.get(i);
	}

	/**
	 * Searches for facts that match the given search term across author, text, and
	 * type attributes.
	 * 
	 * @param searchTerm The search term to match against fact attributes.
	 * @return A list of facts that match the search term.
	 */
	public List<Fact> searchAll(String searchTerm) {
		final String searchText = searchTerm.toLowerCase();
		List<Fact> tempList = new ArrayList<>();
		factList.forEach(e -> {
			if (e.getAuthor().toLowerCase().contains(searchText))
				tempList.add(e);
			else if (e.getText().toLowerCase().contains(searchText))
				tempList.add(e);
			else if (e.getType().toLowerCase().contains(searchText))
				tempList.add(e);
		});

		return tempList.size() > 0 ? tempList : null;
	}

	/**
	 * Searches for facts based on a given predicate. This is new improvement over
	 * the given search functionality (on the original code). The logic of the
	 * search is pushed to the user of this class.This is possible due the the use
	 * predicate, since this function receive the condition code instead of a text
	 * to search.
	 * 
	 * @param predicate The predicate used to filter facts.
	 * @return A list of facts that match the predicate.
	 */
	public List<Fact> search(Predicate<Fact> predicate) {
		List<Fact> temp = factList.stream().filter(predicate).collect(Collectors.toList());
		return temp.size() > 0 ? temp : null;
	}

	/**
	 * Retrieves a random fact from the fact list.
	 * 
	 * @return A random fact from the list, or null if the list is empty.
	 */
	public Fact getRandom() {
		if (factList.size() <= 0)
			return null;
		int index = new Random().nextInt(factList.size());
		Fact fact = factList.get(index);

		return fact;
	}

	/**
	 * Adds all facts from the given fact list to this fact list.
	 * 
	 * @param newList The fact list containing facts to be added.
	 */
	public void addAll(FactList newList) {
		factList.addAll(newList.factList);
	}
}
