package edu.wgu.dmass13.c196.model.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.Context;
import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import edu.wgu.dmass13.c196.model.entity.*;
import edu.wgu.dmass13.c196.model.dao.*;
import edu.wgu.dmass13.c196.model.typeConverter.*;

@Database(entities = {Assessment.class, Course.class, Mentor.class, Term.class, TermCourse.class, CourseAssessment.class, CourseMentor.class
}, version = 18, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract MentorDAO mentorDAO();
    public abstract TermDAO termDAO();
    public abstract PrepopulatationDAO PrepopulatationDAO();

    public synchronized static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "C196")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getMentor());
                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getAssessment());
                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getCourse());
                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getTerm());
                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getCourseAssessment());
                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getCourseMentor());
                                            getDatabase(context).PrepopulatationDAO()._insertAll(DataStart.getTermCourse());

                                        }
                                    });
                                }
                            })
                            .build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
