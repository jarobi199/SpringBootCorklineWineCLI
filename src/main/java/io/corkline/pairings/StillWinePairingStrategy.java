package io.corkline.pairings;

import io.corkline.enums.WineBodyStyle;
import io.corkline.enums.WineColor;
import io.corkline.interfaces.PairingStrategy;
import io.corkline.model.StillWine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StillWinePairingStrategy implements PairingStrategy<StillWine> {

    private static final Map<WineColor, List<String>> BASE_BY_COLOR = Map.of(
            WineColor.RED,  List.of("grilled red meat", "aged hard cheeses"),
            WineColor.WHITE, List.of("roast chicken", "soft cheeses"),
            WineColor.ROSE, List.of("charcuterie", "light salads")
    );

    private static final Map<WineBodyStyle, String> WEIGHT_SUPPLEMENT = Map.of(
            WineBodyStyle.FULL_BODIED, "dishes with rich, fatty sauces",
            WineBodyStyle.MEDIUM,  "roasted vegetables",
            WineBodyStyle.LIGHT, "delicate, lighter fare"
    );

    @Override
    public List<String> suggestPairings(StillWine stillWine) {
        List<String> suggestions = new ArrayList<>(BASE_BY_COLOR.get(stillWine.getWineColor()));
        suggestions.add(WEIGHT_SUPPLEMENT.get(stillWine.getBodyStyle()));

        return suggestions;
    }
}
