package arvagas.orders.repositories;

import org.springframework.data.repository.CrudRepository;

import arvagas.orders.models.Agent;

public interface AgentRepository extends CrudRepository<Agent, Long> {
  Agent findByAgentcode(long id);
}
