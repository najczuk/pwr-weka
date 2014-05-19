import weka.core.Attribute;
import weka.core.Instances;

/**
 * User: Adrian
 * Date: 5/18/14
 * Time: 7:36 PM
 */
public class GainRatioAttrEval {
    Instances data;

    public Instances getData() {
        return data;
    }

    Attribute evaluatedAttr, classAttr;
    int[][] classAttrFreqMatrix;
    int evaluatedAttrDistinctValues, classAttrDistinctValues;
    double[] attributeProbability, classProbability;
    double[][] conditionalClassProbability;
    double logBase = 2;

    public GainRatioAttrEval(Instances data, String evaluatedAttr, String classAttr) {
        this.data = data;
        this.evaluatedAttr = data.attribute(evaluatedAttr);
        this.classAttr = data.attribute(classAttr);
        this.evaluatedAttrDistinctValues = data.numDistinctValues(this.evaluatedAttr);
        this.classAttrDistinctValues = data.numClasses();

        classAttrFreqMatrix = initializeClassAttrFreqMatrix();
        classProbability = calculateProbabilityForClasses();
        attributeProbability = calculateProbabilityForAttributes();
        conditionalClassProbability = calculateConditionalProbabilityForClasses();
//        System.out.println("hAttr: " + hAttribute());
//        System.out.println("hClass: " + hClass());
//        System.out.println("hClassAttr: " + hClassAttribute());
//        System.out.println((hClass() - hClassAttribute()) / hAttribute());

    }

    public double gain() {
        System.out.println(toString());
        return Double.isNaN((hClass() - hClassAttribute()) / hAttribute()) ? 0 :(hClass() - hClassAttribute()) / hAttribute();
    }

    private int[][] initializeClassAttrFreqMatrix() {
        evaluatedAttrDistinctValues = evaluatedAttr.numValues();
        classAttrDistinctValues = classAttr.numValues();
        classAttrFreqMatrix = new int[classAttrDistinctValues][evaluatedAttrDistinctValues];

        for (int instanceNum = 0; instanceNum < data.numInstances(); instanceNum++) {
            classAttrFreqMatrix[(int) data.instance(instanceNum).value(classAttr)]
                    [(int) data.instance(instanceNum).value(evaluatedAttr)]++;
        }
//        System.out.println(Arrays.deepToString(classAttrFreqMatrix));
        return classAttrFreqMatrix;


    }

    private double hAttribute() {
        double entropy = 0.0;
        for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
            entropy += attributeProbability[attributeIndex] * (Math.log(attributeProbability[attributeIndex]) / Math.log(logBase));
        }
        return -entropy;

    }

    private double hClass() {
        double entropy = 0.0;
        for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
            entropy += classProbability[classIndex] * Math.log(classProbability[classIndex]) / Math.log(logBase);
        }
        return -entropy;
    }

    private double hClassAttribute() {
        double entropy = 0.0;
        double tmpAttrProb;
        double tmpConditionalEntropy;

        for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
            tmpConditionalEntropy = 0.0;
            tmpAttrProb = attributeProbability[attributeIndex];
            for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
                double partialEntropy = conditionalClassProbability[classIndex][attributeIndex]
                        * Math.log(conditionalClassProbability[classIndex][attributeIndex]) / Math.log(logBase);
                tmpConditionalEntropy += Double.isNaN(-partialEntropy) ? 0 : partialEntropy;
            }
            entropy += tmpAttrProb * (-tmpConditionalEntropy == Double.NaN ? 0 : -tmpConditionalEntropy);
        }
        return entropy;
    }

    private double[][] calculateConditionalProbabilityForClasses() {
        double[][] conditionalProbabilityForClasses = new double[classAttrDistinctValues][evaluatedAttrDistinctValues];
        double[] attributesValuesTotal = new double[evaluatedAttrDistinctValues];
        for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
            for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
                conditionalProbabilityForClasses[classIndex][attributeIndex] = classAttrFreqMatrix[classIndex][attributeIndex];
                attributesValuesTotal[attributeIndex] += classAttrFreqMatrix[classIndex][attributeIndex];
            }
        }
        for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
            for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
                conditionalProbabilityForClasses[classIndex][attributeIndex] = conditionalProbabilityForClasses[classIndex][attributeIndex]
                        / attributesValuesTotal[attributeIndex];
            }
        }

//        System.out.println("Conditional Classes prob: " + Arrays.deepToString(conditionalProbabilityForClasses));
        return conditionalProbabilityForClasses;
    }

    private double[] calculateProbabilityForAttributes() {
        double[] probabilityForAttributes = new double[evaluatedAttrDistinctValues];
        for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
            for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
                probabilityForAttributes[attributeIndex] += classAttrFreqMatrix[classIndex][attributeIndex];
            }
            probabilityForAttributes[attributeIndex] /= data.numInstances();
        }
//        System.out.println("Attr prob: " + Arrays.toString(probabilityForAttributes));
        return probabilityForAttributes;
    }

    private double[] calculateProbabilityForClasses() {
        double[] probabilityForClasses = new double[classAttrDistinctValues];
        for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
            for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
                probabilityForClasses[classIndex] += classAttrFreqMatrix[classIndex][attributeIndex];
            }
            probabilityForClasses[classIndex] /= data.numInstances();
        }
//        System.out.println("Classes prob: " + Arrays.toString(probabilityForClasses));
        return probabilityForClasses;

    }


    public void printData() {
        for (int i = data.numInstances() - 1; i >= 0; i--) {
            System.out.println(data.get(i).toString());
        }
    }

    @Override
    public String toString() {
        String toString = ("EvalAttr: " + evaluatedAttr.toString() + " ClassAttr: " + classAttr.toString() + " hAttr: "
                + hAttribute() + "| hClass: " + hClass() + "| hClassAttr: " + hClassAttribute() + "|gain: "
                + (hClass() - hClassAttribute()) / hAttribute());
        return toString;
    }
}
