package io.corkline.alert;

import io.corkline.enums.AlertType;
import io.corkline.model.Bottle;

public record AlertResult(Bottle bottle, AlertType alertType, String message) {}