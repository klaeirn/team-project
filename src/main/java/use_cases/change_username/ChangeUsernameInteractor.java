package use_cases.change_username;


public class ChangeUsernameInteractor implements ChangeUsernameInputBoundary{

    private final ChangeUsernameDataAccessInterface dataAccessInterface;
    private final ChangeUsernameOutputBoundary changeUsernamePresenter;


    public ChangeUsernameInteractor(ChangeUsernameDataAccessInterface dataAccessInterface, 
    ChangeUsernameOutputBoundary changeUsernamePresenter) {

        this.dataAccessInterface = dataAccessInterface;
        this.changeUsernamePresenter = changeUsernamePresenter;
    }
    

    public void execute(ChangeUsernameInputData inputData) {

        try {

            this.dataAccessInterface.replace(inputData.getNewUsername());
            final ChangeUsernameOutputData outputData = new ChangeUsernameOutputData(inputData.getNewUsername());
            this.changeUsernamePresenter.prepareSuccessView(outputData);

        } catch(RuntimeException e) {
            
            changeUsernamePresenter.prepareFailView("Username" + inputData.getNewUsername() +  "already exists");
        }

    }

    public void switchToChangeUsernameView() {changeUsernamePresenter.switchToChangeUsernameView(); }
}
