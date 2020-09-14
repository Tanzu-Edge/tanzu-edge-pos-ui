package vmw.tanzu.gaf.pos.dao.entity;

import lombok.Getter;
import lombok.Setter;

public class AuthResponse {
	@Getter @Setter
	private String authToken;
	@Getter @Setter
	private String refreshToken;
}
