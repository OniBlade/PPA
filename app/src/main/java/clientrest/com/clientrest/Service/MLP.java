package clientrest.com.clientrest.Service;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.TrainingSet;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.Utils;

/**
 * Created by Fagner Roger on 03/10/2017.
 */

public class MLP {

    private final static String TAG = "MLP";
    private StringReader string_train;
    private StringReader string_train_test;
    private DecimalFormat formated;
    private StringBuilder header;
    private MultilayerPerceptron mlp;
    private Instances train;
    private Instances test;
    private Context context;

    public MLP(Context context) {
        this.context = context;
    }

    public void RetrainMLP() {
        setString_train(getArffFormattedFile());
        this.train = ObjInstances(getString_train());
        MultilayerPerceptron();
    }

    public MLP(Context context, String train_test) {
        Log.e(TAG,train_test);
        this.context = context;
        this.test = ObjInstances(getStringReadTest(train_test));
        setMlp(getMLPDataBase());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public StringReader getString_train() {
         return string_train;
    }

    public void setString_train(String string_train) {
        this.string_train = new StringReader(string_train);
    }

    public StringReader getStringReadTest(String string_train_test) {
        DBHelper database = new DBHelper(context);
        String header = database.getHeaderMLP();
        return new StringReader(header + string_train_test);
    }

    private MultilayerPerceptron getMlp() {
        return mlp;
    }

    private void setMlp(MultilayerPerceptron mlp) {
        this.mlp = mlp;
    }

    private Instances getTrain() {
        return train;
    }

    private Instances getTest() {
        return test;
    }

    private String getFormated(double prediction) {
        this.formated = new DecimalFormat("#.######");
        return formated.format(Double.valueOf(prediction));
    }

    private String getArffFormattedFile() {
        DBHelper database = new DBHelper(context);
        StringBuilder arff = new StringBuilder();
        StringBuilder data = new StringBuilder();
        //Set<Integer> deviceType = new HashSet<>();
        Set<String> dataType = new HashSet<>();

        String delimiter = ",";
        List<TrainingSet> trainingSetList = database.getListTrainingSeT();
        for (int i = 0; i < trainingSetList.size(); i++) {
            data.append(trainingSetList.get(i).getDeviceType() + delimiter);
            data.append(trainingSetList.get(i).getDataType() + delimiter);
            data.append(trainingSetList.get(i).getUserBenefit() + delimiter);
            data.append(trainingSetList.get(i).getRetention() + delimiter);
            data.append(trainingSetList.get(i).getLocation() + delimiter);
            data.append(trainingSetList.get(i).getShared() + delimiter);
            data.append(trainingSetList.get(i).getInferred() + delimiter);
            data.append(trainingSetList.get(i).getResult() + "\n");
            //deviceType.add(trainingSetList.get(i).getDeviceType());
            dataType.add(trainingSetList.get(i).getDataType());
        }
        header = getHeaderArff(database.getAllConsumer(), dataType);
        arff.append(header.toString());
        arff.append(data.toString());
        arff.append("\n");
        Log.e(TAG,arff.toString());
        return arff.toString();
    }

    private StringBuilder getHeaderArff(Set<Integer> deviceType, Set<String> dataType) {
        StringBuilder header = new StringBuilder();
        String delimiter = ",";
        header.append("@relation train\n");
        header.append("@attribute device_type {");
        Iterator<Integer> deviceTypeAsIterator = deviceType.iterator();
        while (deviceTypeAsIterator.hasNext()) {
            header.append(deviceTypeAsIterator.next() + delimiter);
        }
        header = header.delete(header.length() - 1, header.length());
        header.append("}\n");
        header.append("@attribute data_type {");

        Iterator<String> dataTypeAsIterator = dataType.iterator();
        while (dataTypeAsIterator.hasNext()) {
            header.append(dataTypeAsIterator.next() + delimiter);
        }
        header = header.delete(header.length() - 1, header.length());
        header.append("}\n");
        header.append("@attribute user_benefit {user,consumer,user_consumer}\n");
        header.append("@attribute retention {satisfied_purpose,forever}\n");
        header.append("@attribute location {semi_public,public,your_place,someone_else_place}\n");
        header.append("@attribute shared {no,yes}\n");
        header.append("@attribute inferred {no,yes}\n");
        header.append("@attribute result {allow,deny,negotiate}\n");
        header.append("@data\n");
        return header;
    }


    private MultilayerPerceptron getMLPDataBase() {
        DBHelper database = new DBHelper(context);
        MultilayerPerceptron model = null;
        try {
            model = (MultilayerPerceptron) database.getMLP();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    private void MultilayerPerceptron() {
        try {
            MultilayerPerceptron model = new MultilayerPerceptron();
            model.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 4"));
            model.buildClassifier(getTrain());
            setMlp(model);
            DBHelper database = new DBHelper(context);
            database.saveMLP(model, header);

        } catch (Exception ex) {
            Logger.getLogger(MLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Instances ObjInstances(StringReader str) {
        try {
            Instances tn = new Instances(str);
            tn.setClassIndex(tn.numAttributes() - 1);
            return tn;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MLP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public Evaluation crossValidate() throws Exception {
        Evaluation eval = new Evaluation(getTrain());
        eval.crossValidateModel(getMlp(), getTrain(), 10, new Random(1));
        return eval;
    }

    public String[] getPrediction() {
        try {
            String[] params = new String[2];
            double aux = 0;
            double[] prediction;
            prediction = getMlp().distributionForInstance(getTest().get(0)); // verificar erro
            for (int i = 0; i < prediction.length; i++) {
                if (i == 0) {
                    aux = prediction[i];
                    params[0] = getTest().classAttribute().value(i);
                    params[1] = getFormated(prediction[i]);
                } else {
                    if (aux < prediction[i]) {
                        params[0] = getTest().classAttribute().value(i);
                        params[1] = getFormated(prediction[i]);
                    }
                }
            }
            return params;
        } catch (Exception ex) {
            Logger.getLogger(MLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
