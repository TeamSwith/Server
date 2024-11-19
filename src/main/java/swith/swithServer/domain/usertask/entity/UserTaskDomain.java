//package swith.swithServer.domain.usertask.domain;
//
//import jakarta.persistence.*;
//import lombok.*;
//import swith.swithServer.domain.group.domain.GroupDomain;
//import swith.swithServer.domain.task.domain.Task;
//import swith.swithServer.domain.user.domain.User;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserTaskDomain {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id", nullable = false)
//    private Task task;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)
//    private GroupDomain group;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, length = 10)
//    private TaskStatus status;
//
//    public enum TaskStatus {
//        PENDING,
//        COMPLETED
//    }
//}