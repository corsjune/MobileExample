package edu.wgu.dmass13.c196.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.model.entity.TermCourse;

@Dao
public interface TermCourseDAO {

    @Query("select * from termcourse where termId = :termId")
    List<TermCourse> getAllTermCoursesForTerm(long termId);




    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTermCourse(TermCourse term);


}

