import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

/**
 * Pool.
 *
 * @author Bryan Hill A01020530
 * @version Pool 1.0
 */
public class Pool {
    /**
     * Default name for the pool.
     */
    public static final String DEFAULT_POOL_NAME = "Unnamed";
    /**
     * Default pool temperature in celsius.
     */
    public static final double DEFAULT_POOL_TEMP_CELSIUS = 40.0;
    /**
     * Minimum poop temperature in celsius.
     */
    public static final double MINIMUM_POOL_TEMP_CELSIUS = 0.0;
    /**
     * Maximum pool temerature in celsius.
     */
    public static final double MAXIMUM_POOL_TEMP_CELSIUS = 100.0;
    /**
     * Neutral pH level.
     */
    public static final double NEUTRAL_PH  = 7.0;
    /**
     * Default nutrient coefficient.
     */
    public static final double DEFAULT_NUTRIENT_COEFFICIENT = 0.5;
    /**
     * Minimum nutrient coefficient.
     */
    public static final double MINIMUM_NUTRIENT_COEFFICIENT = 0.0;
    /**
     * Maximum nutrient coefficient.
     */
    public static final double MAXIMUM_NUTRIENT_COEFFICIENT = 1.0;

    private String name;
    private double volumeLitres;
    private double temperatureCelsius;
    private double pH;
    private double nutrientCoefficient;
    private int identificationNumber;
    private ArrayList<Guppy> guppiesInPool;
    private Random randomNumberGenerator;

    private static int numberOfPools = 0;

    /**
     * Zero parameter constructor for objects of class Pool.
     */
    public Pool() {
        this.name = DEFAULT_POOL_NAME;
        this.temperatureCelsius = DEFAULT_POOL_TEMP_CELSIUS;
        this.pH = NEUTRAL_PH;
        this.nutrientCoefficient = DEFAULT_NUTRIENT_COEFFICIENT;
        this.guppiesInPool = new ArrayList<>();
        this.randomNumberGenerator = new Random(0);
        this.identificationNumber = ++numberOfPools;
    }

