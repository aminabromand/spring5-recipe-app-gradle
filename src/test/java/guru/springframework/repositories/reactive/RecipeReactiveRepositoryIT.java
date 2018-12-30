package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;

	@Before public void setUp() throws Exception{
		recipeReactiveRepository.deleteAll().block();
		categoryReactiveRepository.deleteAll().block();
		unitOfMeasureReactiveRepository.deleteAll().block();

//		RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
//		recipeBootstrap.onApplicationEvent(null);
	}

	@Test
	public void testRecipeSave(){
		Recipe recipe = new Recipe();
		recipe.setDescription("Yummy");

		recipeReactiveRepository.save(recipe).block();

		Long count = recipeReactiveRepository.count().block();

		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	public void findByDescription(){
		String testDescription = "Foo";
		Recipe recipe = new Recipe();
		recipe.setDescription(testDescription);
		recipeReactiveRepository.save(recipe).block();

//		Mono<Category> categoryMono = recipeReactiveRepository.findById( testDescription );
//		assertEquals( testDescription, categoryMono.block().getDescription() );
//		assertNotNull(categoryMono.block().getId());
	}
}