package com.store.open.openStore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.open.openStore.model.ProductPicture;
import com.store.open.openStore.model.Category;
import com.store.open.openStore.model.CategoryPicture;

@Repository
public interface CategoryPictureRepository extends JpaRepository<CategoryPicture, Integer> {

	public Optional<CategoryPicture> findByCategoryId(Integer categoryId);

}