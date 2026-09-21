package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.BottleType;
import io.corkline.model.Bottle;
import io.corkline.repository.BottleRepository;
import io.corkline.util.BarChartUtil;
import io.corkline.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    @Autowired
    private BottleRepository bottleRepository;

    public void generateCellarSummary() {
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId());
        int totalBottles = bottles.size();

        Set<String > uniqueLabels = new HashSet<>();
        bottles.forEach(bottle -> uniqueLabels.add(bottle.getLabel()));
        int totalUniqueLabels = uniqueLabels.size();

        double totalPurchaseValue = bottles.stream().mapToDouble(Bottle::getPrice).sum();

        System.out.println("| CELLAR SUMMARY |");
        Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[yellow, bold]TOTAL BOTTLES[/]",
                        "[yellow, bold]TOTAL UNIQUE LABELS[/]",
                        "[yellow, bold]TOTAL PURCHASE VALUE[/]"
                )
                .row(String.valueOf(totalBottles), String.valueOf(totalUniqueLabels), InputHandler.formatAsMoney(totalPurchaseValue))
                .render();
        System.out.println();

        Map<String, Double> purchaseValueByTypeMap = new HashMap<String, Double>();
        for(BottleType bottleType : BottleType.values()) {
            double purchaseValueByType = bottles.stream().filter(bottle -> bottleType.equals(bottle.getBottleType())).mapToDouble(Bottle::getPrice).sum();
            purchaseValueByTypeMap.put(bottleType.name(), purchaseValueByType);
        }

        BarChartUtil.Builder barChart = BarChartUtil.builder().title("PURCHASE VALUE BY BOTTLE TYPE");
        for (Map.Entry<String, Double> entry : purchaseValueByTypeMap.entrySet()) {
            barChart.bar(entry.getKey(), entry.getValue());
        }
        barChart.render();
    }
}
