package org.example.bumditbul_be.domain.subject.service.LegacySubjectServiceImpl;

import org.example.bumditbul_be.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final UserService userService;
    private volatile List<Map<String, Object>> subjects = new CopyOnWriteArrayList<>();
    private volatile List<Map<String, Object>> examRanges = new CopyOnWriteArrayList<>();

    public SubjectServiceImpl(UserService userService) { this.userService = userService; }
    @Override public List<Map<String, Object>> getSubjects() { return subjects; }
    @Override public List<Map<String, Object>> saveSubjects(List<Map<String, Object>> subjects) { this.subjects = subjects; return this.subjects; }
    @Override public List<Map<String, Object>> getExamRanges() { return examRanges; }
    @Override public List<Map<String, Object>> saveExamRanges(List<Map<String, Object>> ranges) {
        this.examRanges = ranges;
        if (!ranges.isEmpty() && ranges.get(0).containsKey("examDate")) userService.setExamDate(ranges.get(0).get("examDate").toString());
        return this.examRanges;
    }
}
