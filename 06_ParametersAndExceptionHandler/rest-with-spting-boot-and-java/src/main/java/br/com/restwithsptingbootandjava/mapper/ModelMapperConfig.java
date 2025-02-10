package br.com.restwithsptingbootandjava.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.restwithsptingbootandjava.data.vo.v1.BookVO;
import br.com.restwithsptingbootandjava.model.Book;
import org.modelmapper.ModelMapper;

import br.com.restwithsptingbootandjava.data.vo.v1.PersonVO;
import br.com.restwithsptingbootandjava.model.Person;

public class ModelMapperConfig {

//	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	private static ModelMapper mapper = new ModelMapper();
	
	static {
		mapper.createTypeMap(
				Person.class, 
				PersonVO.class)
			.addMapping(Person::getId, PersonVO::setKey);
		mapper.createTypeMap(
			Book.class,
			BookVO.class
		).addMapping(Book::getId, BookVO::setKey);
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D>parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		
		for (O o: origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		
		return destinationObjects;
	}
	
}
