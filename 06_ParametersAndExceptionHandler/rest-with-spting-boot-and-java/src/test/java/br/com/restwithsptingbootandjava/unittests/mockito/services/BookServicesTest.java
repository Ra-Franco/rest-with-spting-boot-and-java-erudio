package br.com.restwithsptingbootandjava.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.restwithsptingbootandjava.data.vo.v1.BookVO;
import br.com.restwithsptingbootandjava.data.vo.v1.PersonVO;
import br.com.restwithsptingbootandjava.exceptions.RequiredObjectsIsNullException;
import br.com.restwithsptingbootandjava.model.Book;
import br.com.restwithsptingbootandjava.model.Person;
import br.com.restwithsptingbootandjava.repositories.BookRepository;
import br.com.restwithsptingbootandjava.services.BookServices;
import br.com.restwithsptingbootandjava.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook mockBook;

    @InjectMocks
    private BookServices bookServices;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUpMocks() {
        mockBook = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll(){
        List<Book> books = mockBook.mockEntityList();
        when(bookRepository.findAll()).thenReturn(books);

        var bookList = bookServices.findAll();
        assertNotNull(bookList);
        assertEquals(bookList.size(), 14);

        var bookOne = bookList.get(1);
        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertNotNull(bookOne.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));

        assertEquals("Title test1", bookOne.getTitle());
        assertEquals("Author test1", bookOne.getAuthor());
        assertEquals(BigDecimal.valueOf(1), bookOne.getPrice());
        assertEquals(new Date(1), bookOne.getLaunchDate());

        var bookSeven = bookList.get(7);
        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());
        assertNotNull(bookSeven.toString().contains("links: [</book/v1/7>;rel=\"self\"]"));

        assertEquals("Title test7", bookSeven.getTitle());
        assertEquals("Author test7", bookSeven.getAuthor());
        assertEquals(BigDecimal.valueOf(7), bookSeven.getPrice());
        assertEquals(new Date(7), bookSeven.getLaunchDate());

        var bookEleven = bookList.get(11);
        assertNotNull(bookEleven);
        assertNotNull(bookEleven.getKey());
        assertNotNull(bookEleven.getLinks());
        assertNotNull(bookEleven.toString().contains("links: [</book/v1/11>;rel=\"self\"]"));

        assertEquals("Title test11", bookEleven.getTitle());
        assertEquals("Author test11", bookEleven.getAuthor());
        assertEquals(BigDecimal.valueOf(11), bookEleven.getPrice());
        assertEquals(new Date(11), bookEleven.getLaunchDate());
    }
    @Test
    void testFindById() {
        Book entity = mockBook.mockEntity(1);
        entity.setId(1L);
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        var result = bookServices.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertNotNull(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));

        assertEquals("Title test1", result.getTitle());
        assertEquals("Author test1", result.getAuthor());
        assertEquals(BigDecimal.valueOf(1), result.getPrice());
        assertEquals(new Date(1), result.getLaunchDate());
    }

    @Test
    void testUpdate() {
        Book entity = mockBook.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = mockBook.mockVO(1);
        vo.setKey(1L);
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        when(bookRepository.save(any(Book.class))).thenReturn(persisted);


        var result = bookServices.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertEquals("Title test1", result.getTitle());
        assertEquals("Author test1", result.getAuthor());
        assertEquals(BigDecimal.valueOf(1), result.getPrice());
        assertEquals(new Date(1), result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectsIsNullException.class, () -> {
            bookServices.update(null);
        });
        String expectedMessage = "Its no allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreate() {
        Book entity = mockBook.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = mockBook.mockVO(1);
        vo.setKey(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(persisted);


        var result = bookServices.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertEquals("Title test1", result.getTitle());
        assertEquals("Author test1", result.getAuthor());
        assertEquals(BigDecimal.valueOf(1), result.getPrice());
        assertEquals(new Date(1), result.getLaunchDate());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectsIsNullException.class, () -> {
            bookServices.create(null);
        });
        String expectedMessage = "Its no allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testDelete() {
        Book entity = mockBook.mockEntity(1);
        entity.setId(1L);
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        bookServices.deleteById(1L);
    }

}
