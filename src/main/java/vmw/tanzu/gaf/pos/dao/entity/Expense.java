package vmw.tanzu.gaf.pos.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class Expense {
	
	
	public Expense(String id, String description, double amount, String expenseTypeId, String createdById,
			String updatedById, String createdAt, String spentAt, String updatedAt) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.expenseTypeId = expenseTypeId;
		this.createdById = createdById;
		this.updatedById = updatedById;
		this.createdAt = createdAt;
		this.spentAt = spentAt;
		this.updatedAt = updatedAt;
	}

	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String description;
	
	@Getter @Setter
	private double amount;
	
	@Getter @Setter
	private String expenseTypeId;
	
	@Getter @Setter @JsonIgnore
	private String createdById;
	
	@Getter @Setter @JsonIgnore
	private String updatedById;
	
	@Getter @Setter @JsonIgnore
	private String createdAt;
	
	@Getter @Setter
	private String spentAt;
	
	@Getter @Setter @JsonIgnore
	private String updatedAt;
}
