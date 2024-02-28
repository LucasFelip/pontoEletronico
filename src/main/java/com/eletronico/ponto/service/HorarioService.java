package com.eletronico.ponto.service;

import com.eletronico.ponto.model.Horario;
import com.eletronico.ponto.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioService {
    @Autowired
    HorarioRepository repository;

    public void adicionarHorario(Horario horario) throws Exception {
        if (findAll().size() >= 3) {
            throw new Exception("Limite de 3 horários já alcançado. Não é possível adicionar mais horários.");
        } else {
            addHorario(horario);
        }
    }

    public List<Horario> listarHorario() throws Exception {
        List<Horario> todosHorarios = findAll();
        if (todosHorarios.isEmpty()) {
            throw new Exception("Nenhum horário cadastrado para ser listado.");
        }
        return todosHorarios;
    }

    public Horario addHorario(Horario horario) {
        return repository.save(horario);
    }

    public List<Horario> findAll() {
        return repository.findAll();
    }
}
