package ui.receptionist;

public class DoctorTable {
    String name, department, roomNumber, availability;
    String phoneNo;

    public DoctorTable(String name, String department, String roomNumber, String availability, String phoneNo){
        this.name = name;
        this.department = department;
        this.roomNumber = roomNumber;
        this.availability = availability;
        this.phoneNo = phoneNo;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


}