    /**
     *Multi-parameter constructor for objects of class Pool. Invalid arguments will
     * generate IllegalArgumentExceptions.
     *
     * @param newName cannot be null, empty, or a String of whitespace.
     * @param newVolume cannot be less than zero.
     * @param newTemperature cannot be less than the set minimum pool
     *                       or more than the set maximum pool temperature.
     * @param newPH must be between 0 and 14.
     * @param newNutrientCoefficient must be between 0 and 1.
     */
    public Pool(String newName, double newVolume, double newTemperature, double newPH,
                double newNutrientCoefficient) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Must enter a name");
        } else {
            this.name = formatTitle(newName.trim());
        }
        if (newVolume < 0.0) {
            this.volumeLitres = 0.0;
        } else {
            this.volumeLitres = newVolume;
        }
        if (newTemperature < MINIMUM_POOL_TEMP_CELSIUS
                || newTemperature > MAXIMUM_POOL_TEMP_CELSIUS) {
            this.temperatureCelsius = DEFAULT_POOL_TEMP_CELSIUS;
        } else {
            this.temperatureCelsius = newTemperature;
        }
        if (newPH >= 0 && newPH <= 14.0) {
            this.pH = newPH;
        } else {
            this.pH = NEUTRAL_PH;
        }
        if (newNutrientCoefficient >= 0 && newNutrientCoefficient <= 1.0) {
            this.nutrientCoefficient = newNutrientCoefficient;
        } else {
            this.nutrientCoefficient = DEFAULT_NUTRIENT_COEFFICIENT;
        }
        this.guppiesInPool = new ArrayList<>();
        this.randomNumberGenerator = new Random(0);
        this.identificationNumber = ++numberOfPools;

    }

    /**
     * Return name.
     *
     * @return name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Return Volume in litres.
     *
     * @return volume as a double.
     */
    public double getVolumeLitres() {
        return volumeLitres;
    }

    /**
     * Return temperature in celsius.
     *
     * @return temperature as a double.
     */
    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    /**
     * Return nutrient coefficient.
     *
     * @return nutrient coefficient as a double.
     */
    public double getNutrientCoefficient() {
        return nutrientCoefficient;
    }

    /**
     * Return pH.
     *
     * @return pH as a double.
     */
    public double getPH() {
        return pH;
    }

    /**
     * Return identification number.
     *
     * @return identification number as a int.
     */
    public int getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * Set the volume in litres. Any values less than 0 will be set to 0.
     *
     * @param volumeLitres as a double
     */
    public void setVolumeLitres(double volumeLitres) {
        if (volumeLitres > 0) {
            this.volumeLitres = volumeLitres;
        }
    }

    /**
     * Set temperature in celsius.
     *
     * @param temperatureCelsius as a double.
     */
    public void setTemperatureCelsius(double temperatureCelsius) {
        if (temperatureCelsius > MAXIMUM_POOL_TEMP_CELSIUS
                || temperatureCelsius < MINIMUM_POOL_TEMP_CELSIUS) {

        } else {
            this.temperatureCelsius = temperatureCelsius;
        }

    }

    /**
     * Set pH. Ignores values less than 0 and greater than 14.
     *
     * @param pH as a double.
     */
    public void setpH(double pH) {
        if (pH >= 14.0 && pH <= 0) {
            this.pH = pH;
        }
    }

    /**
     * Set nutrientCoefficient. Ignores values less than 0 and greater than 1.
     *
     * @param nutrientCoefficient as a double.
     */
    public void setNutrientCoefficient(double nutrientCoefficient) {
        if (nutrientCoefficient >= 0 && nutrientCoefficient <= 1.0) {
            this.nutrientCoefficient = nutrientCoefficient;
        }
    }

    /**
     * Changes the nutrient coefficient by the specified delta.
     If the new nutrient
     * coefficient < MINIMUM_NUTRIENT_COEFFICIENT, set the coefficient to the minimum value
     If the new nutrient
     * coefficient < MAXIMUM_NUTRIENT_COEFFICIENT, set the coefficient to the maximum value.
     *
     * @param delta a double
     */
    public void changeNutrientCoefficient(double delta) {
        this.nutrientCoefficient += delta;
        if (getNutrientCoefficient() > MAXIMUM_NUTRIENT_COEFFICIENT) {
            setNutrientCoefficient(MAXIMUM_NUTRIENT_COEFFICIENT);
        }
        if (getNutrientCoefficient() < MINIMUM_NUTRIENT_COEFFICIENT) {
            setNutrientCoefficient(MINIMUM_NUTRIENT_COEFFICIENT);
        }
    }

    /**
     * Change the temperature by a specified delta.
     * If the new temperature is more than the maximum, set it to the maximum value.
     * If the new temperature is more than the minimum, set it to the minimum value.
     *
     * @param delta a double
     */
    public void changeTemperature(double delta) {
        this.temperatureCelsius += delta;
        if (getTemperatureCelsius() > MAXIMUM_POOL_TEMP_CELSIUS) {
            this.temperatureCelsius = MAXIMUM_POOL_TEMP_CELSIUS;
        }
        if (getTemperatureCelsius() < MINIMUM_POOL_TEMP_CELSIUS) {
            this.temperatureCelsius = MINIMUM_POOL_TEMP_CELSIUS;
        }
    }

    /**
     * Get the number of pools created.
     *
     * @return numberOfPools as a int.
     */
    public static int getNumberCreated() {
        return numberOfPools;
    }

    /**
     * Add a guppy to the pool. Ignore if there is no guppy object passed.
     *
     * @param guppy as a Guppy object
     * @return true if a Guppy object is added to the pool, false if a Guppy is not added
     */
    public boolean addGuppy(Guppy guppy) {
        if (guppy == null) {
            return false;
        }
        this.guppiesInPool.add(guppy);
        return true;
    }

    /**
     * Get the population of living guppies from the pool. Does not remove dead guppies.
     *
     * @return population as a int
     */
    public int getPopulation() {
        int population = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (gup.getIsAlive()) {
                population++;
            }
        }
        return population;
    }

    /**
     * Determine which guppies in the pool have died of malnutrition in a week.
     * Determined by comparing a guppies current health coefficient with a
     * random number between 0 and 1.
     * If the guppies health is less than the random number, it has died.
     * Returns the number of guppies that died.
     *
     * @return deaths as a int
     */
    public int applyNutrientCoefficient() {
        int deaths = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (gup.getHealthCoefficient() < this.randomNumberGenerator.nextDouble()) {
                deaths++;
                gup.setIsAlive(false);
            }
        }
        return deaths;
    }

    /**
     * Removes guppies from the pool that have died.
     * Returns the number of guppies that have been removed.
     *
     * @return dead as a int.
     */
    public int removeDeadGuppies() {
        int dead = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (!gup.getIsAlive()) {
                dead++;
                it.remove();
            }
        }
        return dead;
    }

    /**
     * Determine and return the total number of litres of water required by
     * the living guppies in the pool.
     *
     * @return total as a double
     */
    public double getGuppyVolumeRequirementInLitres() {
        double total = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (gup.getIsAlive()) {
                total += gup.getVolumeNeeded();
            }
        }
        total = total / 1000;
        return total;
    }

    /**
     * Determine and return the average age of living guppies in the pool.
     * Ignores any dead guppies.
     *
     * @return average as a double
     */
    public double getAverageAgeInWeeks() {
        double average = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (gup.getIsAlive()) {
                average += gup.getAgeInWeeks();
            }
        }
        if (average == 0) {
            return 0;
        }
        average = average / getPopulation();
        return average;
    }

    /**
     * Determine and return the average health coefficient of the living guppies in the pool.
     * Ignores dead guppies.
     *
     * @return average as a double
     */
    public double getAverageHealthCoefficient() {
        double average = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (gup.getIsAlive()) {
                average += gup.getHealthCoefficient();
            }
        }
        if (average == 0) {
            return 0;
        }
        average = average / getPopulation();
        return average;
    }

    /**
     * Determine and return the percent of living guppies in the pool that are female.
     * Ignores dead guppies.
     *
     * @return percent of live pool poulation that are female.
     */
    public double getFemalePercentage() {
        double percent = 0;
        if (this.guppiesInPool.isEmpty()) {
            return 0;
        }
        Iterator<Guppy> it = this.guppiesInPool.iterator();
        while (it.hasNext()) {
            Guppy gup = it.next();
            if (gup.getIsAlive()) {
                if (gup.getIsFemale()) {
                    percent++;
                }
            }
        }
        if (percent == 0) {
            return 0.0;
        }
        percent /= getPopulation();
        return percent;
    }

    /**
     * Formats a title and returns it with the first letter in upper case and the
     * rest in lower case. If passed null, return a null.
     *
     * @param title the title to format
     * @return the formatted title, as a String
     */
    public String formatTitle(String title) {
        if (title != null && title.trim().length() > 0) {
            String first = title.trim().toUpperCase().substring(0, 1);
            String rest = title.trim().toLowerCase().substring(1);
            return first + rest;
        } else {
            return null;
        }
    }

    /**
     * Print the pool's details to screen.
     * Includes: Name, Volume, Temperature, pH, NutrientCoefficient,
     * Identification#, and the number of guppies in the pool.
     */
    public void printDetails() {
        System.out.println("Name: " + this.name + "\nVolume: " + getVolumeLitres()
                + "\nTemperature: " + getTemperatureCelsius() + "\npH: " + getPH()
                + "\nNutrientCoefficient: " + getNutrientCoefficient() + "\nIdentification#: "
                + getIdentificationNumber() + "\nGuppies: " + getPopulation());
    }

    @Override
    public String toString() {
        return "Pool{"
                + "name='" + name + '\''
                + ", volumeLitres=" + volumeLitres
                + ", temperatureCelsius=" + temperatureCelsius
                + ", pH=" + pH
                + ", nutrientCoefficient=" + nutrientCoefficient
                + ", identificationNumber=" + identificationNumber
                + ", guppiesInPool=" + guppiesInPool
                + ", randomNumberGenerator=" + randomNumberGenerator
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pool pool = (Pool) o;
        return Double.compare(pool.getVolumeLitres(), getVolumeLitres()) == 0
                && Double.compare(pool.getTemperatureCelsius(), getTemperatureCelsius()) == 0
                && Double.compare(pool.getPH(), getPH()) == 0
                && Double.compare(pool.getNutrientCoefficient(), getNutrientCoefficient()) == 0
                && getIdentificationNumber() == pool.getIdentificationNumber()
                && Objects.equals(getName(), pool.getName())
                && Objects.equals(guppiesInPool, pool.guppiesInPool)
                && Objects.equals(randomNumberGenerator, pool.randomNumberGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getVolumeLitres(), getTemperatureCelsius(),
                getPH(), getNutrientCoefficient(), getIdentificationNumber(),
                guppiesInPool, randomNumberGenerator);
    }

    public static void main(String[] args) {
        Pool test = new Pool();
        Guppy tests = new Guppy();
        test.addGuppy(tests);
        test.printDetails();
    }
}
