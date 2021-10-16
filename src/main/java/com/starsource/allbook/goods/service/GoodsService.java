package com.starsource.allbook.goods.service;

import com.starsource.allbook.goods.domain.Goods;
import com.starsource.allbook.goods.domain.GoodsRepository;
import com.starsource.allbook.goods.dto.GoodsResponseDto;
import com.starsource.allbook.goods.dto.GoodsSaveRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsResponseDto save(GoodsSaveRequestDto goodsSaveRequestDto) {
        return GoodsResponseDto.of(goodsRepository.save(goodsSaveRequestDto.toEntity()));
    }

    @Transactional(readOnly = true)
    public List<GoodsResponseDto> findAll() {
        return goodsRepository.findAll()
                       .stream()
                       .map(GoodsResponseDto::of)
                       .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GoodsResponseDto findGoodsById(Long id) {
        Goods goods = goodsRepository.findById(id)
                              .orElseThrow();
        return GoodsResponseDto.of(goods);
    }
}
