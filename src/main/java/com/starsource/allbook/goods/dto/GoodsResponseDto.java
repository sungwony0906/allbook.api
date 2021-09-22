package com.starsource.allbook.goods.dto;

import com.starsource.allbook.goods.domain.Goods;
import com.starsource.allbook.goods.domain.GoodsStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GoodsResponseDto {

    private Long id;
    private String name;
    private GoodsStatus goodsStatus;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    public GoodsResponseDto(Long id, String name, GoodsStatus goodsStatus, LocalDateTime startDateTime, LocalDateTime endDateTime){
        this.id = id;
        this.name = name;
        this.goodsStatus = goodsStatus;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static GoodsResponseDto of(Goods goods) {
        return GoodsResponseDto.builder()
                .id(goods.getId())
                .name(goods.getName())
                .goodsStatus(goods.getGoodsStatus())
                .startDateTime(goods.getStartDateTime())
                .endDateTime(goods.getEndDateTime())
                .build();
    }
}
