package stockBPM.stockManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class stockController {

	private DatabaseController stockData = new DatabaseController("localhost:3306","stock","camundabpm","disp20", null);
	
	public stockController() {
	stockData.openConnection();
	}
	
	@GetMapping("list")
	public ArrayList<Object> listStock() throws SQLException{
		ArrayList<Object> data = (stockData.read("select * from fish"));
	    return data;
	}
	
	@PostMapping(value = "restock", consumes = "application/json")
	public String restockItem (@RequestBody stockItem item) throws SQLException {
		return stockData.restock(item.getName(), item.getQuantity()); 
	}
	
	@PostMapping(value = "order", consumes = "application/json")
	public Object newOrder (@RequestBody orderItem order) throws SQLException {
		return stockData.newOrder(order); 
	}

}
