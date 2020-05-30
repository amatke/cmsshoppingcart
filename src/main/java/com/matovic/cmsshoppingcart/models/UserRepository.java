package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
