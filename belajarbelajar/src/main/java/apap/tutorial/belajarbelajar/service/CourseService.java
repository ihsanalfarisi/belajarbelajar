package apap.tutorial.belajarbelajar.service;
import apap.tutorial.belajarbelajar.model.CourseModel;
import java.util.List;

public interface CourseService {

    void addCourse(CourseModel course);

    List<CourseModel> getListCourse();

    CourseModel getCourseByCodeCourse(String code);

    void deleteCourse(CourseModel course);
}
