package br.edu.utfpr.springapi.mapper;

import br.edu.utfpr.springapi.dto.AplicacaoDTO;
import br.edu.utfpr.springapi.model.Aplicacao;
import org.springframework.stereotype.Component;

@Component
public class AplicacaoMapper {

    public AplicacaoDTO toDTO(Aplicacao aplicacao) {
        if (aplicacao == null) {
            return null;
        }

        AplicacaoDTO dto = new AplicacaoDTO();
        dto.setId(aplicacao.getId());
        dto.setTalhaoId(aplicacao.getTalhao() != null ? aplicacao.getTalhao().getId().toString() : null);
        dto.setEquipamentoId(aplicacao.getEquipamento() != null ? aplicacao.getEquipamento().getId().toString() : null);
        dto.setTipoAplicacaoId(aplicacao.getTipoAplicacao() != null ? aplicacao.getTipoAplicacao().getId().toString() : null);
        dto.setDataInicio(aplicacao.getDataInicio());
        dto.setDataFim(aplicacao.getDataFim());
        dto.setDosagem(aplicacao.getDosagem() != null ? aplicacao.getDosagem().toString() : null);
        dto.setVolumeAplicado(aplicacao.getVolumeAplicado() != null ? aplicacao.getVolumeAplicado().toString() : null);
        dto.setOperador(aplicacao.getOperador());
        dto.setCondicaoClimatica(aplicacao.getCondicaoClimatica());
        dto.setObservacoes(aplicacao.getObservacoes());
        dto.setFinalizada(aplicacao.getFinalizada());

        return dto;
    }
}