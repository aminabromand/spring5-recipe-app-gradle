package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface RecipeService{

	Flux<Recipe> getRecipes();
	Mono<Recipe> findById(String l);
    Mono<RecipeCommand> findCommandById(String l);
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
	Mono<Void> deleteById(String idToDelete);
}
