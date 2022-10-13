package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.CourseModel;
import apap.tutorial.belajarbelajar.model.PengajarModel;
import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import apap.tutorial.belajarbelajar.service.CourseService;
import apap.tutorial.belajarbelajar.service.PengajarService;
import apap.tutorial.belajarbelajar.service.PenyelenggaraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Qualifier("courseServiceImpl")
    @Autowired
    private CourseService courseService;

    @Qualifier("pengajarServiceImpl")
    @Autowired
    private PengajarService pengajarService;

    @Qualifier("penyelenggaraServiceImpl")
    @Autowired
    private PenyelenggaraService penyelenggaraService;

    @GetMapping("/course/add")
    public String addCourseFormPage(Model model) {
        CourseModel course = new CourseModel();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();
        List<PenyelenggaraModel> listPenyelenggaraNew = new ArrayList<>();
        List<PengajarModel> listPengajar = pengajarService.getListPengajar();
        List<PengajarModel> listPengajarNew = new ArrayList<>();

        course.setListPenyelenggara(listPenyelenggaraNew);
        course.getListPenyelenggara().add(new PenyelenggaraModel());
        course.setListPengajar(listPengajarNew);
        course.getListPengajar().add(new PengajarModel());

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        model.addAttribute("listPengajarExisting", listPengajar);

        return "form-add-course";
    }

    @PostMapping("/course/add")
    public String addCourseSubmitPage(@ModelAttribute CourseModel course, Model model) {
        if (course.getListPenyelenggara() == null) {
            course.setListPenyelenggara(new ArrayList<>());
        }

        List<PengajarModel> listPengajar = course.getListPengajar();
        for (PengajarModel pengajar : listPengajar) {
            pengajar.setCourse(course);
        }

        course.setListPengajar(listPengajar);
        courseService.addCourse(course);
        System.out.println(course.getCode());
        model.addAttribute("code", course.getCode());

        return "add-course";
    }

    @GetMapping("/course/viewall")
    public String listCourse(Model model) {
        List<CourseModel> listCourse = courseService.getListCourse();
        model.addAttribute("listCourse", listCourse);
        return "viewall-course";
    }

    @GetMapping("/course/view")
    public String viewDetailCoursePage(@RequestParam(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        List<PengajarModel> listPengajar = course.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = course.getListPenyelenggara();
        model.addAttribute("listPengajar", listPengajar);
        model.addAttribute("listPenyelenggara", listPenyelenggara);
        model.addAttribute("course", course);
        return "view-course";
    }

    @GetMapping("course/view-query")
    public String viewDetailCoursePageQuery(@RequestParam(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        List<PengajarModel> listPengajar = course.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = course.getListPenyelenggara();
        model.addAttribute("listPengajar", listPengajar);
        model.addAttribute("listPenyelenggara", listPenyelenggara);
        model.addAttribute("course", course);
        return "view-course";
    }

    @GetMapping("course/update/{code}")
    public String updateCourseFormPage(@PathVariable(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        model.addAttribute("course", course);
        return "form-update-course";
    }

    @PostMapping("course/update")
    public String updateCourseSubmitPage(@ModelAttribute CourseModel course, Model model) {
        CourseModel updatedCourse = courseService.updateCourse(course);
        model.addAttribute("code", updatedCourse.getCode());
        return "update-course";
    }

    @GetMapping("/course/viewall-sort")
    public String listCourseSort(Model model) {
        List<CourseModel> listCourse = courseService.getListCourseSort();
        model.addAttribute("listCourse", listCourse);
        return "viewall-course";
    }

    @GetMapping("course/delete/{code}")
    public String deleteCourse(@PathVariable(value = "code") String code, Model model) {
        CourseModel course = courseService.getCourseByCodeCourse(code);
        String res = courseService.checkDeleteCourse(course);
        model.addAttribute("course", course);
        return res;
    }

    @PostMapping(value="/course/add", params = {"addRow"})
    private String addRowCourseMultiple(@ModelAttribute CourseModel course, Model model) {
        if(course.getListPenyelenggara() == null) {
            course.setListPenyelenggara(new ArrayList<>());
        }
        course.getListPenyelenggara().add(new PenyelenggaraModel());
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();
        List<PengajarModel> listPengajar = course.getListPengajar();


        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        model.addAttribute("listPengajar", listPengajar);

        return "form-add-course";
    }

    @PostMapping(value="/course/add", params = {"deleteRow"})
    private String deleteRowCourseMultiple(@ModelAttribute CourseModel course, @RequestParam("deleteRow") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        course.getListPenyelenggara().remove(rowId.intValue());

        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);

        return "form-add-course";
    }

    @PostMapping(value="/course/add", params = {"addRowPengajar"})
    private String addRowCourseMultiplePengajar(@ModelAttribute CourseModel course, Model model) {
        if(course.getListPengajar() == null) {
            course.setListPengajar(new ArrayList<>());
        }

        course.getListPengajar().add(new PengajarModel());
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();
        List<PengajarModel> listPengajar = course.getListPengajar();

        model.addAttribute("course", course);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);
        model.addAttribute("listPengajar", listPengajar);

        return "form-add-course";
    }

    @PostMapping(value="/course/add", params = {"deleteRowPengajar"})
    private String deleteRowCourseMultiplePengajar(@ModelAttribute CourseModel course, @RequestParam("deleteRowPengajar") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        course.getListPengajar().remove(rowId.intValue());

        List<PengajarModel> listPengajar = pengajarService.getListPengajar();
        List<PenyelenggaraModel> listPenyelenggara = penyelenggaraService.getListPenyelenggara();

        model.addAttribute("course", course);
        model.addAttribute("listPengajarExisting", listPengajar);
        model.addAttribute("listPenyelenggaraExisting", listPenyelenggara);

        return "form-add-course";
    }
}