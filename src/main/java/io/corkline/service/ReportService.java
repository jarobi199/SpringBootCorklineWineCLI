package io.corkline.service;

import io.corkline.authentication.SessionContext;
import io.corkline.enums.BottleType;
import io.corkline.enums.DrinkingWindowStatus;
import io.corkline.model.*;
import io.corkline.repository.BottleRepository;
import io.corkline.repository.CellarLocationRepository;
import io.corkline.repository.TastingLogRepository;
import io.corkline.util.BarChartUtil;
import io.corkline.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private TastingLogRepository tastingLogRepository;
    @Autowired
    private CellarLocationRepository cellarLocationRepository;
    @Autowired
    private BottleService bottleService;

    public void generateCellarSummary() {
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId());
        int totalBottles = bottles.size();

        Set<String > uniqueLabels = new HashSet<>();
        bottles.forEach(bottle -> uniqueLabels.add(bottle.getLabel()));
        int totalUniqueLabels = uniqueLabels.size();

        double totalPurchaseValue = bottles.stream().mapToDouble(Bottle::getPrice).sum();

        Map<BottleType, Double> purchaseValueByTypeMap = new HashMap<BottleType, Double>();
        for(BottleType bottleType : BottleType.values()) {
            double purchaseValueByType = bottles.stream().filter(bottle -> bottleType.equals(bottle.getBottleType())).mapToDouble(Bottle::getPrice).sum();
            purchaseValueByTypeMap.put(bottleType, purchaseValueByType);
        }

        CellarSummary cellarSummary = new CellarSummary(totalBottles, totalUniqueLabels, totalPurchaseValue, purchaseValueByTypeMap);

        System.out.println("| CELLAR SUMMARY |");
        Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[yellow, bold]TOTAL BOTTLES[/]",
                        "[yellow, bold]TOTAL UNIQUE LABELS[/]",
                        "[yellow, bold]TOTAL PURCHASE VALUE[/]"
                )
                .row(String.valueOf(cellarSummary.totalBottles()), String.valueOf(cellarSummary.totalUniqueLabels()), InputHandler.formatAsMoney(cellarSummary.totalPurchaseValue()))
                .render();
        System.out.println();

        BarChartUtil.Builder barChart = BarChartUtil.builder().title("PURCHASE VALUE BY BOTTLE TYPE");
        for (Map.Entry<BottleType, Double> entry : cellarSummary.valueByType().entrySet()) {
            barChart.bar(entry.getKey().name(), entry.getValue());
        }
        barChart.render();
    }

    public void generateDrinkingWindowReport(Bottle bottle) {
        DrinkingWindow drinkingWindow = bottle.calculateDrinkingWindow();
        long yearsUntilPeak = ChronoUnit.YEARS.between(LocalDate.now(), LocalDate.ofYearDay(drinkingWindow.peakStartYear(), 1));
        DrinkingWindowReport drinkingWindowReport = new DrinkingWindowReport(bottle.getLabel(), bottle.getVintageYear(), drinkingWindow.peakStartYear(), drinkingWindow.peakEndYear(), getDrinkingWindowStatus(drinkingWindow), (int) yearsUntilPeak);

        System.out.println("| DRINKING WINDOW REPORT |");
        Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[yellow, bold]BOTTLE LABEL[/]",
                        "[yellow, bold]VINTAGE YEAR[/]",
                        "[yellow, bold]PEAK WINDOW START[/]",
                        "[yellow, bold]PEAK WINDOW END[/]",
                        "[yellow, bold]STATUS[/]",
                        "[yellow, bold]YEARS UNTIL PEAK[/]"
                )
                .row(drinkingWindowReport.bottleLabel(), String.valueOf(drinkingWindowReport.vintageYear()), String.valueOf(drinkingWindowReport.peakStartYear()), String.valueOf(drinkingWindowReport.peakEndYear()),
                        drinkingWindowReport.currentStatus().name(), String.valueOf(drinkingWindowReport.yearsUntilPeak()))
                .render();
        System.out.println();
    }

    public void generateTastingRatingsByProducer() {
        List<ProducerTasting> producerTastings = new ArrayList<>();
        List<TastingLog> tastingLogs = tastingLogRepository.findByUserId(SessionContext.getUser().getId());
        Set<String> producers = tastingLogs.stream().map(TastingLog::getBottleProducer).collect(Collectors.toSet());
        for (String producer : producers) {
            List<TastingLog> tastingLogList = tastingLogs.stream().filter(tastingLog -> tastingLog.getBottleProducer().equals(producer)).toList();
            int tastingLogCount = tastingLogList.size();
            double averageRating = tastingLogList.stream().mapToInt(TastingLog::getRating).average().orElse(0.0);
           producerTastings.add(new ProducerTasting(producer, averageRating, tastingLogCount));
        }

        producerTastings.sort(Comparator.comparing(ProducerTasting::averageRating).reversed());

        BarChartUtil.Builder barChart = BarChartUtil.builder().title("AVERAGE TASTING RATING BY PRODUCER").valueFormat("%.1f");
        for (ProducerTasting producerTasting : producerTastings) {
            barChart.bar(producerTasting.producer(), producerTasting.averageRating(), producerTasting.tastingCount() + " tastings");
        }
        barChart.render();
    }

    private DrinkingWindowStatus getDrinkingWindowStatus(DrinkingWindow drinkingWindow) {
        DrinkingWindowStatus status = null;
        LocalDate startPeak = LocalDate.ofYearDay(drinkingWindow.peakStartYear(), 1);
        LocalDate endPeak = LocalDate.ofYearDay(drinkingWindow.peakEndYear(), 1);
        LocalDate today = LocalDate.now();

        if(today.isAfter(endPeak)) {
            status = DrinkingWindowStatus.PAST_PEAK;
        }
        else if (today.isBefore(startPeak)) {
            status = DrinkingWindowStatus.TOO_YOUNG;
        }
        else if ((startPeak.isAfter(today)) && (today.isBefore(endPeak))) {
            status = DrinkingWindowStatus.AT_PEAK;
        }
        return status;
    }

    public void generateLowStockAndFavorites() {
        List<Bottle> bottles = bottleRepository.findByUserId(SessionContext.getUser().getId()).stream()
                .filter(bottle -> bottle.isFavorite() && (bottle.getQuantity() < SessionContext.getUser().getFavoriteStockThreshold())).sorted(Comparator.comparing(Bottle::getQuantity)).toList();
        System.out.println("| LOW STOCK AND FAVORITES |");
        bottleService.displayBottles(bottles, true);
    }

    public void generateStorageConditions() {
        List<CellarLocation> cellarLocations = cellarLocationRepository.findByUserId(SessionContext.getUser().getId());
      if(!cellarLocations.isEmpty()) {
          for (CellarLocation cellarLocation : cellarLocations) {

          }
      }
      else
      {
        System.out.println(" There are no cellar locations");
      }
    }
}
