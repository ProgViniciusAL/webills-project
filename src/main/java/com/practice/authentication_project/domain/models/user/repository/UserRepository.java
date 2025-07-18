package com.practice.authentication_project.domain.models.user.repository;

import com.practice.authentication_project.domain.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);

}
