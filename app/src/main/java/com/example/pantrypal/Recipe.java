public class Recipe {
    
    int ID; //non-null
    String image; //non-null
    int servings;
    String sourceURL;
    int minutes;
    String title; //non-null
    String[] instructions;
    Ingredient[] ingredients;

    public Recipe(int inID, String inImage, String inTitle) {
        this.ID = inID;
        this.image = inImage;
        this.title = inTitle;
    }

    public void initInstructions() {
        //use Analyze Recipe Instructions api call to populate instructions array attribute
    }

    public void initInfo() {
        //use get Recipe Information api call to populate attributes servings, minutes, etc.
        //loop through ingrediants given by that api call and put each one into an ingrediant object, then put those in attribute ingredients[]
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int inID) {
        this.ID = inID;
    }

    public String getImage() {
        return this.image;
    }

    public void setAttribute(String inImage) {
        this.image = inImage;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int inServings) {
        this.servings = inServings;
    }

    public String getSourceURL() {
        return this.sourceURL;
    }

    public void setSourceURL(String inSourceURL) {
        this.sourceURL = inSourceURL;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int inMinutes) {
        this.minutes = inMinutes;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String inTitle) {
        this.title = inTitle;
    }

    public String[] getInstructions() {
        return this.instructions[];
    }

    public Ingredient[] getIngredients() {
        return this.ingredients[];
    }

}