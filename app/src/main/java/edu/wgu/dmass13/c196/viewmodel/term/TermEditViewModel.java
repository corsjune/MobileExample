package edu.wgu.dmass13.c196.viewmodel.term;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.model.repository.TermRepository;

public class TermEditViewModel extends AndroidViewModel {

    private TermRepository _repository;
    private Term _currentTerm = new Term();


    public TermEditViewModel(Application application) {
        super(application);
        _repository = new TermRepository(application);
    }

    public void save() {

        if (_currentTerm.TermID == null) {
            _repository.createTerm(_currentTerm);
        } else {
            _repository.updateTerm(_currentTerm);
        }

    }

    public void setTerm(Term term) {
        _currentTerm = term;
    }

    public Term getTerm() {
        return _currentTerm;
    }


}
