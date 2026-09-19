package io.corkline.interfaces;

import io.corkline.alert.AlertResult;
import io.corkline.model.Bottle;

import java.util.List;

public interface AlertStrategy {
    boolean supports(Bottle bottle);
    List<AlertResult> evaluate(Bottle bottle);
}