package com.alura.literatura;

import com.alura.literatura.service.AutorService;
import com.alura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
public class Principal implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    public static void main(String[] args) {
        SpringApplication.run(Principal.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Guardando libros desde la API Gutendex...");
        libroService.guardarLibrosDesdeGutendex();
        System.out.println("Libros guardados exitosamente.");

        while (!salir) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un determinado año");
            System.out.println("5. Listar libros por idioma");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    var libro = libroService.buscarLibroPorTitulo(titulo);
                    if (libro.isPresent()) {
                        System.out.println("Libro encontrado: " + libro.get().getTitulo());
                    } else {
                        System.out.println("Libro no encontrado. Registrando en la base de datos...");
                        var libroDesdeApi = libroService.buscarEnApiGutendex(titulo);
                        if (libroDesdeApi != null) {
                            libroService.registrarLibro(libroDesdeApi);
                            System.out.println("Libro registrado exitosamente.");
                        } else {
                            System.out.println("Libro no encontrado en la API Gutendex.");
                        }
                    }
                }
                case 2 -> libroService.listarLibros()
                        .forEach(libro -> System.out.println("Título: " + libro.getTitulo()));
                case 3 -> autorService.listarAutores()
                        .forEach(autor -> System.out.println("Autor: " + autor.getNombre()));
                case 4 -> {
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    autorService.listarAutoresVivos()
                            .forEach(autor -> System.out.println("Autor: " + autor.getNombre() + ", Año: " + year));
                }
                case 5 -> {
                    System.out.print("Ingrese el idioma (es, en, fr, pt): ");
                    String idioma = scanner.nextLine();
                    libroService.listarLibrosPorIdioma(idioma)
                            .forEach(libro -> System.out.println("Idioma: " + idioma + ", Título: " + libro.getTitulo()));
                }
                case 0 -> {
                    System.out.println("Saliendo del programa...");
                    salir = true;
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
