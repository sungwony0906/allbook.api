package com.starsource.allbook.reserve.service;

import com.starsource.allbook.reserve.domain.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
}
