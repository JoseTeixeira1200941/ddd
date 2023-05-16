# OO Analysis #

The construction process of the domain model is based on the client specifications, especially the nouns (for _concepts_) and verbs (for _relations_) used. 

## Rationale to identify domain conceptual classes ##
To identify domain conceptual classes, start by making a list of candidate conceptual classes inspired by the list of categories suggested in the book "Applying UML and Patterns: An Introduction to Object-Oriented Analysis and Design and Iterative Development". 


### _Aggregates and Conceptual Classes and their Value Objects_ ###

#### Board Aggregate
- Shared Board
- Author
- User List
- Title
- Row Title
- Row
- Column Title
- Column
- Board History
- Post-It
- Timestamp
#### User Aggregate
- User
- Email
- Password
- Short Name
- Full Name
#### Meeting Aggregate
- Meeting
- Duration
- Date
- Meeting Access List
- Invite
#### Question Type Aggregate
- Question Type
#### Student Aggregate
- Student
- Tax Payer Number
- Birth Date
- Name
- Mecanographic Number
#### Manager Aggregate
- Manager
#### Class Aggregate
- Class
- Title
- Date
- Duration
- Extra Class Access List
- Extra Class
- Day of the Week*-
#### Course Aggregate
- Course
- Name
- Capacity
- Description
- Title
- Code
- State
- Student Enrollment
- Status
#### Evaluation Aggregate
- Evaluation
- Student Answer
- Exam Taken
#### Exam Aggregate
- Exam
- Date
- Title
- Correct Answer
- Description
- Sequence of Sections
- Question
#### Teacher Aggregate
- Teacher
- Acronym
- Birth Date
- Tax Payer Number
- Name




## Domain Model

![DM.svg](Domain_Model_G45.svg)



