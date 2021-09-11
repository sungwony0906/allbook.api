package com.starsource.allbook.sample.ui;

import com.starsource.allbook.sample.dto.SampleRequestDto;
import com.starsource.allbook.sample.dto.SampleResponseDto;
import com.starsource.allbook.sample.service.SampleService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SampleController {

    private final SampleService sampleService;

    @PostMapping("/v1/sample")
    public ResponseEntity<SampleResponseDto> create(@RequestBody SampleRequestDto request){
        SampleResponseDto response = sampleService.create(request);
        return ResponseEntity.created(URI.create("/v1/sample" + response.getId()))
                       .body(response);
    }

    @GetMapping("/v1/sample")
    public ResponseEntity<List<SampleResponseDto>> getList() {
        return ResponseEntity.ok(sampleService.findAll());
    }
}
