package diet;

import java.util.HashMap;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    
	private Food caller;
	private String name;
	private double calories, proteins, carbs, fat,weight;
	private HashMap<NutritionalElement,Double> ingredients=new HashMap<NutritionalElement, Double>();
	private Boolean update=false;
	
	public Recipe(Food self,String name) {
		this.name=name;
		caller=self;
	}
	
	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement prova= caller.getRawMaterial(material);
		
		ingredients.put(prova, quantity);
		update=true;
		return this;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		valueUpdate();
		return calories;
	}

	@Override
	public double getProteins() {
		valueUpdate();
		return proteins;
	}

	@Override
	public double getCarbs() {
		valueUpdate();
		return carbs;
	}

	@Override
	public double getFat() {
		valueUpdate();
		return fat;
	}
	/**
	 * if the values of calories etc are not updated it does it
	 */
	private void valueUpdate() {
		if (update) {
			calories=proteins=fat=carbs=weight=0.0;
			ingredients.forEach((ingred,qt)->{weight+=qt;});
			ingredients.forEach((ingred,qt)->{
				calories+=ingred.getCalories()/100*qt/weight*100;
				proteins+=((((ingred.getProteins()/100)*qt) /weight)*100);
				carbs+=((((ingred.getCarbs()/100)*qt) /weight)*100);
				fat+=((((ingred.getFat()/100)*qt) /weight)*100);
			});
			update=false;
		}
	}
	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expressed nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
	    // a recipe expressed nutritional values per 100g
		return true;
	}

}
