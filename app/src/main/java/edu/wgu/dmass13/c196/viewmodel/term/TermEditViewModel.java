package edu.wgu.dmass13.c196.viewmodel.term;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.v4.util.LongSparseArray;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.entity.Term;
import edu.wgu.dmass13.c196.model.entity.TermCourse;
import edu.wgu.dmass13.c196.model.repository.CourseRepository;
import edu.wgu.dmass13.c196.model.repository.TermRepository;

public class TermEditViewModel extends AndroidViewModel {

    private TermRepository _repository;
    private Term _currentTerm;
    private LiveData<List<Course>> _allCourses;

    public LongSparseArray<Boolean> getCourseCheckState() {
        return _courseCheckState;
    }

    public void setCourseCheckState(LongSparseArray<Boolean> courseCheckState) {
        this._courseCheckState = courseCheckState;
    }

    private LongSparseArray<Boolean> _courseCheckState = new LongSparseArray<Boolean>();


    public TermEditViewModel(Application application) {
        super(application);
        _repository = new TermRepository(application);

        _allCourses = new CourseRepository(application).getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return _allCourses;
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

        LongSparseArray<Boolean> sc = new LongSparseArray<Boolean>();

        for (TermCourse temp : term.SelectedCourses) {
            sc.append(temp.CourseID, true);
        }

        _courseCheckState = sc;
    }

    public Term getTerm() {
        return _currentTerm;
    }


}
