package br.com.devgabrel.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.devgabrel.events.dto.ErrorMessage;
import br.com.devgabrel.events.dto.SubscriptionResponse;
import br.com.devgabrel.events.exception.EventNotFoundException;
import br.com.devgabrel.events.exception.SubscriptionConflitException;
import br.com.devgabrel.events.exception.UserIndicadorNotFoundException;
import br.com.devgabrel.events.model.User;
import br.com.devgabrel.events.service.SubscriptionService;

@RestController
public class SubscriptionController {
    
    @Autowired
    private SubscriptionService subService;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, 
                                                @RequestBody User subscriber,
                                                @PathVariable(required = false) Integer userId) { 
        try {
            SubscriptionResponse res = subService.createNewSubscription(prettyName, subscriber, userId);
            if (res != null) {
                return ResponseEntity.ok(res);
            }
            
        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));

        } catch (SubscriptionConflitException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
            
        } catch (UserIndicadorNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> getRanking(@PathVariable String prettyName) {
        try {
            return ResponseEntity.ok(subService.getCompleteRanking(prettyName).subList(0, 3));
        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> getRankingByUser(@PathVariable String prettyName, @PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(subService.getRankingByUser(prettyName, userId));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }
}
