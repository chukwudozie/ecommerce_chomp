package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.users.Role;
import com.chompfooddeliveryapp.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface  UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long id);
    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    Optional<User> getUserByPassword(String password);


}
