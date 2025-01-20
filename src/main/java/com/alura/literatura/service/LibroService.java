package com.alura.literatura.service;

import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String GUTENDEX_URL = "https://gutendex.com/books";



    // Método para buscar un libro por título en la base de datos
    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    // Método para buscar un libro en la API de Gutendex por título
    public Optional<Libro> buscarEnApiGutendex(String titulo) {
        try {
            // Hacemos una petición GET a la API con el título como parámetro
            String urlConParametro = GUTENDEX_URL + "?search=" + titulo;
            JsonNode respuesta = restTemplate.getForObject(urlConParametro, JsonNode.class);

            if (respuesta != null && respuesta.has("results") && respuesta.get("results").size() > 0) {
                JsonNode libroJson = respuesta.get("results").get(0); // Tomamos el primer resultado
                String tituloLibro = libroJson.get("title").asText();
                String idioma = libroJson.get("languages").get(0).asText();
                String autor = libroJson.has("authors") && libroJson.get("authors").size() > 0
                        ? libroJson.get("authors").get(0).get("name").asText()
                        : "Autor desconocido";

                // Creamos un objeto Libro
                Libro libro = new Libro(tituloLibro, idioma, autor);
                return Optional.of(libro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Método para registrar un libro en la base de datos
    public Libro registrarLibro(Libro libro) {
        // Evitamos duplicados verificando si el libro ya existe
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libro.getTitulo());
        if (libroExistente.isEmpty()) {
            return libroRepository.save(libro);
        } else {
            System.out.println("El libro ya está registrado.");
            return libroExistente.get();
        }
    }

    // Método para obtener todos los libros de la base de datos
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    // Método para listar libros por idioma
    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    // Método para obtener libros desde Gutendex (sin búsqueda específica)
    public List<Libro> obtenerLibrosDesdeGutendex() {
        List<Libro> libros = new ArrayList<>();
        try {
            JsonNode respuesta = restTemplate.getForObject(GUTENDEX_URL, JsonNode.class);
            if (respuesta != null && respuesta.has("results")) {
                for (JsonNode libroJson : respuesta.get("results")) {
                    String titulo = libroJson.get("title").asText();
                    String idioma = libroJson.get("languages").get(0).asText();
                    String autor = libroJson.has("authors") && libroJson.get("authors").size() > 0
                            ? libroJson.get("authors").get(0).get("name").asText()
                            : "Autor desconocido";

                    Libro libro = new Libro(titulo, idioma, autor);
                    libros.add(libro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libros;
    }

    public void guardarLibrosDesdeGutendex() {
        List<Libro> libros = obtenerLibrosDesdeGutendex();
        for (Libro libro : libros) {
            // Evitar duplicados verificando si el libro ya existe en la base de datos
            if (libroRepository.findByTitulo(libro.getTitulo()).isEmpty()) {
                libroRepository.save(libro);
            }
            System.out.println("Libros guardados desde Gutendex.");


            }
        }
    }
