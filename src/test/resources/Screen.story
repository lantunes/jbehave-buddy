Scenario: open the screen for the first time

Given a screen
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is disabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: selecting new story from the file menu enables add scenario button only

Given a screen
When I select new story from the file menu
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is enabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: adding a new scenario enables the controls

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
Then the scenario combo box is enabled
And the scenario combo box has selected item "Test"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled


Scenario: adding a new scenario works only if there is a description

Given a screen
When I select new story from the file menu
And I add a new scenario called ""
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is enabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: adding a new scenario works only if there is a non-empty description

Given a screen
When I select new story from the file menu
And I add a new scenario called " "
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is enabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: adding a new scenario does not work if the user clicks cancel

Given a screen
When I select new story from the file menu
And I decline to add a new scenario
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is enabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: adding a new story prompts the user if another story is open

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I select new story from the file menu
Then I am warned that I am about to lose my work


Scenario: proceeding with a new story when another story is open disables controls

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I select new story from the file menu
And I confirm that I want to create a new story
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is enabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: not proceeding with a new story when another story is open

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I select new story from the file menu
And I decline that I want to create a new story
Then the scenario combo box is enabled
And the scenario combo box has selected item "Test"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled


Scenario: editing a story description

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I edit the scenario description changing it to "Test2"
Then the scenario combo box has selected item "Test2"


Scenario: editing a story description by entering no description

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I edit the scenario description changing it to ""
Then the scenario combo box has selected item "Test"


Scenario: editing a story description by entering blank description

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I edit the scenario description changing it to " "
Then the scenario combo box has selected item "Test"


Scenario: editing a story description by entering an existing description

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I add a new scenario called "Test2"
And I edit the scenario description changing it to "Test"
Then I am warned that the scenario description already exists
And the scenario combo box has selected item "Test2"


Scenario: deleting the only scenario

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I delete the scenario
And I accept that I want to delete the scenario
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is enabled
And the edit scenario button is disabled
And the delete scenario button is disabled


Scenario: declining to delete the only scenario

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I delete the scenario
And I decline that I want to delete the scenario
Then the scenario combo box is enabled
And the scenario combo box has selected item "Test"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled


Scenario: deleting a scenario when more than one exists

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I add a new scenario called "Test2"
And I delete the scenario
And I accept that I want to delete the scenario
Then the scenario combo box is enabled
And the scenario combo box has selected item "Test"
And the scenario combo box does not contain item "Test2"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled


Scenario: opening an existing story prompts the user if another story is open

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I select open existing story from the file menu
Then I am warned that I am about to lose my work


Scenario: proceeding with opening an existing story when another story is open

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I select open existing story from the file menu
And I confirm that I want to open an existing story
Then the scenario combo box is enabled
And the scenario combo box has selected item "Default1"
And the scenario combo box does not contain item "Test"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled

Scenario: proceeding with opening an existing story when another story is not open

Given a screen
When I select open existing story from the file menu
And I select an existing story
Then the scenario combo box is enabled
And the scenario combo box has selected item "Default1"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled


Scenario: not proceeding with opening an existing story when another story is open

Given a screen
When I select new story from the file menu
And I add a new scenario called "Test"
And I select open existing story from the file menu
And I decline that I want to open an existing story
Then the scenario combo box is enabled
And the scenario combo box has selected item "Test"
And the scenario combo box does not contain item "Default1"
And the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is enabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is enabled
And the refresh story button is enabled
And the add scenario button is enabled
And the edit scenario button is enabled
And the delete scenario button is enabled


Scenario: not proceeding with opening an existing story when another story is not open

Given a screen
When I select open existing story from the file menu
And I cancel opening an existing story
Then the add param value button is disabled
And the remove param value button is disabled
And the generate examples button is disabled
And the add example button is disabled
And the remove example button is disabled
And the copy to clipboard button is disabled
And the refresh story button is disabled
And the scenario combo box is disabled
And the add scenario button is disabled
And the edit scenario button is disabled
And the delete scenario button is disabled


