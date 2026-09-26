package io.corkline.interfaces;

import io.corkline.model.Bottle;

import java.util.List;

public interface PairingStrategy <T extends Bottle> {
    List<String> suggestPairings(T bottle);
}
