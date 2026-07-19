package com.ayushman.Ecommerce.repo;

import com.ayushman.Ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

      User findByEmail(String Email);


}
