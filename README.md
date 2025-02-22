# Survey State Machine Design Choices

## 1. State Machine Implementation
- I chose to use an `enum` (`State`) to represent the five states (Created, EIP, Completed, Verified, Deleted) because it allows me to ensure that only valid states are used.
- The `Survey` class includeds a `currentState` parameter and a `questions` list to store the current state and questions.

## 2. State Transitions
- Each method (`addQuestion`, `removeQuestion`, `complete`, `verify`, `delete`) checks the current state before performing any action, ensuring that only valid transitions occur as per the statechart.
- For example, `addQuestion` transitions from `Created` to `EIP` when the first question is added, and it prevents adding more than 5 questions in `EIP`.
- `verify` simulates external and automated reviews (assumed to pass in this implementation) to transition from `Completed` to `Verified` or back to `EIP` if reviews fail.

## 3. Handling Invalid Operations
- Invalid operations (e.g., adding a question in `Verified` or `Deleted` states) are handled by printing an error message and returning `false`. This approach provides clear feedback without throwing exceptions, making the state machine user-friendly for testing.
- In a production environment, I might consider throwing exceptions or using a logging mechanism for more robust error handling.

## 4. Question Limit (0 ≤ N ≤ 5)
- The `MAX_QUESTIONS` constant is set to 5, and the `addQuestion` method enforces this limit. The `removeQuestion` method ensures that the number of questions doesn’t go below 0.

## 5. Test Cases
- The `main` method includes comprehensive test cases that exercise all valid paths in the state machine, including:
  - Starting from `Created`, adding questions to reach `EIP`, completing the survey, and verifying it.
  - Testing invalid operations (e.g., adding questions in `Verified` or `Deleted` states).
  - Deleting a survey at any stage and verifying immutability in `Deleted` state.

## 6. Why This Design?
- This design maximizes readability and maintainability by using an enum for states and clear method names for transitions.
- It adheres strictly to the statechart, ensuring all transitions and constraints (e.g., question limits, immutable `Deleted` state) are enforced.
- The implementation is extensible—if new states or transitions are needed, they can be added to the `State` enum and corresponding methods without major refactoring.