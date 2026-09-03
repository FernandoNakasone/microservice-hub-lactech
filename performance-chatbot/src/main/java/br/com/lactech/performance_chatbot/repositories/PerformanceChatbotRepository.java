package br.com.lactech.performance_chatbot.repositories;

import br.com.lactech.performance_chatbot.entities.PerformanceChatbot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceChatbotRepository extends JpaRepository<PerformanceChatbot, Long> {

    List<PerformanceChatbot> findAllByCpf(String cpf);

}
