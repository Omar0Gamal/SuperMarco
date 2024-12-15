package com.supermacro;

public class Report {

    private int reportId;
    private String title;
    private String description;
    private MarketingEmp author;
    private static int count = 1;

    public Report(String title, String description, MarketingEmp author) {
        this.reportId = count;
        count++;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MarketingEmp getAuthor() {
        return author;
    }

    public void setAuthor(MarketingEmp author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return """
                Report {
                    reportId = %reportId%
                    title = '%title%'
                    description = '%description%'
                    author = '%author%'
                }""".replace("%reportId%", String.valueOf(reportId))
                .replace("%title%", title)
                .replace("%description%", description)
                .replace("%author%", author.getUsername());
    }

}
