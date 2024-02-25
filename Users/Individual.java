package Users;

import java.time.LocalDate;
import java.util.ArrayList;


public class Individual extends User {
    private String name;
    private String address;
    private String cnp;
    private LocalDate dateBirth;
    private JuridicPerson placeJob;
    private Position position;
    private boolean accordSuperior;

    public boolean getAccordSuperior() {
        return accordSuperior;
    }

    public void setAccordSuperior(boolean accord_superior_firm) {
        accordSuperior = accord_superior_firm;

    }


    public Individual(int id, String name, String Address, String cnp, LocalDate date, JuridicPerson job, Position pos, boolean accordSuperior) {
        setIdUser(id);
        setName(name);
        setAddress(Address);
        setCnp(cnp);
        setDateBirth(date);
        setPlaceJob(job);
        setPosition(pos);
        setAccordSuperior(accordSuperior);


        listAttributes = new ArrayList<>();
        listAttributes.add(String.valueOf(id));
        listAttributes.add(name);
        listAttributes.add(Address);
        listAttributes.add(cnp);
        listAttributes.add(date.toString());
        listAttributes.add(job.getOfficialName());
        listAttributes.add(pos.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String CNP_user) {
        cnp = CNP_user;
    }
    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public JuridicPerson getPlaceJob() {
        return placeJob;
    }

    public void setPlaceJob(JuridicPerson placeJOB_user) {
        placeJob = placeJOB_user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position_firm) {
        position = position_firm;
    }


}
