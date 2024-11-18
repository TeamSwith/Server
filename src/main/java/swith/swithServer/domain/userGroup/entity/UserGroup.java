package swith.swithServer.domain.userGroup.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.Group;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private StudyGroup studyGroup;

    public UserGroup(User user, StudyGroup studyGroup){
        this.user = user;
        this.studyGroup = studyGroup;
    }

}
