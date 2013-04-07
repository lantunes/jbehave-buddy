package org.bigtesting.jbehave.buddy.tests.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bigtesting.jbehave.buddy.ui.ExamplesGenerator;
import org.bigtesting.jbehave.buddy.ui.ParametersProvider;
import org.bigtesting.jbehave.buddy.util.StringUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestExamplesGenerator {

    @Test
    public void numExamples_NoParams() {

        Assert.assertEquals(0, generator.numExamples());
    }

    @Test
    public void numExamples_SingleNoValueParam() {

        params.put("param1");
        Assert.assertEquals(1, generator.numExamples());
    }

    @Test
    public void numExamples_SingleThreeValueParam() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param1", "p1Val3");
        Assert.assertEquals(3, generator.numExamples());
    }

    @Test
    public void numExamples_TwoTwoValueAndOneNoValueParam() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");
        params.put("param3");
        Assert.assertEquals(4, generator.numExamples());
    }

    @Test
    public void generateExamples_NoParams() {

        String[][] examples = generator.generateExamples();
        Assert.assertNotNull(examples);
        Assert.assertEquals(0, examples.length);
    }

    @Test
    public void generateExamples_SingleNoValueParam() {

        params.put("param1");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1" }, 
                { "?" } 
        });
    }

    @Test
    public void generateExamples_SingleValueParam() {

        params.put("param1", "p1Val1");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1" }, 
                { "p1Val1" } 
        });
    }

    @Test
    public void generateExamples_SingleTwoValueParam() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1" }, 
                { "p1Val1" }, 
                { "p1Val2" } 
        });
    }

    @Test
    public void generateExamples_SingleThreeValueParam() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param1", "p1Val3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1" }, 
                { "p1Val1" }, 
                { "p1Val2" }, 
                { "p1Val3" } 
        });
    }

    @Test
    public void generateExamples_TwoNoValueParams() {

        params.put("param1");
        params.put("param2");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2" }, 
                { "?",      "?" } 
        });
    }

    @Test
    public void generateExamples_ThreeNoValueParams() {

        params.put("param1");
        params.put("param2");
        params.put("param3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "?",      "?",      "?" } 
        });
    }

    @Test
    public void generateExamples_TwoSingleValueParams() {

        params.put("param1", "p1Val1");
        params.put("param2", "p2Val1");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2" }, 
                { "p1Val1", "p2Val1" } 
        });
    }

    @Test
    public void generateExamples_ThreeSingleValueParams() {

        params.put("param1", "p1Val1");
        params.put("param2", "p2Val1");
        params.put("param3", "p3Val1");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "p2Val1", "p3Val1" } 
        });
    }

    @Test
    public void generateExamples_OneSingleValueAndOneTwoValueParam() {

        params.put("param1", "p1Val1");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2" }, 
                { "p1Val1", "p2Val1" }, 
                { "p1Val1", "p2Val2" } 
        });
    }

    @Test
    public void generateExamples_OneSingleValueAndTwoNoValueParams() {

        params.put("param1", "p1Val1");
        params.put("param2");
        params.put("param3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "?",      "?" } 
        });
    }

    @Test
    public void generateExamples_OneTwoValueAndTwoNoValueParams() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param2");
        params.put("param3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "?",      "?" }, 
                { "p1Val2", "?",      "?" } 
        });
    }

    @Test
    public void generateExamples_TwoSingleValueAndOneTwoValueParam() {

        params.put("param1", "p1Val1");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");
        params.put("param3", "p3Val1");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "p2Val1", "p3Val1" },
                { "p1Val1", "p2Val2", "p3Val1" } 
        });
    }

    @Test
    public void generateExamples_TwoSingleValueAndOneNoValueParam() {

        params.put("param1", "p1Val1");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");
        params.put("param3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "p2Val1", "?" },
                { "p1Val1", "p2Val2", "?" } 
        });
    }

    @Test
    public void generateExamples_TwoTwoValueAndOneNoValueParam() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");
        params.put("param3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "p2Val1", "?" },
                { "p1Val1", "p2Val2", "?" }, 
                { "p1Val2", "p2Val1", "?" }, 
                { "p1Val2", "p2Val2", "?" } 
        });
    }

    @Test
    public void generateExamples_TwoTwoValueParams() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2" }, 
                { "p1Val1", "p2Val1" }, 
                { "p1Val1", "p2Val2" },
                { "p1Val2", "p2Val1" },
                { "p1Val2", "p2Val2" }
        });
    }

    @Test
    public void generateExamples_ThreeTwoValueParams() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");
        params.put("param3", "p3Val1");
        params.put("param3", "p3Val2");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "p2Val1", "p3Val1" },
                { "p1Val1", "p2Val1", "p3Val2" }, 
                { "p1Val1", "p2Val2", "p3Val1" },
                { "p1Val1", "p2Val2", "p3Val2" }, 
                { "p1Val2", "p2Val1", "p3Val1" },
                { "p1Val2", "p2Val1", "p3Val2" }, 
                { "p1Val2", "p2Val2", "p3Val1" },
                { "p1Val2", "p2Val2", "p3Val2" }
         });
    }

    @Test
    public void generateExamples_ThreeThreeValueParams() {

        params.put("param1", "p1Val1");
        params.put("param1", "p1Val2");
        params.put("param1", "p1Val3");
        params.put("param2", "p2Val1");
        params.put("param2", "p2Val2");
        params.put("param2", "p2Val3");
        params.put("param3", "p3Val1");
        params.put("param3", "p3Val2");
        params.put("param3", "p3Val3");

        assertThatExamples().isEqualTo(new String[][] { 
                { "param1", "param2", "param3" }, 
                { "p1Val1", "p2Val1", "p3Val1" },
                { "p1Val1", "p2Val1", "p3Val2" }, 
                { "p1Val1", "p2Val1", "p3Val3" },
                { "p1Val1", "p2Val2", "p3Val1" }, 
                { "p1Val1", "p2Val2", "p3Val2" },
                { "p1Val1", "p2Val2", "p3Val3" }, 
                { "p1Val1", "p2Val3", "p3Val1" },
                { "p1Val1", "p2Val3", "p3Val2" }, 
                { "p1Val1", "p2Val3", "p3Val3" },
                { "p1Val2", "p2Val1", "p3Val1" }, 
                { "p1Val2", "p2Val1", "p3Val2" },
                { "p1Val2", "p2Val1", "p3Val3" }, 
                { "p1Val2", "p2Val2", "p3Val1" },
                { "p1Val2", "p2Val2", "p3Val2" }, 
                { "p1Val2", "p2Val2", "p3Val3" },
                { "p1Val2", "p2Val3", "p3Val1" }, 
                { "p1Val2", "p2Val3", "p3Val2" },
                { "p1Val2", "p2Val3", "p3Val3" }, 
                { "p1Val3", "p2Val1", "p3Val1" },
                { "p1Val3", "p2Val1", "p3Val2" }, 
                { "p1Val3", "p2Val1", "p3Val3" },
                { "p1Val3", "p2Val2", "p3Val1" }, 
                { "p1Val3", "p2Val2", "p3Val2" },
                { "p1Val3", "p2Val2", "p3Val3" }, 
                { "p1Val3", "p2Val3", "p3Val1" },
                { "p1Val3", "p2Val3", "p3Val2" }, 
                { "p1Val3", "p2Val3", "p3Val3" }
        });
    }

    /*--------------------------*/

    private MockParametersProvider params;
    private ExamplesGenerator generator;

    @Before
    public void before() {
        params = new MockParametersProvider();
        generator = new ExamplesGenerator(params);
    }

    private ExamplesAssertion assertThatExamples() {
        return new ExamplesAssertion(generator.generateExamples());
    }

    private static final class ExamplesAssertion {

        private final String[][] actualExamples;

        public ExamplesAssertion(String[][] actualExamples) {
            this.actualExamples = actualExamples;
        }

        public void isEqualTo(String[][] expectedExamples) {
            // check that the number of rows is correct
            Assert.assertEquals(expectedExamples.length, actualExamples.length);

            // check that the content of each row is correct
            for (int i = 0; i < actualExamples.length; i++) {
                String[] actualRow = actualExamples[i];
                String[] expectedRow = expectedExamples[i];
                Assert.assertArrayEquals(expectedRow, actualRow);
            }
        }
    }

    private static final class MockParametersProvider implements ParametersProvider {

        private final Map<String, Set<String>> parameterValues = new HashMap<String, Set<String>>();
        private final Set<String> activeParameters = new HashSet<String>();

        public void put(String param) {
            put(param, null);
        }

        public void put(String param, String value) {
            activeParameters.add(param);
            Set<String> vals = parameterValues.get(param);
            if (vals == null) {
                vals = new HashSet<String>();
                parameterValues.put(param, vals);
            }
            if (value != null) {
                vals.add(value);
            }
        }

        public String[] getActiveParameters() {
            return StringUtil.getSortedStrings(activeParameters);
        }

        public String[] getValues(String parameter) {
            return StringUtil.getSortedStrings(parameterValues.get(parameter));
        }
    }
}
