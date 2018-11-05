package edu.wgu.dmass13.c196.globals;

public class Enums {

    public enum CourseStatus {
        InProgress,
        Completed,
        Dropped,
        PlanToTake
    }

    public enum AssessmentType {
        Objective,
        Performance
    }


    public class ActivityActionTypes {
        public static final short
                Assessment_Edit = 0,
                Assessment_List = 1,
                Course_Edit = 2,
                Course_List = 3,
                Mentor_Edit = 4,
                Mentor_List = 5,
                Term_Edit = 6,
                Term_List = 7;
    }

    public class CRUD_Mode {
        public static final short
                READ_ONLY = 0,
                EDIT = 1,
                CREATE = 2,
                DELETE = 3;
    }

}
