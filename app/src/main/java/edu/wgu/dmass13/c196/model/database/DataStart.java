package edu.wgu.dmass13.c196.model.database;

import java.util.ArrayList;

import edu.wgu.dmass13.c196.globals.Enums;
import edu.wgu.dmass13.c196.model.entity.Assessment;
import edu.wgu.dmass13.c196.model.entity.*;

public class DataStart {

    public static  Mentor[] getMentor() {
        ArrayList<Mentor> returnValue = new ArrayList<>();
        Mentor x  = new Mentor();
        x.Name = "Wayne Massey";
        x.MentorID=1L;
        x.Email="Wayne@WhiteHouse.Com";
        x.PhoneNumber="704-555-1212";
        returnValue.add(x);

        Mentor y = new Mentor();
        y.Name = "George Floyd";
        y.MentorID=2L;
        y.Email="Floyd@Pink.Com";
        y.PhoneNumber="518-555-1212";
        returnValue.add(y);


        return returnValue.toArray(new Mentor[returnValue.size()]);
    }

    public static  Assessment[] getAssessment() {
        ArrayList<Assessment> returnValue = new ArrayList<>();
        Assessment x  = new Assessment();

        x.Name ="C196";
        x.AssessmentType = Enums.AssessmentType.Performance;
        x.AssessmentID = 1L;

        returnValue.add(x);

        return returnValue.toArray(new Assessment[returnValue.size()]);
    }


    public static Course[] getCourse() {
        ArrayList<Course> returnValue = new ArrayList<>();

        Course x  = new Course();
        x.CourseTitle ="Mobile Apps";
        x.Notes ="Hooray! This App is finally Done!!";
        x.Status ="Hopefully Done :-)";
        x.CourseID=1L;

        returnValue.add(x);

        return returnValue.toArray(new Course[returnValue.size()]);
    }


    public static CourseAssessment[] getCourseAssessment() {
        ArrayList<CourseAssessment> returnValue = new ArrayList<>();
        CourseAssessment x  = new CourseAssessment();

        x.AssessmentID =1L;
        x.CourseID=1L;


        returnValue.add(x);

        return returnValue.toArray(new CourseAssessment[returnValue.size()]);
    }

    public static CourseMentor[] getCourseMentor() {
        ArrayList<CourseMentor> returnValue = new ArrayList<>();
        CourseMentor x  = new CourseMentor();

        x.MentorID =2L;
        x.CourseID=1L;

        returnValue.add(x);

        return returnValue.toArray(new CourseMentor[returnValue.size()]);
    }

    public static Term[] getTerm() {
        ArrayList<Term> returnValue = new ArrayList<>();
        Term x  = new Term();

        x.Title ="Winter 2018";
        x.TermID =1L;

        returnValue.add(x);

        return returnValue.toArray(new Term[returnValue.size()]);
    }

    public static TermCourse[] getTermCourse() {
        ArrayList<TermCourse> returnValue = new ArrayList<>();
        TermCourse x  = new TermCourse();

        x.TermID=1L;
        x.CourseID=1L;

        returnValue.add(x);

        return returnValue.toArray(new TermCourse[returnValue.size()]);
    }

}
