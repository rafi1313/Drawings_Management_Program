package model;

import java.util.Date;

public class Project {
    private int id;
    private String projectName;
    private int rate;
    private Date projectDate;
    private Date deadline;
    private int clientId;
    private String clientName;

    public Project(int id, String projectName, int rate, Date projectDate, Date deadline, int clientId, String clientName) {
        this.id = id;
        this.projectName = projectName;
        this.rate = rate;
        this.projectDate = projectDate;
        this.deadline = deadline;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(Date projectDate) {
        this.projectDate = projectDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
