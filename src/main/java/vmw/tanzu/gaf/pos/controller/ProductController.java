package vmw.tanzu.gaf.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vmw.tanzu.gaf.pos.dao.entity.Product;

@RestController
public class ProductController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	@GetMapping("/api/products")
    public List<Product> products(@RequestParam(required = false) String q, @RequestParam String per_page, @RequestParam String page) {
		return this.searchQuery(per_page, page, q, false);
    }
	
	@GetMapping("/api/products/{id}")
    public Product productsById(@PathVariable(name = "id") String q) {
		List<Product> products =  
				this.searchQuery(null, null, q, true);
		if(products!=null && products.size()>0) {
			return products.get(0);
		}
		return null;
    }
	
	@GetMapping("/api/products/all")
    public List<Product> products(@RequestParam String q) {
		return this.searchQuery(null, null, q, false);
    }
	
	@GetMapping("/api/products/all/items")
    public List<Product> productsAll() {
		return this.searchQuery(null, null, null, false);
    }
	
	@DeleteMapping("/api/products/{id}")
    public void deleteById(@PathVariable(name = "id") String q) {
		this.deleteQuery(q);
    }
	
	@PostMapping("/api/products")
    public void createProduct(@RequestBody Product product) {
		System.out.println(product.getName());
		this.insertQuery(product);
    }

	public List<Product> searchQuery(String perPage, String page, String productId, boolean exactId) {

		String productsQuery="";
		
		if(exactId){
			productsQuery = "SELECT * FROM product "  
					+ ((productId != null && !productId.isEmpty())? "WHERE id = '" + productId + "'":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}else{
			productsQuery = "SELECT * FROM product "  
					+ ((productId != null && !productId.isEmpty())? "WHERE id LIKE '%" + productId + "%' ":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}
		
		System.out.println("Product query : " + productsQuery);
		//Read records:
		List<Product> products = jdbcTemplate.query(productsQuery,
				(resultSet, rowNum) -> new Product(resultSet.getString("id"), 
						resultSet.getString("name"), resultSet.getString("description"), resultSet.getDouble("costPrice"), resultSet.getDouble("sellingPrice"), 
						resultSet.getString("productTypeId"), resultSet.getString("createdById"), resultSet.getString("updatedById"), resultSet.getString("createdAt"), 
						resultSet.getString("updatedAt")));
		
		return products;
	}
	
	public void deleteQuery(String productId) {

		if((productId != null && !productId.isEmpty())) {
			String deleteQuery = "DELETE FROM product WHERE id = '"+ productId + "'";
			System.out.println("Product query : " + deleteQuery);
			jdbcTemplate.execute(deleteQuery);
		}else {
			System.out.println("ProductId is null or empty : " + productId);
		}
		return;
	}
	
	public void insertQuery(Product product) {
		String insertQuery = "INSERT INTO \"product\"(\"createdAt\", \"updatedAt\", \"id\", \"name\", \"description\", \"costPrice\", \"sellingPrice\", \"productTypeId\", \"createdById\", \"updatedById\") VALUES (datetime('now'), datetime('now'), ?, ?, ?, ?, ?, ?, ?, ?) ";
		jdbcTemplate.update(insertQuery, product.getId(), product.getName(), product.getDescription(), product.getCostPrice(), product.getSellingPrice(), product.getProductTypeId(), "admin", "admin");
		return;
	}

	
	
}