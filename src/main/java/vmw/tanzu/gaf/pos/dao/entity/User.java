package vmw.tanzu.gaf.pos.dao.entity;

import lombok.Getter;
import lombok.Setter;

public class User{
	@Getter @Setter
	private String userid;
	@Getter @Setter
	private String password;
	public User(String userid, String password) {
		super();
		this.userid = userid;
		this.password = password;
	}
}	