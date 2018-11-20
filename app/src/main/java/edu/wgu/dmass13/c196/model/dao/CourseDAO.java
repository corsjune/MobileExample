package edu.wgu.dmass13.c196.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.*;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.entity.CourseMentor;
import edu.wgu.dmass13.c196.model.entity.CourseAssessment;

@Dao
public abstract class CourseDAO {

    public LiveData<List<Course>> getAllCourses() {
        LiveData<List<Course>> courses = _getAllCourses();
        LiveData<List<Course>> returnValue;

        returnValue = Transformations.map(courses, (List<Course> newData) -> {
            return getHydratedCourse(newData);
        });

        return returnValue;
    }

    public List<Course> getHydratedCourse(List<Course> courses) {

        List<Course> returnValue = new ArrayList<>();

        for (Course temp : courses) {
            returnValue.add(getCourseWithChildSets(temp));
        }

        return returnValue;
    }

    public LiveData<Course> getSingleCourse(long courseId) {
        //https://stackoverflow.com/questions/44667160/android-room-insert-relation-entities-using-room
        //https://proandroiddev.com/clean-easy-new-how-to-architect-your-app-part-4-livedata-transformations-f0fd9f313ec6
        LiveData<Course> course = _getSingleCourse(courseId);
        LiveData<Course> returnValue;

        returnValue = Transformations.map(course, (Course newData) -> {
            return getCourseWithChildSets(newData);
        });

        return returnValue;
    }

    private Course getCourseWithChildSets(Course myCourse) {
        myCourse.assignedAssignments = _getAllCourseAssessmentForCourse(myCourse.CourseID);
        myCourse.assignedMentors = _getAllCourseMentorForCourse(myCourse.CourseID);

        if (myCourse.assignedAssignments == null) {
            myCourse.assignedAssignments = new ArrayList<CourseAssessment>();
        }

        if (myCourse.assignedMentors == null) {
            myCourse.assignedMentors = new ArrayList<CourseMentor>();
        }
        return myCourse;
    }

    @Transaction
    public void createCourse(Course course) {
        long id = _createCourse(course);

        for (CourseAssessment temp : course.assignedAssignments) {
            temp.CourseID = id;
            _createCourseAssessment(temp);
        }

        for (CourseMentor temp : course.assignedMentors) {
            temp.CourseID = id;
            _createCourseMentor(temp);
        }
    }

    @Transaction
    public void updateCourse(Course course) {
        _updateCourse(course);
        _deleteCourseMentor(course.CourseID);
        for (CourseMentor temp : course.assignedMentors) {
            _createCourseMentor(temp);
        }

        _deleteCourseAssessment(course.CourseID);
        for (CourseAssessment temp : course.assignedAssignments) {
            _createCourseAssessment(temp);
        }
    }

    @Query("select * from course")
    public abstract LiveData<List<Course>> _getAllCourses();

    @Query("select * from course where courseid = :courseId")
    public abstract LiveData<Course> _getSingleCourse(long courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long _createCourse(Course course);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _updateCourse(Course course);

    @Query("delete from course where courseid = :courseId")
    public abstract void deleteCourse(long courseId);

    @Query("select * from coursementor where courseid = :courseId")
    public abstract List<CourseMentor> _getAllCourseMentorForCourse(long courseId);

    @Query("delete from coursementor where courseid = :courseId")
    public abstract void _deleteCourseMentor(long courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _createCourseMentor(CourseMentor cm);

    @Query("select * from courseassessment where courseid = :courseId")
    public abstract List<CourseAssessment> _getAllCourseAssessmentForCourse(long courseId);

    @Query("delete from courseassessment where courseid = :courseId")
    public abstract void _deleteCourseAssessment(long courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _createCourseAssessment(CourseAssessment ca);


}
