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

import vmw.tanzu.gaf.pos.dao.entity.Customer;

@RestController
public class CustomersController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	@GetMapping("/api/customers")
    public List<Customer> customers(@RequestParam(required = false) String q, @RequestParam String per_page, @RequestParam String page) {
		return this.searchQuery(per_page, page, q, false);
    }
	
	@GetMapping("/api/customers/{id}")
    public Customer customersById(@PathVariable(name = "id") String q) {
		List<Customer> customers =  
				this.searchQuery(null, null, q, true);
		if(customers!=null && customers.size()>0) {
			return customers.get(0);
		}
		return null;
    }
	
	@GetMapping("/api/customers/all")
    public List<Customer> customers(@RequestParam String q) {
		return this.searchQuery(null, null, q, false);
    }
	
	@GetMapping("/api/customers/all/items")
    public List<Customer> customersAll() {
		return this.searchQuery(null, null, null, false);
    }
	
	@DeleteMapping("/api/customers/{id}")
    public void deleteById(@PathVariable(name = "id") String q) {
		this.deleteQuery(q);
    }
	
	@PostMapping("/api/customers")
    public void createCustomer(@RequestBody Customer Customers) {
		System.out.println(Customers.getDescription());
		this.insertQuery(Customers);
    }

	public List<Customer> searchQuery(String perPage, String page, String id, boolean exactId) {

		String customersQuery="";
		
		if(exactId){
			customersQuery = "SELECT * FROM customer "  
					+ ((id != null && !id.isEmpty())? "WHERE id = '" + id + "'":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}else{
			customersQuery = "SELECT * FROM customer "  
					+ ((id != null && !id.isEmpty())? "WHERE id LIKE '%" + id + "%' ":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}
		
		System.out.println("customers query : " + customersQuery);
		//Read records:
		List<Customer> customers = jdbcTemplate.query(customersQuery,
				(resultSet, rowNum) -> new Customer(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("address"), resultSet.getString("mobile") , resultSet.getString("email") ,
						resultSet.getString("createdById") , resultSet.getString("updatedById") , resultSet.getString("createdAt") , resultSet.getString("updatedAt") ));
		
		return customers;
	}
	
	public void deleteQuery(String id) {

		if((id != null && !id.isEmpty())) {
			String deleteQuery = "DELETE FROM customer WHERE id = '"+ id + "'";
			System.out.println("Customer query : " + deleteQuery);
			jdbcTemplate.execute(deleteQuery);
		}else {
			System.out.println("id is null or empty : " + id);
		}
		return;
	}
	
	public void insertQuery(Customer customer) {
		String insertQuery = "INSERT INTO \"customer\"(\"createdAt\", \"updatedAt\", \"id\", \"description\", \"name\", \"address\", \"email\", \"mobile\", \"createdById\", \"updatedById\") VALUES (datetime('now'), datetime('now'), ?, ?, ?, ?, ?, ?, ?, ?) ";
		jdbcTemplate.update(insertQuery, customer.getId(), customer.getDescription(), customer.getName(), customer.getAddress(), customer.getEmail(),customer.getMobile(),"admin", "admin");
		return;
	}

	

}
