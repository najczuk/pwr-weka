import utils.ArffFileHandler;
import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
        Instances data = ArffFileHandler.readARFFFile("D:\\workspace\\pwr\\mgr1\\pwr-weka\\data\\13168055L3_1.arff");
        Instances discretizedData = ArffFileHandler.discretizeNumericData(data);
        GainRatioAttrEval evaluator = new GainRatioAttrEval(discretizedData, "okres_pobieral_dochod", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "ktore_rolowanie", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "miesieczny_dochod_netto", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "okres_bedzie_pobieral_dochod", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "plec", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "wiek", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "rodzaj_zr_dochodu", "status_pozyczki");
        System.out.println(evaluator.gain());
        evaluator = new GainRatioAttrEval(discretizedData, "kwota_kredytu", "status_pozyczki");
        System.out.println(evaluator.gain());
    }


}
