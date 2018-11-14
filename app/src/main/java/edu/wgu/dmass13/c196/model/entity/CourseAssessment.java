package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = Assessment.class,
                parentColumns = "AssessmentID",
                childColumns = "AssessmentID"),
        @ForeignKey(entity = Course.class,
                parentColumns = "CourseID",
                childColumns = "CourseID")
})
public class CourseAssessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long CourseAssessmentID = null;

    public Long CourseID;

    public Long AssessmentID;

}
