package classes;

/**
 * Enum des différents terrains.
 * Chaque terrain est associé un chemin vers une image utilisée par la méthode
 * {@code Dessin.dessin}
 */
public enum NatureTerrain {
    EAU("assets/Eau.jpg"),
    FORET("assets/Foret.jpg"),
    ROCHE("assets/Rocks.jpg"),
    TERRAIN_LIBRE("assets/Grass_h.jpg"),
    HABITAT("assets/House.jpg");

    private String image_path;

    private NatureTerrain(String image_path) {
        this.image_path = image_path;
    }

    public String getImagePath() {
        return this.image_path;
    }
}
