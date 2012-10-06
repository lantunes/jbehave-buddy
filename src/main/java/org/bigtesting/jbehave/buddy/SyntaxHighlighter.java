package org.bigtesting.jbehave.buddy;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;

public class SyntaxHighlighter {

	private JFrame frame;
	private StyledDocument doc;
	private StoryParser parser = new RegexStoryParser();
	private Keywords kw = new Keywords();
	private final Pattern paramPattern;
	private StepsTextPane stepsTextPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SyntaxHighlighter window = new SyntaxHighlighter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SyntaxHighlighter() {
		initialize();
		paramPattern = Pattern.compile("(<).*?(>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(textPane);
		frame.getContentPane().add(scrollPane, "cell 0 0,grow");
		stepsTextPane = new StepsTextPane(textPane);
		scrollPane.setRowHeaderView(stepsTextPane);
		doc = textPane.getStyledDocument();
        addStylesToDocument(doc);
        doc.addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent evt) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						handleTextEdit();
					}
				});
			}
			@Override
			public void insertUpdate(DocumentEvent evt) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						handleTextEdit();
					}
				});
			}
			@Override
			public void changedUpdate(DocumentEvent evt) {
			}
		});
	}
	
	private void addStylesToDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
 
        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");
 
        Style bold = doc.addStyle("bold", regular);
        StyleConstants.setBold(bold, true);
        
        Style italic = doc.addStyle("italic", regular);
        StyleConstants.setItalic(italic, true);
        
        Style red = doc.addStyle("red", regular);
        StyleConstants.setForeground(red, Color.red);

        Style blueItalic = doc.addStyle("blue-italic", italic);
        StyleConstants.setForeground(blueItalic, Color.blue);
    }
	
	private void handleTextEdit() {
		try {
			
			//clear all text styles first
			doc.setCharacterAttributes(0, doc.getLength(), doc.getStyle("regular"), true);
			
			//get the entire editor text content
			String text = doc.getText(0, doc.getLength());
			
			//highlight matching text
			for (String keyword : Keywords.defaultKeywords().values()) {
				
				int keywordStartIndex = text.indexOf(keyword);
				while (keywordStartIndex > -1) {
					
					if (keyword.equals(kw.ignorable())) {
						//highlight commented out lines
						int lineEndIndex = text.indexOf("\n", keywordStartIndex);
						int commentLength = ((lineEndIndex != -1) ? lineEndIndex : doc.getLength()) - keywordStartIndex;
						doc.setCharacterAttributes(keywordStartIndex, commentLength, doc.getStyle("italic"), true);
					} else {
						//highlight all other keywords bold
						doc.setCharacterAttributes(keywordStartIndex, keyword.length(), doc.getStyle("bold"), true);
					}
					
					keywordStartIndex = text.indexOf(keyword, keywordStartIndex + 1);
				}
			}
			
			//perform semantic analysis
			Story story = parser.parseStory(text);
			//each step is a String that can be composed of one or more lines
			List<String> steps = new ArrayList<String>();
			if (!story.getScenarios().isEmpty()) {
				steps.addAll(story.getScenarios().get(0).getSteps());
			}
			ValidRegions validRegions = new ValidRegions(steps, text);
			
			TextLines lines = new TextLines(text);
			for (int i = 0; i < lines.count(); i++) {
				
				Line line = lines.getLine(i);
				
				//check if the line is part of a valid step
				if (!validRegions.contains(line.offset())) {
					//underline the erroneous line in red
					doc.setCharacterAttributes(line.offset(), line.content().length(), doc.getStyle("red"), true);
					continue;
				}
				
				//highlight any parameters in blue
				Matcher m = paramPattern.matcher(line.content());
				while (m.find()) {
					int paramStart = line.offset() + m.start();
					int paramLength = m.group().length();
					doc.setCharacterAttributes(paramStart, paramLength, doc.getStyle("blue-italic"), true);
					//notify that a parameter has been entered
					//String param = m.group().substring(1, paramLength-1);
					//System.out.println("param: " + param);
				}
			}
		
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private static class TextLines {
		
		private final List<Line> lines = new ArrayList<Line>();
		
		public TextLines(String text) {
			String[] textLines = text.split("\n");
			int[] lineOffsets = getLineOffsets(textLines, text);
			for (int i = 0; i < textLines.length; i++) {
				lines.add(new Line(textLines[i], lineOffsets[i]));
			}
		}
		
		public int count() {
			return lines.size();
		}
		
		private int[] getLineOffsets(String[] lines, String text) {
			int[] offsets = new int[lines.length];
			int charCount = 0;
			for (int i = 0; i < offsets.length; i++) {
				offsets[i] = charCount;
				charCount += lines[i].length() + 1;
			}
			return offsets;
		}
		
		public Line getLine(int index) {
			return lines.get(index);
		}
	}
	
	private static class Line {
		private final String content;
		private final int offset;
		
		public Line(String content, int offset) {
			this.content = content;
			this.offset = offset;
		}
		
		public String content() {
			return content;
		}
		
		public int offset() {
			return offset;
		}
	}
	
	private static class Region {
		public int startIndex;
		public int endIndex;
		
		public int hashCode() {
			final int prime = 31;
		    int result = 1;
		    result = prime * result + startIndex;
		    result = prime * result + endIndex;
		    return result;
		}
		
		public boolean equals(Object o) {
			if (o == null) return false;
			if (o == this) return true;
			if (!(o instanceof Region)) return false;
			Region that = (Region)o;
			return this.startIndex == that.startIndex && this.endIndex == that.endIndex;
		}
	}
	
	private static class ValidRegions {
		private final List<Region> validRegions = new ArrayList<Region>();
		
		public ValidRegions(List<String> steps, String text) {
			for (String step : steps) {
				int startIndex = text.indexOf(step);
				while (startIndex > -1) {
					Region region = new Region();
					region.startIndex = startIndex;
					region.endIndex = region.startIndex + step.length();
					addValidRegion(region);
					
					startIndex = text.indexOf(step, startIndex + 1);
				}
			}
		}
		
		public void addValidRegion(Region region) {
			if (validRegions.contains(region)) return;
			validRegions.add(region);
		}
		
		public boolean contains(int charIndex) {
			for (Region region : validRegions) {
				if (charIndex >= region.startIndex && charIndex < region.endIndex)
					return true;
			}
			return false;
		}
	}
}
