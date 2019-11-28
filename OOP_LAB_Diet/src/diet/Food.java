package diet;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;


/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {
	
	
	private TreeSet<RawMaterial> rawMaterials = new TreeSet<>(
			(RawMaterial arg0,RawMaterial arg1)->{return arg0.getName().compareToIgnoreCase(arg1.getName());});
	private TreeSet<Product> products = new TreeSet<>(
			(Product arg0,Product arg1)->{return arg0.getName().compareToIgnoreCase(arg1.getName());});
	private TreeSet<Recipe> recipes = new TreeSet<>(
			(Recipe arg0,Recipe arg1)->{return arg0.getName().compareToIgnoreCase(arg1.getName());});
	private Menu menù;
	
	/**
	 * Define a new raw material.
	 * The nutritional values are specified for a conventional 100g quantity
	 * @param name unique name of the raw material
	 * @param calories calories per 100g
	 * @param proteins proteins per 100g
	 * @param carbs carbs per 100g
	 * @param fat fats per 100g
	 */
	public void defineRawMaterial(String name, double calories, double proteins,double carbs,double fat){
		rawMaterials.add(new RawMaterial(name, calories, proteins, carbs, fat));
	}
	
	/**
	 * Retrieves the collection of all defined raw materials
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials(){
		//return Collection.class.cast(rawMaterials);
		return (Collection)rawMaterials;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * @param name  name of the raw material
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name){
		for(RawMaterial mat : rawMaterials) {
			if(mat.getName().equals(name))
				return mat;
		}
		System.out.println("error, ingredient not found");
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * @param name unique name of the product
	 * @param calories calories for a product unit
	 * @param proteins proteins for a product unit
	 * @param carbs carbs for a product unit
	 * @param fat fats for a product unit
	 */
	public void defineProduct(String name,
								  double calories,
								  double proteins,
								  double carbs,
								  double fat){
		products.add(new Product(name, calories, proteins, carbs, fat));
	}
	
	/**
	 * Retrieves the collection of all defined products
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products(){
		return (Collection)products;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name){
		for(Product prod : products) {
			if(prod.getName().equals(name))
				return prod;
		}
		
		return null;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe recipe= new Recipe(this,name);
		recipes.add(recipe);
		return recipe;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes(){
		return (Collection)recipes;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * @param name  name of the recipe
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name){		
		for(Recipe rec : recipes) {
			if(rec.getName().equals(name))
				return rec;
		}
		
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		return new Menu(this,name);
	}
	
}
