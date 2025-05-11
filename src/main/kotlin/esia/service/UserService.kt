package esia.service

import esia.model.Authority
import esia.model.User
import esia.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    val user =
        userRepository.findByUsername(username).orElseThrow {
          UsernameNotFoundException("User $username not found")
        }

    val authorities = user.authorities.map { SimpleGrantedAuthority(it.authority) }

    return org.springframework.security.core.userdetails.User.withUsername(user.username)
        .password(user.password)
        .authorities(authorities)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!user.enabled)
        .build()
  }

  @Transactional
  fun registerUser(username: String, password: String, fullName: String, age: Int): User {
    if (userRepository.findByUsername(username).isPresent) {
      throw IllegalArgumentException("Username already exists")
    }

    val authority = Authority(username = username, authority = "ROLE_USER")

    val user =
        User(
            username = username,
            password = passwordEncoder.encode(password),
            fullName = fullName,
            age = age,
            authorities = mutableSetOf(authority))

    return userRepository.save(user)
  }
}
