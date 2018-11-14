package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class CourseAssessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long CourseAssessmentID = null;

    public Long CourseID;

    public Long AssessmentID;

}
