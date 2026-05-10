package org.example.bumditbul_be.domain.schedule.presentation.LegacyScheduleController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@Tag(name = "Schedule", description = "일정 API")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) { this.scheduleService = scheduleService; }

    @Operation(summary = "일정 생성")
    @PostMapping("/generate") public Map<String,String> generate(){ return scheduleService.generate(); }
    @Operation(summary = "일정 생성 상태 조회(SSE)")
    @GetMapping(value = "/status", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter status() throws IOException {
        SseEmitter emitter = new SseEmitter(1_000L);
        for (Map<String, String> s : scheduleService.statuses()) emitter.send(SseEmitter.event().name("status").data(s));
        emitter.complete();
        return emitter;
    }
    @Operation(summary = "일정 재생성 횟수 조회")
    @GetMapping("/regen-count") public Map<String,Integer> regen(){ return scheduleService.regenCount(); }
    @Operation(summary = "캘린더 조회")
    @GetMapping("/calendar") public Map<String,Object> calendar(){ return scheduleService.calendar(LocalDate.now()); }
    @Operation(summary = "일일 할 일 조회")
    @GetMapping("/daily") public List<Map<String,Object>> daily(){ return scheduleService.daily(); }
    @Operation(summary = "할 일 완료 처리")
    @PatchMapping("/{id}/done") public ResponseEntity<Map<String,Object>> done(@PathVariable Long id, @RequestBody Map<String, Boolean> req){ return ResponseEntity.ok(scheduleService.done(id, req.getOrDefault("done", false))); }
}
