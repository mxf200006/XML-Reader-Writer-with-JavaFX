The project incorporates functionality to read facts from an XML file and display them to the user via a graphical user interface (GUI). Additionally, users can add more facts to the XML file through the application. The project structure includes two test classes that cover all test scenarios for the FactList class and adding new facts to the XML file.

GUI:
Fact Display: Displays a fact along with its details like author, type, and text.
Navigation: Allows users to navigate through facts using a "Next" button.
Search: Enables users to search for specific facts based on text and search mode (such as by author or type).
Add New Fact: Provides functionality to add new facts by opening a pop-up window with input fields for author, type, and text.
XML File Handling:
The application reads facts from an XML file located at "data/facts.xml" during initialization.
The FactListViewModel class manages the loading and handling of facts from the XML file.
When the application starts, it loads facts from the XML file and displays them to the user.
Adding New Facts:
Users can add new facts through the GUI by clicking the "Add New Fact" button.
Upon clicking this button, a pop-up window opens with input fields for author, type, and text of the new fact.
After entering the necessary information and clicking the "Save" button in the pop-up window, the new fact is added to the XML file.
The application updates the status bar with appropriate messages, such as success or error messages, based on the outcome of adding a new fact.
Test Classes:
The project includes two test classes that cover all test scenarios for the FactList class and the functionality of adding new facts to the XML file.
These test classes ensure the correctness and reliability of the application's core logic and data handling, including reading from and writing to the XML file.
Test scenarios may include loading facts from XML, verifying fact properties, adding new facts, handling edge cases, and ensuring data integrity.

Please see Wiki for more detials.
