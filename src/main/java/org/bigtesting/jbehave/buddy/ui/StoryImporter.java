package org.bigtesting.jbehave.buddy.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;

public class StoryImporter {

    public StoryModel importStory(File storyFile, final IScreen screen) throws Exception {
        
        StoryModel storyModel = new StoryModel();
        StoryParser parser = new RegexStoryParser();
        String storyContents = readFile(storyFile);
        if (storyContents != null && storyContents.trim().length() > 0) {
            Story story = parser.parseStory(storyContents);
            addScenarios(screen, storyModel, story);
        }
        return storyModel;
    }

    private void addScenarios(final IScreen screen, StoryModel storyModel, Story story) {
        
        for (Scenario scenario : story.getScenarios()) {
            ScenarioModel scenarioModel = new ScenarioModel(scenario.getTitle(), screen);
            addSteps(scenario, scenarioModel);
            addExamplesAndParamValues(scenario, scenarioModel);
            storyModel.addExistingScenario(scenarioModel);
        }
    }

    private void addExamplesAndParamValues(Scenario scenario, ScenarioModel scenarioModel) {
        
        ExamplesTable examplesTable = scenario.getExamplesTable();
        if (examplesTable != null && examplesTable.getRowCount() > 0) {
            String[][] examples = new String[examplesTable.getRowCount() + 1][examplesTable.getHeaders().size()];
            Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();                
            for (int i = 0; i < examplesTable.getHeaders().size(); i++) {
                String header = examplesTable.getHeaders().get(i);
                headerIndexMap.put(header, i);
                examples[0][i] = header;
            }
            for (int i = 0; i < examplesTable.getRowCount(); i++) {
                Map<String, String> row = examplesTable.getRow(i);
                for (String header : row.keySet()) {
                    String cellValue = row.get(header);
                    examples[i+1][headerIndexMap.get(header)] = cellValue;
                    scenarioModel.addParamValue(header, cellValue);
                }
            }
            scenarioModel.setExamples(examples);
        }
    }

    private void addSteps(Scenario scenario, ScenarioModel scenarioModel) {
        
        StringBuilder steps = new StringBuilder();
        for (Iterator<String> itr = scenario.getSteps().iterator(); itr.hasNext();) {
            String step = itr.next();
            steps.append(step);
            if (itr.hasNext()) {
                steps.append("\n");
            }
        }
        scenarioModel.setSteps(steps.toString());
    }
    
    private String readFile(File file) throws IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String separator = System.getProperty("line.separator");

        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append(separator);
        }

        return stringBuilder.toString();
    }
}
