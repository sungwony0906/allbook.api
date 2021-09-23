package com.starsource.allbook.goods.domain;

import com.starsource.allbook.common.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Goods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "goods_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GoodsStatus goodsStatus;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Builder
    public Goods(String name, GoodsStatus goodsStatus, LocalDateTime startDateTime, LocalDateTime endDateTime){
        this.name = name;
        this.goodsStatus = goodsStatus;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
