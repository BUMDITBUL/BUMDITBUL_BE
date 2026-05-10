package org.example.bumditbul_be.domain.subject.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bumditbul_be.domain.BaseTimeEntity;
import org.example.bumditbul_be.domain.subject.domain.enum.DifficultyLevel;
import org.example.bumditbul_be.domain.user.domain.entity.UserEntity;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name="subjects", indexes=@Index(name="idx_subjects_user_test", columnList = "user_id, test_schedule"))
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubjectEntity extends BaseTimeEntity {
    @Id @UuidGenerator @Column(name="subject_id", nullable=false, updatable=false)
    private String subjectId;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;
    @Column(name="subject_name", nullable=false, length=100) private String subjectName;
    @Column(name="start_page", nullable=false) private Integer startPage;
    @Column(name="end_page", nullable=false) private Integer endPage;
    @Column(name="current_page", nullable=false) private Integer currentPage;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=1) private DifficultyLevel difficulty = DifficultyLevel.중;
    @Column(name="test_schedule", nullable=false) private LocalDate testSchedule;
}
