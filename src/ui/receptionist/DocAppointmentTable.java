package ui.receptionist;

public class DocAppointmentTable {
    String name, date, time;
    String phoneNo;

    public DocAppointmentTable(String name, String date, String time, String phoneNo){
        this.name = name;
        this.phoneNo = phoneNo;
        this.date = date;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
