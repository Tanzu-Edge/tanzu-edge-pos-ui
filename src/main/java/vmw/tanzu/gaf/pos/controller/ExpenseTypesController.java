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

import vmw.tanzu.gaf.pos.dao.entity.ExpenseType;

@RestController
public class ExpenseTypesController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	@GetMapping("/api/expensetypes")
    public List<ExpenseType> expenseTypes(@RequestParam(required = false) String q, @RequestParam String per_page, @RequestParam String page) {
		return this.searchQuery(per_page, page, q, false);
    }
	
	@GetMapping("/api/expensetypes/{id}")
    public ExpenseType expenseTypesById(@PathVariable(name = "id") String q) {
		List<ExpenseType> expenseTypes =  
				this.searchQuery(null, null, q, true);
		if(expenseTypes!=null && expenseTypes.size()>0) {
			return expenseTypes.get(0);
		}
		return null;
    }
	
	@GetMapping("/api/expensetypes/all")
    public List<ExpenseType> expenseTypes(@RequestParam String q) {
		return this.searchQuery(null, null, q, false);
    }
	
	@GetMapping("/api/expensetypes/all/items")
    public List<ExpenseType> expenseTypesAll() {
		return this.searchQuery(null, null, null, false);
    }
	
	@DeleteMapping("/api/expensetypes/{id}")
    public void deleteById(@PathVariable(name = "id") String q) {
		this.deleteQuery(q);
    }
	
	@PostMapping("/api/expensetypes")
    public void createProduct(@RequestBody ExpenseType productType) {
		System.out.println(productType.getDescription());
		this.insertQuery(productType);
    }

	public List<ExpenseType> searchQuery(String perPage, String page, String id, boolean exactId) {

		String expenseTypesQuery="";
		
		if(exactId){
			expenseTypesQuery = "SELECT * FROM expense_type "  
					+ ((id != null && !id.isEmpty())? "WHERE id = '" + id + "'":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}else{
			expenseTypesQuery = "SELECT * FROM expense_type "  
					+ ((id != null && !id.isEmpty())? "WHERE id LIKE '%" + id + "%' ":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}
		
		System.out.println("expense_type query : " + expenseTypesQuery);
		//Read records:
		List<ExpenseType> expenseTypes = jdbcTemplate.query(expenseTypesQuery,
				(resultSet, rowNum) -> new ExpenseType(resultSet.getString("id"),
						resultSet.getString("description"), resultSet.getString("createdById"), resultSet.getString("updatedById"),
						resultSet.getString("createdAt"), resultSet.getString("updatedAt")));
		
		return expenseTypes;
	}
	
	public void deleteQuery(String id) {

		if((id != null && !id.isEmpty())) {
			String deleteQuery = "DELETE FROM expense_type WHERE id = '"+ id + "'";
			System.out.println("Product query : " + deleteQuery);
			jdbcTemplate.execute(deleteQuery);
		}else {
			System.out.println("ProductId is null or empty : " + id);
		}
		return;
	}
	
	public void insertQuery(ExpenseType product) {
		String insertQuery = "INSERT INTO \"expense_type\"(\"createdAt\", \"updatedAt\", \"id\", \"description\", \"createdById\", \"updatedById\") VALUES (datetime('now'), datetime('now'), ?, ?, ?, ?) ";
		jdbcTemplate.update(insertQuery, product.getId(), product.getDescription(), "admin", "admin");
		return;
	}

	

}
