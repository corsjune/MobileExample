package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;

import java.io.Serializable;

import edu.wgu.dmass13.c196.utilities.StringUtility;

@Entity
public class Mentor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long mentorID;

    public String Name;

    public String PhoneNumber;

    public String Email;
}
