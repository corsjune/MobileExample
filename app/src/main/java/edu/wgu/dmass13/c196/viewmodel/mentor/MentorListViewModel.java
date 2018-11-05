package edu.wgu.dmass13.c196.viewmodel.mentor;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.extensions.*;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.model.repository.MentorRepository;

public class MentorListViewModel extends AndroidViewModel {

    private MentorRepository _repository;

    private LiveData<List<Mentor>> _allMentors;

    public MentorListViewModel(Application application) {
        super(application);
        _repository = new MentorRepository(application);
        _allMentors = _repository.getAllMentors();
    }

    public LiveData<List<Mentor>> getAllMentors() {
        return _allMentors;
    }

    public void deleteMentor(Long mentorID) {
        _repository.deleteMentor(mentorID);
    }
}
