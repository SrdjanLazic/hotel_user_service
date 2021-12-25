package com.raf.hoteluserservice.service;

import com.raf.hoteluserservice.dto.ManagerCreateDto;
import com.raf.hoteluserservice.dto.ManagerDto;
import com.raf.hoteluserservice.dto.TokenRequestDto;
import com.raf.hoteluserservice.dto.TokenResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerService {

    // TODO

    Page<ManagerDto> findAll(Pageable pageable);

    ManagerDto addManager(ManagerCreateDto managerCreateDto);

    ManagerDto getManager(Long id);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
