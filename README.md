# CMS Shopping cart application

### 1. Entities Pages, Categories and Products 
	-Products have their category (@OneToOne, cascade deleting). Product have foreign key of category

### 2. CRUD operations for Pages, Categories and Products

### 3. Thymeleaf representation for Pages, Categories, Products
	-Names, descriptions, image, price ...

### 4. Validation for Adding or Editing (Pages, Categories or Products):
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

			