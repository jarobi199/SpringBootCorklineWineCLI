package io.corkline.pairings;

import io.corkline.enums.SpiritType;
import io.corkline.interfaces.PairingStrategy;
import io.corkline.model.Spirit;

import java.util.List;
import java.util.Map;

public class SpiritPairingStrategy implements PairingStrategy<Spirit> {

    private static final Map<SpiritType, List<String>> SPIRIT_TYPE_PAIRINGS = Map.of(
            SpiritType.WHISKEY, List.of("dark chocolate", "blue cheese", "charcuterie"),
            SpiritType.BRANDY, List.of("apple tart", "duck breast", "creamy brie"),
            SpiritType.RUM, List.of("jerk chicken", "pineapple upside-down cake", "fried plantains"),
            SpiritType.GIN, List.of("smoked salmon", "cucumber sandwiches", "goat cheese"),
            SpiritType.TEQUILA, List.of("fish tacos", "guacamole", "ceviche"),
            SpiritType.OTHER, List.of("mixed nuts", "mild cheeses", "potato chips")
    );

    @Override
    public List<String> suggestPairings(Spirit spirit) {
        return SPIRIT_TYPE_PAIRINGS.getOrDefault(spirit.getSpiritType(), List.of("No specific pairing notes yet"));
    }
}
