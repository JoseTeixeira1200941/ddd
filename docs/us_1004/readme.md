# US1004 - Open and Close Courses

## 1. Requirements Engineering


### 1.1. User Story Description


As Manager, I want to open and close courses.

### 1.2. Customer Specifications and Clarifications


**From the specifications document:**

> - FRC03 - Open/Close Course Only managers are able to execute this functionality.
> - "(...) A course may be open or closed. A
    closed course can not have any activity (...)".


**From the client clarifications:**

> **Question:** "To access the user info - scheduled vaccine type and vaccination history -, should the nurse enter user's SNS number?"
>
> **Answer:** The nurse should select a SNS user from a list of users that are in the center to take the vaccine.

-

> **Question:** "Supposing that the SNS user has already received a dose of a given vaccine type (for example, COVID-19), the user can only receive the same vaccine or a different one with the same vaccine type?"
>
> **Answer:** The SNS user can only receive the same vaccine.
>
> Related information:
>
> - A SNS user is fully vaccinated when he receives all doses of a given vaccine.
> - A SNS user that has received a single-dose vaccine is considered fully vaccinated and will not take more doses.
> - A SNS user that is fully vaccinated will not be able to schedule a new vaccine of the type for which he is already fully vaccinated.


> **Question:** "In US 08 says: "At the end of the recovery period, the user should receive a SMS message informing the SNS user that he can leave the vaccination center." How should the SNS user receive and have access to the SMS message?"
>
> **Answer:** "A file named SMS.txt should be used to receive/record the SMS messages. We will not use a real word service to send SMSs."


### 1.3. Acceptance Criteria

- n/a


### 1.4. Found out Dependencies


* "US1002: As Manager, I want to create courses."



### 1.5 Input and Output Data


**Input Data:**

* Typed data:
    * Number of the Course on the list

* Selected data:
    * Course


**Output Data:**

* UI
    * Course closed or open by the manager
* File
    * n/a

### 1.6. System Sequence Diagram (SSD)


![US8_SSD](US8_SSD.svg)

### 1.7 Other Relevant Remarks

* n/a


## 2. OO Analysis

### 2.1. Relevant Domain Model Excerpt

![US8_MD](US8_MD.svg)

### 2.2. Other Remarks

n/a


## 3. Design - User Story Realization

### 3.1. Rationale


