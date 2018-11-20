package edu.wgu.dmass13.c196.viewmodel.course;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.repository.CourseRepository;

public class CourseListViewModel extends AndroidViewModel {

    private CourseRepository _repository;

    private LiveData<List<Course>> _allCourses;

    public CourseListViewModel(Application application) {
        super(application);
        _repository = new CourseRepository(application);
        _allCourses = _repository.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return _allCourses;
    }

    public void deleteCourse(Long courseID) {
        _repository.deleteCourse(courseID);
    }
}
