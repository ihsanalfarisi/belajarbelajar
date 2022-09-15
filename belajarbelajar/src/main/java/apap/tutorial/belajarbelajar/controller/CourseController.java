package apap.tutorial.belajarbelajar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.service.CourseService;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("course/add")
    public String addCourse(
            @RequestParam(value = "code", required = true) String code,
            @RequestParam(value = "nameCourse", required = true) String nameCourse,
            @RequestParam(value = "description", required = true) String description,
            @RequestParam(value = "jumlahSks", required = true) String jumlahSks,
            Model model) {
        model.addAttribute("code", code);
        CourseModel course = courseService.getCourseByCodeCourse(code);

        if (course == null) {
            course = new CourseModel(code, nameCourse, description, Integer.parseInt(jumlahSks));

            courseService.addCourse(course);
            model.addAttribute("code", code);
            model.addAttribute("nameCourse", nameCourse);

            return "add-course";
        } else {
            return "found-course";
        }
    }

    @RequestMapping("course/viewAll")
    public String listCourse(Model model) {

        List<CourseModel> listCourse = courseService.getListCourse();

        model.addAttribute("listCourse", listCourse);
        return "viewall-course";
    }

    @RequestMapping("course/view")
    public String detailCourse(@RequestParam(value = "code") String code, Model model) {

        CourseModel course = courseService.getCourseByCodeCourse(code);

        model.addAttribute("course", course);
        return "view-course";
    }

    @GetMapping("course/view/code-course/{code}")
    public String viewCourse(@PathVariable (value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);

        model.addAttribute("course", course);
        return "view-course";
    }

    @GetMapping("course/update/code-course/{code}/jumlah-sks/{jumlahSks}")
    public String updateJumlahSks(@PathVariable (value = "code") String code, @PathVariable(value = "jumlahSks") int jumlahSks, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        model.addAttribute("code", code);
        if (course != null) {
            course.setJumlahSks(jumlahSks);
            return "update-sks";
        } else {
            return "not-found";
        }
    }

    @RequestMapping(value = "course/delete/code-course/{code}")
    public String deleteCourse(@PathVariable (value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        model.addAttribute("code", code);
        if (course != null) {
            courseService.deleteCourse(course);
            return "delete-course";
        } else {
            return "not-found";
        }
    }

    @RequestMapping(value = "course/delete/code-course/")
    public String deleteCourseNull(Model model) {
        return "null-code";
    }
}