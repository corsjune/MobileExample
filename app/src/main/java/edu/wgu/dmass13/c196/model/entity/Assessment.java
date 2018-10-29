package edu.wgu.dmass13.c196.model.entity;

import edu.wgu.dmass13.c196.globals.Enums;
import android.arch.persistence.room.*;

import java.io.Serializable;

@Entity
public class Assessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long AssessmentID;

    public Enums.AssessmentType AssessmentType;

}
