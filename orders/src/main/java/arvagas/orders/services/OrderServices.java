package arvagas.orders.services;

import java.util.List;

import arvagas.orders.models.Order;
import arvagas.orders.views.AdvanceAmountOrders;

public interface OrderServices {
  Order findById(long id);

  List<AdvanceAmountOrders> getAdvanceAmountOrders();
}
