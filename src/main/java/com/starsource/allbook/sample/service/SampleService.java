package com.starsource.allbook.sample.service;

import com.starsource.allbook.sample.domain.SampleRepository;
import com.starsource.allbook.sample.dto.SampleRequestDto;
import com.starsource.allbook.sample.dto.SampleResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleResponseDto create(SampleRequestDto request) {
        return SampleResponseDto.of(sampleRepository.save(request.toSample()));
    }

    public List<SampleResponseDto> findAll() {
        return sampleRepository.findAll()
                       .stream()
                       .map(SampleResponseDto::of)
                       .collect(Collectors.toList());
    }
}
