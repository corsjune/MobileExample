package edu.wgu.dmass13.c196.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.*;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.model.entity.TermCourse;

@Dao
public abstract class TermDAO {


    public LiveData<List<Term>> getAllTerms() {
        LiveData<List<Term>> terms = _getAllTerms();
        LiveData<List<Term>> returnValue;

        returnValue = Transformations.map(terms, (List<Term> newData) -> {
            return getHydratedTerm(newData);
        });

        return returnValue;
    }

    public List<Term> getHydratedTerm(List<Term> terms) {

        List<Term> returnValue = new ArrayList<>();

        for (Term temp : terms) {
            returnValue.add(getTermWithCourses(temp));
        }

        return returnValue;
    }

    public LiveData<Term> getSingleTerm(long termId) {
        //https://stackoverflow.com/questions/44667160/android-room-insert-relation-entities-using-room
        //https://proandroiddev.com/clean-easy-new-how-to-architect-your-app-part-4-livedata-transformations-f0fd9f313ec6
        LiveData<Term> term = _getSingleTerm(termId);
        LiveData<Term> returnValue;

        returnValue = Transformations.map(term, (Term newData) -> {
            return getTermWithCourses(newData);
        });

        return returnValue;
    }

    private Term getTermWithCourses(Term myTerm) {
        myTerm.SelectedCourses = _getAllTermCoursesForTerm(myTerm.TermID);

        if (myTerm.SelectedCourses == null) {
            myTerm.SelectedCourses = new ArrayList<TermCourse>();
        }

        return myTerm;
    }

    @Transaction
    public void createTerm(Term term) {
        long id = _createTerm(term);

        for (TermCourse temp : term.SelectedCourses) {
            temp.TermID = id;
            _createTermCourse(temp);
        }
    }

    @Transaction
    public void updateTerm(Term term) {
        _updateTerm(term);
        _deleteTermCourse(term.TermID);
        for (TermCourse temp : term.SelectedCourses) {
            _createTermCourse(temp);
        }

    }


    @Query("select * from term")
    public abstract LiveData<List<Term>> _getAllTerms();

    @Query("select * from term where termid = :termId")
    public abstract LiveData<Term> _getSingleTerm(long termId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long _createTerm(Term term);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _updateTerm(Term term);

    @Query("delete from term where termid = :termId")
    public abstract void deleteTerm(long termId);

    @Query("select * from termcourse where termId = :termId")
    public abstract List<TermCourse> _getAllTermCoursesForTerm(long termId);

    @Query("delete from termcourse where termId = :termId")
    public abstract void _deleteTermCourse(long termId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _createTermCourse(TermCourse term);


}

