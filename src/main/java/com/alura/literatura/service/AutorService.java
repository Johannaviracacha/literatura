package com.alura.literatura.service;

import com.alura.literatura.model.Autor;
import com.alura.literatura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutoresVivos() {
        return autorRepository.findByVivo(true);
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }
}

