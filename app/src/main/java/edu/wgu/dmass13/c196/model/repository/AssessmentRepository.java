package edu.wgu.dmass13.c196.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.wgu.dmass13.c196.model.dao.AssessmentDAO;
import edu.wgu.dmass13.c196.model.database.AppDatabase;
import edu.wgu.dmass13.c196.model.entity.Assessment;

public class AssessmentRepository {

    private AssessmentDAO _myAssessmentDao;
    private LiveData<List<Assessment>> _AllAssessments;

    public AssessmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        _myAssessmentDao = db.assessmentDAO();
        _AllAssessments = _myAssessmentDao.getAllAssessments();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return _AllAssessments;
    }

    public void createAssessment(Assessment assessment) {
        new insertAsyncTask(_myAssessmentDao).execute(assessment);
    }

    public void updateAssessment(Assessment assessment) {
        new updateAsyncTask(_myAssessmentDao).execute(assessment);
    }

    public void deleteAssessment(Long assessmentId) {
        new deleteAsyncTask(_myAssessmentDao).execute(assessmentId);
    }

    public LiveData<Assessment> selectAssessment(Long assessmentId) {
        return _myAssessmentDao.getSingleAssessment(assessmentId);
    }

    private static class insertAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        insertAsyncTask(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDao.createAssessment(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        updateAsyncTask(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDao.updateAssessment(params[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Long, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        deleteAsyncTask(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            mAsyncTaskDao.deleteAssessment(params[0]);
            return null;
        }
    }

}