import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class FileHandler {

    public String saveToJsonFile(List<FileInfo> fileInfos, String timestamp) throws IOException {
        String filePath = "path" + timestamp + ".json";
        try (FileWriter file = new FileWriter(filePath)) {
            JSONArray taskDetailsArray = new JSONArray();
            for (FileInfo fileInfo : fileInfos) {
                JSONObject taskDetails = new JSONObject();
                taskDetails.put("taskName", fileInfo.getTaskName());
                taskDetails.put("status", fileInfo.getStatus());
                taskDetails.put("timestamp", fileInfo.getTimestamp());
                taskDetailsArray.put(taskDetails);
            }
            file.write(taskDetailsArray.toString(4));
            System.out.println("Task details saved to: " + filePath);
        } catch (Exception e) {
            System.err.println("Exception occurred at saveToJsonFile: " + e.getMessage());
            throw e;
        }
        return filePath;
    }

    public String writeJsonFileToExcel(String jsonFilePath, String timestamp) throws IOException {
        String excelFilePath = "  patha " + timestamp + ".xlsx";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            JSONArray taskDetailsArray = new JSONArray(jsonContent.toString());
            Workbook workbook;
            Sheet sheet;
            File excelFile = new File(excelFilePath);

            if (excelFile.exists()) {
                FileInputStream fis = new FileInputStream(excelFile);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Tasks");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Task Name");
                headerRow.createCell(1).setCellValue("Status");
                headerRow.createCell(2).setCellValue("Timestamp");
            }

            int rowNum = sheet.getLastRowNum() + 1;
            for (int i = 0; i < taskDetailsArray.length(); i++) {
                JSONObject taskDetails = taskDetailsArray.getJSONObject(i);
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(taskDetails.getString("taskName"));
                row.createCell(1).setCellValue(taskDetails.getString("status"));
                row.createCell(2).setCellValue(taskDetails.getString("timestamp"));
            }

            FileOutputStream fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);
            workbook.close();
            fos.close();

            System.out.println("Task details successfully added to Excel file: " + excelFilePath);
        } catch (Exception e) {
            System.err.println("Exception occurred at writeJsonFileToExcel: " + e.getMessage());
            throw e;
        }
        return excelFilePath;
    }
}
