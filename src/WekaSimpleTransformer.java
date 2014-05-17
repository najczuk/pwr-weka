/**
 * Created with IntelliJ IDEA.
 * User: Adrian
 * Date: 12/05/14
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.io.IOException;

public class WekaSimpleTransformer {
    Instances data;

    public void printData() {
        for (int i = data.numInstances() - 1; i >= 0; i--) {
            System.out.println(data.get(i).toString());
        }
    }

    public void removeAttribute() {
        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "1";
        Remove remove = new Remove();
        try {
            remove.setOptions(options);
            remove.setInputFormat(data);
            data = Filter.useFilter(data, remove);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /**
     *
     */
    public void removeInstances() {
        RemoveWithValues filter = new RemoveWithValues();
        String[] options1 = new String[4];
        String[] options2 = new String[7];
        options1[0] = "-C";
        options1[1] = "5";
        options1[2] = "-S";
        options1[3] = "900";

        options2[0] = "-C";
        options2[1] = "1";
        options2[2] = "-S";
        options2[3] = "0.0";
        options2[4] = "-L";
        options2[5] = "3";
        options2[6] = "-H";

        try {
            filter.setOptions(options1);
            filter.setInputFormat(data);
            data = Filter.useFilter(data, filter);

            filter.setOptions(options2);
            filter.setInputFormat(data);
            data = Filter.useFilter(data, filter);


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void readARFFFile(String path) {
        DataSource source = null;
        try {
            source = new DataSource(path);
            data = source.getDataSet();
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void saveData(String path) {

        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        try {
            saver.setFile(new File(path));
            saver.writeBatch();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}



