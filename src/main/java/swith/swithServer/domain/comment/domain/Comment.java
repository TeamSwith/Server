//package swith.swithServer.domain.comment.domain;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//import swith.swithServer.domain.group.domain.GroupDomain;
//import swith.swithServer.domain.user.domain.User;
//import swith.swithServer.domain.study.domain.StudyDomain;
//import swith.swithServer.domain.common.BaseEntity;
//
//@Getter
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Comment extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long commentId;
//
//    @NotNull
//    @Column(length = 255) // 댓글 최대 길이 설정
//    private String content;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "study_id", nullable = false)
//    private Study study;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)
//    private GroupDomain group;
//
//    public void updateContent(String content) {
//        this.content = content;
//    }
//}
//
//
//// // 스터디 참조 코드 추가 예정
//
//// @Getter
//// @Entity
//// @NoArgsConstructor(access = AccessLevel.PROTECTED)
//// public class Comment extends BaseEntity {
//
////     @Id
////     @GeneratedValue(strategy = GenerationType.IDENTITY)
////     private Long commentId;
//
////     @NotNull
////     @Column(columnDefinition = "TEXT")
////     private String content;
//
//// //    @ManyToOne(fetch = FetchType.LAZY)
//// //    @JoinColumn(name = "studyId",  nullable = false)
//// //    private Study study;
//
////     @ManyToOne(fetch = FetchType.LAZY)
////     @JoinColumn(name = "userId",  nullable = false)
////     private User user;
//
//// //    public void updateContent(String content) {
//// //        this.content = content;
//// //    }
//// }