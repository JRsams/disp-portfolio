package stockBPM.stockManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class stockController {

	private Database stockData = new Database("localhost:3306","stock","camundabpm","disp20", null);
	
	public stockController() {
	stockData.openConnection();
	}
	
	@GetMapping("list")
	public ArrayList<Object> request() throws SQLException{
		ArrayList<Object> data = (stockData.query("select * from fish"));
	    return data;
	}

}
