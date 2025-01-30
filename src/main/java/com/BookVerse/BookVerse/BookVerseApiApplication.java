package com.BookVerse.BookVerse;


import com.BookVerse.BookVerse.user.entity.Role;
import com.BookVerse.BookVerse.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BookVerseApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(BookVerseApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			roleRepository.findByName("USER").ifPresentOrElse(
					 role -> System.out.println("Role already exists"),
					 () -> {
						 roleRepository.save(Role.builder().name("USER").build());
						 System.out.println("Role created");
					 }
			 );

		};
	}

}
