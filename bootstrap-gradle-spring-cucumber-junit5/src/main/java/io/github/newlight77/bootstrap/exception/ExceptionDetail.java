package io.github.newlight77.bootstrap.exception;

public class ExceptionDetail {
    private String classname;
    private String date;
    private String message;
    private String path;
    private String params;

    public ExceptionDetail(String classname, String date, String message, String path, String params) {
        this.classname = classname;
        this.date = date;
        this.message = message;
        this.path = path;
        this.params = params;
    }
}

