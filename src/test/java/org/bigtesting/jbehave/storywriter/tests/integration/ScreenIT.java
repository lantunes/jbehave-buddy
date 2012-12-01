package org.bigtesting.jbehave.storywriter.tests.integration;

import static org.jbehave.core.io.CodeLocations.*;

import java.util.List;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.junit.Test;

public class ScreenIT {

    @Test
    public void screen() throws Exception {
        
        Embedder embedder = new ScreenEmbedder();
        List<String> storyPaths = new StoryFinder().findPaths(codeLocationFromPath("src/test/resources"),
                "Screen.story", "");
        embedder.runStoriesAsPaths(storyPaths);
    }
}
