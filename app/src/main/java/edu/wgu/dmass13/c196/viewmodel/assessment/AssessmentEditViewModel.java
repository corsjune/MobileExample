package edu.wgu.dmass13.c196.viewmodel.assessment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.repository.AssessmentRepository;

public class AssessmentEditViewModel extends AndroidViewModel {

    private AssessmentRepository _repository;
    private Assessment _currentAssessment = new Assessment();


    public AssessmentEditViewModel(Application application) {
        super(application);
        _repository = new AssessmentRepository(application);
    }

    public void save() {

        if (_currentAssessment.AssessmentID == null) {
            _repository.createAssessment(_currentAssessment);
        } else {
            _repository.updateAssessment(_currentAssessment);
        }

    }

    public void setAssessment(Assessment assessment) {
        _currentAssessment = assessment;
    }

    public Assessment getAssessment() {
        return _currentAssessment;
    }


}
