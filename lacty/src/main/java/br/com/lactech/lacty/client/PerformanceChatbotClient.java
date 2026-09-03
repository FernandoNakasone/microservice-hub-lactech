package br.com.lactech.lacty.client;

import br.com.lactech.lacty.dto.PerformanceChatbotRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ms-performance-chatbot")
public interface PerformanceChatbotClient {

    @RequestMapping(method = RequestMethod.POST,
            value = ("/performance-chatbot"))
    void salvarInteracao(@RequestBody PerformanceChatbotRequestDTO dto);

}
