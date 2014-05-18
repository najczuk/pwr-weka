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
    double logBase = 2.0;

    public GainRatioAttrEval(Instances data, String evaluatedAttr, String classAttr) {
        this.data = data;
        this.evaluatedAttr = data.attribute(evaluatedAttr);
        this.classAttr = data.attribute(classAttr);
        this.evaluatedAttrDistinctValues = data.numDistinctValues(this.evaluatedAttr);
        this.classAttrDistinctValues = data.numClasses();
        initializeClassAttrFreqMatrix();
    }

    private void initializeClassAttrFreqMatrix() {
        evaluatedAttrDistinctValues = evaluatedAttr.numValues();
        classAttrDistinctValues = classAttr.numValues();
        classAttrFreqMatrix = new int[classAttrDistinctValues][evaluatedAttrDistinctValues];

        for (int instanceNum = 0; instanceNum < data.numInstances(); instanceNum++) {
            classAttrFreqMatrix[(int) data.instance(instanceNum).value(classAttr)]
                    [(int) data.instance(instanceNum).value(evaluatedAttr)]++;
        }


    }

    private double[] calculateConditionalProbabilityForClasses(){
        double[] conditionalProbabilityForClasses = new double[classAttrDistinctValues];
        for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {

        }
    }

    private double[] calculateProbabilityForAttributes() {
        double[] probabilityForAttributes = new double[evaluatedAttrDistinctValues];
        for (int attributeIndex = 0; attributeIndex < evaluatedAttrDistinctValues; attributeIndex++) {
            for (int classIndex = 0; classIndex < classAttrDistinctValues; classIndex++) {
                probabilityForAttributes[attributeIndex] += classAttrFreqMatrix[classIndex][attributeIndex];
            }
            probabilityForAttributes[attributeIndex] /= data.numInstances();
        }
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
        return probabilityForClasses;

    }

//    public double gain() {
//        return (hClass(classAttrFreqMatrix) - hClassAtribbute(classAttrFreqMatrix)) / hAttribute(classAttrFreqMatrix);
//    }


    public void printData() {
        for (int i = data.numInstances() - 1; i >= 0; i--) {
            System.out.println(data.get(i).toString());
        }
    }
}
