package com.starsource.allbook.member.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.starsource.allbook.config.auth.CustomOAuth2UserService;
import com.starsource.allbook.member.domain.Address;
import com.starsource.allbook.member.domain.MemberStatus;
import com.starsource.allbook.member.domain.Role;
import com.starsource.allbook.member.dto.MemberResponseDto;
import com.starsource.allbook.member.dto.MemberSaveRequestDto;
import com.starsource.allbook.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberApiController.class)
@WithMockUser(value = "spring", roles = "MEMBER")
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    MemberService memberService;

    @MockBean
    CustomOAuth2UserService customOAuth2UserService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void 회원가입_성공() throws Exception {
        //given
        Address address = new Address("city", "street", "100");
        MemberSaveRequestDto memberSaveRequestDto = new MemberSaveRequestDto(Role.MEMBER,
                MemberStatus.ACTIVE, "tester", "tester@mail.com", "", address, "password");
        MemberResponseDto mockResponseDto = MemberResponseDto.of(memberSaveRequestDto.toEntity());

        doReturn(mockResponseDto)
                .when(memberService)
                .saveMember(any(MemberSaveRequestDto.class));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/members")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(mapper.writeValueAsString(
                                                              memberSaveRequestDto)))
                                      .andExpect(status().isCreated())
                                      .andReturn();

        MemberResponseDto responseDto = mapper.readValue(
                mvcResult.getResponse().getContentAsString(), MemberResponseDto.class);
        assertThat(mockResponseDto.getName()).isEqualTo(responseDto.getName());
        assertThat(mockResponseDto.getEmail()).isEqualTo(responseDto.getEmail());
    }
}