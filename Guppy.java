/**
 * Guppy.
 *
 * @author Bryan Hill A01020530
 * @version 1.0
 */
public class Guppy {
    /**
     * The age at which a fry becomes a young Guppy.
     */
    public static final int YOUNG_FISH_AGE_IN_WEEKS = 10;
    /**
     * The age at which a young Guppy becomes a mature Guppy.
     */
    public static final int MATURE_FISH_AGE_IN_WEEKS = 30;
    /**
     * The maximum age of a Guppy.
     */
    public static final int MAXIMUM_AGE_IN_WEEKS = 50;
    public static final double MINIMUM_WATER_VOLUME_ML = 250.0;
    /**
     * Default genus of Guppy.
     */
    public static final String DEFAULT_GENUS = "Poecilia";
    public static final String DEFAULT_SPECIES = "reticulata";
    public static final double DEFAULT_HEALTH_COEFFICIENT = 0.5;
    public static final double MINIMUM_HEALTH_COEFFICIENT = 0.0;
    public static final double MAXIMUM_HEALTH_COEFFICIENT = 1.0;

    private String genus;
    private String species;
    private int ageInWeeks;
    private boolean isFemale;
    private int generationNumber;
    private boolean isAlive;
    private double healthCoefficient;
    private int identificationNumber;

    private static int numberOfGuppiesBorn = 0;

    /**
     * Construct an object of type Guppy that has no params passed.
     */
    public Guppy() {
        this.genus = DEFAULT_GENUS;
        this.species = DEFAULT_SPECIES;
        this.isFemale = true;
        this.isAlive = true;
        this.healthCoefficient = DEFAULT_HEALTH_COEFFICIENT;
        this.identificationNumber = ++numberOfGuppiesBorn;
    }

    /**
     * Construct an obect of type Guppy that has params passed.
     *
     * @param newGenus             string
     * @param newSpecies           string
     * @param newAgeInWeeks        int
     * @param newIsFemale          boolean
     * @param newGenerationNumber  int
     * @param newHealthCoefficient double
     */
    public Guppy(String newGenus,
                 String newSpecies,
                 int newAgeInWeeks,
                 boolean newIsFemale,
                 int newGenerationNumber,
                 double newHealthCoefficient) {
        if (newGenus == null || newGenus.trim().isEmpty()) throw new IllegalArgumentException("Illegal input");
        if (newSpecies == null || newSpecies.trim().isEmpty()) throw new IllegalArgumentException("Illegal input");
        if (MAXIMUM_AGE_IN_WEEKS <= newAgeInWeeks || newAgeInWeeks < 0)
            throw new IllegalArgumentException("Illegal input");
        if (newGenerationNumber < 0) throw new IllegalArgumentException("Illegal input");
        if (MAXIMUM_HEALTH_COEFFICIENT < newHealthCoefficient
                || newHealthCoefficient < MINIMUM_HEALTH_COEFFICIENT)
            throw new IllegalArgumentException("Illegal input");
        this.genus = formatTitle(newGenus.trim());
        this.ageInWeeks = newAgeInWeeks;
        this.species = newSpecies.trim().toLowerCase();
        this.isFemale = newIsFemale;
        this.generationNumber = newGenerationNumber;
        this.healthCoefficient = newHealthCoefficient;
        this.isAlive = true;
        this.identificationNumber = ++numberOfGuppiesBorn;

    }

