package org.bigtesting.jbehave.storywriter.tests.ui;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.bigtesting.jbehave.storywriter.ui.ScenarioParameters;
import org.junit.Test;

public class TestScenarioParameters {

    @Test
    public void handleParameters() {

        ScenarioParameters sp = new ScenarioParameters();

        Set<String> params = new HashSet<String>();
        params.add("test");
        params.add("test2");
        sp.handleParameters(params);
        assertThat(sp.getActiveParameters()).containsOnly("test", "test2");
        assertThat(sp.getValues("test")).isEmpty();
        assertThat(sp.getValues("test2")).isEmpty();

        params = new HashSet<String>();
        params.add("test2");
        sp.handleParameters(params);
        assertThat(sp.getActiveParameters()).containsOnly("test2");
        assertThat(sp.getValues("test")).isEmpty();
        assertThat(sp.getValues("test2")).isEmpty();

        sp.addValue("test2", "val2a");
        sp.addValue("test2", "val2b");

        params = new HashSet<String>();
        params.add("test");
        params.add("test2");
        params.add("test3");
        sp.handleParameters(params);
        assertThat(sp.getActiveParameters()).containsOnly("test", "test2","test3");
        assertThat(sp.getValues("test")).isEmpty();
        assertThat(sp.getValues("test2")).containsOnly("val2a", "val2b");
        assertThat(sp.getValues("test3")).isEmpty();

        sp.addValue("test", "val1a");
        sp.addValue("test2", "val2c");
        sp.addValue("test3", "val3a");

        params = new HashSet<String>();
        sp.handleParameters(params);
        assertThat(sp.getActiveParameters()).isEmpty();
        assertThat(sp.getValues("test")).containsOnly("val1a");
        assertThat(sp.getValues("test2")).containsOnly("val2a", "val2b",
                "val2c");
        assertThat(sp.getValues("test3")).containsOnly("val3a");
    }
}
