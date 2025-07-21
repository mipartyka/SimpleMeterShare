package com.sms.simplemetershare.repository;

import com.sms.simplemetershare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
