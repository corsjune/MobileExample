package edu.wgu.dmass13.c196.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.CourseAssessment;

@Dao
public interface CourseAssessmentDAO {

    @Query("select * from courseassessment where courseid = :courseid")
    LiveData<List<CourseAssessment>> getAllCourseAssessmentsForCourse(long courseid);

    @Query("select * from courseassessment where courseassessmentid = :courseassessmentId")
    LiveData<CourseAssessment> getCourseassessment(long courseassessmentId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createCourseAssessment(CourseAssessment courseassessment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourseAssessment(CourseAssessment courseassessment);

    @Query("delete from courseassessment where courseassessmentid = :courseassessmentId")
    void deleteCourseAssessment(long courseassessmentId);
}

