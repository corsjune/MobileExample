package edu.wgu.dmass13.c196.model.dao;

import android.arch.persistence.room.*;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Course;

@Dao
public interface CourseDAO {

    @Query("select * from course")
    List<Course> getAllCourses();

    @Query("select * from course where courseid = :courseId")
    List<Course> getCourse(long courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createCourse(Course course);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(Course course);

    @Query("delete from course where courseid = :courseId")
    void deleteCourse(long courseId);

}
