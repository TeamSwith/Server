package swith.swithServer.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
