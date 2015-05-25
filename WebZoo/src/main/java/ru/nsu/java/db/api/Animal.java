package ru.nsu.java.db.api;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Animal {

    private Integer id;
    private String name;
    private String kind;
    private Date comingDate;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String iName) {
        this.name = iName;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {

        return kind;
    }

    public void setComingDate(Date comingDate) {
        this.comingDate = comingDate;
    }

    public String getComingDate() {
        String curStringDate = new SimpleDateFormat("dd.MM.yyyy").format(comingDate);
        return curStringDate;
    }
}
