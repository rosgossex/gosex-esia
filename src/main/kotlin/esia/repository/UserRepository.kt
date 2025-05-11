package esia.repository

import esia.model.User
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
  fun findByUsername(username: String): Optional<User>
}
