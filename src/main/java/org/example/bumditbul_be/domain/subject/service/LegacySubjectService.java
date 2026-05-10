package org.example.bumditbul_be.domain.subject.service.LegacySubjectService;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    List<Map<String, Object>> getSubjects();
    List<Map<String, Object>> saveSubjects(List<Map<String, Object>> subjects);
    List<Map<String, Object>> getExamRanges();
    List<Map<String, Object>> saveExamRanges(List<Map<String, Object>> ranges);
}
