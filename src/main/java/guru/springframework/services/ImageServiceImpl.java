package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeReactiveRepository recipeReactiveRepository;
    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }
    @Override
//    @Transactional
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file){
        log.debug("I'm in the image service save method");
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId).map(
                recipe -> {
                    try {
                        Byte[] byteObjects = new Byte[file.getBytes().length];
                        int i = 0;
                        for (byte b : file.getBytes()){
                            byteObjects[i++] = b;
                        }
                        recipe.setImage(byteObjects);
                        log.debug("I'm in the image service setting image");
                        return recipe;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
        );
        log.debug("I'm in the image service saving");
        recipeReactiveRepository.save(recipeMono.block()).block();
        log.debug("I'm in the image service saved");
        return Mono.empty();
    }
}
