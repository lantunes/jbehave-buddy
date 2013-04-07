Scenario:  Default1
 
Given step represents a precondition to an event
When step represents the occurrence of the event
Then step represents the outcome of the event
 
Scenario:  Default2
 
Given a <precondition>
When a negative event occurs
Then a the outcome should <be-captured>   
 
Examples: 
precondition |be-captured     |
abc          |be captured     |
xyz          |not be captured |