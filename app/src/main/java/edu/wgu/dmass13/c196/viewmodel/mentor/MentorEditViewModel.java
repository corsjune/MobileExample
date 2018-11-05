package edu.wgu.dmass13.c196.viewmodel.mentor;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.model.repository.MentorRepository;

public class MentorEditViewModel extends AndroidViewModel {

    private MentorRepository _repository;
    private Mentor _currentMentor = new Mentor();


    public MentorEditViewModel(Application application) {
        super(application);
        _repository = new MentorRepository(application);
    }

    public void save() {

        if (_currentMentor.MentorID == null) {
            _repository.createMentor(_currentMentor);
        } else {
            _repository.updateMentor(_currentMentor);
        }

    }

    public void setMentor(Mentor mentor) {
        _currentMentor = mentor;
    }

    public Mentor getMentor() {
        return _currentMentor;
    }


}
