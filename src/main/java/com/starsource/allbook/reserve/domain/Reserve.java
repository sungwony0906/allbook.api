package com.starsource.allbook.reserve.domain;

import com.starsource.allbook.common.entity.BaseEntity;
import com.starsource.allbook.goods.domain.Goods;
import com.starsource.allbook.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reserve extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private ReserveType reserveType;

    @Enumerated(EnumType.STRING)
    private ReserveStatus reserveStatus;

    private LocalDateTime confirmDateTime;

    private LocalDateTime cancelDateTime;

    @ManyToOne
    @JoinColumn(name = "confirm_member_id")
    private Member confirmMember;

    @ManyToOne
    @JoinColumn(name = "cancel_member_id")
    private Member cancelMember;

    @Builder
    public Reserve(Goods goods, Member member, LocalDateTime startDateTime, LocalDateTime endDateTime, ReserveType reserveType, ReserveStatus reserveStatus,
            LocalDateTime confirmDateTime, LocalDateTime cancelDateTime, Member confirmMember, Member cancelMember){
        this.goods = goods;
        this.member = member;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reserveType = reserveType;
        this.reserveStatus = reserveStatus;
        this.confirmDateTime = confirmDateTime;
        this.cancelDateTime = cancelDateTime;
        this.confirmMember = confirmMember;
        this.cancelMember = cancelMember;
    }

    /**
     * 예약을 생성한다
     * @return 신규 생성 예약
     */
    public static Reserve createReserve(Member member, Goods goods, LocalDateTime startDateTime, LocalDateTime endDateTime, ReserveType reserveType) {
        Reserve reserve = Reserve.builder()
                .member(member)
                .goods(goods)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .reserveType(reserveType)
                .reserveStatus(ReserveStatus.WAIT)
                .build();
        return reserve;
    }
}
