package use_cases.take_shared_quiz;

import entities.Quiz;

public interface TakeSharedQuizDataAccessInterface {

    /**
     * Returns the quiz for the given shared hash.
     *
     * @param hash the shared quiz code
     * @return the Quiz associated with this code
     */

    Quiz getFromHash(String hash);
}
