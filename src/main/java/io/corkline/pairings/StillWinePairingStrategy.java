package io.corkline.pairings;

import io.corkline.interfaces.PairingStrategy;
import io.corkline.model.StillWine;

import java.util.List;

public class StillWinePairingStrategy implements PairingStrategy<StillWine> {
    @Override
    public List<String> suggestPairings(StillWine stillWine) {
        return List.of();
    }
}
