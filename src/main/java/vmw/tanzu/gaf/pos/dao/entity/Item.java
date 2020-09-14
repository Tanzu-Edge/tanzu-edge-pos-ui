package vmw.tanzu.gaf.pos.dao.entity;

import lombok.Getter;
import lombok.Setter;

public class Item {
	@Getter @Setter
	private String id;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private Double qty;
	@Getter @Setter
	private Double price;
	@Getter @Setter
	private Double discount;
	@Getter @Setter
	private Double discountTotal;
	@Getter @Setter
	private Double sellingPrice;
	@Getter @Setter
	private Double totalPrice;
}
