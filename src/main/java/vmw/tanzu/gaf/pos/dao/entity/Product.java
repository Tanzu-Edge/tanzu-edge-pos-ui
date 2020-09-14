package vmw.tanzu.gaf.pos.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class Product {
	
	public Product(String id, String name, String description, double costPrice, double sellingPrice,
			String productTypeId, String createdById, String updatedById, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.costPrice = costPrice;
		this.sellingPrice = sellingPrice;
		this.productTypeId = productTypeId;
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
	private double costPrice;
	
	@Getter @Setter
	private double sellingPrice;
	
	@Getter @Setter
	private String productTypeId;
	
	@Getter @Setter @JsonIgnore
	private String createdById;
	
	@Getter @Setter @JsonIgnore
	private String updatedById;
	
	@Getter @Setter @JsonIgnore
	private String createdAt;
	
	@Getter @Setter @JsonIgnore
	private String updatedAt;

}
