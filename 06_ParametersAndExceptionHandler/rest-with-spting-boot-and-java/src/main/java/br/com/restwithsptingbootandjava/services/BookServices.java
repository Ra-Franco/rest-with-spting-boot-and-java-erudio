package br.com.restwithsptingbootandjava.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.restwithsptingbootandjava.controllers.BookController;
import br.com.restwithsptingbootandjava.data.vo.v1.BookVO;
import br.com.restwithsptingbootandjava.exceptions.RequiredObjectsIsNullException;
import br.com.restwithsptingbootandjava.exceptions.ResourceNotFoundException;
import br.com.restwithsptingbootandjava.mapper.ModelMapperConfig;
import br.com.restwithsptingbootandjava.model.Book;
import br.com.restwithsptingbootandjava.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices {

    @Autowired
    private BookRepository bookRepository;

    public List<BookVO> findAll(){
        var books = ModelMapperConfig.parseListObjects(bookRepository.findAll(), BookVO.class);
        books.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
        return books;
    }

    public BookVO findById(Long id){
        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + id));
        var vo = ModelMapperConfig.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book){
        if (book == null) throw new RequiredObjectsIsNullException();
        var entity = bookRepository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + book.getKey()));
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var entityToVo = bookRepository.save(entity);
        var vo = ModelMapperConfig.parseObject(entityToVo, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book){
        if (book == null) throw new RequiredObjectsIsNullException();
        var entity = ModelMapperConfig.parseObject(book, Book.class);
        var vo = ModelMapperConfig.parseObject(bookRepository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
        return vo;
    }

    public void deleteById(Long id){
        var entity = bookRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
        bookRepository.delete(entity);
    }
}
