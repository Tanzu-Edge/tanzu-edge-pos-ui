package vmw.tanzu.gaf.pos.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vmw.tanzu.gaf.pos.dao.entity.AuthResponse;
import vmw.tanzu.gaf.pos.dao.entity.PosUser;

@RestController
public class AuthController {
	
	@PostMapping("/api/login")
    public AuthResponse login(@RequestBody PosUser user) throws Exception{
		AuthResponse rsp = new AuthResponse();
		
		if(user.getUserid()!=null 
				&& user.getPassword()!=null) {
			
			PosUser dbUser = this.searchQuery(user.getUserid());
			if(dbUser!=null
					&& dbUser.getPassword()!= null
					&& dbUser.getPassword().equals(user.getPassword())) {
				int leftLimit = 48; // numeral '0'
			    int rightLimit = 122; // letter 'z'
			    int targetStringLength = 163;
			    Random random = new Random();
			 
			    String generatedString = random.ints(leftLimit, rightLimit + 1)
			      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			      .limit(targetStringLength)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
				rsp.setAuthToken(generatedString);
				rsp.setRefreshToken(generatedString);
				return rsp;
			}
			else {
				throw new Exception("User not found");
			}
		}
		else {
			throw new Exception("User not found");
		}
    }
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
    
	public PosUser searchQuery(String id) {

		String userQuey="";
		
		userQuey = "SELECT * FROM pos_user where id = '"+id+"'"  ;
		
		System.out.println("User query : " + userQuey);
		//Read records:
		List<PosUser> usrs = jdbcTemplate.query(userQuey,
				(resultSet, rowNum) -> new PosUser(resultSet.getString("id"), 
						resultSet.getString("password")));
		
		return usrs.get(0);
	}

}
