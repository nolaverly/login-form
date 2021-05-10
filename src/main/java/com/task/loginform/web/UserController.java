package com.task.loginform.web;

import com.task.loginform.model.Password;
import com.task.loginform.model.User;
import com.task.loginform.repository.UserRepository;
import com.task.loginform.service.MyUserPrincipal;
import com.task.loginform.service.SecurityService;
import com.task.loginform.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({ "/", "/welcome" })
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping("/updatePassword")
    public String updatePasswordpage(Model model, Password password) {

        return "updatePassword";
    }

    @PostMapping("/simpanPassword")
    public String simpanPassword(Model model, @Validated Password password, BindingResult rs) {
        MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User users = this.userRepository.getOne(user.getId());

        String msg = "";

        if ((encoder.matches(password.getoldPassword(), users.getPassword()))
                && (password.getnewPassword().equals(password.getconfirmNewPassword()))) {
            users.setPassword(encoder.encode(password.getnewPassword()));
            userRepository.save(users);
            msg = "Password berhasil diubah";
            System.out.println("Password berhasil diubah");
        } else {
            msg = "Password lama tidak benar atau konfirmasi password berbeda";
            System.out.println("Tidak berhasil ubah password");
        }

        model.addAttribute("msg", msg);
        return "updatePassword";
    }

}