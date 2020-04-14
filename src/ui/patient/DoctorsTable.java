package ui.patient;

public class DoctorsTable {
    String docName, docDepartment, appDate, appTime, docRoom;

    /**
     * Constructor for table object.
     * @param docName name of the doctor.
     * @param docDepartment department of the doctor.
     * @param appDate date of appointment.
     * @param appTime time of appointment.
     * @param docRoom room of the doctor.
     */
    public DoctorsTable(String docName, String docDepartment, String appDate, String appTime, String docRoom) {
        this.docName = docName;
        this.docDepartment = docDepartment;
        this.appDate = appDate;
        this.appTime = appTime;
        this.docRoom = docRoom;
    }

    /**
     * gets doctor name
     * @return doctor name
     */
    public String getDocName() {
        return docName;
    }

    /**
     * sets doctor name
     * @param docName name to be set
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * gets doctor department
     * @return doctor department.
     */
    public String getDocDepartment() {
        return docDepartment;
    }

    /**
     * sets doctor department.
     * @param docDepartment department to be set.
     */
    public void setDocDepartment(String docDepartment) {
        this.docDepartment = docDepartment;
    }

    /**
     * gets appointment date.
     * @return appointment date.
     */
    public String getAppDate() {
        return appDate;
    }

    /**
     * sets appointment date
     * @param appDate date to be set.
     */
    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    /**
     * gets appointment time
     * @return appointment time.
     */
    public String getAppTime() {
        return appTime;
    }

    /**
     * sets appointment time
     * @param appTime time to be set.
     */
    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    /**
     * gets doctor room.
     * @return doctor's room.
     */
    public String getDocRoom() {
        return docRoom;
    }

    /**
     * sets doctor room
     * @param docRoom room to be set.
     */
    public void setDocRoom(String docRoom) {
        this.docRoom = docRoom;
    }
}
