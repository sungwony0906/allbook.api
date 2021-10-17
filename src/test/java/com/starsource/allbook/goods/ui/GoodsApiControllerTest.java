package com.starsource.allbook.goods.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.starsource.allbook.config.auth.CustomOAuth2UserService;
import com.starsource.allbook.goods.domain.GoodsStatus;
import com.starsource.allbook.goods.dto.GoodsResponseDto;
import com.starsource.allbook.goods.dto.GoodsSaveRequestDto;
import com.starsource.allbook.goods.service.GoodsService;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GoodsApiController.class)
@WithMockUser(value = "spring", roles = "MEMBER")
class GoodsApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GoodsService goodsService;

    @MockBean
    CustomOAuth2UserService customOAuth2UserService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void 상품_저장_실패_파라미터_없음() throws Exception {
        //given
        String name = "anyName";
        LocalDateTime now = LocalDateTime.now();
        GoodsResponseDto mockResponseDto = makeGoodsResponseDto(name, now);

        doReturn(mockResponseDto).when(goodsService).save(any(GoodsSaveRequestDto.class));

        //when
        mockMvc.perform(post("/api/v1/goods")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void 상품_저장_실패_파라미터_불충분() throws Exception {
        //given
        String name = "anyName";
        LocalDateTime now = LocalDateTime.now();
        GoodsResponseDto mockResponseDto = makeGoodsResponseDto(name, now);
        GoodsSaveRequestDto requestDto = new GoodsSaveRequestDto(null, GoodsStatus.ACTIVE, now,
                now.plusDays(1));

        doReturn(mockResponseDto).when(goodsService).save(any(GoodsSaveRequestDto.class));

        //when
        mockMvc.perform(post("/api/v1/goods")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void 상품_저장_성공() throws Exception {
        //given
        String name = "anyName";
        LocalDateTime now = LocalDateTime.now();
        GoodsResponseDto mockResponseDto = makeGoodsResponseDto(name, now);

        GoodsSaveRequestDto requestDto = new GoodsSaveRequestDto(name, GoodsStatus.ACTIVE, now,
                now.plusDays(1));

        doReturn(mockResponseDto).when(goodsService).save(any(GoodsSaveRequestDto.class));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/goods")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(mapper.writeValueAsString(requestDto)))
                                      .andExpect(status().isCreated())
                                      .andReturn();

        GoodsResponseDto responseDto = mapper.readValue(
                mvcResult.getResponse().getContentAsString(), GoodsResponseDto.class);

        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getName()).isEqualTo(name);
        assertThat(responseDto.getStartDateTime()).isEqualTo(now);
        assertThat(responseDto.getEndDateTime()).isEqualTo(now.plusDays(1));
    }

    @Test
    void 상품_전체조회_성공() throws Exception {
        //given
        String name = "anyName";
        LocalDateTime now = LocalDateTime.now();
        GoodsResponseDto mockResponseDto = makeGoodsResponseDto(name, now);

        doReturn(Lists.newArrayList(mockResponseDto)).when(goodsService).findAll();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/goods"))
                                      .andExpect(status().isOk())
                                      .andReturn();

        List<GoodsResponseDto> list = mapper.readValue(mvcResult.getResponse()
                                                               .getContentAsString(),
                new TypeReference<List<GoodsResponseDto>>() {});

        GoodsResponseDto responseDto = list.get(0);
        assertThat(list.size()).isEqualTo(1);
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getName()).isEqualTo(name);
        assertThat(responseDto.getGoodsStatus()).isEqualTo(GoodsStatus.ACTIVE);
        assertThat(responseDto.getStartDateTime()).isEqualTo(now);
        assertThat(responseDto.getEndDateTime()).isEqualTo(now.plusDays(1));
    }

    @Test
    void 상품_조회() throws Exception {
        //given
        long id = 1L;
        String name = "anyName";
        LocalDateTime now = LocalDateTime.now();
        GoodsResponseDto mockResponseDto = makeGoodsResponseDto(name, now);

        doReturn(mockResponseDto).when(goodsService).findGoodsById(id);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/goods/" + id))
                                      .andExpect(status().isOk())
                                      .andReturn();

        //then
        GoodsResponseDto responseDto = mapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<GoodsResponseDto>() {
                });

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getName()).isEqualTo(name);
        assertThat(responseDto.getGoodsStatus()).isEqualTo(GoodsStatus.ACTIVE);
        assertThat(responseDto.getStartDateTime()).isEqualTo(now);
        assertThat(responseDto.getEndDateTime()).isEqualTo(now.plusDays(1));
    }

    private GoodsResponseDto makeGoodsResponseDto(String name, LocalDateTime now) {
        GoodsResponseDto mockResponseDto = GoodsResponseDto.builder()
                                                   .id(1L)
                                                   .name(name)
                                                   .goodsStatus(GoodsStatus.ACTIVE)
                                                   .startDateTime(now)
                                                   .endDateTime(now.plusDays(1))
                                                   .build();
        return mockResponseDto;
    }
}