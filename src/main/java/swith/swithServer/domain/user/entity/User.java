package swith.swithServer.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    private User(String email,String nickname) {
        this.email=email;
        this.nickname=nickname;
        this.snsType=SnsType.KAKAO;
        this.userStatus=UserStatus.ACTIVE;
        this.userRole=UserRole.NORMAL;
    }

}
