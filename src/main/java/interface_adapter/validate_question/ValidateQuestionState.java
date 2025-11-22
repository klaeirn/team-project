package interface_adapter.validate_question;

import java.util.ArrayList;
import java.util.List;

public class ValidateQuestionState {
    private String validationError = "";
    private boolean valid = false;
    private String title = "";
    private List<String> options = new ArrayList<>();
    private String answer = "";
    private Integer editingIndex = null;

    public String getValidationError() {return validationError;}

    public void setValidationError(String validationError) {this.validationError = validationError;}

    public boolean isValid() {return valid;}

    public void setValid(boolean valid) {this.valid = valid;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public List<String> getOptions() {return options;}

    public void setOptions(List<String> options) {this.options = options;}

    public String getAnswer() {return answer;}

    public void setAnswer(String answer) {this.answer = answer;}

    public Integer getEditingIndex() {return editingIndex;}

    public void setEditingIndex(Integer editingIndex) {this.editingIndex = editingIndex;}
}
