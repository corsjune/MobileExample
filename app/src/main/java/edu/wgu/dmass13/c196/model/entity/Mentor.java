package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;

import java.io.Serializable;

@Entity
public class Mentor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long MentorID = null;

    public String Name;

    public String PhoneNumber;

    public String Email;
}
