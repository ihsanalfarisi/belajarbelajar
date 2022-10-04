package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseService {
    void addCourse(CourseModel course);
    List<CourseModel> getListCourse();
    CourseModel getCourseByCodeCourse(String code);
    CourseModel getCourseByCodeCourseQuery(String code);
    CourseModel updateCourse(CourseModel course);
    List<CourseModel> getListCourseSort();
    void deleteCourse(CourseModel course);
    String checkDeleteCourse(CourseModel course);
    boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir);
}