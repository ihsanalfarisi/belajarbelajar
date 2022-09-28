package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;
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
}