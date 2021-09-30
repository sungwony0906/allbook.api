package com.starsource.allbook.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.starsource.allbook.member.domain.Address;
import com.starsource.allbook.member.domain.MemberRepository;
import com.starsource.allbook.member.domain.MemberStatus;
import com.starsource.allbook.member.domain.Role;
import com.starsource.allbook.member.dto.MemberResponseDto;
import com.starsource.allbook.member.dto.MemberSaveRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@WithMockUser(value = "spring", roles = "MEMBER")
public class MemberApiAcceptanceTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    void 회원_가입() {
        //given
        String name = "tester";
        String email = "tester@mail.com";
        MemberSaveRequestDto memberSaveRequestDto = 회원_요청_DTO_생성(name, email);

        //when
        ExtractableResponse<Response> response = 회원_가입_요청(memberSaveRequestDto);

        //then
        회원_가입_성공(response, memberSaveRequestDto);
    }

    @Test
    void 회원_조회() {
        String name = "tester";
        String email = "tester@mail.com";
        MemberSaveRequestDto memberSaveRequestDto = 회원_요청_DTO_생성(name, email);

        MemberResponseDto responseDto = 회원_가입_성공(회원_가입_요청(memberSaveRequestDto), memberSaveRequestDto);
        ExtractableResponse<Response> response = 회원_조회_요청(responseDto);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isNotNull();
    }

    private MemberSaveRequestDto 회원_요청_DTO_생성(String name, String email) {
        Address address = new Address("city", "street", "100");
        MemberSaveRequestDto memberSaveRequestDto = new MemberSaveRequestDto(Role.MEMBER,
                MemberStatus.ACTIVE, name, email, "", address, "password");
        return memberSaveRequestDto;
    }

    private ExtractableResponse<Response> 회원_가입_요청(MemberSaveRequestDto memberSaveRequestDto) {
        return RestAssured
                       .given()
                           .log().all()
                           .body(memberSaveRequestDto)
                           .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .auth()
                           .form("spring", encoder.encode("secret"))
                       .when()
                           .post("/api/v1/members")
                       .then()
                           .log().all()
                           .extract();
    }

    private MemberResponseDto 회원_가입_성공(ExtractableResponse<Response> response, MemberSaveRequestDto memberSaveRequestDto) {
        MemberResponseDto responseDto;
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        responseDto = response.as(MemberResponseDto.class);

        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getName()).isEqualTo(memberSaveRequestDto.getName());
        assertThat(responseDto.getEmail()).isEqualTo(memberSaveRequestDto.getEmail());
        return responseDto;
    }

    private ExtractableResponse<Response> 회원_조회_요청(MemberResponseDto responseDto) {
        return RestAssured
                       .given()
                           .log().all()
                       .when()
                           .get("/api/v1/members/"+responseDto.getId())
                       .then()
                           .log().all()
                           .extract();
    }
}
