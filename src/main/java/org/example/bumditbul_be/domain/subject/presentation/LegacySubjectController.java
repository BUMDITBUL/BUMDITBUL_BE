package org.example.bumditbul_be.domain.subject.presentation.LegacySubjectController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Subject", description = "과목/시험범위 API")
public class SubjectController {
    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService) { this.subjectService = subjectService; }

    @Operation(summary = "과목 목록 조회")
    @GetMapping("/subjects") public List<Map<String, Object>> subjects(){ return subjectService.getSubjects(); }
    @Operation(summary = "과목 목록 저장")
    @PutMapping("/subjects") public List<Map<String, Object>> putSubjects(@RequestBody List<Map<String, Object>> req){ return subjectService.saveSubjects(req); }
    @Operation(summary = "시험범위 조회")
    @GetMapping("/exam-ranges") public List<Map<String, Object>> ranges(){ return subjectService.getExamRanges(); }
    @Operation(summary = "시험범위 저장")
    @PutMapping("/exam-ranges") public List<Map<String, Object>> putRanges(@RequestBody List<Map<String, Object>> req){ return subjectService.saveExamRanges(req); }
}
