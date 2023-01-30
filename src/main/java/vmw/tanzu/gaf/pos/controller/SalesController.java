package vmw.tanzu.gaf.pos.controller;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.Metrics;
import vmw.tanzu.gaf.pos.dao.entity.Sale;

@RestController
public class SalesController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${posBackend:localhost:8080/api/sales/blah}")
	private String posBackend;
	
	@Value("${pos.storeId}")
	private String storeId;
	
	@GetMapping("/api/sales/initTransaction")
    public String initTransaction() {
		return this.generateTransactionId();
    }
	
	@PostMapping("/api/sales/blah")
	public void blahTransaction(@RequestBody Sale sale){
		System.out.println("Items on order " +sale.getItems().size() + " Order Total " + sale.getNetTotal()+" Sale CC Name " + sale.getCcData().getName());
		// post to rest/service(rabbitMQ Source) on the edge to post on rabbitMQ
		
	}
	
	@PostMapping("/api/sales/post")
	public Object postTransaction(@RequestBody Sale sale) {
		
		System.out.println("URI "+posBackend);
		ResponseEntity<Object> response = restTemplate.postForEntity( posBackend, sale, Object.class );
		
		Metrics.counter("inStore.purchases", "store.ID", storeId).increment(sale.getTotal());
		
		return response;

	}
	private String generateTransactionId() {
		String selectQuery = "SELECT count as \"cnt\" FROM \"transaction_id\" \"TransactionId\" WHERE \"TransactionId\".\"id\" = ?";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	
		ZonedDateTime zdt = ZonedDateTime.now();
		 
		//Get formatted String
		String zdtString = formatter.format(zdt);
		Integer cnt = 0;
		
		try{
			cnt = (Integer) jdbcTemplate.queryForObject(
					selectQuery, new Object[] { zdtString }, Integer.class);
		}catch(EmptyResultDataAccessException e) {
			//System.out.println("Empty Result Set");
		}
		cnt++;		
		String insertQuery = "INSERT INTO \"transaction_id\"(\"createdAt\", \"updatedAt\", \"id\", \"count\", \"createdById\", \"updatedById\") VALUES (now(), now(), ?, ?, ?, ?)";
		String updateQuery = "UPDATE \"transaction_id\" SET \"count\" = ?, \"updatedAt\" = now() WHERE \"id\" = ?";
		if(cnt==1) {
			jdbcTemplate.update(insertQuery, zdtString, cnt, "admin", "admin");
		}else {
			jdbcTemplate.update(updateQuery, cnt, zdtString);
		}
			
		String headerId = zdtString+cnt;
		String transactionHeader = "INSERT INTO \"transaction_header\"(\"createdAt\", \"updatedAt\", \"id\", \"discountOnItems\", \"discountOnTotal\", \"tax\", \"taxPercentageString\", \"billAmount\", \"netAmount\", "
				+ "\"amountPaid\", \"salesType\", \"transactionStatus\", \"comments\", \"customerId\", \"isActive\", \"createdById\", \"updatedById\") VALUES (now(), now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL, ?, ?, ?)";
		jdbcTemplate.update(transactionHeader,Integer.parseInt(headerId),0,0,0,"",0,0,0,1,0,true,"admin","admin");
		
		return headerId;
	}
	
	
	
	

}
