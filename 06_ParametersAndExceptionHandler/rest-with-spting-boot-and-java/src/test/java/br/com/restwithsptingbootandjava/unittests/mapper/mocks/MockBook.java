package br.com.restwithsptingbootandjava.unittests.mapper.mocks;

import br.com.restwithsptingbootandjava.data.vo.v1.BookVO;
import br.com.restwithsptingbootandjava.data.vo.v1.PersonVO;
import br.com.restwithsptingbootandjava.model.Book;
import br.com.restwithsptingbootandjava.model.Person;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookVO mockVO() {
        return mockVO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("Title test"+ number);
        book.setAuthor("Author test"+ number);
        book.setPrice(BigDecimal.valueOf(number));
        book.setLaunchDate(new Date(number));
        book.setId(number.longValue());
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setTitle("Title test"+ number);
        book.setAuthor("Author test"+ number);
        book.setPrice(BigDecimal.valueOf(number));
        book.setLaunchDate(new Date(number));
        book.setKey(number.longValue());
        return book;
    }
}
