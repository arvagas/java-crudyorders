package arvagas.orders.controllers;

import javax.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import arvagas.orders.models.Customer;
import arvagas.orders.services.CustomerServices;
import arvagas.orders.views.OrderCounts;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
  @Autowired
  private CustomerServices customerServices;

  @GetMapping(value = "/orders", produces = "application/json")
  public ResponseEntity<?> findAllCustomers() {
    List<Customer> customerList = new ArrayList<>();

    customerList = customerServices.findAllCustomers();

    return new ResponseEntity<>(customerList, HttpStatus.OK);
  }

  @GetMapping(value = "/customer/{id}", produces = "application/json")
  public ResponseEntity<?> findCustomerById(@PathVariable long id) {
    Customer customer = customerServices.findById(id);

    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @GetMapping(value = "/namelike/{likename}", produces = "application/json")
  public ResponseEntity<?> findCustomerByLikeName(@PathVariable String likename) {
    List<Customer> customerList = customerServices.findByNameLike(likename);

    return new ResponseEntity<>(customerList, HttpStatus.OK);
  }

  @GetMapping(value = "/orders/count", produces = "application/json")
  public ResponseEntity<?> findCustomerOrderCount() {
    List<OrderCounts> list = customerServices.getOrderCounts();

    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping(value = "/customer", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer customer) {
    customer.setCustcode(0);

    Customer newCustomer = customerServices.save(customer);

    HttpHeaders responseHeaders = new HttpHeaders();
    URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("{custcode}")
      .buildAndExpand(newCustomer.getCustcode())
      .toUri();
    responseHeaders.setLocation(newCustomerURI);

    return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.OK);
  }

  @PutMapping(value = "/customer/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> replaceCustomerById(@PathVariable long id, @Valid Customer customer) {
    customer.setCustcode(id);

    Customer replaceCustomer = customerServices.save(customer);

    return new ResponseEntity<>(replaceCustomer, HttpStatus.OK);
  }

  @PatchMapping(value = "/customer/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> updateCustomerById(@PathVariable long id, @Valid Customer customer) {
    Customer updateCustomer = customerServices.update(id, customer);

    return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
  }
}