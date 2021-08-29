package arvagas.orders.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arvagas.orders.models.Customer;
import arvagas.orders.models.Order;
import arvagas.orders.models.Payment;
import arvagas.orders.repositories.CustomerRepository;
import arvagas.orders.repositories.OrderRepository;
import arvagas.orders.repositories.PaymentRepository;
import arvagas.orders.views.AdvanceAmountOrders;

@Transactional
@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices {
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PaymentRepository paymentRepository;

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

  @Transactional
  @Override
  public Order save(Order order) {
    Order newOrder = new Order();

    if (order.getOrdnum() != 0) {
      orderRepository.findById(order.getOrdnum())
        .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
      newOrder.setOrdnum(order.getOrdnum());
    }

    newOrder.setOrdamount(order.getOrdamount());
    newOrder.setAdvanceamount(order.getAdvanceamount());
    newOrder.setOrderdescription(order.getOrderdescription());

    Customer customer = customerRepository.findById(order.getCustomer().getCustcode())
      .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " not found!"));
    newOrder.setCustomer(customer);

    newOrder.getPayments().clear();
    for (Payment p : order.getPayments()) {
      Payment payment = paymentRepository.findById(p.getPaymentid())
        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
      newOrder.getPayments().add(payment);
    }

    return orderRepository.save(newOrder);
  }
}