| Interaction ID | Question: Which class is responsible for... | Answer  | Justification (with patterns)  |
|:-------------  |:--------------------- |:------------|:---------------------------- |
| Step 1 |	... interacting with the actor? | VaccineAdministrationUI  |  Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.           |
| | ...coordinating the US? | VaccineAdministrationController | Controller
| | ...serving as an intermediary between the UI layer and the Domain layer? | VaccineAdministrationController | Controller: direct communication between UI classes and domain classes must be avoided.
| | ...storing the centers? | CenterStore | Pure Fabrication: in order to promote reuse and to attend High Cohesion and Low Coupling patterns, the CenterStore exists to be responsible for saving and returning the centers.
| | ...returning the center store? | Company | IE: owns/knows all stores
| | ...returning the nurse's center? | CenterStore | IE: owns/knows all centers
| | ...returning the center's waiting list? | Center | IE: owns/knows it's own data
| | ...storing the SNS Users? | SNSUserStore | IE: Pure Fabrication: in order to promote reuse and to attend High Cohesion and Low Coupling patterns, the SNSUserStore exists to be responsible for saving and returning the SNS Users.
| | ...returning the SNSUser's SNS number? | SNSUser | IE: owns/knows it's own data
| | ...storing the vaccination schedules? | VaccinationScheduleStore | Pure Fabrication: in order to promote reuse and to attend High Cohesion and Low Coupling patterns, the VaccinationScheduleStore exists to be responsible for saving and returning the vaccination schedules.
| | ...returning the user's vaccination schedule? | VaccinationScheduleStore | IE: knows/owns all vaccination schedules
| | ...returning the user's vaccine type for the schedule? | VaccinationSchedule | IE: knows/owns it's own data
| | ...storing the vaccine administrations? | VaccineAdministrationStore | Pure Fabrication: in order to promote reuse and to attend High Cohesion and Low Coupling patterns, the VaccineAdministrationStore exists to be responsible for saving and returning the vaccine administrations.
| | ...returning the vaccine administration store? | VaccineAdministrationStore | IE: knows/owns it's own data
| | ...returning the sns user to compare? | VaccineAdministration | IE: knows/owns it's own data
| | ...returning the vaccine to compare? | VaccineAdministration | IE: knows/owns it's own data
| | ...returning the vaccine type ID to compare? | Vaccine | IE: knows/owns it's own data
| | ...returning the vaccine type ID to compare? | VaccineType | IE: knows/owns it's own data
| | ...returning the vaccine type name? | VaccineType | IE: knows/owns it's own data
| | ...returning list of available vaccines? | VaccineStore | IE: knows/owns all vaccines
| | ...mapping available vaccines into a list of vaccine DTOs? | VaccineMapper | PureFabrication: in order to promote reuse and to attend High Cohesion and Low Coupling patterns, the VaccineMapper exists in order to create VaccineDTO's and returning a list of them
| | ...instantiating a new DTO that represents a vaccine list? | VaccineMapper | Creator (Rule 3): VaccineMapper closely uses VaccineListDTO
| | ...returning vaccine information? | Vaccine | IE: knows/owns it's own data
| | ...instantiating a new DTO that represents a vaccine? | VaccineMapper | Creator (Rule 4): VaccineMapper has the data to initialize VaccineDTO.
| | ...returning vaccine list? | VaccineMapper | IE: knows/owns it's own data
| | ...instantiating a new VaccineList? | VaccineAdministrationController | Creator (Rule 4): Controller has the data to initialize VaccineList.
| Step 2 | ...showing a list of available vaccines for the scheduled vaccine type? | VaccineAdministrationUI | UI: responsible for user interaction
| Step 3 | ...selecting the vaccine? | N/A
| Step 4 | ...asking for confirmation? | VaccineAdministrationUI | UI: responsible for user interaction
| Step 5 | ...confirming the operation? | N/A
| | ...getting a vaccine by it's ID? | VaccineStore | IE: knows/owns all vaccines
| | ...instantiating a vaccine administration? | VaccineAdministrationStore | Creator (Rules 1, 2 and 4)
| | ...returning the number of dose? | VaccineAdministration | IE: knows/owns it's own data
| | ...storing user in the waiting list | SNSUserList | Pure Fabrication: in order to promote reuse and to attend High Cohesion and Low Coupling patterns, the SNSUserList exists to be responsible for saving and returning a list of SNSUsers
| | ...removing user from waiting list? | SNSUserList | IE: knows/owns it's own data
| | ...returning recovery room? | Center | IE: knows/owns it's own data
| | ...adding a user to the recovery room? | SNSUserList | IE: knows/owns some SNSUsers
| | ...creating a TimerTask? | VaccineAdministrationController | Creator (Rules 3 and 4)
| | ...creating a Timer? | VaccineAdministrationController | Creator (Rule 3): Controller works closely with Timer
| | ...returning message delay time? | Company | IE: knows data from configuration file
| | ...scheduling a timer? | Timer |
| | ...sending SMS? | SendSMS | PureFabrication:
| | ...removing snsUser from recovery room | SNSUserList | IE: knows/owns it's own data
| Step 6 | informing (in)success of the operation | VaccineAdministrationUI | UI: responsible for user interaction


### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Company
* Center
* VaccineAdministration
* SNSUser
* VaccinationSchedule
* VaccineType
* VaccineStore
* Vaccine

Other software classes (i.e. Pure Fabrication) identified:

* VaccineAdministrationUI
* VaccineAdministrationController
* CenterStore
* SNSUserList
* VaccineAdministrationStore
* VaccinationScheduleStore
* VaccineStore
* VaccineMapper
* VaccineList
* VaccineListDTO
* VaccineDTO
* SendSMS


## 3.2. Sequence Diagram (SD)

![US8_SD](US8_SD.svg)

## 3.3. Class Diagram (CD)

![US8_CD](US8_CD.svg)
