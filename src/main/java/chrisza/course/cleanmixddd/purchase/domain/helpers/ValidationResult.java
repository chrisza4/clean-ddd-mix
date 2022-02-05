package chrisza.course.cleanmixddd.purchase.domain.helpers;

import java.util.ArrayList;

public class ValidationResult {
    private final ArrayList<String> errorMessages;

    public ValidationResult(ArrayList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public boolean isValid() {
        return this.errorMessages == null || this.errorMessages.size() == 0;
    }

    public ArrayList<String> getErrorMessages() {
        return this.errorMessages;
    }
}
