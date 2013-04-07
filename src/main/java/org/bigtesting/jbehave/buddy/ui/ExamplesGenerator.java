package org.bigtesting.jbehave.buddy.ui;

public class ExamplesGenerator {

    private static final String UNKNOWN_VALUE = "?";

    private final ParametersProvider paramProvider;

    public ExamplesGenerator(ParametersProvider paramProvider) {
        this.paramProvider = paramProvider;
    }

    public String[][] generateExamples() {

        String[] parameters = paramProvider.getActiveParameters();
        if (parameters == null || parameters.length == 0) {
            return new String[0][0];
        }

        int numExamples = numExamples();
        String[][] examples = new String[numExamples + 1][parameters.length];
        examples[0] = parameters;

        new CombinationGenerator().permute(examples, parameters, 0);

        return examples;
    }

    private class CombinationGenerator {

        private int row = 1;

        public void permute(String[][] examples, String[] parameters, int currParam) {

            String[] values = paramProvider.getValues(parameters[currParam]);
            if (values == null || values.length == 0) {
                values = new String[] { UNKNOWN_VALUE };
            }
            for (int i = 0; i < values.length; i++) {

                examples[row][currParam] = values[i];

                if (currParam == parameters.length - 1) {

                    if (row + 1 < examples.length) {
                        for (int r = 0; r < parameters.length - 1; r++) {
                            examples[row + 1][r] = examples[row][r];
                        }
                        row++;
                    }

                } else {
                    permute(examples, parameters, currParam + 1);
                }
            }
        }
    }

    public int numExamples() {

        String[] parameters = paramProvider.getActiveParameters();
        if (parameters == null || parameters.length == 0) {
            return 0;
        }

        int rowsNeeded = 1;
        for (int i = 0; i < parameters.length; i++) {
            String[] values = paramProvider.getValues(parameters[i]);
            if (values == null || values.length == 0) {
                continue;
            }
            rowsNeeded *= values.length;
        }
        return rowsNeeded;
    }
}
