package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.repository.CourseDb;
import apap.tutorial.belajarbelajar.repository.PengajarDb;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseDb courseDb;

    @Override
    public void addCourse(CourseModel course) {
        courseDb.save(course);
    }

    @Override
    public List<CourseModel> getListCourse() {
        return courseDb.findAll();
    }

    @Override
    public CourseModel getCourseByCodeCourse(String code) {
        Optional<CourseModel> course = courseDb.findByCode(code);
        if (course.isPresent()) {
            return course.get();
        } else return null;
    }

    @Override
    public CourseModel getCourseByCodeCourseQuery(String code) {
        Optional<CourseModel> course = courseDb.findByCodeUsingQuery(code);
        if (course.isPresent()) {
            return course.get();
        } else return null;
    }

    @Override
    public CourseModel updateCourse(CourseModel course) {
        courseDb.save(course);
        return course;
    }

    @Override
    public List<CourseModel> getListCourseSort() {
        return courseDb.findAll(Sort.by(Sort.Direction.ASC, "nameCourse"));
    }

    public void deleteCourse(CourseModel course) {
        courseDb.delete(course);
    }

    public String checkDeleteCourse(CourseModel course) {
        if (course.getTanggalBerakhir().isBefore(LocalDateTime.now()) && course.getListPengajar().isEmpty()) {
            deleteCourse(course);
            return "delete-course";
        } else return "error-delete-course";
    }

    @Override
    public boolean isClosed(LocalDateTime tanggalDimulai, LocalDateTime tanggalBerakhir) {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(tanggalDimulai) || now.isAfter(tanggalBerakhir);
    }
}