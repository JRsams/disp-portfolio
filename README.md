# DISP-Portfolio 2021
Deliverables:
- Zachman Framework
- basic business process 
- elaborate business
process
- Deployed business process
- JUnit testing 
- Github versioning (see repo history)



## Installation

```
git clone https://gitlab.uwe.ac.uk/j2-sams/disp-portfolio.git
```
From Eclipse IDE:

**Test And Create Target**

stock-rest-spring -> **Run As** -> **Maven Install**

**Run the Application**

CamundaApplication.java -> **Run As** -> **Java Application**

## Tests
- TestDatabaseConnection.java - Scope: Pass if a connection can be made to the database.
- TestProcessStartWithVariabes.java - Scope: Pass if a process engine can be created with variables.
- TestOrderOutcome.java (process model test) - Test the expected behaviour of the process model.

**TestOrderOutcome scope and details**

Scope: Tests the expected process model behaviour for orders which should succeed, orders which require an item to be restocked and orders which are not valid. 

- orderSuccessful - Tests the path when an order is made for an item which exists in the database & has adequate stock.

- orderFailureEnd - Test the path when an order is made for an item which is not in stock or the requested quantity would exceed the current stock level for that item.

- orderErrorLoopback - Test the path when an order is made for an item which is invalid. **e.g.** an order for less than one of an item **or** an order for an item which is not in the database.




## Usage

**Camunda Process engine**

- Tasklist -> Start process -> **orderProcess**

**RestAPI**

Endpoints:
- http://localhost:8080/stock/list (returns all items in stock database) Method: GET
Response: ```["trout","£9.99","Stock count 0",
"pollock","£16.95","Stock count 3","brill","£22.50",
"Stock count 0","skate","£15.95","Stock count 0","monkfish","£24.99","Stock count 0"]```

- http://localhost:8080/stock/restock 
(increments the quantity of given item by the quantity specified) Method: POST, Consumes Order. 
Example body: ```{"name": "skate","quantity": 1}```
Response: ```{"message": "1 new stock ordered for skate","result": "success"}```

- http://localhost:8080/stock/order (decrements the quantity of a given item by the quantity specified) Method: POST, Consumes Order.
Example body: ```{"name": "skate","quantity": 1}```  
Response: ```{"message": "New Order for 1 x skate","result": "success"}```
- http://localhost:8080/stock/price (returns the price of a single item) Method: POST, Consumes Order. Example body: ```{"name": "skate"}```                 
Response ```{"message": "£15.95","result": "success"}```
