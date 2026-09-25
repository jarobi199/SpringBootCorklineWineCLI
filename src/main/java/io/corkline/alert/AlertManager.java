package io.corkline.alert;

import io.corkline.interfaces.AlertStrategy;
import io.corkline.model.Bottle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertManager {

    private final List<AlertStrategy> strategies;

    public AlertManager(List<AlertStrategy> strategies) {
        this.strategies = strategies;
    }
    public List<AlertResult> evaluate(List<Bottle> bottles) {
        return bottles.stream()
            .flatMap(b -> strategies.stream()
                .filter(s -> s.supports(b))
                .flatMap(s -> s.evaluate(b).stream()))  // flatten each strategy's list too
            .collect(Collectors.toList());
    }
}