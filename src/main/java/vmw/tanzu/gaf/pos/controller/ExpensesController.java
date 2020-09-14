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

import vmw.tanzu.gaf.pos.dao.entity.Expense;

@RestController
public class ExpensesController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	@GetMapping("/api/expenses")
    public List<Expense> expenses(@RequestParam(required = false) String q, @RequestParam String per_page, @RequestParam String page) {
		return this.searchQuery(per_page, page, q, false);
    }
	
	@GetMapping("/api/expenses/{id}")
    public Expense expensesById(@PathVariable(name = "id") String q) {
		List<Expense> expenses =  
				this.searchQuery(null, null, q, true);
		if(expenses!=null && expenses.size()>0) {
			return expenses.get(0);
		}
		return null;
    }
	
	@GetMapping("/api/expenses/all")
    public List<Expense> expenses(@RequestParam String q) {
		return this.searchQuery(null, null, q, false);
    }
	
	@GetMapping("/api/expenses/all/items")
    public List<Expense> expensesAll() {
		return this.searchQuery(null, null, null, false);
    }
	
	@DeleteMapping("/api/expenses/{id}")
    public void deleteById(@PathVariable(name = "id") String q) {
		this.deleteQuery(q);
    }
	
	@PostMapping("/api/expenses")
    public void createExpense(@RequestBody Expense Expenses) {
		System.out.println(Expenses.getDescription());
		this.insertQuery(Expenses);
    }

	public List<Expense> searchQuery(String perPage, String page, String id, boolean exactId) {

		String expensesQuery="";
		
		if(exactId){
			expensesQuery = "SELECT * FROM expense "  
					+ ((id != null && !id.isEmpty())? "WHERE id = '" + id + "'":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}else{
			expensesQuery = "SELECT * FROM expense "  
					+ ((id != null && !id.isEmpty())? "WHERE id LIKE '%" + id + "%' ":"")
					+ ((perPage != null && !perPage.isEmpty())? " LIMIT " + perPage:"");
		}
		
		System.out.println("expenses query : " + expensesQuery);
		//Read records:
		List<Expense> expenses = jdbcTemplate.query(expensesQuery,
				(resultSet, rowNum) -> new Expense(resultSet.getString("id") , resultSet.getString("description") , resultSet.getDouble("amount") , resultSet.getString("expenseTypeId") , resultSet.getString("createdById") ,
						resultSet.getString("updatedById") , resultSet.getString("createdAt") , resultSet.getString("spentAt") , resultSet.getString("updatedAt")));
		
		return expenses;
	}
	
	public void deleteQuery(String id) {

		if((id != null && !id.isEmpty())) {
			String deleteQuery = "DELETE FROM expense WHERE id = '"+ id + "'";
			System.out.println("Customer query : " + deleteQuery);
			jdbcTemplate.execute(deleteQuery);
		}else {
			System.out.println("id is null or empty : " + id);
		}
		return;
	}
	
	public void insertQuery(Expense expense) {
		String insertQuery = "INSERT INTO \"expense\"(\"createdAt\", \"updatedAt\", \"id\", \"description\", \"amount\", \"spentAt\", \"expenseTypeId\", \"createdById\", \"updatedById\") VALUES (datetime('now'), datetime('now'), ?, ?, ?, ?, ?, ?, ?) ";
		jdbcTemplate.update(insertQuery, expense.getId(), expense.getDescription(), expense.getAmount(), expense.getSpentAt(), expense.getExpenseTypeId(),"admin", "admin");
		return;
	}

	

}