    /**
     * Increment the age of the Guppy object by 1.
     */
    public void incrementAge() {
        this.ageInWeeks += 1;
        if (this.ageInWeeks >= MAXIMUM_AGE_IN_WEEKS) this.isAlive = false;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    /**
     * Return the genus.
     *
     * @return genus as a string
     */
    public String getGenus() {
        return this.genus;
    }

    /**
     * return the species.
     *
     * @return species as a string
     */
    public String getSpecies() {

        return this.species;
    }

    /**
     * return age in weeks.
     *
     * @return age in weeks as a int
     */
    public int getAgeInWeeks() {
        return this.ageInWeeks;
    }

    /**
     * set age in weeks to param if possible.
     *
     * @param ageInWeeks as an int
     */
    public void setAgeInWeeks(int ageInWeeks) {
        if (MAXIMUM_AGE_IN_WEEKS >= ageInWeeks && ageInWeeks >= 0) {
            this.ageInWeeks = ageInWeeks;
        }
    }

    /**
     * determine if the guppy is a female.
     *
     * @return isfemale as a boolean
     */
    public boolean getIsFemale() {
        return this.isFemale;
    }

    /**
     * determine if the guppy is alive.
     *
     * @return isAlive as a boolean
     */
    public boolean getIsAlive() {
        return this.isAlive;
    }

    /**
     * set the life status of the guppy.
     *
     * @param alive as a boolean
     */
    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * return the healthcoefficient of the guppy.
     *
     * @return healthcoefficient as a double
     */
    public double getHealthCoefficient() {
        return this.healthCoefficient;
    }

    /**
     * set the health coefficient  of the guppy.
     *
     * @param healthCoefficient double
     */
    public void setHealthCoefficient(double healthCoefficient) {
        if (healthCoefficient <= MAXIMUM_HEALTH_COEFFICIENT && healthCoefficient >= MINIMUM_HEALTH_COEFFICIENT) {
            this.healthCoefficient = healthCoefficient;
        }
    }

    /**
     * return the identification number of a guppy.
     *
     * @return identificationnumber as an int
     */
    public int getIdentificationNumber() {
        return this.identificationNumber;
    }

    /**
     * get the total number of gupppies born.
     *
     * @return numberofguppies born as an int
     */
    public static int getNumberOfGuppiesBorn() {
        return numberOfGuppiesBorn;
    }

    /**
     * determine the amount of water volume needed for a guppy.
     *
     * @return a voulume of water as a double
     */
    public double getVolumeNeeded() {

        if (getAgeInWeeks() < YOUNG_FISH_AGE_IN_WEEKS) {
            return MINIMUM_WATER_VOLUME_ML;
        } else if (getAgeInWeeks() <= MATURE_FISH_AGE_IN_WEEKS) {
            return MINIMUM_WATER_VOLUME_ML * ageInWeeks / YOUNG_FISH_AGE_IN_WEEKS;
        } else if (getAgeInWeeks() <= MAXIMUM_AGE_IN_WEEKS) {
            return MINIMUM_WATER_VOLUME_ML * 1.5;
        } else {
            return 0.0;
        }

    }

    /**
     * change a guppies health coefficient by the specified delta. If the new health
     * doefficient
     *
     * @param delta double
     */
    public void changeHealthCoefficient(double delta) {
        if (delta + this.healthCoefficient <= MINIMUM_HEALTH_COEFFICIENT) {
            this.healthCoefficient = 0.0;
            this.isAlive = false;
        } else if (delta + this.healthCoefficient > MAXIMUM_HEALTH_COEFFICIENT)
            this.healthCoefficient = MAXIMUM_HEALTH_COEFFICIENT;
        else this.healthCoefficient += delta;
    }

    /**
     * return a string representation of the guppy.
     *
     * @return String, A representation of guppy
     */
    @java.lang.Override
    public java.lang.String toString() {
        return "Guppy{"
                + "genus='" + genus + '\''
                + ", species='" + species + '\''
                + ", ageInWeeks=" + ageInWeeks
                + ", isFemale=" + isFemale
                + ", generationNumber=" + generationNumber
                + ", isAlive=" + isAlive
                + ", healthCoefficient=" + healthCoefficient
                + ", identificationNumber=" + identificationNumber
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Guppy guppy = (Guppy) object;
        return ageInWeeks == guppy.ageInWeeks
                && isFemale == guppy.isFemale
                && generationNumber == guppy.generationNumber
                && isAlive == guppy.isAlive
                && Double.compare(guppy.healthCoefficient, healthCoefficient) == 0
                && identificationNumber == guppy.identificationNumber
                && java.util.Objects.equals(genus, guppy.genus)
                && java.util.Objects.equals(species, guppy.species);
    }

    public String formatTitle(String title) {
        String first = title.trim().toUpperCase().substring(0, 1);
        String rest = title.trim().toLowerCase().substring(1);
        return first + rest;

    }

    public static void main(String[] args) {
        Guppy test = new Guppy();
        Guppy test1 = new Guppy();
        System.out.println(test.toString());
        System.out.println(test1.toString());
    }

}


