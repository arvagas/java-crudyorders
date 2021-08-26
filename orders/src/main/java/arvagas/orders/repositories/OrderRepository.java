package arvagas.orders.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import arvagas.orders.models.Order;
import arvagas.orders.views.AdvanceAmountOrders;

public interface OrderRepository extends CrudRepository<Order, Long> {
  Order findByOrdnum(long id);

  @Query(value = "SELECT o.ordnum, o.advanceamount, c.custname " +
                  "FROM orders o LEFT JOIN customers c " +
                  "ON o.custcode = c.custcode " +
                  "WHERE o.advanceamount > 0",
                  nativeQuery = true)
  List<AdvanceAmountOrders> getAdvanceAmountOrders();
}
