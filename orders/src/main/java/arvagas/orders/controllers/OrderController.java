package arvagas.orders.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import arvagas.orders.models.Order;
import arvagas.orders.services.OrderServices;
import arvagas.orders.views.AdvanceAmountOrders;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
  @Autowired
  private OrderServices orderServices;

  @GetMapping(value = "/order/{id}", produces = "application/json")
  public ResponseEntity<?> findOrderById(@PathVariable long id) {
    Order orderObj = orderServices.findById(id);

    return new ResponseEntity<>(orderObj, HttpStatus.OK);
  }

  @GetMapping(value = "/advanceamount", produces = "application/json")
  public ResponseEntity<?> getOrderAdvanceAmount() {
    List<AdvanceAmountOrders> list = orderServices.getAdvanceAmountOrders();

    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping(value = "/order", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order) {
    order.setOrdnum(0);

    Order newOrder = orderServices.save(order);

    HttpHeaders responseHeaders = new HttpHeaders();
    URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{ordnum}")
        .buildAndExpand(newOrder.getOrdnum())
        .toUri();
    responseHeaders.setLocation(newRestaurantURI);

    return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping(value = "/order/{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<?> replaceOrderById(@PathVariable long id, @RequestBody @Valid Order order) {
    order.setOrdnum(id);

    Order replaceOrder = orderServices.save(order);

    return new ResponseEntity<>(replaceOrder, HttpStatus.OK);
  }

  @DeleteMapping(value = "/order/{id}")
  public ResponseEntity<?> deleteOrderById(@PathVariable long id) {
    orderServices.delete(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
