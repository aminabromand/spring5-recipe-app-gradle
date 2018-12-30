package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest{

	IndexController indexController;

	@Mock
	Model model;

	@Mock
	RecipeServiceImpl recipeService;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks( this );
		indexController = new IndexController( recipeService );
	}

	@Test public void testMockMVC() throws Exception {

		Set recipesData = new HashSet();
		Flux<Recipe> recipes = Flux.fromIterable(recipesData);
		when(recipeService.getRecipes()).thenReturn( recipes);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		mockMvc.perform(get("/"))
						.andExpect( status().isOk() )
						.andExpect( view().name( "index" ) );
	}

	@Test public void getIndexPage(){

		Set recipesData = new HashSet();
		Recipe recipe1;
		recipe1 = new Recipe();
		recipe1.setId( "1" );
		recipesData.add(recipe1);
		Recipe recipe2;
		recipe2 = new Recipe();
		recipe2.setId( "2" );
		recipesData.add(recipe2);

		Flux<Recipe> recipes = Flux.fromIterable(recipesData);

		when(recipeService.getRecipes()).thenReturn( recipes);
		ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass( List.class );

		//when
		String result = indexController.getIndexPage( model );

		//then
		String expected_result = "index";
		assertEquals( result, expected_result );

		verify(recipeService, times(1)).getRecipes();
//		verify(model, times(1)).addAttribute( "recipes", recipesData );
		verify(model, times(1)).addAttribute( eq("recipes"), any(List.class) );
		verify(model, times(1)).addAttribute( eq("recipes"), argumentCaptor.capture() );
		List<Recipe> setInController = argumentCaptor.getValue();
		assertEquals( 2, setInController.size() );
		assertEquals( recipesData.size(), setInController.size() );
	}
}