# Survey State Machine Design

## 1. State Machine Implementation
- I use `enum` (`State`) to represent the five states (Created, EIP, Completed, Verified, Deleted) because it allows me to ensure that only valid states are used.
- The `Survey` class includeds a `currentState` parameter and a `questions` list to store the current state and questions.

## 2. State contorl 
- Each method (`addQuestion`, `removeQuestion`, `complete`, `verify`, `delete`) checks the current state before actions.
- For example, `addQuestion` transitions from `Created` to `EIP` when the first question is added, and it prevents adding more than 5 questions in `EIP`.
- Use two temporary functions externalReview() and toolReview() for `verify` simulating transition from `Completed` to `Verified` or back to `EIP` if reviews fail.

## 3. Error Handling
- Invalid operations (e.g., adding a question in `Verified` or `Deleted` states) are handled by printing an error message and returning `false`. This approach provides clear feedback without throwing exceptions, making the state machine user-friendly for testing.
- In a production environment, I might consider throwing exceptions or using a logging mechanism for more robust error handling.

## 4. Question Limit (0 ≤ N ≤ 5)
- I set `MAX_QUESTIONS` constant as 5, and in `addQuestion` method make sure this limit, in `removeQuestion` make sure doesn’t go below 0.

## 5. Test Cases
- The `main` method includes all  test cases that each path through the state machine, including events that are not allowed or responded to in a given state.
