package swith.swithServer.domain.usertask.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.group.entity.GroupDomain;
import swith.swithServer.domain.user.entity.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserTask extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private GroupDomain group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TaskStatus taskStatus;

    public UserTask(GroupDomain group, User user, TaskStatus taskStatus) {
        this.group = group;
        this.user = user;
        this.taskStatus = taskStatus;
    }

    public void updateTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}