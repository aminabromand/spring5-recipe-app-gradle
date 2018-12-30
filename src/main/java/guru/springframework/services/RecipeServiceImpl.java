package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe,
							 RecipeToRecipeCommand recipeToRecipeCommand){
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes(){
		log.debug("I'm in the recipe service");
//		Set<Recipe> recipeSet = new HashSet<>();
//		recipeReactiveRepository.findAll().iterator().forEachRemaining( recipeSet::add );
//		return recipeSet;
		return recipeReactiveRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String l) {
		Mono<Recipe> recipeMono = recipeReactiveRepository.findById(l);

//		if (recipeMono.) {
//			throw new NotFoundException("Recipe Not Found! For ID value: " + l.toString());
//		}

		return recipeMono;
	}

	@Override
//	@Transactional
	public Mono<RecipeCommand> findCommandById(String l) {
//		return recipeToRecipeCommand.convert(findById(l).block());
		return findById(l).map(recipeToRecipeCommand::convert);
	}

	@Override
//	@Transactional
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Mono<Recipe> savedRecipe = recipeReactiveRepository.save(detachedRecipe);
		log.debug("Saved RecipeId: " + savedRecipe.block().getId());
		return savedRecipe.map(recipeToRecipeCommand::convert);
	}

	@Override
    public Mono<Void> deleteById(String idToDelete) {
	    return recipeReactiveRepository.deleteById(idToDelete);
    }
}
