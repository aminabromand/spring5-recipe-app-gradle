package guru.springframework.repositories.reactive;

import guru.springframework.bootstrap.RecipeBootstrap;
import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	@Before public void setUp() throws Exception{
		unitOfMeasureReactiveRepository.deleteAll().block();
	}

	@Test
	public void testCategorySave(){
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription("Foo");

		unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

		Long count = unitOfMeasureReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	public void findByDescription(){
		String testDescription = "Foo";
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(testDescription);
		unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

		Mono<UnitOfMeasure> unitOfMeasureMono = unitOfMeasureReactiveRepository.findByDescription( testDescription );
		assertEquals( testDescription, unitOfMeasureMono.block().getDescription() );
		assertNotNull(unitOfMeasureMono.block().getId());
	}
}