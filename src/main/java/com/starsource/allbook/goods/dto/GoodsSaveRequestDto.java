package com.starsource.allbook.goods.dto;

import com.starsource.allbook.goods.domain.Goods;
import com.starsource.allbook.goods.domain.GoodsStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoodsSaveRequestDto {

    @NotEmpty
    private String name;

    @NotNull
    private GoodsStatus goodsStatus;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    public Goods toEntity() {
        return Goods.builder()
                .name(name)
                .goodsStatus(goodsStatus)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
