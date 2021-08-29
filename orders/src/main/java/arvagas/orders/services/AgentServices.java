package arvagas.orders.services;

import arvagas.orders.models.Agent;

public interface AgentServices {
  Agent findById(long id);

  void delete(long id);
}
