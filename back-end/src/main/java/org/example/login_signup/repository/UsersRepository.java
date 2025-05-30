package org.example.login_signup.repository;

import org.example.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByEmailAndLoginType(String email, String loginType);
    boolean existsByNickname(String nickname);
}
