package com.matovic.cmsshoppingcart.models;

import com.matovic.cmsshoppingcart.models.entities.Admin;
import com.matovic.cmsshoppingcart.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);

}
