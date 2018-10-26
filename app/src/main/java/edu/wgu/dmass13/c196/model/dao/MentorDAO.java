package edu.wgu.dmass13.c196.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Mentor;

@Dao
public interface MentorDAO {


    @Query("select * from mentor")
    LiveData<List<Mentor>> getAllMentors();

    @Query("select * from mentor where mentorid = :mentorId")
    List<Mentor> getMentor(long mentorId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createMentor(Mentor mentor);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMentor(Mentor mentor);

    @Query("delete from mentor where mentorid = :mentorId")
    void deleteMentor(long mentorId);

}
