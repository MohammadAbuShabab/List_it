package com.store.open.openStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.open.openStore.model.ProductPicture;
import com.store.open.openStore.model.Category;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture, Integer> {

	public List<ProductPicture> findByProductId(Integer productId);

}