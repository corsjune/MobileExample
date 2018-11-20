package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Ignore
    public List<CourseMentor> assignedMentors = new ArrayList<CourseMentor>();

    @Ignore
    public List<CourseAssessment> assignedAssignments = new ArrayList<CourseAssessment>();

}
