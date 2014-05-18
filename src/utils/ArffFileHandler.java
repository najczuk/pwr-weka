package utils;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

import java.io.File;
import java.io.IOException;

/**
 * User: Adrian
 * Date: 5/18/14
 * Time: 7:54 PM
 */
public class ArffFileHandler {

    public static Instances readARFFFile(String path) {
        Instances data;
        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource(path);
            data = source.getDataSet();
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveData(Instances data, String path) {

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

    public static Instances discretizeNumericData(Instances data) {
        Instances discretizedData;
        Discretize discretizeFilter = new Discretize();
        try {
            discretizeFilter.setInputFormat(data);
            discretizedData = new Instances(Filter.useFilter(data, discretizeFilter));
            return discretizedData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
