package com.example.spring_project_work.dto;

public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private Integer estimatedHours;
    private Integer completedHours;
    private Integer remainedHours;

    public TaskDTO() {}

    // Constructor matching the query result
    public TaskDTO(String title, String state, String description, Integer estimation, Integer remainingEffort, Integer completedHours) {
        this.title = title;
        this.status = state;
        this.description = description;
        this.estimatedHours = estimation;
        this.remainedHours = remainingEffort;
        this.completedHours = completedHours;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getCompletedHours() {
        return completedHours;
    }

    public void setCompletedHours(Integer completedHours) {
        this.completedHours = completedHours;
    }

    public Integer getRemainedHours() {
        return remainedHours;
    }

    public void setRemainedHours(Integer remainedHours) {
        this.remainedHours = remainedHours;
    }
}
