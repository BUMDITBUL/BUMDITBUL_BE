package org.example.bumditbul_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "dailyStudyPlan")
public class StudyLog {

    @Id
    @UuidGenerator
    @Column(name = "log_id", updatable = false, nullable = false)
    private String logId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private DailyStudyPlan dailyStudyPlan;

    @Column(name = "actual_start_page", nullable = false)
    private int actualStartPage;

    @Column(name = "actual_end_page", nullable = false)
    private int actualEndPage;

    @Column(name = "completed_at", nullable = false, updatable = false)
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        this.completedAt = LocalDateTime.now();
    }

    @Builder
    public StudyLog(DailyStudyPlan dailyStudyPlan,
                    int actualStartPage, int actualEndPage) {
        this.dailyStudyPlan = dailyStudyPlan;
        this.actualStartPage = actualStartPage;
        this.actualEndPage = actualEndPage;
    }
}
