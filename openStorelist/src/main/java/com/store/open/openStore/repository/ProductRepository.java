package com.store.open.openStore.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.open.openStore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	
	@Query("SELECT u FROM Product u WHERE ((u.name like concat('%',:searched,'%')) OR (u.description like concat('%',:searched,'%'))) "
			+ " and u.category.id = :categoryId order by DATE_FORMAT(dateAdded, '%Y-%m-%dT%TZ') desc")
	List<Product> searchInCategory(@Param("searched") String searched, @Param("categoryId") Integer categoryId);
	
	@Query("SELECT u FROM Product u WHERE ((u.name like concat('%',:searched,'%')) OR (u.description like concat('%',:searched,'%'))) "
			+ "order by DATE_FORMAT(dateAdded, '%Y-%m-%dT%TZ') desc")
	List<Product> searchInAllCategories(@Param("searched") String searched);
	
	List<Product> findByCategoryId(Integer id);
	
	List<Product> findByOwnerId(Long userId);

}