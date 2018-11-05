package edu.wgu.dmass13.c196.viewmodel.assessment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.repository.AssessmentRepository;

public class AssessmentListViewModel extends AndroidViewModel {

    private AssessmentRepository _repository;

    private LiveData<List<Assessment>> _allAssessments;

    public AssessmentListViewModel(Application application) {
        super(application);
        _repository = new AssessmentRepository(application);
        _allAssessments = _repository.getAllAssessments();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return _allAssessments;
    }

    public void deleteAssessment(Long assessmentID) {
        _repository.deleteAssessment(assessmentID);
    }
}
