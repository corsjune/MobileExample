package edu.wgu.dmass13.c196.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.CourseMentor;
import edu.wgu.dmass13.c196.model.entity.Term;

@Dao
public interface CourseMentorDAO {

    @Query("select * from coursementor where mentorId = :mentorId")
    LiveData<List<CourseMentor>> getAllCourseMentorsForCourse(long mentorId);

    @Query("select * from coursementor where coursementorid = :coursementorId")
    LiveData<CourseMentor> getCourseMentor(long coursementorId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createCourseMentor(CourseMentor coursementor);

    @Query("delete from coursementor where coursementorid = :coursementorId")
    void deleteCourseMentor(long coursementorId);
}

