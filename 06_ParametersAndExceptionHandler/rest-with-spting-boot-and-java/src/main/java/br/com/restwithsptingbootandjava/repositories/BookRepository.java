package br.com.restwithsptingbootandjava.repositories;

import br.com.restwithsptingbootandjava.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
