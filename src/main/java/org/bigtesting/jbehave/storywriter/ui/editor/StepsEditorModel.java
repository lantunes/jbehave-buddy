package org.bigtesting.jbehave.storywriter.ui.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bigtesting.jbehave.storywriter.ui.EditorStyle;
import org.bigtesting.jbehave.storywriter.ui.StepsDocument;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;

public class StepsEditorModel {

    private final StepsDocument doc;
    private final Pattern paramPattern;

    private StoryParser parser = new RegexStoryParser();
    private Keywords kw = new Keywords();

    private final List<ParametersListener> parametersListeners = new ArrayList<ParametersListener>();

    public StepsEditorModel(StepsDocument doc) {
        this.doc = doc;
        this.doc.addStylesToDocument();
        // TODO the value between the angle brackets must be a valid variable name (e.g. not an empty string, etc.)
        paramPattern = Pattern.compile("(<).*?(>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    }

    public void addParametersListener(ParametersListener listener) {
        this.parametersListeners.add(listener);
    }

    public String getText() {
        return doc.getEntireTextContent();
    }
   
    public void setSteps(String steps) {
        doc.setText(steps);
    }
    
    public void handleTextEdit() {
        try {

            doc.clearAllTextStyles();
            String text = doc.getEntireTextContent();
            highlightText(text);
            performSemanticAnalysis(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void performSemanticAnalysis(String text) {

        ValidRegions validRegions = getValidRegions(text);
        TextLines lines = new TextLines(text);
        Set<String> parameters = new HashSet<String>();
        for (int i = 0; i < lines.count(); i++) {
            Line line = lines.getLine(i);
            if (lineIsPartOfStep(validRegions, line)) {
                List<String> parametersFoundOnLine = highlightParameters(line);
                parameters.addAll(parametersFoundOnLine);
            }
        }
        for (ParametersListener listener : parametersListeners) {
            listener.handleParameters(parameters);
        }
    }

    private boolean lineIsPartOfStep(ValidRegions validRegions, Line line) {
        if (!validRegions.contains(line.offset())) {
            doc.highlightTerm(line.offset(), line.content().length(), EditorStyle.RED);
            return false;
        }
        return true;
    }

    private List<String> highlightParameters(Line line) {
        List<String> parameters = new ArrayList<String>();
        Matcher m = paramPattern.matcher(line.content());
        while (m.find()) {
            int paramStart = line.offset() + m.start();
            int paramLength = m.group().length();
            doc.highlightTerm(paramStart, paramLength, EditorStyle.BLUE_ITALIC);
            String param = m.group().substring(1, paramLength - 1);
            parameters.add(param);
        }
        return parameters;
    }

    private ValidRegions getValidRegions(String text) {

        Story story = parser.parseStory(text);
        // each step is a String that can be composed of one or more lines
        List<String> steps = new ArrayList<String>();
        if (!story.getScenarios().isEmpty()) {
            steps.addAll(story.getScenarios().get(0).getSteps());
        }
        return new ValidRegions(steps, text);
    }

    private void highlightText(String text) {

        for (String keyword : Keywords.defaultKeywords().values()) {

            int keywordStartIndex = text.indexOf(keyword);
            while (keywordStartIndex > -1) {

                if (keyword.equals(kw.ignorable())) {
                    doc.highlightLine(keywordStartIndex, EditorStyle.LIGHT_GRAY_ITALIC);
                } else {
                    doc.highlightTerm(keywordStartIndex, keyword.length(), EditorStyle.BOLD);
                }

                keywordStartIndex = text.indexOf(keyword, keywordStartIndex + 1);
            }
        }
    }
}
