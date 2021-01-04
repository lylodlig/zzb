package com.huania.eew_bid.utils.upload;

public class FileData {
    private String path;
    private String fileName;
    private String fileId;

    public FileData() {
    }

    public FileData(String filename, String path) {
        this.fileName = filename;
        this.path = path;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj != null && obj instanceof FileData && fileName.equals(((FileData) obj).fileName));
    }
}
