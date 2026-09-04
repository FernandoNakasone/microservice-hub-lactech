package br.com.lactech.performance_chatbot.service;

import br.com.lactech.performance_chatbot.dto.PerformanceChatbotRequestDTO;
import br.com.lactech.performance_chatbot.dto.PerformanceChatbotResponseDTO;
import br.com.lactech.performance_chatbot.entities.PerformanceChatbot;
import br.com.lactech.performance_chatbot.exceptions.ResourceNotFoundException;
import br.com.lactech.performance_chatbot.repositories.PerformanceChatbotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceChatbotService {

    @Autowired
    private PerformanceChatbotRepository performanceChatbotRepository;

    @Transactional(readOnly = true)
    public List<PerformanceChatbotResponseDTO> findAllPerformanceChatbots(){

        return performanceChatbotRepository.findAll().stream().map(PerformanceChatbotResponseDTO :: new).toList();

    }

    @Transactional(readOnly = true)
    public List<PerformanceChatbotResponseDTO> findAllPerformanceChatbotsByCpf(String cpf){

        List<PerformanceChatbot> list = performanceChatbotRepository.findAllByCpf(cpf);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException( "Nenhuma performance encontrada para o CPF: " + cpf );
        }

        return list.stream().map(PerformanceChatbotResponseDTO :: new).toList();
    }

    @Transactional
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
