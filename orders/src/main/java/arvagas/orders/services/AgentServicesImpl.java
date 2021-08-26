package arvagas.orders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arvagas.orders.models.Agent;
import arvagas.orders.repositories.AgentRepository;

@Transactional
@Service
public class AgentServicesImpl implements AgentServices {
  @Autowired
  private AgentRepository agentRepository;

  @Override
  public Agent findById(long id) {
    Agent agent = agentRepository.findByAgentcode(id);

    return agent;
  }
}
