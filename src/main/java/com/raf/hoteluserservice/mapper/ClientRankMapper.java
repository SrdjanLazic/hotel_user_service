package com.raf.hoteluserservice.mapper;

import com.raf.hoteluserservice.domain.ClientRank;
import com.raf.hoteluserservice.dto.ClientRankDto;
import org.springframework.stereotype.Component;

@Component
public class ClientRankMapper {


    public ClientRankMapper() {
    }

    public ClientRankDto clientRankToClientRankDto(ClientRank clientRank) {
        ClientRankDto clientRankDto = new ClientRankDto();
        clientRankDto.setDiscount(clientRank.getDiscount());
        clientRankDto.setMaxNumberOfReservations(clientRank.getMaxNumberOfReservations());
        clientRankDto.setMinNumberOfReservations(clientRank.getMinNumberOfReservations());
        return clientRankDto;
    }

}
