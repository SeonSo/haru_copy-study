package com.project1.haruco.web.controller;

import com.project1.haruco.service.CertificationService;
import com.project1.haruco.web.dto.request.certification.CertificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/certification")
@RestController
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("")
    public Long createCertification(
            @RequestBody CertificationRequestDto certificationRequestDto,
            @AuthenticationPrincipal UserDetails userDetails){

        log.info("certificationRequestDto : {} ",certificationRequestDto);
        return certificationService.createCertification(certificationRequestDto,userDetails);

    }
}
