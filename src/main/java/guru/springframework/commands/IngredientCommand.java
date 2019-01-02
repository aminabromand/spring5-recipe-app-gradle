package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;

    @NotBlank
    private String description;

    @Min(1)
    @NotNull
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand uom;
}
