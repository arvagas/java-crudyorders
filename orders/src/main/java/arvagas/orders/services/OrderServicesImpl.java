package arvagas.orders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arvagas.orders.models.Order;
import arvagas.orders.repositories.OrderRepository;
import arvagas.orders.views.AdvanceAmountOrders;

@Transactional
@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices {
  @Autowired
  private OrderRepository orderRepository;

  @Override
  public Order findById(long id) {
    Order orderObj = orderRepository.findByOrdnum(id);

    return orderObj;
  }

  @Override
  public List<AdvanceAmountOrders> getAdvanceAmountOrders() {
    List<AdvanceAmountOrders> list = orderRepository.getAdvanceAmountOrders();

    return list;
  }
}
