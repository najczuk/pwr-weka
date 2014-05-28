import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.Random;

/**
 * User: Adrian
 * Date: 5/28/14
 * Time: 6:23 PM
 */
public class CrossValidator {
    int numberOfRounds, numberOfFolds;
    double accuracy, tnrate, tprate, gMean, auc;
    Instances data;
    Classifier classifier;

    public static void main(String[] args) {

    }

    public CrossValidator(int numberOfRounds, int numberOfFolds, Instances data, Classifier classifier) {
        this.numberOfRounds = numberOfRounds;
        this.numberOfFolds = numberOfFolds;
        this.data = data;
        this.classifier = classifier;
        try {
            crossValidate();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private double[][] crossValidate() throws Exception {
        double[][] allRoundsConfusionMatix = new double[2][2];
        for (int i = 0; i < numberOfRounds; i++) {
            Instances randData = divideDataIntoFolds(data, numberOfFolds);


            double[][] partialConfusionMatrix = evaluateDataSet(classifier, numberOfFolds, randData);
            allRoundsConfusionMatix[0][0] += partialConfusionMatrix[0][0];
            allRoundsConfusionMatix[0][1] += partialConfusionMatrix[0][1];
            allRoundsConfusionMatix[1][0] += partialConfusionMatrix[1][0];
            allRoundsConfusionMatix[1][1] += partialConfusionMatrix[1][1];

        }
        allRoundsConfusionMatix[0][0] /= numberOfRounds;
        allRoundsConfusionMatix[0][1] /= numberOfRounds;
        allRoundsConfusionMatix[1][0] /= numberOfRounds;
        allRoundsConfusionMatix[1][1] /= numberOfRounds;

        setTNRate(allRoundsConfusionMatix);
        setTPRate(allRoundsConfusionMatix);
        setAccuracy(allRoundsConfusionMatix);
        setAUC(allRoundsConfusionMatix);
        setGMean(allRoundsConfusionMatix);

        return allRoundsConfusionMatix;
    }

    public String qualityResults() {
        return "Accuracy: " + accuracy + " |TNRate: " + tnrate + " |TPRate: " + tprate + " |gMean: " + gMean + " |AUC: " + auc;
    }


    private void setAccuracy(double[][] confusionMatrix) {
        double truePositive = confusionMatrix[0][0];
        double falseNegative = confusionMatrix[0][1];
        double falsePositive = confusionMatrix[1][0];
        double trueNegative = confusionMatrix[1][1];
        double accuracy = (truePositive + trueNegative) / (truePositive + falseNegative + falsePositive + trueNegative);

        this.accuracy = accuracy;
    }

    private void setTNRate(double[][] confusionMatrix) {
        double falsePositive = confusionMatrix[1][0];
        double trueNegative = confusionMatrix[1][1];
        double tnrate = trueNegative / (trueNegative + falsePositive);

        this.tnrate = tnrate;

    }

    private void setTPRate(double[][] confusionMatrix) {
        double truePositive = confusionMatrix[0][0];
        double falseNegative = confusionMatrix[0][1];
        double tprate = truePositive / (truePositive + falseNegative);

        this.tprate = tprate;

    }

    private void setGMean(double[][] confusionMatrix) {
        double truePositive = confusionMatrix[0][0];
        double falseNegative = confusionMatrix[0][1];
        double falsePositive = confusionMatrix[1][0];
        double trueNegative = confusionMatrix[1][1];
        double gMean = Math.sqrt(truePositive / (truePositive + falseNegative) * trueNegative / (trueNegative + falsePositive));

        this.gMean = gMean;

    }

    private void setAUC(double[][] confusionMatrix) {
        double truePositive = confusionMatrix[0][0];
        double falseNegative = confusionMatrix[0][1];
        double falsePositive = confusionMatrix[1][0];
        double auc = (1 + truePositive / (truePositive + falseNegative) - falsePositive / (falsePositive + truePositive)) / 2;

        this.auc = auc;

    }

    /**
     * @param classifier
     * @param numberOfFolds
     * @param randData
     * @return confusion matrix for cross validation training and test
     * @throws Exception
     */
    private static double[][] evaluateDataSet(Classifier classifier, int numberOfFolds, Instances randData) throws Exception {

        Evaluation eval = new Evaluation(randData);
        double[][] evaluationConfusionMatix = new double[2][2];
        for (int n = 0; n < numberOfFolds; n++) {
            Instances train = randData.trainCV(numberOfFolds, n);
            Instances test = randData.testCV(numberOfFolds, n);
            classifier.buildClassifier(train);
            eval.evaluateModel(classifier, test);
        }
        double[][] partialConfusionMatrix = eval.confusionMatrix();

        evaluationConfusionMatix[0][0] += partialConfusionMatrix[0][0];
        evaluationConfusionMatix[0][1] += partialConfusionMatrix[0][1];
        evaluationConfusionMatix[1][0] += partialConfusionMatrix[1][0];
        evaluationConfusionMatix[1][1] += partialConfusionMatrix[1][1];

        return evaluationConfusionMatix;

    }

    private static Instances divideDataIntoFolds(Instances data, int numberOfFolds) {
        Random rand = new Random();
        Instances randData = new Instances(data);
        randData.randomize(rand);
        randData.stratify(numberOfFolds);
        return randData;
    }


}
