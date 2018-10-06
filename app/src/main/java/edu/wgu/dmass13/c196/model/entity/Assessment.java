package edu.wgu.dmass13.c196.model.entity;

import edu.wgu.dmass13.c196.globals.Enums;
import android.arch.persistence.room.*;

@Entity
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    public long AssessmentID;

    public Enums.AssessmentType AssessmentType;

}
