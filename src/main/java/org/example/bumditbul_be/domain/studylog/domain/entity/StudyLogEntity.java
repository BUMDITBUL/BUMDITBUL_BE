package org.example.bumditbul_be.domain.studylog.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bumditbul_be.domain.BaseTimeEntity;
import org.example.bumditbul_be.domain.plan.domain.entity.DailyStudyPlanEntity;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name="study_logs")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyLogEntity extends BaseTimeEntity {
    @Id @UuidGenerator @Column(name="log_id", nullable=false, updatable=false)
    private String logId;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="plan_id", nullable=false)
    private DailyStudyPlanEntity plan;
    @Column(name="actual_start_page", nullable=false) private Integer actualStartPage;
    @Column(name="actual_end_page", nullable=false) private Integer actualEndPage;
}
