package com.starsource.allbook.member.ui;

import com.starsource.allbook.member.dto.MemberResponseDto;
import com.starsource.allbook.member.dto.MemberSaveRequestDto;
import com.starsource.allbook.member.dto.MemberUpdateRequestDto;
import com.starsource.allbook.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/api/v1/members")
    public ResponseEntity saveMember(@RequestBody @Valid MemberSaveRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.saveMember(requestDto);
        return ResponseEntity.created(URI.create("/api/v1/members/" + responseDto.getId()))
                       .body(responseDto);
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/api/v1/members/{id}")
    public ResponseEntity<MemberResponseDto> deleteMember(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMember(id));
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("/api/v1/members/{id}")
    public ResponseEntity updateMember(@PathVariable("id") Long id, @RequestBody @Valid MemberUpdateRequestDto requestDto) {
        return null;
    }
}
