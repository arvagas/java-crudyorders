package arvagas.orders.services;

import javax.persistence.EntityNotFoundException;

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

  @Transactional
  @Override
  public void delete(long id) {
    Agent agent = agentRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found!"));
    
    if (agent.getCustomers().size() == 0) {
      agentRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException("Agent " + id + " has customers. Please reassign customers to new agent before removing.");
    }
  }
}
