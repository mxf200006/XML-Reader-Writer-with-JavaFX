package newfacts;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FactViewerUI extends Application {

	private FactListViewModel viewModel; 
	private Label factDisplayLabel;	//Display the fact details
	private Label statusBar;		//Display the status information
	StringProperty statusBarTextProperty;	//To hold the value of status bar. This make is easy to manipulate the status bar text.
	private Button nextButton;				//Search next fact
	private Button addButton;				//Add new fact
	private TextField searchField;			//Search box for a fact
	private ComboBox<FactSearchMode> searchModeComboBox;	//Type of facts
	private Button searchButton;			//To initiate the search operation

	//Button, textfield, combobox size
	private final int CONTROLS_BTN_WIDTH = 200;	
	private final int CONTROL_BTN_HEIGHT = 10;
	
	private final int FACT_LABEL_W = 600;
	private final int FACT_LABEL_H = 200;
	private final String filePath = "data/facts.xml";
	

	
	@Override
	public void start(Stage primaryStage) { 
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);

		factListViewModel(filePath); //fact view
		factDisplayLabel();
		nextButton();
		addButton();
		createStatusBar(); 
		
		HBox factPanel = new HBox(10);
		factPanel.getChildren().addAll(factDisplayLabel, nextButton);
		factPanel.setPadding(new Insets(10));

		searchField();
		comboBox();
		searchButton();

		VBox controlPanel = new VBox(10);
		controlPanel.getChildren().addAll(searchField, searchModeComboBox, searchButton, nextButton, addButton);
		controlPanel.setPadding(new Insets(10));

		root.setCenter(factDisplayLabel);
		root.setLeft(controlPanel);
		root.setBottom(statusBar);
		primaryStage.setTitle("Fact Viewer");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void createStatusBar() {
		statusBarTextProperty = new SimpleStringProperty("Thanks for using our application.");
		statusBar = new Label();
		statusBar.setPadding(new Insets(10));
		statusBar.textProperty().bind(statusBarTextProperty);
	}

	/**
	 * Adds a button to initiate the process of adding a new fact.
	 * When clicked, this button opens a pop-up window with input fields for the author, type, and text of the new fact.
	 * The user can enter the necessary information and save the new fact by clicking the "Save" button in the pop-up window.
	 * If the author or text fields are empty, an error message is displayed.
	 * I could have improve this code for maintenance wise. However, considering the time limitation, and fact that is simple application, 
	 * I chose to code it here. 
	 */
	private void addButton() {
		final int W = 300;
		final int H = 10;
		addButton = new Button("Add New Fact");
		addButton.setPrefSize(CONTROLS_BTN_WIDTH, CONTROL_BTN_HEIGHT);
		addButton.setOnAction(e -> {
			// Create a new pop-up window
			Stage popupStage = new Stage();
			GridPane popupLayout = new GridPane();
			popupLayout.setHgap(10);
			popupLayout.setVgap(10);
			popupLayout.setPadding(new Insets(10));

			// Input fields and labels
			Label authorLabel = new Label("Author:");
			Label typeLabel = new Label("Type:");
			Label textLabel = new Label("Text:");
			TextField authorField = new TextField();
			TextField typeField = new TextField();
			TextField textField = new TextField();
			Button saveButton = new Button("Save");
			Label statusLabel = new Label();
			statusLabel.setText(NewFactStatus.INFO.getMessage());
			statusLabel.setPrefSize(W, H);
			statusLabel.setAlignment(Pos.TOP_LEFT);

			// Save button action
			saveButton.setOnAction(saveEvent -> {
				String author = authorField.getText();
				String type = typeField.getText();
				String text = textField.getText();
				NewFactStatus saveStatus = viewModel.saveNewFact(author, type, text);
				
				if(saveStatus == NewFactStatus.SAVED) {
					statusLabel.setText(saveStatus.getMessage());
					authorField.setText("");
					typeField.setText("");
					textField.setText("");
				}
				else {
					statusLabel.setText(saveStatus.getMessage());
				}
				
			});

			popupLayout.addRow(0, authorLabel, authorField);
			popupLayout.addRow(1, typeLabel, typeField);
			popupLayout.addRow(2, textLabel, textField);
			popupLayout.add(saveButton, 0, 3, 2, 1);
			popupLayout.add(statusLabel, 0, 4, 2, 1);

			Scene popupScene = new Scene(popupLayout);
			popupStage.setScene(popupScene);
			popupStage.setTitle("Add New Fact");
			popupStage.show();
		});
	}

	/**
	 * Initializes the fact display label by creating a new instance of Label,
	 * setting its preferred size, applying a bold font style with a specific font size,
	 * and adding a border to the label for visual distinction.
	 */
	private void factDisplayLabel() {
		
	    factDisplayLabel = new Label(); // Creates a new instance of Label
	    factDisplayLabel.setPrefSize(FACT_LABEL_W, FACT_LABEL_H); // Sets preferred size
	    factDisplayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16"); // Sets font style and size
	 
	    
	}

	/**
	 * Initializes the next button by creating a new instance with the text "Next",
	 * setting its preferred size, and configuring its action event to load a random fact
	 * from the FactListViewModel when clicked.
	 */
	private void nextButton() {

	    nextButton = new Button("Next"); 
	    nextButton.setPrefSize(CONTROLS_BTN_WIDTH, CONTROL_BTN_HEIGHT);
	    // Configures action event to load a random fact
	    nextButton.setOnAction(e -> viewModel.loadRandomFact()); 
	    
	}

	/**
	 * Initializes the search text field by creating a new instance of TextField
	 * and setting its preferred size to the specified width and height.
	 */
	private void searchField() {
	    searchField = new TextField();
	    searchField.setPrefSize(CONTROLS_BTN_WIDTH, CONTROL_BTN_HEIGHT); 
	}

	/**
	 * Initializes the search button by creating a new instance with the text "Search",
	 * setting its preferred size, and configuring its action event to perform a search
	 * operation based on the input text from the search field and the selected search mode.
	 * If the search text is empty, it updates the status bar text accordingly.
	 */
	private void searchButton() {
	    searchButton = new Button("Search"); // Creates a new instance of Button with text "Search"
	    searchButton.setPrefSize(CONTROLS_BTN_WIDTH, CONTROL_BTN_HEIGHT); // Sets preferred size
	    searchButton.setOnAction(e -> {
	        String text = searchField.getText(); // Gets the text from the search field
	        if (text.isBlank()) {
	            // Updates status bar text if search text is empty
	            statusBarTextProperty.set("Please enter a text to search.");
	            return;
	        }
	        // Performs search based on input text and selected search mode
	        viewModel.searchFact(text, searchModeComboBox.getValue());
	    });
	}

	/**
	 * Initializes the search mode combo box by creating a new instance,
	 * setting its preferred size, populating it with search mode values,
	 * and setting the default selected value to FactSearchMode.ALL.
	 */
	private void comboBox() {
	    searchModeComboBox = new ComboBox<>(); // Creates a new instance of ComboBox
	    searchModeComboBox.setPrefSize(CONTROLS_BTN_WIDTH, CONTROL_BTN_HEIGHT); // Sets preferred size
	    
	    // Populates with search mode values
	    searchModeComboBox.setItems(FXCollections.observableArrayList(FactSearchMode.values())); 
	    
	    // Sets the default selected value
	    searchModeComboBox.setValue(FactSearchMode.AUTHOR); 
	}

	/**
	 * Initializes the FactListViewModel by creating a new instance, setting up a listener
	 * for changes in the fact property, and loading initial facts from a specified XML file.
	 * The listener updates the fact display label whenever a new fact is loaded.
	 */
	private void factListViewModel(String filePath) {
	    viewModel = new FactListViewModel(); // Creates a new instance of FactListViewModel
	    viewModel.getFactProperty().addListener((observable, oldValue, newValue) -> {
	        // Listens for changes in the fact property
	        if (newValue != null) {
	            updateFactLabel(newValue); // Updates the fact display label with the new fact
	        }
	    });
	    viewModel.loadFacts(filePath); // Loads initial facts from the specified XML file
	}

	private void updateFactLabel(String fact) {
		factDisplayLabel.setText(fact);
	}

	public static void main(String[] args) {
		launch(args);
	}
}