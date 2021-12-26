package com.raf.hoteluserservice.service;

import com.raf.hoteluserservice.dto.ClientDto;
import com.raf.hoteluserservice.dto.TokenRequestDto;
import com.raf.hoteluserservice.dto.TokenResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
