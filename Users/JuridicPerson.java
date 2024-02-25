package Users;

import java.time.LocalDate;
import java.util.ArrayList;

public class JuridicPerson extends User {
    private String officialName;
    private String Address;
    private String juridicForm;
    private String uniqueCode;
    private String activityCod;
    private LocalDate dateBirth;

    public JuridicPerson(int id, String name, String Address, String juridicForm, String uniqueCode, String activity_COD, LocalDate dateBirth, String infixExpressionOfAccessPolicy) {

        setIdUser(id);
        setOfficialName(name);
        setAddress(Address);
        setJuridicForm(juridicForm);
        setUniqueCode(uniqueCode);
        setActivityCod(activity_COD);
        setDateBirth(dateBirth);
        setInfixExpressionOfAccessPolicy(infixExpressionOfAccessPolicy);


        this.listAttributes = new ArrayList<>();

        listAttributes.add(String.valueOf(id));
        listAttributes.add(name);
        listAttributes.add(Address);
        listAttributes.add(juridicForm);
        listAttributes.add(uniqueCode);
        listAttributes.add(activity_COD);
        listAttributes.add(dateBirth.toString());

    }

    public String getOfficialName() {
        return officialName;
    }


    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }


    public String getAddress() {
        return Address;
    }


    public void setAddress(String address) {
        Address = address;
    }


    public String getJuridicForm() {
        return juridicForm;
    }


    public void setJuridicForm(String juridicForm) {
        this.juridicForm = juridicForm;
    }


    public String getUniqueCode() {
        return uniqueCode;
    }


    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }


    public String getActivityCod() {
        return activityCod;
    }


    public void setActivityCod(String activityCod) {
        this.activityCod = activityCod;
    }


    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
}
