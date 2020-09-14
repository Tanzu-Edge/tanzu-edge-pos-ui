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

import vmw.tanzu.gaf.pos.dao.entity.ProductType;

@RestController
public class ProductTypesController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	@GetMapping("/api/producttypes")
    public List<ProductType> productTypes(@RequestParam(required = false) String q, @RequestParam String per_page, @RequestParam String page) {
		return this.searchQuery(per_page, page, q, false);
    }
	
	@GetMapping("/api/producttypes/{id}")
    public ProductType productTypesById(@PathVariable(name = "id") String q) {
		List<ProductType> productTypes =  
				this.searchQuery(null, null, q, true);
		if(productTypes!=null && productTypes.size()>0) {
			return productTypes.get(0);
		}
		return null;
    }
	
	@GetMapping("/api/producttypes/all")
    public List<ProductType> productTypes(@RequestParam String q) {
		return this.searchQuery(null, null, q, false);
    }
	
	@GetMapping("/api/producttypes/all/items")
    public List<ProductType> productTypesAll() {
		return this.searchQuery(null, null, null, false);
    }
	
	@DeleteMapping("/api/producttypes/{id}")
    public void deleteById(@PathVariable(name = "id") String q) {
		this.deleteQuery(q);
    }
	
	@PostMapping("/api/producttypes")
    public void createProduct(@RequestBody ProductType productType) {
		System.out.println(productType.getDescription());
		this.insertQuery(productType);
    }

	public List<ProductType> searchQuery(String perPage, String page, String id, boolean exactId) {

		String productTypesQuery="";
		
		if(exactId){
			productTypesQuery = "SELECT * FROM product_type "  
					+ ((id != null && !id.isEmpty())? "WHERE id = '" + id + "'":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}else{
			productTypesQuery = "SELECT * FROM product_type "  
					+ ((id != null && !id.isEmpty())? "WHERE id LIKE '%" + id + "%' ":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}
		
		System.out.println("product_type query : " + productTypesQuery);
		//Read records:
		List<ProductType> productTypes = jdbcTemplate.query(productTypesQuery,
				(resultSet, rowNum) -> new ProductType(resultSet.getString("id"),
						resultSet.getString("description"), resultSet.getString("createdById"), resultSet.getString("updatedById"),
						resultSet.getString("createdAt"), resultSet.getString("updatedAt")));
		
		return productTypes;
	}
	
	public void deleteQuery(String id) {

		if((id != null && !id.isEmpty())) {
			String deleteQuery = "DELETE FROM product_type WHERE id = '"+ id + "'";
			System.out.println("Product query : " + deleteQuery);
			jdbcTemplate.execute(deleteQuery);
		}else {
			System.out.println("ProductId is null or empty : " + id);
		}
		return;
	}
	
	public void insertQuery(ProductType product) {
		String insertQuery = "INSERT INTO \"product_type\"(\"createdAt\", \"updatedAt\", \"id\", \"description\", \"createdById\", \"updatedById\") VALUES (datetime('now'), datetime('now'), ?, ?, ?, ?) ";
		jdbcTemplate.update(insertQuery, product.getId(), product.getDescription(), "admin", "admin");
		return;
	}

	

}
