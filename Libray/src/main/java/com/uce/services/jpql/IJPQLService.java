package com.uce.services.jpql;

import com.uce.services.dto.LiteralDosDTO;
import com.uce.services.dto.LiteralUnoDTO;

import java.util.List;

public interface IJPQLService {
    List<LiteralUnoDTO> getLiteralUno();

    List<LiteralDosDTO.TaskSummary> getLiteralDos();
}
