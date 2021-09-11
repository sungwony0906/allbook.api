package com.starsource.allbook.sample.dto;

import com.starsource.allbook.sample.domain.Sample;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SampleResponseDto {
    private Long id;
    private String message;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public SampleResponseDto(final String message) {
        this.message = message;
    }

    public static SampleResponseDto of(final Sample sample) {
        return new SampleResponseDto(sample.getId(),
                sample.getMessage(),
                sample.getCreateTime(),
                sample.getUpdateTime());
    }
}
