import utils.ArffFileHandler;
import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
//        WekaSimpleTransformer simpleTransformer = new WekaSimpleTransformer();
//        simpleTransformer.readARFFFile( "filepath");
//        simpleTransformer.removeInstances();
//        simpleTransformer.removeAttribute();
//        simpleTransformer.printData();
//        simpleTransformer.saveData("filepath");
        Instances data = ArffFileHandler.readARFFFile("D:\\workspace\\pwr\\mgr1\\pwr-weka\\data\\13168055L3_1.arff");
        Instances discretizedData = ArffFileHandler.discretizeNumericData(data);
        GainRatioAttrEval evaluator = new GainRatioAttrEval(discretizedData, "wiek", "status_pozyczki");

//        ArffFileHandler.saveData(evaluator.getData(),"D:\\workspace\\pwr\\mgr1\\pwr-weka\\data\\13168055L3_x.arff");
        System.out.println(evaluator.gain());

    }


}
