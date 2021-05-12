package org.camunda.stock;
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
	public Response restockItem (@RequestBody orderItem item) throws SQLException {
		return stockData.restock(item); 
	}

	@PostMapping(value = "order", consumes = "application/json")
	public Response newOrder (@RequestBody orderItem order) throws SQLException {
		return stockData.newOrder(order);
	}
	
	@PostMapping(value = "price", consumes = "application/json")
	public Response getPrice (@RequestBody stockItem item) throws SQLException{
		String statement = "select price from fish where name = '" + item.getName() + "'";
		ArrayList<Object> data = (stockData.read(statement));
		Response response = new Response();
		if  (data.isEmpty()) {
			response.setMessage("Item name error: " + "no item with name " + item.getName());
			response.setResult("failure");
			return response;
		}
		String price = (String) data.get(0);
		response.setMessage(price);
		response.setResult("success");
	    return response;
	}

}
