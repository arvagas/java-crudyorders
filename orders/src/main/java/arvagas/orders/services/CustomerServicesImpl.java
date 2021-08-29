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
}
