package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TermCourse implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long TermCourseID = null;

    public Long CourseID;

    public Long TermID;

}
