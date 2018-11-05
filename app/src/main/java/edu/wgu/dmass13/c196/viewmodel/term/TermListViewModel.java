package edu.wgu.dmass13.c196.viewmodel.term;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.model.repository.TermRepository;

public class TermListViewModel extends AndroidViewModel {

    private TermRepository _repository;

    private LiveData<List<Term>> _allTerms;

    public TermListViewModel(Application application) {
        super(application);
        _repository = new TermRepository(application);
        _allTerms = _repository.getAllTerms();
    }

    public LiveData<List<Term>> getAllTerms() {
        return _allTerms;
    }

    public void deleteTerm(Long termID) {
        _repository.deleteTerm(termID);
    }
}
