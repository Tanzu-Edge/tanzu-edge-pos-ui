package vmw.tanzu.gaf.pos.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class Vendor {
	
	public Vendor(String id, String name, String description, String address, String mobile, String email,
			String createdById, String updatedById, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.mobile = mobile;
		this.email = email;
		this.createdById = createdById;
		this.updatedById = updatedById;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String description;
	
	@Getter @Setter
	private String address;
	
	@Getter @Setter
	private String mobile;
	
	@Getter @Setter
	private String email;
	
	@Getter @Setter @JsonIgnore
	private String createdById;
	
	@Getter @Setter @JsonIgnore
	private String updatedById;
	
	@Getter @Setter @JsonIgnore
	private String createdAt;
	 
	@Getter @Setter @JsonIgnore
	private String updatedAt;
}
