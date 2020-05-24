# CMS Shopping cart application

### 1. Entities with lombok support
    -Java Bean models with @Data lombok.Data; support for getters, setters...

### 2. Spring MVC request mapping 
    -Request mapping (@GetMapping, @PostMaping) within @Controller class 
    
### 3. Spring Data JPA for CRUD and complex DB operatins:
        Entities(Page, Product, Category,..) - @Entity, @Table, @Id,     @GeneratedValue(strategy = GenerationType.IDENTITY) ,...
        Repositories for each entity (PageRepository,..) - interfaces which extends generic class JpaRepository<T,ID>
        
### 4. Thymeleaf representation for Pages, Categories, Products
	-Names, descriptions, image, price ...

### 5. Validation for Adding or Editing (Pages, Categories or Products):
	-javax.validation in entities @Min, @Pattern 
	-Spring BindingResult with javax.validation @Valid
		        if (bindingResult.hasErrors()) {
					model.addAttribute("productName", currentProduct.getName());
					model.addAttribute("categories", productRepository.findAll());
					return "admin/products/edit";
				}
	-Thymeleaf checking errors:
			<div th:if="${#fields.hasErrors('*')}" class="alert alert-danger">
				There are errors
			</div>
			<span class="error" th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></span>

### 6. SQL - MySql 
    	-Products have their category (@OneToOne, cascade deleting). Product have foreign key of category
    		