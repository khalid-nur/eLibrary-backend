package com.elibrary.backend.modules.auth.repository;

import com.elibrary.backend.modules.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing user
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
