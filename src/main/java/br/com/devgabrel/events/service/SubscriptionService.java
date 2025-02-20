package br.com.devgabrel.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devgabrel.events.dto.SubscriptionResponse;
import br.com.devgabrel.events.exception.EventNotFoundException;
import br.com.devgabrel.events.exception.SubscriptionConflitException;
import br.com.devgabrel.events.exception.UserIndicadorNotFoundException;
import br.com.devgabrel.events.model.Event;
import br.com.devgabrel.events.model.Subscription;
import br.com.devgabrel.events.model.User;
import br.com.devgabrel.events.repo.EventRepo;
import br.com.devgabrel.events.repo.SubscriptionRepo;
import br.com.devgabrel.events.repo.UserRepo;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo evtRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subRepo;
    
    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        
        Event evt = evtRepo.findByPrettyName(eventName);
        if (evt == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe!");
        }

        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        User indicador = userRepo.findById(userId).orElse(null);
        if (indicador == null) {
            throw new UserIndicadorNotFoundException("Usuário " + userId + " indicador não existe!");
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);

        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null) {
            throw new SubscriptionConflitException("Usuário " + userRec.getName() + " já está inscrito no evento " + evt.getTitle());
        }

        Subscription res = subRepo.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/" + res.getEvent().getPrettyName() + "/" + res.getSubscriber().getId());
    }
}
