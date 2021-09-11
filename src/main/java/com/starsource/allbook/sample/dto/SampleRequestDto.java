package com.starsource.allbook.sample.dto;

import com.starsource.allbook.sample.domain.Sample;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SampleRequestDto {

    private String message;

    public Sample toSample() {
        return new Sample(this.message);
    }
}
