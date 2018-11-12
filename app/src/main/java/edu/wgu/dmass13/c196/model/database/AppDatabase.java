package edu.wgu.dmass13.c196.model.database;

import android.content.Context;
import android.arch.persistence.room.*;
import edu.wgu.dmass13.c196.model.entity.*;
import edu.wgu.dmass13.c196.model.dao.*;
import edu.wgu.dmass13.c196.model.typeConverter.*;

@Database(entities = {Assessment.class,  Course.class, Mentor.class, Term.class
}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract MentorDAO mentorDAO();
    public abstract TermDAO termDAO();

    public synchronized static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "C196")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
