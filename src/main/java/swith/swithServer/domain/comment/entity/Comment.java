package swith.swithServer.domain.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.common.BaseEntity;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(length = 255)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudyGroup studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;


    public void updateContent(String content) {
        this.content = content;
    }

    public Comment(String content, Study study, User user, StudyGroup studyGroup) {
        this.content = content;
        this.study = study;
        this.user = user;
        this.studyGroup = studyGroup;
    }
}