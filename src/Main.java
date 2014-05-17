public class Main {

    public static void main(String[] args) {
        WekaSimpleTransformer simpleTransformer = new WekaSimpleTransformer();
        simpleTransformer.readARFFFile( "filepath");
        simpleTransformer.removeInstances();
        simpleTransformer.removeAttribute();
        simpleTransformer.printData();
        simpleTransformer.saveData("filepath");
    }
}
