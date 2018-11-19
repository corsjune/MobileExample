package edu.wgu.dmass13.c196.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.wgu.dmass13.c196.model.dao.CourseDAO;
import edu.wgu.dmass13.c196.model.database.AppDatabase;
import edu.wgu.dmass13.c196.model.entity.Course;

public class CourseRepository {

    private CourseDAO _myCourseDao;
    private LiveData<List<Course>> _AllCourses;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        _myCourseDao = db.courseDAO();
        _AllCourses = _myCourseDao.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return _AllCourses;
    }

    public void createCourse(Course course) {
        new insertAsyncTask(_myCourseDao).execute(course);
    }

    public void updateCourse(Course course) {
        new updateAsyncTask(_myCourseDao).execute(course);
    }

    public void deleteCourse(Long courseId) {
        new deleteAsyncTask(_myCourseDao).execute(courseId);
    }

    public LiveData<Course> selectCourse(Long courseId) {
        return _myCourseDao.getSingleCourse(courseId);
    }

    private static class insertAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        insertAsyncTask(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.createCourse(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        updateAsyncTask(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.updateCourse(params[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Long, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        deleteAsyncTask(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            mAsyncTaskDao.deleteCourse(params[0]);
            return null;
        }
    }

}