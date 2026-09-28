package io.corkline.pairings;

import io.corkline.enums.DosageLevel;
import io.corkline.interfaces.PairingStrategy;
import io.corkline.model.SparklingWine;

import java.util.List;
import java.util.Map;

public class SparklingWinePairingStrategy implements PairingStrategy<SparklingWine> {

    private static final Map<DosageLevel, List<String>> DOSAGE_LEVEL_PAIRINGS = Map.of(
            DosageLevel.BRUT_NATURE, List.of("sushi", "raw oysters", "sashimi"),
            DosageLevel.EXTRA_BRUT, List.of("grilled seafood", "goat cheese", "smoked salmon"),
            DosageLevel.BRUT, List.of("oysters", "fried food", "salty snacks"),
            DosageLevel.SEC, List.of("spicy Thai curry", "glazed pork belly", "mature cheddar"),
            DosageLevel.DEMI_SEC, List.of("fruit tarts", "foie gras", "blue cheese")
    );

    @Override
    public List<String> suggestPairings(SparklingWine sparklingWine) {
        return DOSAGE_LEVEL_PAIRINGS.getOrDefault(sparklingWine.getDosageLevel(), List.of("No specific pairing notes yet"));
    }

}
