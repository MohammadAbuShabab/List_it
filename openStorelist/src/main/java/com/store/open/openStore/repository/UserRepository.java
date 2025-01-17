package com.store.open.openStore.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.open.openStore.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
 User findByEmail(String email);
}