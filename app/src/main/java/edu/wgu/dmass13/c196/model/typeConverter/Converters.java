package edu.wgu.dmass13.c196.model.typeConverter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

import edu.wgu.dmass13.c196.globals.Enums;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static int  fromCourseStatus(Enums.CourseStatus value) {
        return value.ordinal();
    }

    @TypeConverter
    public static Enums.CourseStatus ToCourseStatus(int value) {
        return Enums.CourseStatus.values()[value];
    }

    @TypeConverter
    public static  int fromAssessmentType(Enums.AssessmentType value) {
        return value.ordinal();
    }

    @TypeConverter
    public static Enums.AssessmentType ToAssessmentType(int value) {
        return Enums.AssessmentType.values()[value];
    }


}
