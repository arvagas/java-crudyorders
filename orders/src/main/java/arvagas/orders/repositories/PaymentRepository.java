package arvagas.orders.repositories;

import org.springframework.data.repository.CrudRepository;

import arvagas.orders.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
  
}
