package org.example.bumditbul_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "daily_study_plans",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_plan_subject_date",
            columnNames = {"subject_id", "study_date"}
        )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"subject", "studyLog"})
public class DailyStudyPlan {

    @Id
    @UuidGenerator
    @Column(name = "plan_id", updatable = false, nullable = false)
    private String planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "study_date", nullable = false)
    private LocalDate studyDate;

    @Column(name = "plan_start_page", nullable = false)
    private int planStartPage;

    @Column(name = "plan_end_page", nullable = false)
    private int planEndPage;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "dailyStudyPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudyLog studyLog;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public DailyStudyPlan(Subject subject, LocalDate studyDate,
                           int planStartPage, int planEndPage) {
        this.subject = subject;
        this.studyDate = studyDate;
        this.planStartPage = planStartPage;
        this.planEndPage = planEndPage;
    }

    public void complete() {
        this.isCompleted = true;
    }

    public void updateRange(int planStartPage, int planEndPage) {
        this.planStartPage = planStartPage;
        this.planEndPage = planEndPage;
    }
}
