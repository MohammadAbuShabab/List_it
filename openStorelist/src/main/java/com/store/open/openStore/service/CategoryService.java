package com.store.open.openStore.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.open.openStore.model.Category;
import com.store.open.openStore.model.CategoryDto;
import com.store.open.openStore.model.CategoryPicture;
import com.store.open.openStore.model.Product;
import com.store.open.openStore.repository.CategoryPictureRepository;
import com.store.open.openStore.repository.Categoryrepository;
import com.store.open.openStore.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private Categoryrepository categoryrepository;
	
	@Autowired
	private CategoryPictureRepository categoryPictureRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	public CategoryDto categoryToCategoryDto(Category category) {
		String picture = getCategoryPicture(category.getId());
		CategoryDto categoryDto = new CategoryDto(category.getId(), category.getCategoryName(), category.getDescription(), picture);
		return categoryDto;
	}
	
	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = new Category(categoryDto.getId(), categoryDto.getCategoryName(), categoryDto.getDescription());
		return category;
	}
	
	public String getCategoryPicture(Integer categoryId) {
		CategoryPicture categoryFile = categoryPictureRepository.findByCategoryId(categoryId).get();
		return categoryFile.getFile();
	}

	public List<Category> listCategories() {
		return categoryrepository.findAll();
	}

	public void createCategory(CategoryDto categoryDto) {
		Category category = categoryDtoToCategory(categoryDto);
		category = categoryrepository.save(category);
		
		String picture = categoryDto.getPictureBase64Encoded();
		
		CategoryPicture categoryPicture = new CategoryPicture();
		categoryPicture.setFile(picture);
		categoryPicture.setCategory(category);
		categoryPicture = categoryPictureRepository.save(categoryPicture);
		
		category.setAttachment(categoryPicture);
		category = categoryrepository.save(category);
	}

	public Category readCategory(String categoryName) {
		return categoryrepository.findByCategoryName(categoryName);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryrepository.findById(categoryId);
	}

	public void updateCategory(Integer categoryID, CategoryDto newCategory) {
		Category category = categoryrepository.findById(categoryID).get();
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		categoryrepository.save(category);
		
		String picture = newCategory.getPictureBase64Encoded();
		
		CategoryPicture categoryPictureInDB = categoryPictureRepository.findByCategoryId(categoryID).get();
	
		if (categoryPictureInDB != null) {
			categoryPictureInDB.setFile(picture);
			categoryPictureInDB.setCategory(category);
			categoryPictureInDB = categoryPictureRepository.save(categoryPictureInDB);
			
			category.setAttachment(categoryPictureInDB);
		} else {
			categoryPictureInDB = new CategoryPicture();
			categoryPictureInDB.setFile(picture);
			categoryPictureInDB.setCategory(category);
			categoryPictureInDB = categoryPictureRepository.save(categoryPictureInDB);
		}
		
		category = categoryrepository.save(category);
	}
	
	public boolean deleteCategory(Integer id) {
		List<Product> productsOfCategory = productRepository.findByCategoryId(id);
		if (!productsOfCategory.isEmpty()) {
			return false;
		}
		
		Optional<CategoryPicture> pictureOpt = categoryPictureRepository.findByCategoryId(id);
		if (pictureOpt.isPresent()) {
			categoryPictureRepository.delete(pictureOpt.get());
		}
		categoryrepository.deleteById(id);
		return true;
	}
}