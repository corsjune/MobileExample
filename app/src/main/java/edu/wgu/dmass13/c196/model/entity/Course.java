package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

import edu.wgu.dmass13.c196.globals.Enums;

@Entity
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long CourseID = null;

    public String CourseTitle;

    public String Status;

    public Date StartDate;

    public Date EndDate;

    public boolean StartDateAlert;

    public boolean EndDateAlert;

    public String Notes;

}
