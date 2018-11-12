package edu.wgu.dmass13.c196.model.entity;

import edu.wgu.dmass13.c196.globals.Enums;
import android.arch.persistence.room.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Assessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long AssessmentID = null;

    public short AssessmentType;

    public String Name;

    public Date AssessmentDate;

    public Date GoalDate;

    public boolean GoalAlert;
}
