package br.com.lactech.performance_chatbot.service;

import br.com.lactech.performance_chatbot.dto.PerformanceChatbotRequestDTO;
import br.com.lactech.performance_chatbot.dto.PerformanceChatbotResponseDTO;
import br.com.lactech.performance_chatbot.entities.PerformanceChatbot;
import br.com.lactech.performance_chatbot.repositories.PerformanceChatbotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceChatbotService {

    @Autowired
    PerformanceChatbotRepository performanceChatbotRepository;

    public List<PerformanceChatbotResponseDTO> findAllPerformanceChatbots(){

        return performanceChatbotRepository.findAll().stream().map(PerformanceChatbotResponseDTO :: new).toList();

    }

    public List<PerformanceChatbotResponseDTO> findAllPerformanceChatbotsByCpf(String cpf){

        return performanceChatbotRepository.findAllByCpf(cpf).stream().map(PerformanceChatbotResponseDTO :: new).toList();

    }

    public void savePerformanceChatbot(PerformanceChatbotRequestDTO inputDTO){

        PerformanceChatbot performanceChatbot = new PerformanceChatbot();
        copyDtoToPerformanceChatbot(performanceChatbot, inputDTO);
        performanceChatbotRepository.save(performanceChatbot);
    }

    private void copyDtoToPerformanceChatbot(PerformanceChatbot performanceChatbot, PerformanceChatbotRequestDTO inputDTO) {

        performanceChatbot.setCpf(inputDTO.getCpf());
        performanceChatbot.setIdDuvida(inputDTO.getIdDuvida());

    }

}
