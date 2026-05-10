package org.example.bumditbul_be.domain.schedule.service.LegacyScheduleServiceImpl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Override public Map<String, String> generate() { return Map.of("status", "generating"); }
    @Override public List<Map<String, String>> statuses() { return List.of(Map.of("status", "generating"), Map.of("status", "completed")); }
    @Override public Map<String, Integer> regenCount() { return Map.of("used", 1, "max", 2, "remaining", 1); }
    @Override public Map<String, Object> calendar(LocalDate date) { return Map.of(date.toString(), Map.of("total", 3, "done", 1, "completed", false, "isReview", false)); }
    @Override public List<Map<String, Object>> daily() { return List.of(Map.of("id", 1, "subject", "수학", "material", "교과서", "startPage", 10, "endPage", 18, "difficulty", "상", "done", false, "isReview", false)); }
    @Override public Map<String, Object> done(Long id, boolean done) { return Map.of("id", id, "done", done); }
}
