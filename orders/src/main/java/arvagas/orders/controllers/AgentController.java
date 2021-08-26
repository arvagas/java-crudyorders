package arvagas.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import arvagas.orders.models.Agent;
import arvagas.orders.services.AgentServices;

@RestController
@RequestMapping(value = "/agents")
public class AgentController {
  @Autowired
  AgentServices agentServices;

  @GetMapping(value = "/agent/{id}", produces = "application/json")
  public ResponseEntity<?> findAgentById(@PathVariable long id) {
    Agent agent = agentServices.findById(id);

    return new ResponseEntity<>(agent, HttpStatus.OK);
  }
}
