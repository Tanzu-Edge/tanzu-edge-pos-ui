package vmw.tanzu.gaf.pos.dao.entity;

import lombok.Getter;
import lombok.Setter;

public class CCData {
	
	
	@Getter @Setter
	private String cvc;
	@Getter @Setter
	private String  expiry;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String number;

}
