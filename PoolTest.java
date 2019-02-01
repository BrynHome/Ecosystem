import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.junit.Assert.*;

public class PoolTest {

    private Pool defaultPool;
    private Pool testPool;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        defaultPool = new Pool();
        testPool = new Pool("Skookumchuk",
                1000.0,
                40.0,
                7.0,
                0.5);
    }

    @Test
    public void containsConstantCalledDEFAULT_POOL_NAME() {
        assertTrue(Pool.DEFAULT_POOL_NAME.equals(Pool.DEFAULT_POOL_NAME));
    }

    @Test
    public void containsConstantCalledDEFAULT_POOL_TEMP_CELSIUS() {
        assertEquals(40.0, Pool.DEFAULT_POOL_TEMP_CELSIUS, 0.0);
    }

    @Test
    public void containsConstantCalledMINIMUM_POOL_TEMP_CELSIUS() {
        assertEquals(0.0, Pool.MINIMUM_POOL_TEMP_CELSIUS, 0.0);
    }

    @Test
    public void containsConstantCalledMAXIMUM_POOL_TEMP_CELSIUS() {
        assertEquals(100.0, Pool.MAXIMUM_POOL_TEMP_CELSIUS, 0.0);
    }

    @Test
    public void containsConstantCalledNEUTRAL_PH() {
        assertEquals(7.0, Pool.NEUTRAL_PH, 0.0);
    }

    @Test
    public void containsConstantCalledDEFAULT_NUTRIENT_COEFFICIENT() {
        assertEquals(0.5, Pool.DEFAULT_NUTRIENT_COEFFICIENT, 0.0);
    }

    @Test
    public void containsConstantCalledMINIMUM_NUTRIENT_COEFFICIENT() {
        assertEquals(0.0, Pool.MINIMUM_NUTRIENT_COEFFICIENT, 0.0);
    }

    @Test
    public void containsConstantCalledMAXIMUM_NUTRIENT_COEFFICIENT() {
        assertEquals(1.0, Pool.MAXIMUM_NUTRIENT_COEFFICIENT, 0.0);
    }

    @org.junit.Test
    public void staticPoolCounterIsCorrectlyTrackingPoolsInDefaultConstructor() {
        final int numberAlreadyCreated = Pool.getNumberCreated();
        final int numberCreated = 100;
        for (int i = 0; i < numberCreated; ++i) {
            Pool aPool = new Pool();
        }
        assertEquals(numberAlreadyCreated + numberCreated, Pool.getNumberCreated());
    }

    @org.junit.Test
    public void staticPoolCounterIsCorrectlyTrackingPoolsInMultiParamConstructor() {
        final int numberAlreadyCreated = Pool.getNumberCreated();
        final int numberCreated = 100;
        for (int i = 0; i < numberCreated; ++i)
            new Pool("Skookumchuk",
                    1000.0,
                    Pool.DEFAULT_POOL_TEMP_CELSIUS,
                    Pool.NEUTRAL_PH,
                    Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertEquals(numberAlreadyCreated + numberCreated, Pool.getNumberCreated());
    }

    @Test
    public void defaultPoolIsSetToCorrectValues() {
        assertTrue(defaultPool.getName().equals(Pool.DEFAULT_POOL_NAME));
        assertEquals(0, defaultPool.getVolumeLitres(), 0.0);
        assertEquals(Pool.DEFAULT_POOL_TEMP_CELSIUS, defaultPool.getTemperatureCelsius(), 0.0);
        assertEquals(Pool.NEUTRAL_PH, defaultPool.getPH(), 0.0);
        assertEquals(Pool.DEFAULT_NUTRIENT_COEFFICIENT, defaultPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void multiParamPoolIsSetToCorrectValues() {
        assertTrue(testPool.getName().equals("Skookumchuk"));
        assertEquals(1000.0, testPool.getVolumeLitres(), 0.0);
        assertEquals(Pool.DEFAULT_POOL_TEMP_CELSIUS, testPool.getTemperatureCelsius(), 0.0);
        assertEquals(Pool.NEUTRAL_PH, testPool.getPH(), 0.0);
        assertEquals(Pool.DEFAULT_NUTRIENT_COEFFICIENT, testPool.getNutrientCoefficient(), 0.0);
    }


    @Test
    public void identificationNumbersAreSequentialAndUniqueInDefaultConstruction() {
        Pool first = new Pool();
        int firstID = first.getIdentificationNumber() + 1;
        int numberCreated = 100;
        for (int i = 0; i < numberCreated; ++i) {
            Pool littlePool = new Pool();
            assertEquals(firstID + i, littlePool.getIdentificationNumber());
        }
    }

    @Test
    public void identificationNumbersAreSequentialAndUniqueInMultiParamConstruction() {
        Pool first = new Pool();
        int firstID = first.getIdentificationNumber() + 1;
        int numberCreated = 100;
        for (int i = 0; i < numberCreated; ++i) {
            Pool newPool = new Pool("Skookumchuk",
                    1000.0,
                    Pool.DEFAULT_POOL_TEMP_CELSIUS,
                    Pool.NEUTRAL_PH,
                    Pool.DEFAULT_NUTRIENT_COEFFICIENT);
            assertEquals(firstID + i, newPool.getIdentificationNumber());
        }
    }

    @org.junit.Test
    public void poolNameIsCorrectlyFormattedAndStored() {
        Pool newPool = new Pool("     ohmy     ",
                1000.0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                Pool.NEUTRAL_PH,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertTrue(newPool.getName().equals("Ohmy"));
    }

    @Test
    public void createExceptionWithWhitespaceName() {
        thrown.expect(IllegalArgumentException.class);
        Pool newPool = new Pool("           ",
                1000.0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                Pool.NEUTRAL_PH,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
    }

    @Test
    public void multiParamConstructorSubsZeroForNegativeVolume() {
        Pool newPool = new Pool("Skookumchuk",
                -1.0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                Pool.NEUTRAL_PH,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertEquals(0.0, newPool.getVolumeLitres(), 0.0);
    }

    @Test
    public void multiParamConstructorSubsDEFAULTForNegativeTemperature() {
        Pool newPool = new Pool("Skookumchuk",
                0,
                -1.0,
                Pool.NEUTRAL_PH,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertEquals(Pool.DEFAULT_POOL_TEMP_CELSIUS, newPool.getTemperatureCelsius(), 0.0);
    }

    @Test
    public void multiParamConstructorSubDEFAULTForOverlyHighTemperature() {
        Pool newPool = new Pool("Skookumchuk",
                0,
                1000.0,
                Pool.NEUTRAL_PH,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertEquals(Pool.DEFAULT_POOL_TEMP_CELSIUS, newPool.getTemperatureCelsius(), 0.0);
    }

    @Test
    public void multiParamConstructorSubsNEUTRALForNegativePH() {
        Pool newPool = new Pool("Skookumchuk",
                0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                -1,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertEquals(Pool.NEUTRAL_PH, newPool.getPH(), 0.0);
    }

    @Test
    public void multiParamConstructorSubsNEUTRALForOverlargePH() {
        Pool newPool = new Pool("Skookumchuk",
                0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                15,
                Pool.DEFAULT_NUTRIENT_COEFFICIENT);
        assertEquals(Pool.NEUTRAL_PH, newPool.getPH(), 0.0);
    }

    @Test
    public void multiParamConstructorSubsDEFAULTForNegativeNutrientCoefficient() {
        Pool newPool = new Pool("Skookumchuk",
                0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                Pool.NEUTRAL_PH,
                -0.01);
        assertEquals(Pool.DEFAULT_NUTRIENT_COEFFICIENT, newPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void multiParamConstructorSubsDEFAULTForOverlargeNutrientCoefficient() {
        Pool newPool = new Pool("Skookumchuk",
                0,
                Pool.DEFAULT_POOL_TEMP_CELSIUS,
                Pool.NEUTRAL_PH,
                1.01);
        assertEquals(Pool.DEFAULT_NUTRIENT_COEFFICIENT, newPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void nameAccessorReturnsCorrectName() {
        assertTrue(defaultPool.getName().equals(Pool.DEFAULT_POOL_NAME));
        assertTrue(testPool.getName().equals("Skookumchuk"));
    }

    @Test
    public void volumeAccessorReturnsCorrectVolume() {
        assertEquals(0.0, defaultPool.getVolumeLitres(), 0.0);
        assertEquals(1000.0, testPool.getVolumeLitres(), 0.0);
    }

    @Test
    public void temperatureAccessorReturnsCorrectTemperature() {
        assertEquals(40.0, defaultPool.getTemperatureCelsius(), 0.0);
        assertEquals(40.0, testPool.getTemperatureCelsius(), 0.0);
    }

    @Test
    public void nutrientCoefficientAccessorReturnsCorrectPH() {
        assertEquals(Pool.DEFAULT_NUTRIENT_COEFFICIENT, defaultPool.getNutrientCoefficient(), 0.0);
        assertEquals(Pool.DEFAULT_NUTRIENT_COEFFICIENT, testPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void volumeMutatorIgnoresNegativeArguments() {
        double volume = defaultPool.getVolumeLitres();
        defaultPool.setVolumeLitres(-0.01);
        assertEquals(volume, defaultPool.getVolumeLitres(), 0.0);
    }

    @Test
    public void temperatureMutatorIgnoresNegativeArguments() {
        double temperature = defaultPool.getTemperatureCelsius();
        defaultPool.setTemperatureCelsius(Pool.MINIMUM_POOL_TEMP_CELSIUS - 0.01);
        assertEquals(temperature, defaultPool.getTemperatureCelsius(), 0.0);
    }

    @Test
    public void temperatureMutatorIgnoresOverlargeArguments() {
        double temperature = defaultPool.getTemperatureCelsius();
        defaultPool.setTemperatureCelsius(Pool.MAXIMUM_POOL_TEMP_CELSIUS + 0.01);
        assertEquals(temperature, defaultPool.getTemperatureCelsius(), 0.0);
    }

    @Test
    public void pHMutatorIgnoresNegativeArguments() {
        double pH = defaultPool.getPH();
        defaultPool.setpH(-0.01);
        assertEquals(pH, defaultPool.getPH(), 0.0);
    }

    @Test
    public void pHMutatorIgnoresOverlargeArguments() {
        double pH = defaultPool.getPH();
        defaultPool.setpH(14.01);
        assertEquals(pH, defaultPool.getPH(), 0.0);
    }

    @Test
    public void nutrientCoefficientMutatorIgnoresNegativeArguments() {
        double nutrientCoefficient = defaultPool.getNutrientCoefficient();
        defaultPool.setNutrientCoefficient(-0.01);
        assertEquals(nutrientCoefficient, defaultPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void nutrientCoefficientMutatorIgnoresOverlargeArguments() {
        double nutrientCoefficient = defaultPool.getNutrientCoefficient();
        defaultPool.setNutrientCoefficient(1.01);
        assertEquals(nutrientCoefficient, defaultPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void changeNutrientCoefficientWillNotPermitOverlargeNutrientCoefficients() {
        testPool.changeNutrientCoefficient(1.5);
        assertEquals(1.0, testPool.getNutrientCoefficient(), 0.0);
    }


    @Test
    public void changeNutrientCoefficientWillNotPermitNegativeNutrientCoefficients() {
        testPool.changeNutrientCoefficient(-1.5);
        assertEquals(Pool.MINIMUM_NUTRIENT_COEFFICIENT, testPool.getNutrientCoefficient(), 0.0);
    }

    @Test
    public void changeTemperatureWillNotPermitOverhighTemperature() {
        testPool.changeTemperature(150.0);
        assertEquals(Pool.MAXIMUM_POOL_TEMP_CELSIUS, testPool.getTemperatureCelsius(), 0.0);
    }


    @Test
    public void changeTemperatureWillNotPermitNegativeTemperature() {
        testPool.changeTemperature(-150.0);
        assertEquals(Pool.MINIMUM_POOL_TEMP_CELSIUS, testPool.getTemperatureCelsius(), 0.0);
    }

    @Test
    public void cannotAddNullGuppy() {
        assertFalse(testPool.addGuppy(null));
        assertEquals(0, testPool.getPopulation());
    }

    @Test
    public void addingGuppyReturnsTrue() {
        assertTrue(testPool.addGuppy(new Guppy()));
        assertEquals(1, testPool.getPopulation());
    }

    @Test
    public void removeDeadGuppiesCullsDeadGuppies() {
        Guppy deadGuppy = new Guppy();
        deadGuppy.setIsAlive(false);
        assertTrue(testPool.addGuppy(deadGuppy));
        assertEquals(0, testPool.getPopulation());
        assertEquals(1, testPool.removeDeadGuppies());
        assertEquals(0, testPool.getPopulation());
    }

    @Test
    public void deadGuppiesNeedNoWater() {
        for (int i = 0; i < 100; ++i) {
            Guppy deadGuppy = new Guppy();
            deadGuppy.setIsAlive(false);
            assertTrue(testPool.addGuppy(deadGuppy));
        }
        assertEquals(0.0, testPool.getGuppyVolumeRequirementInLitres(), 0.0);
    }

    @Test
    public void emptyPoolHasAverageAgeOfZero() {
        assertEquals(0.0, testPool.getAverageAgeInWeeks(), 0.0);
    }

    @Test
    public void poolOfLivingGuppiesCalculatesCorrectAverageAge() {
        Random random = new Random();
        int count = 100;
        double ageSum = 0;
        for (int i = 0; i < count; ++i) {
            int randomAge = random.nextInt(50);
            ageSum += randomAge;
            Guppy newGuppy = new Guppy(  "Poecilia",
                    "elegans",
                    randomAge,
                    true,
                    3,
                    0.75);
            testPool.addGuppy(newGuppy);
        }
        assertEquals(ageSum / count, testPool.getAverageAgeInWeeks(), 0.05);
    }

    @Test
    public void deadGuppiesAreNotCountedWhenCalculatingAverageAge() {
        for (int i = 0; i < 100; ++i) {
            Guppy deadGuppy = new Guppy();
            deadGuppy.setIsAlive(false);
            assertTrue(testPool.addGuppy(deadGuppy));
        }
        assertEquals(0.0, testPool.getAverageAgeInWeeks(), 0.0);
    }

    @Test
    public void deadGuppiesAreNotCountedWhenCalculatingAverageHealthCoefficient() {
        for (int i = 0; i < 100; ++i) {
            Guppy deadGuppy = new Guppy();
            deadGuppy.setIsAlive(false);
            assertTrue(testPool.addGuppy(deadGuppy));
        }
        assertEquals(0.0, testPool.getAverageHealthCoefficient(), 0.0);
    }

    @Test
    public void calculatesAverageHealthCoefficientCorrectly() {
        Random random = new Random();
        int count = 100;
        double healthCoefficientSum = 0;
        for (int i = 0; i < count; ++i) {
            double randomHealthCoefficient = random.nextDouble();
            healthCoefficientSum += randomHealthCoefficient;
            Guppy newGuppy = new Guppy(  "Poecilia",
                    "elegans",
                    0,
                    true,
                    3,
                    randomHealthCoefficient);
            testPool.addGuppy(newGuppy);
        }
        assertEquals(healthCoefficientSum / count, testPool.getAverageHealthCoefficient(), 0.05);
    }

    @Test
    public void emptyPoolHasNoFemales() {
        assertEquals(0.0, testPool.getFemalePercentage(), 0.0);
    }

    @Test
    public void mixedPoolCalculatesFemalePercentageCorrectly() {
        Random random = new Random();
        int count = 100;
        int females = 0;
        for (int i = 0; i < count; ++i) {
            boolean female = random.nextBoolean();
            if (female) { females++; }
            Guppy newGuppy = new Guppy(  "Poecilia",
                    "elegans",
                    0,
                    female,
                    3,
                    0.75);
            testPool.addGuppy(newGuppy);
        }
        assertEquals((double) females / count, testPool.getFemalePercentage(), 0.05);
    }
}