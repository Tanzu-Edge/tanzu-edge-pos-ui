package vmw.tanzu.gaf.pos.dao.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Sale {
	
	@Getter @Setter
	private List<Item> items;
	@Getter @Setter
	private Double total;
	@Getter @Setter
	private Double taxAmount;
	@Getter @Setter
	private Double totalDiscount;
	@Getter @Setter
	private Double netTotal;
	@Getter @Setter
	private String transactionId;
	@Getter @Setter
	private CCData ccData;
	
}
