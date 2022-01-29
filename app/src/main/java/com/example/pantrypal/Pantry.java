import java.io.*;

public class Pantry {
    
    Arraylist<Ingredient> myPantry = new ArrayList<Ingredient>;

    public Pantry() {
        //read pantry that is saved in text file and intialize
        //add each item to myPantry[]
        BufferedReader br = new BufferedReader(new FileReader("savedPantry.txt"))) 
        String line;
        int commaIndex;
        while ((line = br.readLine()) != null) {
            commaIndex = line.indexOf(",");
            myPantry.add(new Ingredient(line.substring(0,commaIndex),line.substring(commaIndex + 1)));
        }   
    }

    public void saveToFile() {
        //saves myPantry to a text file so that myPantry can persist between sessions
        //could be called after every addToPantry operation, at set time intervals, or upon closure of the app
        PrintStream fileStream = new PrintStream(new File("savedPantry.txt"));

        int mPLength = myPantry.size();
        for (int i = 0; i < mPLength; i++) {
            fileStream.println(myPantry.get(i).getName() + "," + myPantry.get(i).getImage());
        }
    }

    public Ingredient[] search(String item) {
        //api call for user input "item" that returns a list of ingredients
        //parse json
        //create ingred objects for each ingrdiant (using name and img)
        
        numResults = 10;
        Ingredient[numResults] ingrs;
        //return ingred array
        return ingrs;
    }

    public void addToPantry(String ingr, String img) {
        //appends ingr to myPantry[]
        Ingredient ingrObj = new Ingredient(ingr, img);
        myPantry.add(ingrObj);
    }

    public Recipe[] findRecipes() {

        //parse myPantry array for ingredients and their names
        int mPLength = myPantry.size();
        String[mPLength] ingrNames;
        
        for (int i = 0; i < mPLength; i++) {
            ingrNames[i] = myPantry.get(i).getName();
        }
        
        //use api call "search recipe by ingrediants" using the array of names ingrNames
        //create a recipe object for each returned recipe
        //return array of these recipe objects (each recipe will have many null attributes because full summary api call is not made yet)
    }

    public Ingredient[] getPantry() {
        return this.myPantry;
    }

}