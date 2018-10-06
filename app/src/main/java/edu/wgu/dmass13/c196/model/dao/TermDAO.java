package edu.wgu.dmass13.c196.model.dao;

import android.arch.persistence.room.*;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Term;

@Dao
public interface TermDAO {

    @Query("select * from term")
    List<Term> getAllTerms();

    @Query("select * from term where termid = :termId")
    List<Term> getTerm(long termId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTerm(Term term);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTerm(Term term);

    @Query("delete from term where termid = :termId")
    void deleteTerm(long termId);
}

