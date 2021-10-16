package com.starsource.allbook.goods.ui;

import com.starsource.allbook.goods.dto.GoodsResponseDto;
import com.starsource.allbook.goods.dto.GoodsSaveRequestDto;
import com.starsource.allbook.goods.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoodsApiController {

    private final GoodsService goodsService;

    @ApiOperation(value = "상품 등록")
    @PostMapping("/api/v1/goods")
    public ResponseEntity<GoodsResponseDto> save(@Valid @RequestBody GoodsSaveRequestDto goodsSaveRequestDto) {
        GoodsResponseDto responseDto = goodsService.save(goodsSaveRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/goods/"+responseDto.getId()))
                .body(responseDto);
    }

    @ApiOperation(value = "상품 목록 조회")
    @GetMapping("/api/v1/goods")
    public ResponseEntity<List<GoodsResponseDto>> findAll(){
        return ResponseEntity.ok(goodsService.findAll());
    }

    @ApiOperation(value = "상품 정보 조회")
    @GetMapping("/api/v1/goods/${id}")
    public ResponseEntity<GoodsResponseDto> findGoodsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.findGoodsById(id));
    }
}
