class FileInfo {
    private final String taskName;
    private final String status;
    private final String timestamp;

    public FileInfo(String taskName, String status, String timestamp) {
        this.taskName = taskName;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
