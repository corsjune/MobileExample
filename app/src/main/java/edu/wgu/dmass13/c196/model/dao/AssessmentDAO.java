package edu.wgu.dmass13.c196.model.dao;

import android.arch.persistence.room.*;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Assessment;

@Dao
public interface AssessmentDAO {


    @Query("select * from assessment")
    List<Assessment> getAllAssessments();

    @Query("select * from assessment where assessmentid = :assessmentId")
    List<Assessment> getAssessment(long assessmentId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAssessment(Assessment assessment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(Assessment assessment);

    @Query("delete from assessment where assessmentid = :assessmentId")
    void deleteAssessment(long assessmentId);

}
