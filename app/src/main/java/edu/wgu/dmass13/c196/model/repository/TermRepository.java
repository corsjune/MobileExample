package edu.wgu.dmass13.c196.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.wgu.dmass13.c196.model.dao.TermDAO;
import edu.wgu.dmass13.c196.model.database.AppDatabase;
import edu.wgu.dmass13.c196.model.entity.Term;

public class TermRepository {

    private TermDAO _myTermDao;
    private LiveData<List<Term>> _AllTerms;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        _myTermDao = db.termDAO();
        _AllTerms = _myTermDao.getAllTerms();
    }

    public LiveData<List<Term>> getAllTerms() {
        return _AllTerms;
    }

    public void createTerm(Term term) {
        new insertAsyncTask(_myTermDao).execute(term);
    }

    public void updateTerm(Term term) {
        new updateAsyncTask(_myTermDao).execute(term);
    }

    public void deleteTerm(Long termId) {
        new deleteAsyncTask(_myTermDao).execute(termId);
    }

    public LiveData<Term> selectTerm(Long termId) {
        return _myTermDao.getSingleTerm(termId);
    }

    private static class insertAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDAO mAsyncTaskDao;

        insertAsyncTask(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.createTerm(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDAO mAsyncTaskDao;

        updateAsyncTask(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.updateTerm(params[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Long, Void, Void> {

        private TermDAO mAsyncTaskDao;

        deleteAsyncTask(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            mAsyncTaskDao.deleteTerm(params[0]);
            return null;
        }
    }

}