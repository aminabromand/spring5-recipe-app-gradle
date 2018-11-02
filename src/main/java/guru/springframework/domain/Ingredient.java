package guru.springframework.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class Ingredient{


	private String id;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasure uom;
	private Recipe recipe;

	public Ingredient() {}

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
		this.description = description;
		this.amount = amount;
		this.uom = uom;
	}

}
