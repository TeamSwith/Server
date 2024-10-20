package swith.swithServer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import swith.swithServer.domain.common.BaseEntity;


@Getter
@Entity
@RequiredArgsConstructor
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long group_id;

    @NotNull
    private String group_pw;

    @NotNull
    private int member_num;

    @NotNull
    private String subject;

    @NotNull
    private String period;

    @NotNull
    private String communication;

    private String notice;
}
