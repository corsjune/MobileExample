package edu.wgu.dmass13.c196.viewmodel.course;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.repository.CourseRepository;

public class CourseEditViewModel extends AndroidViewModel {

    private CourseRepository _repository;
    private Course _currentCourse = new Course();


    public CourseEditViewModel(Application application) {
        super(application);
        _repository = new CourseRepository(application);
    }

    public void save() {

        if (_currentCourse.CourseID == null) {
            _repository.createCourse(_currentCourse);
        } else {
            _repository.updateCourse(_currentCourse);
        }

    }

    public void setCourse(Course course) {
        _currentCourse = course;
    }

    public Course getCourse() {
        return _currentCourse;
    }


}
