package ui.doctor;

public class UpcomingTable {
    String name, appDate, appTime, phoneNo;

    public UpcomingTable(String name, String appDate, String appTime, String phoneNo){
        this.name = name;
        this.appDate = appDate;
        this.appTime = appTime;
        this.phoneNo = phoneNo;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }
}
