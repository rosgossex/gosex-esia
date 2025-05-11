package esia.controller

import esia.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class AuthController(private val userService: UserService) {

  @GetMapping("/login")
  fun login(): String {
    return "login"
  }

  @GetMapping("/register")
  fun registerForm(model: Model): String {
    return "register"
  }

  @PostMapping("/register")
  fun register(
      @RequestParam username: String,
      @RequestParam password: String,
      @RequestParam fullName: String,
      @RequestParam age: Int
  ): String {
    userService.registerUser(username, password, fullName, age)
    return "redirect:/login?registered"
  }

  @GetMapping("/")
  fun home(model: Model): String {
    return "home"
  }
}
