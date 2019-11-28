package diet;

import java.util.HashMap;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	private Food caller;
	private String name;
	private double calories, proteins, carbs, fat;
	private HashMap<NutritionalElement,Double> menu=new HashMap<NutritionalElement, Double>();
	private Boolean update=false;
	
	public Menu(Food self,String name) {
		this.name=name;
		caller=self;
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu addRecipe(String recipe, double quantity) {
		menu.put(caller.getRecipe(recipe), quantity);
		update=true;
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		menu.put(caller.getProduct(product),(double) 100);
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		valueUpdate();
		return calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		valueUpdate();
		return proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		valueUpdate();
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		valueUpdate();
		return fat;
	}
	
	private void valueUpdate() {
		if (update) {
			calories=proteins=fat=carbs=0.0;
			menu.forEach((portion,qt)->{
				calories+=(portion.getCalories()/100)*qt;
				proteins+=(portion.getProteins()/100)*qt;
				carbs+=(portion.getCarbs()/100)*qt;
				fat+=(portion.getFat()/100)*qt;
			});
			update=false;
		}
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
