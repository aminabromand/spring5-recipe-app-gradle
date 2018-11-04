package guru.springframework.repositories.reactive;

import guru.springframework.bootstrap.RecipeBootstrap;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;

	@Before public void setUp() throws Exception{
		categoryReactiveRepository.deleteAll().block();
	}

	@Test
	public void testCategorySave(){
		Category category = new Category();
		category.setDescription("Foo");

		categoryReactiveRepository.save(category).block();

		Long count = categoryReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	public void findByDescription(){
		String testDescription = "Foo";
		Category category = new Category();
		category.setDescription(testDescription);
		categoryReactiveRepository.save(category).block();

		Mono<Category> categoryMono = categoryReactiveRepository.findByDescription( testDescription );
		assertEquals( testDescription, categoryMono.block().getDescription() );
		assertNotNull(categoryMono.block().getId());
	}

}