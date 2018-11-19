package edu.wgu.dmass13.c196.model.entity;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.wgu.dmass13.c196.utilities.StringUtility;

@Entity
public class Term implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long TermID;

    public String Title;

    public Date StartDate;

    public Date  EndDate;

    @Ignore
    public List<TermCourse> SelectedCourses = new ArrayList<TermCourse>();


}
