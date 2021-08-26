package arvagas.orders.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arvagas.orders.models.Customer;
import arvagas.orders.repositories.CustomerRepository;
import arvagas.orders.views.OrderCounts;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices {
  @Autowired
  private CustomerRepository customerRepository;

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
}
