package arvagas.orders.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long ordnum;
  private double ordamount;
  private double advanceamount;
  private String orderdescription;

  @ManyToOne
  @JoinColumn(name = "custcode", nullable = false)
  @JsonIgnoreProperties(value = "orders", allowSetters = true)
  private Customer customer;

  @ManyToMany()
  @JoinTable(name = "orderspayments",
  joinColumns = @JoinColumn(name = "ordnum"),
  inverseJoinColumns = @JoinColumn(name = "paymentid"))
  @JsonIgnoreProperties(value = "orders", allowSetters = true)
  private Set<Payment> payments = new HashSet<>();

  @Transient
  public boolean hasvalueforordamount = false;

  @Transient
  public boolean hasvalueforadvanceamount = false;
  
  public Order() {
  }

  public Order(double ordamount, double advanceamount, Customer customer, String orderdescription) {
    this.ordamount = ordamount;
    this.advanceamount = advanceamount;
    this.orderdescription = orderdescription;
    this.customer = customer;
  }

  public Set<Payment> getPayments() {
    return payments;
  }

  public void setPayments(Set<Payment> payments) {
    this.payments = payments;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public long getOrdnum() {
    return ordnum;
  }

  public void setOrdnum(long ordnum) {
    this.ordnum = ordnum;
  }

  public double getOrdamount() {
    return ordamount;
  }

  public void setOrdamount(double ordamount) {
    hasvalueforordamount = true;
    this.ordamount = ordamount;
  }

  public double getAdvanceamount() {
    return advanceamount;
  }

  public void setAdvanceamount(double advanceamount) {
    hasvalueforadvanceamount = true;
    this.advanceamount = advanceamount;
  }

  public String getOrderdescription() {
    return orderdescription;
  }

  public void setOrderdescription(String orderdescription) {
    this.orderdescription = orderdescription;
  }
}