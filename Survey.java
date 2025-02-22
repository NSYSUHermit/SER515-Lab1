import java.util.ArrayList;
import java.util.List;

public class Survey{
    private enum State {
        CREATED, EIP, COMPLETED, VERIFIED, DELETED
    }

    private State currentState;
    private List<String> questions;
    private static int MAX_QUESTION = 5;

    public Survey(){
        this.currentState = State.CREATED;
        this.questions = new ArrayList<>();
    }

    public State getState(){
        return currentState;
    }

    public int getQuestionCount(){
        return questions.size();
    }

    public boolean addQuestion(String question){
        if (currentState == State.DELETED){
            System.out.println("Cannot add question: Survey is deleted.");
            return false;
        }
        if (currentState == State.CREATED){
            questions.add(question);
            currentState = State.EIP;
            System.out.println("Question added.");
            return true;
        }
        if (currentState == State.EIP){
            if(questions.size() < MAX_QUESTION){
                questions.add(question);
                System.out.println("Question added.");
                if (questions.size() == MAX_QUESTION){
                    System.out.println("Max questions reached. Survey Completed.");
                }
                return true;
            }
            else {
                System.out.println("Cannot add questions. Max questions reached.");
                return false;
            }
        }
        System.out.println("Current state: " + currentState + " cannot add question.");
        return false;
    }

    public boolean deleteQuestion(){
        if (currentState == State.DELETED){
            System.out.println("The survey is deleted already.");
            return false;
        }
        if (currentState == State.EIP){
            if (questions.size() > 0){
                questions.remove(questions.size() - 1);
                System.out.println("Question deleted.");

                if (questions.size() == 0) {
                    currentState = State.CREATED;
                    System.out.println("No questions left. State changed to Created.");
                }

                return true;
            }
            else {
                System.out.println("No questions exist.");
                return false;
            }
        }
        System.out.println("Current state: " + currentState + " cannot delete question.");
        return false;
    }

    public boolean completeSurvey(){
        if (currentState == State.DELETED){
            System.out.println("Cannot complete survey since survey is deleted.");
            return false;
        }
        if (currentState == State.EIP){
            currentState = State.COMPLETED;
            System.out.println("Survey completed.");
            return true;
        }
        System.out.println("Current state: " + currentState + " cannot complete survey.");
        return false;
    }

    public boolean verifySurvey() {
        if (currentState == State.DELETED) {
            System.out.println("Cannot verify survey: Survey is deleted.");
            return false;
        }
        if (currentState == State.COMPLETED) {
            if (externalReview() && toolReview()) {
                currentState = State.VERIFIED;
                System.out.println("Survey verified. State changed to Verified.");
                return true;
            } else {
                currentState = State.EIP;
                System.out.println("Review failed. State changed back to EIP.");
                return false;
            }
        }
        System.out.println("Current state: " + currentState + " cannot verify survey.");
        return false;
    }

    public boolean externalReview(){
        System.out.println("External review passed.");
        return true;
    }

    public boolean toolReview(){
        System.out.println("Tool review passed.");
        return true;
    }

    public boolean deleteSurvey() {
        if (currentState != State.DELETED) {
            currentState = State.DELETED;
            System.out.println("Survey marked for deletion. State changed to Deleted.");
            return true;
        }
        System.out.println("Survey is already deleted.");
        return false;
    }

    public static void main(String[] args) {
        Survey survey = new Survey();

        // test case 1: create and add questions
        System.out.println("Test 1: Starting from Created state");
        System.out.println("Current state: " + survey.getState());
        survey.addQuestion("Question 1");
        survey.addQuestion("Question 2");
        System.out.println("Current state: " + survey.getState() + ", Questions: " + survey.getQuestionCount());

        // test case 2: Under EIP add and delete questions
        System.out.println("\nTest 2: Editing in EIP state");
        survey.addQuestion("Question 3");
        survey.addQuestion("Question 4");
        survey.deleteQuestion(); 

        // test case 3: Reach max questions limitation 
        System.out.println("\nTest 3: Editing in EIP state");
        survey.addQuestion("Question 5"); 
        survey.addQuestion("Question 6"); 
        System.out.println("Current state: " + survey.getState() + ", Questions: " + survey.getQuestionCount());

        // test case 4: Comeplete and verify survey
        System.out.println("\nTest 4: Completing and verifying survey");
        survey.completeSurvey();
        survey.verifySurvey();
        System.out.println("Current state: " + survey.getState());

        // test case 5: Attempt action under wrong state
        System.out.println("\nTest 5: Invalid operations in wrong states");
        survey.addQuestion("Invalid Question");
        survey.completeSurvey();

        // delete survey
        survey.deleteSurvey(); 
        survey.addQuestion("Invalid Question");
        survey.completeSurvey();
        survey.verifySurvey();
        System.out.println("Current state: " + survey.getState());

        // test case 6: Delete from Created state
        System.out.println("\nTest 6: Delete from Created state");
        Survey CratedSurvey = new Survey();
        CratedSurvey.deleteSurvey();
        System.out.println("Current state: " + CratedSurvey.getState());

        
        // test case 7: Delete from EIP
        System.out.println("\nTest 7: Delete from EIP");
        Survey EIPSurvey = new Survey();
        EIPSurvey.addQuestion("Question 1");
        EIPSurvey.deleteSurvey();
        System.out.println("Current state: " + EIPSurvey.getState());

        
        // test case 8: Delete from Completed
        System.out.println("\nTest 8: Delete from Completed");
        Survey CompletedSurvey = new Survey();
        CompletedSurvey.addQuestion("Question 1");
        CompletedSurvey.completeSurvey();
        CompletedSurvey.deleteSurvey();
        System.out.println("Current state: " + CompletedSurvey.getState());
    }
}