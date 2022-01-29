public class Pantry {

    Ingredient[] myPantry;

    public Pantry() {
        //read pantry that is saved in text file and intialize
        //add each item to myPantry[]
    }

    public Ingredient[] search(String item) {
        //api call for user input
        //parse json
        //create ingred objects for each ingrdiant
        //return ingred array
    }
 
    public void addToPantry(String ing) {
        //append ing to myPantry[]
    }

    public Recipe[] findRecipes() {
        //parse myPantry array for names
        //use api call "search recipe by ingrediants"
        //create a recipe object for each returned recipe
        //return array of these recipe objects (will have many null attributes because full summary api call is not made yet)
    }

    public Ingredient[] getPantry() {
        return this.myPantry[];
    }
}