package org.example.bumditbul_be.domain.schedule.service.LegacyScheduleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ScheduleService {
    Map<String, String> generate();
    List<Map<String, String>> statuses();
    Map<String, Integer> regenCount();
    Map<String, Object> calendar(LocalDate date);
    List<Map<String, Object>> daily();
    Map<String, Object> done(Long id, boolean done);
}
