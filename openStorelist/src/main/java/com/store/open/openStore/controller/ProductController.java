package com.store.open.openStore.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.open.openStore.model.Category;
import com.store.open.openStore.model.CategoryDto;
import com.store.open.openStore.model.Product;
import com.store.open.openStore.model.ProductDto;
import com.store.open.openStore.model.ProductPicture;
import com.store.open.openStore.model.User;
import com.store.open.openStore.repository.ProductPictureRepository;
import com.store.open.openStore.service.CategoryService;
import com.store.open.openStore.service.ProductService;
import com.store.open.openStore.service.UserService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
	private ProductPictureRepository attachmentrepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    /**
    * By default, the public folder in the working directory is a static resource directory, which can be accessed directly by the client.
    */
    private static final Path PUBLIC_DIR = Paths.get(System.getProperty("user.dir"), "public");
    
    @PostMapping(
    		"/add")
    public ResponseEntity<Boolean> addProduct(@RequestBody ProductDto productDto, Principal principal) throws Exception {
    	
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(false, HttpStatus.CONFLICT);
        }
          
        Category category = optionalCategory.get();
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        productService.addProduct(productDto, category, user);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> body = productService.listProducts();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<ProductDto>> getUserProducts(Principal principal) {
		String email = principal.getName();
        User user = userService.findUserByEmail(email);
        List<ProductDto> body = productService.listUserProductsByUserId(user.getId());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> findByNameOrDescriptionAndCategoryLike(@RequestParam("search") String search,
    		@Nullable @RequestParam("categoryId") Integer categoryId) {
    	List<ProductDto> body = productService.findByNameOrDescriptionAndCategoryLike(search, categoryId);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    
    @GetMapping("/delete/{id}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Integer id, Principal principal) {
		if (id == null) {
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
		}
	
		Optional<Product> optProduct = productService.findById(id);
		if (optProduct.isPresent()) {
			Product product = optProduct.get();
			String email = principal.getName();
	        User user = userService.findUserByEmail(email);
			boolean deleted = productService.deleteProduct(product, user);
			if (deleted) {
				return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Boolean.FALSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		} 
		
		return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.NOT_FOUND);
		
	}
    
    @PostMapping("/update/{productID}")
	public ResponseEntity<Boolean> updateProduct(@PathVariable("productID") Integer productID, @Valid @RequestBody ProductDto product) {
		// Check to see if the category exists.
		if (Objects.nonNull(productService.findById(productID))) {
			// If the category exists then update it.
			productService.updateProduct(productID, product);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}

		// If the category doesn't exist then return a response of unsuccessful.
		return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
	}
    
    @GetMapping("/{productID}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productID") Integer productID) {
    	Optional<Product> productOpt = productService.findById(productID);
    	ProductDto body = null;
    	if (productOpt.isPresent()) {
    		Product product = productOpt.get();
    		List<ProductPicture> attachements = attachmentrepository.findByProductId(productID);
			List<String> attachementDatas = attachements.stream().map(file -> file.getFile()).collect(Collectors.toList());
    		body = productService.getProductFromEntity(product, attachementDatas);
    		return new ResponseEntity<>(body, HttpStatus.OK);
    	}
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
}