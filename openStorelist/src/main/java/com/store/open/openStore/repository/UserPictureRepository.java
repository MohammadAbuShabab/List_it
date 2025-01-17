package com.store.open.openStore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.open.openStore.model.UserPicture;

@Repository
public interface UserPictureRepository extends JpaRepository<UserPicture, Integer> {

	UserPicture findByUserId(Long userId);

}