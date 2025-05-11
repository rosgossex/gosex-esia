package esia.config

import esia.model.Authority
import esia.model.User
import esia.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class TestDataConfig(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

  @Bean
  fun initTestData(): CommandLineRunner {
    return CommandLineRunner { args ->
      if (userRepository.count() == 0L) {
        val adminAuthority = Authority(username = "admin", authority = "ROLE_USER")

        val adminUser =
            User(
                username = "admin",
                password = passwordEncoder.encode("admin"),
                fullName = "Administrator",
                age = 30,
                authorities = mutableSetOf(adminAuthority))

        userRepository.save(adminUser)
      }
    }
  }
}
