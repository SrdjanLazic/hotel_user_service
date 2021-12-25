package com.raf.hoteluserservice.mapper;
import com.raf.hoteluserservice.domain.Client;
import com.raf.hoteluserservice.dto.ClientCreateDto;
import com.raf.hoteluserservice.dto.ClientDto;
import com.raf.hoteluserservice.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    private RoleRepository roleRepository;

    public ClientMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ClientDto clientToClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setEmail(client.getEmail());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setUsername(client.getUsername());
        clientDto.setPhoneNumber(client.getPhoneNumber());
        clientDto.setBirthday(client.getBirthday());
        clientDto.setPassportNumber(client.getPassportNumber());
        clientDto.setNumberOfReservations(client.getNumberOfReservations());
        return clientDto;
    }

    /*

    private boolean verifiedMail = false;
     */

    public Client clientCreateDtoToClient(ClientCreateDto clientCreateDto) {
        Client client = new Client();
        client.setEmail(clientCreateDto.getEmail());
        client.setFirstName(clientCreateDto.getFirstName());
        client.setLastName(clientCreateDto.getLastName());
        client.setUsername(clientCreateDto.getUsername());
        client.setPassword(clientCreateDto.getPassword());
        client.setBirthday(clientCreateDto.getBirthday());
        client.setPhoneNumber(clientCreateDto.getPhoneNumber());
        client.setPassportNumber(clientCreateDto.getPassportNumber());
        client.setRole(roleRepository.findRoleByName("ROLE_CLIENT").get());
        // TODO: proveriti da li treba da se setuje na True
        client.setAccess(true);
        client.setNumberOfReservations(0);
        client.setVerifiedMail(false);
        return client;
    }
}
