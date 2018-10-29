package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

import edu.wgu.dmass13.c196.utilities.StringUtility;

@Entity
public class Term implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long termID;

    public String Title;

    public Date StartDate;

    public Date  EndDate;
}
