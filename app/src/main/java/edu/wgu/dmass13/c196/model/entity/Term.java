package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Term implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long TermID;

    public String Title;

    public Date StartDate;

    public Date  EndDate;

    @Ignore
    public List<TermCourse> SelectedCourses = new ArrayList<TermCourse>();


}
