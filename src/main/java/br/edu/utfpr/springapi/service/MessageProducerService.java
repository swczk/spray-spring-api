package br.edu.utfpr.springapi.service;

import br.edu.utfpr.springapi.config.RabbitConfig;
import br.edu.utfpr.springapi.dto.AplicacaoDTO;
import br.edu.utfpr.springapi.dto.AplicacaoEventDTO;
import br.edu.utfpr.springapi.enums.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendApplicationEvent(EventType eventType, AplicacaoDTO aplicacao, String userId) {
        try {
            AplicacaoEventDTO event = createEvent(eventType, aplicacao, userId);
            
            rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.APLICACAO_ROUTING_KEY,
                event
            );
            
            log.info("Evento enviado: {} para aplicação ID: {}", eventType, aplicacao.getId());
        } catch (Exception e) {
            log.error("Erro ao enviar evento {} para aplicação ID: {}", eventType, aplicacao.getId(), e);
        }
    }

    private AplicacaoEventDTO createEvent(EventType eventType, AplicacaoDTO aplicacao, String userId) {
        return new AplicacaoEventDTO(
            eventType.name(),
            LocalDateTime.now(),
            aplicacao,
            userId,
            eventType.getDescription()
        );
    }
}