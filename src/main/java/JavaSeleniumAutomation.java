import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JavaSeleniumAutomation {

    public static void main(String[] args) {

        // Initialize objects for file handling, ChatGpt suggestion, email notification starts
        FileHandler fileHandler = new FileHandler();
        ChatGptService chatGptService = new ChatGptService();
        EmailService emailService = new EmailService();
        // Initialize objects for file handling, ChatGpt suggestion, email notification ends

        // Initialize selenium chromedriver starts
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Initialize selenium chromedriver ends

        try {

            List<FileInfo> fileInfos = new ArrayList<>();

            // Step 1: Web Automation with Selenium - LambdaTest Sample App Actual testing starts
            // Test case 1: Navigate to the LambdaTest Sample To-Do App
            driver.get("https://lambdatest.github.io/sample-todo-app/");
            fileInfos.add(new FileInfo("Navigate to the LambdaTest Sample To-Do App", "Completed", String.valueOf(Instant.now())));
            Thread.sleep(2000);

            // Test case 2: Add a new task
            String newTask = "Explore Power Automate and ChatGPT Integration";
            WebElement taskInput = driver.findElement(By.id("sampletodotext"));
            taskInput.sendKeys(newTask);
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            fileInfos.add(new FileInfo("Add a new task", "Completed", String.valueOf(Instant.now())));
            Thread.sleep(2000);

            // Test case 3: Verify the new task was added
            List<WebElement> tasks = driver.findElements(By.cssSelector("li"));
            boolean taskAdded = tasks.stream().anyMatch(task -> task.getText().contains(newTask));
            if (taskAdded) {
                fileInfos.add(new FileInfo("Verify the new task was added", "Completed", String.valueOf(Instant.now())));
            } else {
                fileInfos.add(new FileInfo("Verify the new task was added", "Failed", String.valueOf(Instant.now())));
                return;
            }
            Thread.sleep(2000);

            // Test case 4: Mark the added task as completed
            WebElement addedTaskCheckbox = driver.findElement(By.xpath("//li[contains(.,'" + newTask + "')]/input"));
            addedTaskCheckbox.click();
            fileInfos.add(new FileInfo("Mark the added task as completed", "Completed", String.valueOf(Instant.now())));
            Thread.sleep(2000);

            // Test case 5: Verify the status of the added task
            boolean isChecked = addedTaskCheckbox.isSelected();
            if (isChecked) {
                fileInfos.add(new FileInfo("Verify the status of the added task", "Completed", String.valueOf(Instant.now())));
            } else {
                fileInfos.add(new FileInfo("Verify the status of the added task", "Failed", String.valueOf(Instant.now())));
            }
            // Step 1: Web Automation with Selenium - LambdaTest Sample App Actual testing ends
            Thread.sleep(2000);

            String timestamp = String.valueOf(System.currentTimeMillis());

            // Option 2: Create json file and excel file using testcase results starts
            String jsonFilePath = fileHandler.saveToJsonFile(fileInfos, timestamp);
            String excelPath = fileHandler.writeJsonFileToExcel(jsonFilePath, timestamp);
            // Option 2: Create json file and excel file using testcase results starts

            // Bonus: Use ChatGPT’s API to suggest the next task based on the completed task.
            String nextTask = chatGptService.getNextTask(fileInfos.toString());

            // Include the suggestion in the email or task log along with the test case results.
            emailService.sendNotificationEmail(jsonFilePath, excelPath, nextTask);

        } catch (Exception e) {
            System.err.println("Exception occurred at JavaSeleniumAutomation: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }

}