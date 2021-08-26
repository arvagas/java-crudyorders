package arvagas.orders.services;

import java.util.List;

import arvagas.orders.models.Customer;
import arvagas.orders.views.OrderCounts;

public interface CustomerServices {
  List<Customer> findAllCustomers();

  Customer findById(long id);

  List<Customer> findByNameLike(String likename);

  List<OrderCounts> getOrderCounts();
}
