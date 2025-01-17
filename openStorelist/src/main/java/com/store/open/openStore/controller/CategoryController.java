package com.store.open.openStore.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.open.openStore.model.Category;
import com.store.open.openStore.model.CategoryDto;
import com.store.open.openStore.model.Role;
import com.store.open.openStore.model.User;
import com.store.open.openStore.model.enums.USER_ROLE;
import com.store.open.openStore.service.CategoryService;
import com.store.open.openStore.service.UserService;

import javax.validation.Valid;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
    UserService userService;
	
	

	@GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> body = categoryService.listCategories();
        List<CategoryDto> categories = new ArrayList<>();
        
        for (Category category : body) {
        	categories.add(categoryService.categoryToCategoryDto(category));
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

	@PostMapping("/create")
	public ResponseEntity<Boolean> createCategory(@Valid @RequestBody CategoryDto category) {
		if (Objects.nonNull(categoryService.readCategory(category.getCategoryName()))) {
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.CONFLICT);
		}
		categoryService.createCategory(category);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Integer id, Principal principal) {
		String email = principal.getName();
        User user = userService.findUserByEmail(email);
        List<String> userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        if (!userRoles.contains("ROLE_"+USER_ROLE.ADMIN.toString())) {
        	return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);
        }
		if (id == null) {
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
		}
	
		if (categoryService.readCategory(id).isPresent()) {
			boolean deleted = categoryService.deleteCategory(id);
			if (deleted) {
				return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Boolean.FALSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		} 
		
		return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.NOT_FOUND);
		
	}


	@PostMapping("/update/{categoryID}")
	public ResponseEntity<Boolean> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody CategoryDto category) {
		// Check to see if the category exists.
		if (Objects.nonNull(categoryService.readCategory(categoryID))) {
			// If the category exists then update it.
			categoryService.updateCategory(categoryID, category);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}

		// If the category doesn't exist then return a response of unsuccessful.
		return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{categoryID}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryID") Integer categoryID) {
		// Check to see if the category exists.
		Optional<Category> category = categoryService.readCategory(categoryID);
		if (Objects.nonNull(category)) {
			// If the category exists then update it.
			CategoryDto categoryDto = categoryService.categoryToCategoryDto(category.get());
			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		}

		// If the category doesn't exist then return a response of unsuccessful.
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}