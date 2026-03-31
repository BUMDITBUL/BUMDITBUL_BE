package org.example.bumditbul_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "subjects",
    indexes = {
        @Index(name = "idx_subjects_user_test", columnList = "user_id, test_schedule")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user", "dailyStudyPlans"})
public class Subject {

    @Id
    @UuidGenerator
    @Column(name = "subject_id", updatable = false, nullable = false)
    private String subjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;

    @Column(name = "start_page", nullable = false)
    private int startPage;

    @Column(name = "end_page", nullable = false)
    private int endPage;

    @Column(name = "current_page", nullable = false)
    private int currentPage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Difficulty difficulty;

    @Column(name = "test_schedule", nullable = false)
    private LocalDate testSchedule;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyStudyPlan> dailyStudyPlans = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.currentPage = this.startPage;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Subject(User user, String subjectName, int startPage, int endPage,
                   Difficulty difficulty, LocalDate testSchedule) {
        this.user = user;
        this.subjectName = subjectName;
        this.startPage = startPage;
        this.endPage = endPage;
        this.difficulty = difficulty;
        this.testSchedule = testSchedule;
    }

    public void updateSchedule(int startPage, int endPage,
                                Difficulty difficulty, LocalDate testSchedule) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.difficulty = difficulty;
        this.testSchedule = testSchedule;
    }

    public void advanceCurrentPage(int completedPage) {
        this.currentPage = completedPage;
    }

    public int getRemainingPages() {
        return this.endPage - this.currentPage;
    }
}
