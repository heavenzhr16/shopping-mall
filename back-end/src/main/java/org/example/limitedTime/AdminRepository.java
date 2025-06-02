package org.example.limitedTime;

import org.example.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByUsername(String username);
}
