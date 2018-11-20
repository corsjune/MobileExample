package edu.wgu.dmass13.c196.viewmodel.course;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.v4.util.LongSparseArray;

import java.util.List;

import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.entity.Course;
import edu.wgu.dmass13.c196.model.entity.CourseAssessment;
import edu.wgu.dmass13.c196.model.entity.CourseMentor;
import edu.wgu.dmass13.c196.model.entity.Mentor;
import edu.wgu.dmass13.c196.model.repository.AssessmentRepository;
import edu.wgu.dmass13.c196.model.repository.CourseRepository;
import edu.wgu.dmass13.c196.model.repository.MentorRepository;

public class CourseEditViewModel extends AndroidViewModel {

    private CourseRepository _repository;
    private Course _currentCourse;
    private LiveData<List<Mentor>> _allMentors;
    private LiveData<List<Assessment>> _allAssessments;
    private LongSparseArray<Boolean> _mentorCheckState = new LongSparseArray<Boolean>();
    private LongSparseArray<Boolean> _assessmentCheckState = new LongSparseArray<Boolean>();

    public LiveData<List<Mentor>> getAllMentors() {
        return _allMentors;
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return _allAssessments;
    }

    public LongSparseArray<Boolean> getMentorCheckState() {
        return _mentorCheckState;
    }

    public void setMentorCheckState(LongSparseArray<Boolean> mentorCheckState) {
        this._mentorCheckState = mentorCheckState;
    }

    public LongSparseArray<Boolean> getAssessmentCheckState() {
        return _assessmentCheckState;
    }

    public void setAssessmentCheckState(LongSparseArray<Boolean> assessmentCheckState) {
        this._assessmentCheckState = assessmentCheckState;
    }

    public CourseEditViewModel(Application application) {
        super(application);
        _repository = new CourseRepository(application);
        _allMentors = new MentorRepository(application).getAllMentors();
        _allAssessments = new AssessmentRepository(application).getAllAssessments();
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

        LongSparseArray<Boolean> scMentor = new LongSparseArray<Boolean>();

        for (CourseMentor temp : course.assignedMentors) {
            scMentor.append(temp.MentorID, true);
        }

        _mentorCheckState = scMentor;

        LongSparseArray<Boolean> scAssessment = new LongSparseArray<Boolean>();

        for (CourseAssessment temp : course.assignedAssignments) {
            scAssessment.append(temp.AssessmentID, true);
        }

        _assessmentCheckState = scAssessment;


    }

    public Course getCourse() {
        return _currentCourse;
    }


}
