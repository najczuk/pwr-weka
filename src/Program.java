
import weka.core.Attribute;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

public class Program {
    static double podstawaAlgorytmu = 2.0;
    private static Instances data;

    public static void main(String[] args) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\workspace\\pwr\\mgr1\\pwr-weka\\data\\13168055L3_1.arff"));
            data = new Instances(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Attribute evaluator = data.attribute("wiek");// to co wybieramy w wece combobox
        int liczbaWartosciEvaluatora = evaluator.numValues();
        Attribute atrybut = data.attribute("status_pozyczki");//badana klasa
        int liczbaWartosciAtrybutu = atrybut.numValues();

        double macierzIlosciWystapien[][] = new double[liczbaWartosciAtrybutu][liczbaWartosciEvaluatora];

        for (int i = 0; i < data.numInstances(); i++) {//zliczenie ilosci wystapien poszczegolnych atrybutow
            macierzIlosciWystapien[(int) data.instance(i).value(atrybut)][(int) data.instance(i).value(evaluator)]++;
        }
        System.out.println("Gain: " + gain(macierzIlosciWystapien));
    }

    public static double gain(double macierzIlosciWystapien[][]) {
        return (hClass(macierzIlosciWystapien) - hClassAtribbute(macierzIlosciWystapien)) / hAttribute(macierzIlosciWystapien);
    }

    public static double hClass(double macierzIlosciWystapien[][]) {
        double sumaWKolumnach[] = new double[macierzIlosciWystapien[0].length];
        for (int i = 0; i < macierzIlosciWystapien[0].length; ++i) {
            for (int j = 0; j < macierzIlosciWystapien.length; ++j) {
                sumaWKolumnach[i] += macierzIlosciWystapien[j][i];
            }
        }

        double entropia = 0.0;
        for (int i = 0; i < macierzIlosciWystapien[0].length; ++i) {//suma
            double tmp = sumaWKolumnach[i] / (double) data.numInstances();//prawdopodobienstwa p(x)
            entropia += tmp * Math.log(tmp) / Math.log(podstawaAlgorytmu);//razy logarytm
        }
        return -entropia;
    }

    public static double hAttribute(double macierzIlosciWystapien[][]) {
        double sumaWWierszach[] = new double[macierzIlosciWystapien.length];
        for (int i = 0; i < macierzIlosciWystapien.length; ++i) {
            for (int j = 0; j < macierzIlosciWystapien[i].length; ++j) {
                sumaWWierszach[i] += macierzIlosciWystapien[i][j];
            }
        }

        double entropia = 0.0;
        for (int i = 0; i < macierzIlosciWystapien.length; ++i) {//suma
            double tmp = sumaWWierszach[i] / (double) data.numInstances();//prawdopodobienstaw p(x)
            entropia += tmp * Math.log(tmp) / Math.log(podstawaAlgorytmu);//razy logarytm
        }
        return -entropia;
    }

    public static double hClassAtribbute(double macierzIlosciWystapien[][]) {
        double sumaWWierszach[] = new double[macierzIlosciWystapien.length];
        for (int i = 0; i < macierzIlosciWystapien.length; ++i) {
            for (int j = 0; j < macierzIlosciWystapien[i].length; ++j) {
                sumaWWierszach[i] += macierzIlosciWystapien[i][j];
            }
        }

        double entropia = 0.0;
        for (int i = 0; i < macierzIlosciWystapien.length; ++i) {//suma
            double tmp = sumaWWierszach[i] / (double) data.numInstances();//prawdopodobienstwa p(y)
            double czesciowaSumaEntropi = 0;
            for (int j = 0; j < macierzIlosciWystapien[0].length; ++j) {//razy suma
                double tmp2 = macierzIlosciWystapien[i][j] / sumaWWierszach[i];//prawdopodobienstwa p(x|y)
                czesciowaSumaEntropi += tmp2 * Math.log(tmp2) / Math.log(podstawaAlgorytmu);//razy logarytm
            }
            czesciowaSumaEntropi *= tmp;
            entropia += czesciowaSumaEntropi;
        }

        return -entropia;//1/p(x|y)
    }
}