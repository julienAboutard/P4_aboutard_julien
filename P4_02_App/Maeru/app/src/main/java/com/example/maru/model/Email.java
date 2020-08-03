package com.example.maru.model;

import java.util.List;
import java.util.Objects;

public class Email {

    private long id;

    private List<String> listmail;

    public Email (long id, List<String > listmail) {

        this.id = id;
        this.listmail = listmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getListmail() {
        return listmail;
    }

    public void setListmail(List<String> listmail) {
        this.listmail = listmail;
    }

    public void addMail(String mail) {
        listmail.add(mail);
    }

    public  void deleteMail(String mail) {
        listmail.remove(mail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email mail = (Email) o;
        return Objects.equals(id, mail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
