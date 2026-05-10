package org.example.bumditbul_be.domain.plan.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bumditbul_be.domain.BaseTimeEntity;
import org.example.bumditbul_be.domain.subject.domain.entity.SubjectEntity;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "daily_study_plans", indexes = @Index(name="uq_plan_subject_date", columnList = "subject_id, study_date", unique = true))
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyStudyPlanEntity extends BaseTimeEntity {
    @Id @UuidGenerator @Column(name="plan_id", nullable=false, updatable=false)
    private String planId;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="subject_id", nullable=false)
    private SubjectEntity subject;
    @Column(name="study_date", nullable=false) private LocalDate studyDate;
    @Column(name="plan_start_page", nullable=false) private Integer planStartPage;
    @Column(name="plan_end_page", nullable=false) private Integer planEndPage;
    @Column(name="is_completed", nullable=false) private Boolean isCompleted = false;
}
