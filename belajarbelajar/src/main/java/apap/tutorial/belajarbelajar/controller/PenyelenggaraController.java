package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import apap.tutorial.belajarbelajar.model.UserModel;
import apap.tutorial.belajarbelajar.service.PenyelenggaraService;
import apap.tutorial.belajarbelajar.service.RoleService;
import apap.tutorial.belajarbelajar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PenyelenggaraController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Qualifier("penyelenggaraServiceImpl")
    @Autowired
    PenyelenggaraService penyelenggaraService;

    @GetMapping("/penyelenggara/add")
    public String addPenyelenggaraForm(Model model){
        List<UserModel> listUser = userService.getListUser();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.getUserByUsername(username);

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", userModel);

        model.addAttribute("penyelenggara", new PenyelenggaraModel());
        return "form-add-penyelenggara";
    }

    @PostMapping(value = "/penyelenggara/add")
    public String addPenyelenggaraSubmit(
            @ModelAttribute PenyelenggaraModel penyelenggara,
            Model model
    ){

        penyelenggaraService.addPenyelenggara(penyelenggara);
        model.addAttribute("noPenyelenggara", penyelenggara.getNoPenyelenggara());
        return "add-penyelenggara";
    }

    @GetMapping("/penyelenggara/viewall")
    public String viewAllPenyelenggara(
            Model model
    ){
        List<UserModel> listUser = userService.getListUser();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.getUserByUsername(username);

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", userModel);

        model.addAttribute("listPenyelenggara", penyelenggaraService.getListPenyelenggara());
        return "viewall-penyelenggara";
    }
}
