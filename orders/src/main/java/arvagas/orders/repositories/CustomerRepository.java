package arvagas.orders.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import arvagas.orders.models.Customer;
import arvagas.orders.views.OrderCounts;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  List<Customer> findByCustnameContainingIgnoringCase(String likename);

  // Using a custom query, return a list of all customers with the number of orders they have placed.
  @Query(value = "SELECT c.custname, COUNT(o.custcode) AS countorders " +
                  "FROM customers c LEFT JOIN orders o " +
                  "ON c.custcode = o.custcode " +
                  "GROUP BY c.custname",
                  nativeQuery = true)
  List<OrderCounts> findOrderCounts();
}
