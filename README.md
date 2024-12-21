Java Selenium Automation Script for LambdaTest Sample To-Do App

This Java Selenium automation script demonstrates automated testing and interactions with the LambdaTest Sample To-Do App. The script integrates Selenium WebDriver with extended functionalities for file handling, API interaction (ChatGPT), and email notifications to enhance its efficiency and output.

1. Features and Capabilities
   
a. Integration with External Services

FileHandler: Manages file operations such as saving test case results in JSON and Excel formats.
ChatGPT API: Fetches suggestions for further tasks post-automation.
EmailService: Automates the sending of email notifications containing JSON/Excel attachments and task suggestions.

b. Overcoming Challenges

Due to subscription limitations, Power Automate was excluded.
The ChatGPT API, operating on a pay-per-use model, is integrated but may have response limitations.

2. Execution Steps
   
a. Selenium WebDriver Setup

WebDriverManager: Ensures the correct version of ChromeDriver is downloaded.
ChromeDriver: Automates browser actions for the LambdaTest Sample To-Do App.

b. Automated Test Case Execution

Navigation: Opens the LambdaTest Sample To-Do App.
Add Task: Automates adding a new task to the to-do list.
Verify Task: Confirms the task addition.
Complete Task: Marks the task as done.
Validate Status: Verifies the updated status of the task.

c. File Operations

Saves results to a JSON file (saveToJsonFile() method).
Converts JSON to Excel (writeJsonFileToExcel() method).

d. ChatGPT API Integration

Suggests further tasks post-automation based on process results.
e. Email Notifications

Sends test results (JSON/Excel files) and task suggestions to stakeholders via email.

f. Exception Handling
Ensures smooth execution with robust error logging and clean-up mechanisms.

3. Class-Level Overview
   
JavaSeleniumAutomation (Main Class): Implements core automation logic.
FileInfo: Manages test case metadata (names, results, timestamps).
EmailService: Handles email notifications.
FileHandler: Facilitates JSON and Excel file operations.
ChatGptService: Integrates ChatGPT API for generating suggestions.
pom.xml: Contains dependencies for seamless execution.

5. Outcome
   
This script illustrates robust web application testing using Java Selenium, integrating auxiliary services like ChatGPT and email notifications. It showcases efficiency, structured reporting, and actionable insights through task suggestions.
