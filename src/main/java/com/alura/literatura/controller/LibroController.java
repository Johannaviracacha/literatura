package com.alura.literatura.controller;

import com.alura.literatura.model.Libro;
import com.alura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibroController {

    @Autowired
    private LibroService libroService;

    // Endpoint para obtener libros directamente desde la API Gutendex
    @GetMapping("/api/gutendex/libros")
    public List<Libro> obtenerLibrosDeGutendex() {
        return libroService.obtenerLibrosDesdeGutendex();
    }

    // Endpoint para guardar libros obtenidos de Gutendex en la base de datos
    @PostMapping("/api/gutendex/libros/guardar")
    public String guardarLibrosDeGutendex() {
        libroService.guardarLibrosDesdeGutendex();
        return "Libros guardados exitosamente";
    }

    // Otros endpoints existentes
    @GetMapping("/api/libros")
    public List<Libro> listarLibros() {
        return libroService.listarLibros();
    }

    public void mostrarMenu() {
    }
}
