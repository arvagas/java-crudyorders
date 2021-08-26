package arvagas.orders.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
