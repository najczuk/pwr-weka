import utils.ArffFileHandler;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
        Instances data = ArffFileHandler.readARFFFile("D:\\workspace\\pwr\\mgr1\\pwr-weka\\data\\13168055L4_1.arff");

        J48 j48 = new J48();
        NaiveBayes naiveBayes = new NaiveBayes();
        ZeroR zeroR = new ZeroR();
        JRip jRip = new JRip();
        MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
        SMO smo = new SMO();

        int numberOfRounds = 6;
        int numberOfFolds = 5;

        CrossValidator crossValidator = new CrossValidator(numberOfRounds, numberOfFolds, data, j48);
        System.out.println("J48 " + crossValidator.qualityResults());
        crossValidator = new CrossValidator(numberOfRounds, numberOfFolds, data, naiveBayes);

        System.out.println("naiveBayes " + crossValidator.qualityResults());
        crossValidator = new CrossValidator(numberOfRounds, numberOfFolds, data, zeroR);

        System.out.println("zeroR " + crossValidator.qualityResults());
        crossValidator = new CrossValidator(numberOfRounds, numberOfFolds, data, jRip);
        System.out.println("jRip " + crossValidator.qualityResults());
        crossValidator = new CrossValidator(numberOfRounds, numberOfFolds, data, multilayerPerceptron);
        System.out.println("multilayerPerceptron " + crossValidator.qualityResults());
        crossValidator = new CrossValidator(numberOfRounds, numberOfFolds, data, smo);
        System.out.println("smo " + crossValidator.qualityResults());

        /*
        RESULTS   R4 F10
        J48 Accuracy: 0.8679410294852574 |TNRate: 0.35895522388059703 |TPRate: 0.9702881152460985 |gMean: 0.590160984509181 |AUC: 0.9265092453363598
        naiveBayes Accuracy: 0.8438280859570215 |TNRate: 0.3850746268656716 |TPRate: 0.9360744297719088 |gMean: 0.6003819715505405 |AUC: 0.909696801405354
        zeroR Accuracy: 0.8325837081459271 |TNRate: 0.0 |TPRate: 1.0 |gMean: 0.0 |AUC: 0.9162918540729635
        jRip Accuracy: 0.8520739630184908 |TNRate: 0.4082089552238806 |TPRate: 0.9413265306122449 |gMean: 0.6198854084310621 |AUC: 0.9145494809868471
        multilayerPerceptron Accuracy: 0.8540729635182409 |TNRate: 0.3529850746268657 |TPRate: 0.9548319327731093 |gMean: 0.5805526858486061 |AUC: 0.9174574601624883
        smo Accuracy: 0.8631934032983508 |TNRate: 0.28507462686567164 |TPRate: 0.9794417767106842 |gMean: 0.5284070391586857 |AUC: 0.9257262323767181

        RESULTS  R3 F4
        J48 Accuracy: 0.8600699650174912 |TNRate: 0.3243781094527363 |TPRate: 0.9677871148459384 |gMean: 0.5602936325414056 |AUC: 0.9223453340727155
        naiveBayes Accuracy: 0.8442445443944694 |TNRate: 0.39104477611940297 |TPRate: 0.9353741496598639 |gMean: 0.6047918443081209 |AUC: 0.9098092613251089
        zeroR Accuracy: 0.8325837081459271 |TNRate: 0.0 |TPRate: 1.0 |gMean: 0.0 |AUC: 0.9162918540729635
        jRip Accuracy: 0.8552390471430952 |TNRate: 0.3870646766169154 |TPRate: 0.9493797519007604 |gMean: 0.6061941658050787 |AUC: 0.9172379080339467
        multilayerPerceptron Accuracy: 0.85224054639347 |TNRate: 0.3482587064676617 |TPRate: 0.9535814325730293 |gMean: 0.5762751393383745 |AUC: 0.9163775083912923
        smo Accuracy: 0.8607362985174081 |TNRate: 0.2676616915422886 |TPRate: 0.9799919967987195 |gMean: 0.5121584867607393 |AUC: 0.9246782845193456

        RESULTS R3 F3
        J48 Accuracy: 0.861735798767283 |TNRate: 0.3472636815920398 |TPRate: 0.9651860744297719 |gMean: 0.5789421988660449 |AUC: 0.9227390226163458
        naiveBayes Accuracy: 0.8424121272696985 |TNRate: 0.3850746268656716 |TPRate: 0.9343737494998 |gMean: 0.5998363301282392 |AUC: 0.9087526841296276
        zeroR Accuracy: 0.8325837081459271 |TNRate: 0.0 |TPRate: 1.0 |gMean: 0.0 |AUC: 0.9162918540729635
        jRip Accuracy: 0.8512410461435949 |TNRate: 0.38905472636815924 |TPRate: 0.9441776710684273 |gMean: 0.6060831506158646 |AUC: 0.9145227376530961
        multilayerPerceptron Accuracy: 0.8525737131434283 |TNRate: 0.3880597014925373 |TPRate: 0.9459783913565426 |gMean: 0.6058845534986105 |AUC: 0.9154372585642904
        smo Accuracy: 0.8587372980176577 |TNRate: 0.25472636815920396 |TPRate: 0.9801920768307323 |gMean: 0.49968066585522386 |AUC: 0.9237893811915701

        RESULTS R6 F5
        J48 Accuracy: 0.8668165917041479 |TNRate: 0.35771144278606964 |TPRate: 0.969187675070028 |gMean: 0.5888034660052335 |AUC: 0.9257997761450878
        naiveBayes Accuracy: 0.8430784607696152 |TNRate: 0.3835820895522388 |TPRate: 0.9354741896758704 |gMean: 0.5990251617403544 |AUC: 0.9092385112685301
        zeroR Accuracy: 0.8325837081459271 |TNRate: 0.0 |TPRate: 1.0 |gMean: 0.0 |AUC: 0.9162918540729635
        jRip Accuracy: 0.8548225887056472 |TNRate: 0.40696517412935324 |TPRate: 0.9448779511804722 |gMean: 0.6201067810733466 |AUC: 0.9164081398282731
        multilayerPerceptron Accuracy: 0.8467432950191571 |TNRate: 0.41393034825870645 |TPRate: 0.9337735094037616 |gMean: 0.621705069902324 |AUC: 0.9108555522665688
        smo Accuracy: 0.863568215892054 |TNRate: 0.28756218905472636 |TPRate: 0.9793917567026811 |gMean: 0.5306939207298091 |AUC: 0.9258926347227538
        */

        /*
        WNIOSKI
        Wśród wielu testów najlepsze wyniki wyszły dla 6 rund i 5 foldów, przy 10 foldach widać jakby załamanie wyników, z kolei przy 3,4 foldach wartości są już niższe.
         */


    }


}
