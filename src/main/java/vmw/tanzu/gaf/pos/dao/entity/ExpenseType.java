package vmw.tanzu.gaf.pos.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class ExpenseType {
	
	
	public ExpenseType(String id, String description, String createdById, String updatedById, String createdAt,
			String updatedAt) {
		super();
		this.id = id;
		this.description = description;
		this.createdById = createdById;
		this.updatedById = updatedById;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String description;
	
	@Getter @Setter @JsonIgnore
	private String createdById;
	 
	@Getter @Setter @JsonIgnore
	private String updatedById;
	
	@Getter @Setter @JsonIgnore
	private String createdAt;
	
	@Getter @Setter @JsonIgnore
	private String updatedAt;

}
