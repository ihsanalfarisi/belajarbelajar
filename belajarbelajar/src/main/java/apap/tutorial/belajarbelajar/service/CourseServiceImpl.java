package apap.tutorial.belajarbelajar.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import apap.tutorial.belajarbelajar.model.CourseModel;

@Service
public class CourseServiceImpl implements CourseService{
    private List<CourseModel> listCourse;

    public CourseServiceImpl(){
        listCourse = new ArrayList<>();
    }

    @Override
    public void addCourse(CourseModel course) {
        listCourse.add(course);
    }

    @Override
    public List<CourseModel> getListCourse() {
        return listCourse;
    }

    @Override
    public CourseModel getCourseByCodeCourse(String code) {
        for (CourseModel courseModel : listCourse) {
            if (Objects.equals(code, courseModel.getCode())) {
                return courseModel;
            }
        }
        return null;
    }
    @Override
    public void deleteCourse(CourseModel course) {
        listCourse.remove(course);
    }
}
