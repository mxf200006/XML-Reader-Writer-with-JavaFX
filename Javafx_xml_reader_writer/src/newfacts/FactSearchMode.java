package newfacts;

/**
 * Enum representing different modes for searching facts.
 * Each mode corresponds to a specific search criteria: Author, Text, Type, or All.
 */
public enum FactSearchMode {
    /**
     * Search mode based on the author of the fact.
     */
    AUTHOR(0),
    /**
     * Search mode based on the text content of the fact.
     */
    TEXT(1),
    /**
     * Search mode based on the type/category of the fact.
     */
    TYPE(2),
    /**
     * Search mode for searching using all criteria (Author, Text, and Type).
     */
    ALL(3);

    private final int modeValue;

    /**
     * Constructs a FactSearchMode enum with the specified mode value.
     *
     * @param modeValue The integer value representing the search mode.
     */
    FactSearchMode(int modeValue) {
        this.modeValue = modeValue;
    }

    /**
     * Retrieves the integer value associated with this search mode.
     *
     * @return The mode value integer.
     */
    public int getModeValue() {
        return modeValue;
    }
}
