# CMS Shopping cart application

### 1. Entities with lombok support
    -Java Bean models with @Data lombok.Data; support for getters, setters...
    -Backend validation: javax.validation @Min, @Pattern ,.. 

### 2. Spring MVC request mapping 
    -Request mapping (@GetMapping, @PostMaping) within @Controller class 
    
### 3. Spring Data JPA for CRUD and complex DB operatins:
        Entities(Page, Product, Category,..) - @Entity, @Table, @Id,     @GeneratedValue(strategy = GenerationType.IDENTITY) ,...
        Repositories for each entity (PageRepository,..) - interfaces which extends generic class JpaRepository<T,ID>
        
### 4. Backend validation for adding or editing:
	-Spring BindingResult with javax.validation @Valid
            if (bindingResult.hasErrors()) {
                model.addAttribute("productName", currentProduct.getName());
                model.addAttribute("categories", productRepository.findAll());
                return "admin/products/edit";
            }
				
### 5. Thymeleaf
	-Fragments (header, nav and footer)
    -Thymeleaf checking/display errors:
    			<div th:if="${#fields.hasErrors('*')}" class="alert alert-danger">
    				There are errors
    			</div>
    			<span class="error" th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></span>

### 6. SQL - MySql 
    	-Products have their category (@OneToOne, cascade deleting). Product have foreign key of category
    	- application.properties info for DB:
                    spring.datasource.url=jdbc:mysql://localhost/cmsshoppingcart?serverTimezone=UTC
                    spring.datasource.username=root
                    spring.datasource.password=test
                    
### 7. Pagination

### 8. CK editor imported for textarea as rich editor support