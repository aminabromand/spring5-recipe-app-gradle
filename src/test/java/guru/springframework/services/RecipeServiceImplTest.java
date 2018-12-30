package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest{

	RecipeServiceImpl recipeService;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks( this );
		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
    public void getRecipeByIdTest() throws Exception {
	    Recipe recipe = new Recipe();
	    recipe.setId("1");

	    when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

	    Mono<Recipe> recipeReturned = recipeService.findById("1");

	    assertNotNull( "Null recipe returned", recipeReturned );
	    verify(recipeReactiveRepository, times( 1 )).findById(anyString());
	    verify(recipeReactiveRepository, never()).findAll();
    }

//	@Test(expected=NotFoundException.class)
	@Test
	public void getRecipeByIdTestNotFound() throws Exception {

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.empty());

		Mono<Recipe> recipeReturned = recipeService.findById("1");

		assertEquals(null, recipeReturned.block());
	}

	@Test
	public void getRecipeCommandByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

		Mono<RecipeCommand> commandById = recipeService.findCommandById("1");

		assertNotNull("Null recipe returned", commandById);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	public void getRecipesTest(){
		Recipe recipe = new Recipe();
		HashSet recipesData = new HashSet();
		recipesData.add(recipe);

		when(recipeService.getRecipes()).thenReturn( Flux.just(recipe) );

		Flux<Recipe> recipes = recipeService.getRecipes();
		assertEquals( recipes.count().block().longValue(), 1L );
		verify(recipeReactiveRepository, times(1)).findAll();
	}

	@Test
	public void testDeleteById() throws Exception {
		String idToDelete = "2";
		recipeService.deleteById(idToDelete);

		//no, 'when', since method has void return type

		verify(recipeReactiveRepository, times(1)).deleteById(anyString());
	}
}