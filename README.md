# Personal Budget Manager

## Budget Manager with Custom Budget Sections

The application will keep track of budgeting by allowing a user to create a profile and input different sections of 
their budget based on their spending needs and habits. Anybody looking to keep track of their spending can 
use this application. Each person can cater it to their personal lifestyle by adding custom sections 
to their budget (such as food, restaurant, rent, gifts, etc.). Each of these sections of the budget that the
user creates can then be edited to alter the limit (someone decides to allocate more or less money to a certain 
section) or the balance (when the user makes a purchase, or wants to increase their balance for a certain section).

This project interests me because I tend to save money whenever possible and think that it’s important to
stay organized in terms of where money is being spent. By creating an application to keep track of my budget, it will
benefit me by allowing me to keep my budget organized and in a dynamic manner. I tend to keep track of budgeting 
on paper, but when I need to make a change, it’s often not the easiest to do so. By creating this 
application, it makes it easy and intuitive to alter different components of someone's budget. 
By allowing for customizable budget sections, it enables anyone to use the application and cater it to their own needs.

**User Stories:**
- *As a user*, I want to be able to add a budget section to the profile.
- *As a user*, I want to be able to set a limit for a certain budget section.
- *As a user*, I want to be able to subtract money from a balance in a budget section (after spending money).
- *As a user*, I want to be able to add money to the balance of a budget section.
- *As a user*, I want to be able to increase the limit of a budget section.
- *As a user*, I want to be able to decrease the limit of a budget section.
- *As a user*, I want to be able to print out all the budget sections.

**Phase 2 User Stories:**
- *As a user*, I want to be able to be able to save my budget profile to file
- *As a user*, I want to be able to load my budget profile from file

**Instructions for Grader:**
- You can display the current budget sections by clicking "Display Budget Sections"
- You can generate the first required event related to adding Xs to a Y by clicking the "Add Budget Section" button.
- You can generate the second required event related to adding Xs to a Y by clicking the "Edit Budget Section" button,
  entering amount you would like to edit by, and clicking which part of the budget section you would like to edit.
  - There are 4 different events within the "Edit Budget Section" event:
    - Increase limit
    - Decrease limit
    - Increase balance
    - Decrease balance
- You can locate my visual component by running the application, entering a name for the profile, and clicking "OK"
  the visual component is the background of the GUI once the profile has been created).
- You can save the state of my application by clicking the "Save Budget Profile" button.
- You can reload the state of my application by clicking the "Load Budget Profile" button.

**Phase 4: Task 2:**
- Wed Nov 30 23:19:49 PST 2022
Displayed list of budget sections.
- Wed Nov 30 23:19:54 PST 2022
Budget section added to profile.
- Wed Nov 30 23:20:01 PST 2022
Budget section limit increased.
- Wed Nov 30 23:20:02 PST 2022
Budget section limit decreased.
-Wed Nov 30 23:20:03 PST 2022
Money deposited to balance.
- Wed Nov 30 23:20:04 PST 2022
Money withdrawn from balance.