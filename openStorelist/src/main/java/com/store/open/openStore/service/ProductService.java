package com.store.open.openStore.service;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.open.openStore.model.ProductPicture;
import com.store.open.openStore.model.Role;
import com.store.open.openStore.model.User;
import com.store.open.openStore.model.UserDto;
import com.store.open.openStore.model.UserPicture;
import com.store.open.openStore.model.enums.USER_ROLE;
import com.store.open.openStore.model.Category;
import com.store.open.openStore.model.Product;
import com.store.open.openStore.model.ProductDto;
import com.store.open.openStore.repository.Categoryrepository;
import com.store.open.openStore.repository.ProductPictureRepository;
import com.store.open.openStore.repository.ProductRepository;
import com.store.open.openStore.repository.UserPictureRepository;
import com.store.open.openStore.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductPictureRepository attachmentrepository;
	
	@Autowired
	UserPictureRepository userPictureRepository;
	
	@Autowired
	Categoryrepository categoryRepository;
	
	
	public void addProduct(ProductDto productDto, Category category, User owner) {
        Product product = getProductFromDto(productDto, category);
        
        product.setOwner(owner);
        product = productRepository.save(product);

        List<ProductPicture> attachements = new ArrayList<ProductPicture>();
        for (String picture : productDto.getImagesData()) {
        	ProductPicture attachment = new ProductPicture();
        	attachment.setProduct(product);
        	attachment.setFile(picture);
        	attachment = attachmentrepository.save(attachment);
        	attachements.add(attachment);
        }
        product.setAttachments(attachements);

        
        product.setDateAdded(Instant.now().toString());
        product = productRepository.save(product);
    }
	
	private UserDto userToUserDto(User user) {
		UserPicture userPicture = user.getAttachment();
		String picture = userPicture != null ? userPicture.getFile() : null;
		List<String> roles = ((List<Role>) user.getRoles()).stream().map(Role::getName).collect(Collectors.toList());
		UserDto userDto = new UserDto(user.getId(), user.getFirstName() , user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getUsername(),roles , user.getPassword(), picture);
		return userDto;
		
		
	}
	public List<ProductDto> listUserProductsByUserId(Long userId) {
		List<ProductDto> productDtos = new ArrayList<>();
		List<Product> products = productRepository.findByOwnerId(userId);
		products = products.stream().sorted((a,b) -> {
			if (a.getDateAdded() != null && b.getDateAdded() != null) {
				Instant aInstant = Instant.parse(a.getDateAdded());
				Instant bInstant = Instant.parse(b.getDateAdded());
				return aInstant.compareTo(bInstant);
			} 
			return 0;
			
		}).collect(Collectors.toList());
		for (Product product : products) {
			Integer productId = product.getId();
			List<ProductPicture> attachements = attachmentrepository.findByProductId(productId);
			List<String> attachementDatas = attachements.stream().map(file -> file.getFile()).collect(Collectors.toList());
			ProductDto dto = getProductFromEntity(product, attachementDatas);
			dto.setOwnerId(product.getOwner().getId());
			/*
			UserPicture userPicture = userPictureRepository.findByUserId(product.getOwner().getId());
			String picture = userPicture != null ? userPicture.getFile() :  null;
			*/
			dto.setOwner(userToUserDto(userRepository.findById(userId).get()));
			productDtos.add(dto);
		}
		
		return productDtos;
	}
	public List<ProductDto> listProducts() { 
		List<ProductDto> productDtos = new ArrayList<>();
		List<Product> products = productRepository.findAll();
		products = products.stream().sorted((a,b) -> {
			if (a.getDateAdded() != null && b.getDateAdded() != null) {
				Instant aInstant = Instant.parse(a.getDateAdded());
				Instant bInstant = Instant.parse(b.getDateAdded());
				return aInstant.compareTo(bInstant);
			} 
			return 0;
			
		}).collect(Collectors.toList());
		for (Product product : products) {
			Integer productId = product.getId();
			List<ProductPicture> attachements = attachmentrepository.findByProductId(productId);
			List<String> attachementDatas = attachements.stream().map(file -> file.getFile()).collect(Collectors.toList());
			ProductDto dto = getProductFromEntity(product, attachementDatas);
			dto.setOwnerId(product.getOwner().getId());
			UserPicture userPicture = userPictureRepository.findByUserId(product.getOwner().getId());
	        String picture = userPicture != null ? userPicture.getFile() :  null;
			dto.setOwner(new UserDto(product.getOwner().getId(), product.getOwner().getFirstName(), product.getOwner().getLastName(), 
					product.getOwner().getPhoneNumber(), product.getOwner().getEmail(), product.getOwner().getUsername(), 
	        		null, null, picture));
			productDtos.add(dto);
		}
		
		return productDtos;
	}
	
	public List<ProductDto> findByNameOrDescriptionAndCategoryLike(String search, Integer categoryId) {
		List<ProductDto> productDtos = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		if (categoryId != null) {
			products = productRepository.searchInCategory(search, categoryId);
		} else {
			products = productRepository.searchInAllCategories(search);
		}
				
		for (Product product : products) {
			Integer productId = product.getId();
			List<ProductPicture> attachements = attachmentrepository.findByProductId(productId);
			List<String> attachementDatas = attachements.stream().map(file -> file.getFile()).collect(Collectors.toList());
			ProductDto dto = getProductFromEntity(product, attachementDatas);
			dto.setOwnerId(product.getOwner().getId());
			UserPicture userPicture = userPictureRepository.findByUserId(product.getOwner().getId());
	        String picture = userPicture != null ? userPicture.getFile() :  null;
			dto.setOwner(new UserDto(product.getOwner().getId(), product.getOwner().getFirstName(), product.getOwner().getLastName(), 
					product.getOwner().getPhoneNumber(), product.getOwner().getEmail(), product.getOwner().getUsername(), 
	        		null, null, picture));
			productDtos.add(dto);
		}
		
		return productDtos;
    }
	
	public ProductDto getProductFromEntity(Product productIn, List<String> attachements) {
        ProductDto product = new ProductDto();
        product.setId(productIn.getId());
        product.setCategoryId(productIn.getCategory().getId());
        product.setDescription(productIn.getDescription());
        product.setPrice(productIn.getPrice());
        product.setName(productIn.getName());
        product.setDateAdded(productIn.getDateAdded());
        UserPicture userPicture = userPictureRepository.findByUserId(productIn.getOwner().getId());
        String picture = userPicture != null ? userPicture.getFile() :  null;
        product.setOwner(new UserDto(productIn.getOwner().getId(), productIn.getOwner().getFirstName(), productIn.getOwner().getLastName(), 
        		productIn.getOwner().getPhoneNumber(), productIn.getOwner().getEmail(), productIn.getOwner().getUsername(), 
        		null, null, picture));

        product.setImagesData(attachements);
        return product;
    }

	public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setDateAdded(productDto.getDateAdded());
        return product;
    }
	
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}
	
	public boolean deleteProduct(Product product, User deleterUser) {
		List<String> userRoles = deleterUser.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (product.getOwner().getId().equals(deleterUser.getId()) || userRoles.contains("ROLE_"+USER_ROLE.ADMIN.toString())) {
			List<ProductPicture> attachements = attachmentrepository.findByProductId(product.getId());
			if (attachements != null && !attachements.isEmpty()) {
				attachmentrepository.deleteAll(attachements);
			}
			productRepository.delete(product);
			return true;
		}
		
		return false;
	}
	
	public boolean updateProduct(Integer productID, ProductDto productDto) {
		Optional<Product> productOpt = productRepository.findById(productID);
		if (productOpt.isPresent()) {
			Product product = productOpt.get();
			product.setQuantityAvailable(productDto.getQuantityAvailable());
			product.setDescription(productDto.getDescription());
			Category newCategory = categoryRepository.findById(productDto.getCategoryId()).get();
			product.setCategory(newCategory);
			product.setId(productID);
			product.setName(productDto.getName());
			product.setPrice(productDto.getPrice());
			product.setAttachments(null);
			product = productRepository.save(product);
			
			List<ProductPicture> oldAttachements = attachmentrepository.findByProductId(productID);
			if (oldAttachements != null && !oldAttachements.isEmpty()) {
				attachmentrepository.deleteAll(oldAttachements);
			}
			
			
			List<ProductPicture> attachements = new ArrayList<ProductPicture>();
	        for (String picture : productDto.getImagesData()) {
	        	ProductPicture attachment = new ProductPicture();
	        	attachment.setProduct(product);
	        	attachment.setFile(picture);
	        	attachment = attachmentrepository.save(attachment);
	        	attachements.add(attachment);
	        }
	        product.setAttachments(attachements);

	        
	        product.setDateAdded(Instant.now().toString());
	        product = productRepository.save(product);
			return true;
		}
		
		return false;
	}
	
	
}