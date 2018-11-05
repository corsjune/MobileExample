package edu.wgu.dmass13.c196.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.wgu.dmass13.c196.model.dao.MentorDAO;
import edu.wgu.dmass13.c196.model.database.AppDatabase;
import edu.wgu.dmass13.c196.model.entity.Mentor;

public class MentorRepository {

    private MentorDAO _myMentorDao;
    private LiveData<List<Mentor>> _AllMentors;

    public MentorRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        _myMentorDao = db.mentorDAO();
        _AllMentors = _myMentorDao.getAllMentors();
    }

    public LiveData<List<Mentor>> getAllMentors() {
        return _AllMentors;
    }

    public void createMentor(Mentor mentor) {
        new insertAsyncTask(_myMentorDao).execute(mentor);
    }

    public void updateMentor(Mentor mentor) {
        new updateAsyncTask(_myMentorDao).execute(mentor);
    }

    public void deleteMentor(Long mentorId) {
        new deleteAsyncTask(_myMentorDao).execute(mentorId);
    }

    public LiveData<Mentor> selectMentor(Long mentorId) {
        return _myMentorDao.getMentor(mentorId);
    }

    private static class insertAsyncTask extends AsyncTask<Mentor, Void, Void> {

        private MentorDAO mAsyncTaskDao;

        insertAsyncTask(MentorDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mentor... params) {
            mAsyncTaskDao.createMentor(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Mentor, Void, Void> {

        private MentorDAO mAsyncTaskDao;

        updateAsyncTask(MentorDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mentor... params) {
            mAsyncTaskDao.updateMentor(params[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Long, Void, Void> {

        private MentorDAO mAsyncTaskDao;

        deleteAsyncTask(MentorDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            mAsyncTaskDao.deleteMentor(params[0]);
            return null;
        }
    }

}