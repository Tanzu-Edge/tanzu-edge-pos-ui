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

import vmw.tanzu.gaf.pos.dao.entity.Vendor;

@RestController
public class VendorsController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	@GetMapping("/api/vendors")
    public List<Vendor> vendors(@RequestParam(required = false) String q, @RequestParam String per_page, @RequestParam String page) {
		return this.searchQuery(per_page, page, q, false);
    }
	
	@GetMapping("/api/vendors/{id}")
    public Vendor vendorsById(@PathVariable(name = "id") String q) {
		List<Vendor> vendors =  
				this.searchQuery(null, null, q, true);
		if(vendors!=null && vendors.size()>0) {
			return vendors.get(0);
		}
		return null;
    }
	
	@GetMapping("/api/vendors/all")
    public List<Vendor> vendors(@RequestParam String q) {
		return this.searchQuery(null, null, q, false);
    }
	
	@GetMapping("/api/vendors/all/items")
    public List<Vendor> vendorsAll() {
		return this.searchQuery(null, null, null, false);
    }
	
	@DeleteMapping("/api/vendors/{id}")
    public void deleteById(@PathVariable(name = "id") String q) {
		this.deleteQuery(q);
    }
	
	@PostMapping("/api/vendors")
    public void createVendor(@RequestBody Vendor Vendors) {
		System.out.println(Vendors.getDescription());
		this.insertQuery(Vendors);
    }

	public List<Vendor> searchQuery(String perPage, String page, String id, boolean exactId) {

		String vendorsQuery="";
		
		if(exactId){
			vendorsQuery = "SELECT * FROM vendor "  
					+ ((id != null && !id.isEmpty())? "WHERE id = '" + id + "'":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}else{
			vendorsQuery = "SELECT * FROM vendor "  
					+ ((id != null && !id.isEmpty())? "WHERE id LIKE '%" + id + "%' ":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}
		
		System.out.println("vendors query : " + vendorsQuery);
		//Read records:
		List<Vendor> vendors = jdbcTemplate.query(vendorsQuery,
				(resultSet, rowNum) -> new Vendor(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("address"), resultSet.getString("mobile") , resultSet.getString("email") ,
						resultSet.getString("createdById") , resultSet.getString("updatedById") , resultSet.getString("createdAt") , resultSet.getString("updatedAt") ));
		
		return vendors;
	}
	
	public void deleteQuery(String id) {

		if((id != null && !id.isEmpty())) {
			String deleteQuery = "DELETE FROM vendor WHERE id = '"+ id + "'";
			System.out.println("Vendor query : " + deleteQuery);
			jdbcTemplate.execute(deleteQuery);
		}else {
			System.out.println("id is null or empty : " + id);
		}
		return;
	}
	
	public void insertQuery(Vendor vendor) {
		String insertQuery = "INSERT INTO \"vendor\"(\"createdAt\", \"updatedAt\", \"id\", \"description\", \"name\", \"address\", \"email\", \"mobile\", \"createdById\", \"updatedById\") VALUES (datetime('now'), datetime('now'), ?, ?, ?, ?, ?, ?, ?, ?) ";
		jdbcTemplate.update(insertQuery, vendor.getId(), vendor.getDescription(), vendor.getName(), vendor.getAddress(), vendor.getEmail(),vendor.getMobile(),"admin", "admin");
		return;
	}

	

}
