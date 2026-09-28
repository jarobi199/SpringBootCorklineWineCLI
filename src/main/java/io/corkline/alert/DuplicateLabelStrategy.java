package io.corkline.alert;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.AlertType;
import io.corkline.enums.BottleStatus;
import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import io.corkline.repository.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DuplicateLabelStrategy implements AlertStrategy {
    @Autowired
    private BottleRepository bottleRepository;

    @Override
    public boolean supports(Bottle bottle) {
        return BottleStatus.IN_CELLAR.equals(bottle.getStatus());
    }

    @Override
    public List<AlertResult> evaluate(Bottle bottle) {
        List<AlertResult> results = new ArrayList<>();
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId());
        for (Bottle b : bottles) {
            if((b.getProducer().equals(bottle.getProducer())) && (b.getLabel().equals(bottle.getLabel()))) {

            }
        }

        return  results;
    }
}
