package edu.wgu.dmass13.c196.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import edu.wgu.dmass13.c196.model.entity.*;

@Dao
public abstract class PrepopulatationDAO {

    @Insert
    public abstract void _insertAll(Assessment... assessments);

    @Insert
    public abstract  void _insertAll(Course... courses);

    @Insert
    public abstract void  _insertAll(Mentor... mentors);

    @Insert
    public abstract void _insertAll(Term... terms);

    @Insert
    public abstract void _insertAll(CourseMentor... courseMentors);

    @Insert
    public abstract void _insertAll(CourseAssessment... courseAssessments);

    @Insert
    public abstract void _insertAll(TermCourse... termCourses);

}
