package apap.tutorial.belajarbelajar.controller;

import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.model.UserModel;
import apap.tutorial.belajarbelajar.service.RoleService;
import apap.tutorial.belajarbelajar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);

        List<UserModel> listUser = userService.getListUser();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userNow = (User) auth.getPrincipal();
        String username = userNow.getUsername();
        UserModel userModel = userService.getUserByUsername(username);

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", userModel);

        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        user.setIsSso(false);
        userService.addUser(user);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/viewall")
    public String listUser(Model model) {
        List<UserModel> listUser = userService.getListUser();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.getUserByUsername(username);

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", userModel);

        return "viewall-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, Model model) {
        if (id.equals("null")) {
            return "error-view";
        }

        UserModel user = userService.getUserById(id);
        userService.deleteUser(user);

        if (id.equals("null")) {
            return "error-view";
        }
        model.addAttribute( "id",user.getId());
        return "delete-user";
    }

    @GetMapping("/updatePassword")
    public String updatePasswordUser(){
        return "form-update-password";
    }

    @PostMapping("/updatePassword")
    public String updatePostPassword(@ModelAttribute UserModel userModel, String newPassword, String confPassword, Model model){
        UserModel user = userService.getUserByUsername(userModel.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(userModel.getPassword(), user.getPassword())){
            if (newPassword.equals(confPassword)){
                user.setPassword(newPassword);
                userService.addUser(user);
                return "update-password";
            }else {
                model.addAttribute("message", "Konfirmasi Password tidak sama");
            }
        }else {
            model.addAttribute("message", "Password lama invalid");
        }
        return "form-update-password";
    }
}
