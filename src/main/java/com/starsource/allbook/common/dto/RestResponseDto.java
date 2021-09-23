package com.starsource.allbook.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestResponseDto {

    private boolean success;
    private String message;

}
