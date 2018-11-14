package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CourseMentor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long CourseMentorID = null;

    public Long CourseID;

    public Long MentorID;

}
