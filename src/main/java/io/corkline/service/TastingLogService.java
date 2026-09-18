package io.corkline.service;

import io.corkline.model.TastingLog;
import io.corkline.repository.TastingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TastingLogService {
    @Autowired
    private TastingLogRepository tastingLogRepository;

    public void deleteTastingLogsByBottleId(String bottleId) {
        List<TastingLog>  tastingLogs = tastingLogRepository.findByBottleId(bottleId);
        tastingLogRepository.deleteAll(tastingLogs);
    }

}
