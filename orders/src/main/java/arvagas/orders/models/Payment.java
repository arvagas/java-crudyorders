package arvagas.orders.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long paymentid;

  private String type;

  @ManyToMany(mappedBy = "payments")
  @JsonIgnoreProperties(value = "payment", allowSetters = true)
  private Set<Order> orders = new HashSet<>();

  public Payment() {
  }

  public Payment(String type) {
    this.type = type;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  public long getPaymentid() {
    return paymentid;
  }

  public void setPaymentid(long paymentid) {
    this.paymentid = paymentid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}