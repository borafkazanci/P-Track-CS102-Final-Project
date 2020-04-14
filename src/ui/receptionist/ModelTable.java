package ui.receptionist;

import javax.jws.WebParam;

public class ModelTable {
    String name, birthDate, id, insurance, sex, bloodType;

    public ModelTable(String name, String birthDate, String id, String insurance, String sex, String bloodType){
        this.name = name;
        this.birthDate = birthDate;
        this.id = id;
        this.insurance = insurance;
        this.sex = sex;
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getSex() {return sex;}

    public void setSex(String sex) {this.sex = sex;}

    public String getBloodType() {return bloodType;}

    public void setBloodType(String bloodType) {this.bloodType = bloodType;}
}
