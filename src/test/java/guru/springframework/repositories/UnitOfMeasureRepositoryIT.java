package guru.springframework.repositories;

import guru.springframework.bootstrap.RecipeBootstrap;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT{

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@Before public void setUp() throws Exception{
		recipeRepository.deleteAll();
		categoryRepository.deleteAll();
		unitOfMeasureRepository.deleteAll();

		RecipeBootstrap recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
		recipeBootstrap.onApplicationEvent(null);
	}

	@Test
	public void findByDescription(){
		String testDescription = "Teaspoon";
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription( testDescription );
		assertEquals( testDescription, uomOptional.get().getDescription() );
	}

	@Test public void findByDescriptionCup(){
		String testDescription = "Cup";
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription( testDescription );
		assertEquals( testDescription, uomOptional.get().getDescription() );
	}
}