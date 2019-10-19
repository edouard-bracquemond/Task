package com.example.afe.task.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Date;


@Entity(tableName = "task")
public class Task implements Comparable<Task>{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    private int id;



    public boolean isAtemps() {
        return atemps;
    }

    public void setAtemps(boolean atemps) {
        this.atemps = atemps;
    }

    public Task(String name, Date dateDebut, Date dateFin, boolean termine, String description, int priorite, boolean atemps) {

        this.name = name;
        this.dateDebut = dateDebut;
        this.termine = termine;
        this.description = description;
        this.priorite = priorite;
        this.dateFin=dateFin;
        this.termine=termine;
        this.atemps = atemps;
    }


    @ColumnInfo(name="task_name")
    private String name;



    @ColumnInfo(name="task_date_debut")
    private Date dateDebut;



    @ColumnInfo(name="task_date_fin")
    private Date dateFin;


    @ColumnInfo(name="task_termine")
    private boolean termine;
    @ColumnInfo(name="task_atemps")
    private boolean atemps;


    @ColumnInfo(name="task_description")
    private String description;

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    @ColumnInfo(name="task_priorite")
    private int priorite;

    @Override
    public int compareTo(Task t){
        return getDateFin().compareTo(t.getDateFin());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean getTermine() {
        return termine;
    }

    public void setTermine(boolean termine) {
        this.termine = termine;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
