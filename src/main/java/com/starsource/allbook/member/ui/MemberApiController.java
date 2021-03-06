package com.starsource.allbook.member.ui;

import com.starsource.allbook.member.dto.MemberResponseDto;
import com.starsource.allbook.member.dto.MemberSaveRequestDto;
import com.starsource.allbook.member.dto.MemberUpdateRequestDto;
import com.starsource.allbook.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/api/v1/members")
    public ResponseEntity saveMember(@RequestBody @Valid MemberSaveRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        MemberResponseDto responseDto = memberService.saveMember(requestDto);
        return ResponseEntity.created(URI.create("/api/v1/members/" + responseDto.getId()))
                       .body(responseDto);
    }

    @ApiOperation(value = "회원 정보 조회")
    @GetMapping("/api/v1/members/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.findMemberById(id));
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/api/v1/members/{id}")
    public ResponseEntity<MemberResponseDto> deleteMember(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMember(id));
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("/api/v1/members/{id}")
    public ResponseEntity updateMember(@PathVariable("id") Long id, @RequestBody @Valid MemberUpdateRequestDto requestDto) {
        return ResponseEntity.ok(memberService.updateMember(id, requestDto));
    }

    @ApiOperation(value = "비밀번호 수정")
    @PutMapping("/api/v1/members/{id}/password")
    public ResponseEntity updatePassword(@PathVariable("id") Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return ResponseEntity.ok(
                memberService.updatePassword(id,
                        passwordEncoder.encode(oldPassword),
                        passwordEncoder.encode(newPassword)));
    }
}
