package newfacts;

public enum NewFactStatus {
	VALID("Data was valid"), 
	INFO("Please fill the information, then click on saved."),
    SAVED("Your new fact was saved successfully, please enter another fact."),
    AUTHOR_INVALID("Author cannot be empty."),
    TEXT_INVALID("Text cannot be empty."),
    TYPE_INVALID("Type cannot be empty."),
    UNKNOWN_ERROR("An unknown error occurred while saving the fact.");

    private final String message;

    NewFactStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}