package edu.wgu.dmass13.c196.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.model.repository.MentorRepository;

public class MentorViewModel extends AndroidViewModel {

    private MentorRepository _repository;

    private LiveData<List<Mentor>> _allMentors;

    public MentorViewModel(Application application) {
        super(application);
        _repository = new MentorRepository(application);
        _allMentors = _repository.getAllMentors();
    }

    LiveData<List<Mentor>> getAllMentors() {
        return _allMentors;
    }

    public void insert(Mentor mentor) {
        _repository.createMentor(mentor);
    }
}
