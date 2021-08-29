package arvagas.orders.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arvagas.orders.models.Agent;
import arvagas.orders.models.Customer;
import arvagas.orders.models.Order;
import arvagas.orders.models.Payment;
import arvagas.orders.repositories.AgentRepository;
import arvagas.orders.repositories.CustomerRepository;
import arvagas.orders.repositories.OrderRepository;
import arvagas.orders.repositories.PaymentRepository;
import arvagas.orders.views.OrderCounts;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices {
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AgentRepository agentRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public List<Customer> findAllCustomers() {
    List<Customer> customerList = new ArrayList<>();

    customerRepository.findAll().iterator().forEachRemaining((c) -> customerList.add(c));

    return customerList;
  }

  @Override
  public Customer findById(long id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
  }

  @Override
  public List<Customer> findByNameLike(String likename) {
    List<Customer> customerList = customerRepository.findByCustnameContainingIgnoringCase(likename);

    return customerList;
  }

  @Override
  public List<OrderCounts> getOrderCounts() {
    List<OrderCounts> list = customerRepository.findOrderCounts();
    
    return list;
  }

  @Transactional
  @Override
  public Customer save(Customer customer) {
    Customer newCustomer = new Customer();

    if (customer.getCustcode() != 0) {
      customerRepository.findById(customer.getCustcode())
        .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " is not found!"));
      newCustomer.setCustcode(customer.getCustcode());
    }

    newCustomer.setCustname(customer.getCustname());
    newCustomer.setCustcity(customer.getCustcity());
    newCustomer.setWorkingarea(customer.getWorkingarea());
    newCustomer.setCustcountry(customer.getCustcountry());
    newCustomer.setGrade(customer.getGrade());
    newCustomer.setOpeningamt(customer.getOpeningamt());
    newCustomer.setReceiveamt(customer.getReceiveamt());
    newCustomer.setPaymentamt(customer.getPaymentamt());
    newCustomer.setOutstandingamt(customer.getOutstandingamt());
    newCustomer.setPhone(customer.getPhone());

    Agent agent = agentRepository.findById(customer.getAgent().getAgentcode())
      .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent() + " is not found"));
    newCustomer.setAgent(agent);

    newCustomer.getOrders().clear();
    for (Order o : customer.getOrders()) {
      Order newOrder = new Order();
      newOrder.setOrdamount(o.getOrdamount());
      newOrder.setAdvanceamount(o.getAdvanceamount());
      newOrder.setOrderdescription(o.getOrderdescription());

      newOrder.setCustomer(newCustomer);

      newOrder.getPayments().clear();
      for(Payment p : o.getPayments()) {
        Payment newPay = paymentRepository.findById(p.getPaymentid())
          .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
        newOrder.getPayments().add(newPay);
      }

      newCustomer.getOrders().add(newOrder);
    }

    return customerRepository.save(customer);
  }

  @Transactional
  @Override
  public Customer update(long id, Customer customer) {
    Customer updateCustomer = customerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
    
    if (customer.getCustname() != null) {
      updateCustomer.setCustname(customer.getCustname());
    }
    if (customer.getCustcity() != null) {
      updateCustomer.setCustcity(customer.getCustcity());
    }
    if (customer.getWorkingarea() != null) {
      updateCustomer.setWorkingarea(customer.getWorkingarea());
    }
    if (customer.getCustcountry() != null) {
      updateCustomer.setCustcountry(customer.getCustcountry());
    }
    if (customer.getGrade() != null) {
      updateCustomer.setGrade(customer.getGrade());
    }
    if (customer.getOpeningamt() != 0.0) {
      updateCustomer.setOpeningamt(customer.getOpeningamt());
    }
    if (customer.getReceiveamt() != 0.0) {
      updateCustomer.setReceiveamt(customer.getReceiveamt());
    }
    if (customer.getPaymentamt() != 0.0) {
      updateCustomer.setPaymentamt(customer.getPaymentamt());
    }
    if (customer.getOutstandingamt() != 0.0) {
      updateCustomer.setOutstandingamt(customer.getOutstandingamt());
    }
    if (customer.getPhone() != "") {
      updateCustomer.setPhone(customer.getPhone());
    }

    Agent agent = agentRepository.findById(customer.getAgent().getAgentcode())
      .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found!"));
    updateCustomer.setAgent(agent);

    if (customer.getOrders().size() > 0) {
      updateCustomer.getOrders().clear();
      for (Order o : customer.getOrders()) {
        Order order = orderRepository.findById(o.getOrdnum())
          .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " not found!"));
        
        if (o.hasvalueforordamount) {
          order.setOrdamount(o.getOrdamount());
        }
        if (o.hasvalueforadvanceamount) {
          order.setAdvanceamount(o.getAdvanceamount());
        }
        if (o.getOrderdescription() != null) {
          order.setOrderdescription(o.getOrderdescription());
        }

        order.setCustomer(updateCustomer);

        if (o.getPayments().size() > 0) {
          order.getPayments().clear();

          for (Payment p : o.getPayments()) {
            Payment payment = paymentRepository.findById(p.getPaymentid())
              .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
            order.getPayments().add(payment);
          }
        }
      }
    }

    return customerRepository.save(updateCustomer);
  }

  public void delete(long id) {
    customerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));
    
    customerRepository.deleteById(id);
  }
}
