package app;

import javax.swing.*;
import java.awt.*;

import data_access.FileUserDataAccessObject;
import entities.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_username.ChangeUsernameController;
import interface_adapter.change_username.ChangeUsernamePresenter;
import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.quickstart.QuickstartViewModel;
import interface_adapter.quiz_menu.QuizMenuController;
import interface_adapter.quickstart.QuickstartController;
import interface_adapter.quickstart.QuickstartPresenter;
import interface_adapter.quiz_menu.QuizMenuViewModel;
import interface_adapter.select_existing_quiz.SelectExistingQuizController;
import interface_adapter.select_existing_quiz.SelectExistingQuizViewModel;

import use_cases.login.LoginInputBoundary;
import use_cases.login.LogInInteractor;
import use_cases.login.LoginOutputBoundary;

import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameInteractor;
import use_cases.change_username.ChangeUsernameOutputBoundary;
import use_cases.quickstart.QuickstartInputBoundary;
import use_cases.quickstart.QuickstartInteractor;

import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.create_quiz.CreateQuizPresenter;
import use_cases.create_quiz.CreateQuizInputBoundary;
import use_cases.create_quiz.CreateQuizOutputBoundary;
import use_cases.create_quiz.CreateQuizInteractor;
import entities.QuizFactory;

import view.ChangeUsernameView;
import view.LoggedInView;
import view.LoginView;
import view.QuizMenuView;
import view.QuickstartView;
import view.SelectExistingQuizView;
import view.ViewManager;
import view.CreateQuizView;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);
    final QuizFactory quizFactory = new QuizFactory();


    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;
    private ChangeUsernameView changeUsernameView;
    private ChangeUsernameViewModel changeUsernameViewModel;
    
    private CreateQuizView createQuizView;
    private CreateQuizViewModel createQuizViewModel;
    private CreateQuizController createQuizController;
    private QuizMenuView quizMenuView;
    private QuizMenuViewModel quizMenuViewModel;
    private QuickstartView quickstartView;
    private QuickstartViewModel quickStartViewModel;
    private SelectExistingQuizView selectExistingQuizView;
    private SelectExistingQuizViewModel selectExistingQuizViewModel;



    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LogInInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addQuizMenuView() {
        quizMenuViewModel = new QuizMenuViewModel();
        quizMenuView = new QuizMenuView(quizMenuViewModel);
        cardPanel.add(quizMenuView, quizMenuView.getViewName());
        return this;
    }

    public AppBuilder addQuickstartView() {
        quickStartViewModel = new QuickstartViewModel();
        quickstartView = new QuickstartView(quickStartViewModel);
        cardPanel.add(quickstartView, quickstartView.getViewName());
        return this;
    }

    public AppBuilder addSelectExistingQuizView() {
        selectExistingQuizViewModel = new SelectExistingQuizViewModel();
        selectExistingQuizView = new SelectExistingQuizView(selectExistingQuizViewModel);
        cardPanel.add(selectExistingQuizView, selectExistingQuizView.getViewName());
        return this;
    }

    public AppBuilder addQuickstartUseCase() {
        final QuickstartPresenter presenter = new QuickstartPresenter(viewManagerModel);
        final QuickstartInputBoundary interactor = new QuickstartInteractor(presenter);
        final QuickstartController controller = new QuickstartController(interactor);
        if (quickstartView != null) {
            quickstartView.setQuickstartController(controller);
        }
        return this;
    }

    public AppBuilder addChangeUsernameView() {
        changeUsernameViewModel = new ChangeUsernameViewModel();
        changeUsernameView = new ChangeUsernameView(changeUsernameViewModel);
        cardPanel.add(changeUsernameView, changeUsernameView.getViewName());
        return this;
    }

    public AppBuilder addChangeUsernameUseCase() {
        final ChangeUsernameOutputBoundary changeUsernameOutputBoundary = new ChangeUsernamePresenter(changeUsernameViewModel, viewManagerModel);
        final ChangeUsernameInputBoundary changeUsernameInteractor = new ChangeUsernameInteractor(
                userDataAccessObject, changeUsernameOutputBoundary);

        ChangeUsernameController changeUsernameController = new ChangeUsernameController(changeUsernameInteractor);
        changeUsernameView.setChangeUsernameController(changeUsernameController);
        loggedInView.setChangeUsernameController(changeUsernameController);

        return this;
    }
    
    public AppBuilder addCreateQuizView() {
        createQuizViewModel = new CreateQuizViewModel();
        createQuizView = new CreateQuizView(createQuizViewModel);
        cardPanel.add(createQuizView, createQuizView.getViewName());
        return this;
    }
    
    public AppBuilder addCreateQuizUseCase() {
        final CreateQuizOutputBoundary createQuizOutputBoundary = new CreateQuizPresenter(createQuizViewModel, viewManagerModel);
        final CreateQuizInputBoundary createQuizInteractor = new CreateQuizInteractor(
                userDataAccessObject, quizFactory, userDataAccessObject, createQuizOutputBoundary);
        
        createQuizController = new CreateQuizController(createQuizInteractor);
        createQuizView.setCreateQuizController(createQuizController);
        
        return this;
    }
    
    

    public AppBuilder addQuizMenuController() {
        final QuizMenuController quizMenuController = new QuizMenuController(viewManagerModel);
        if (loggedInView != null) {
            loggedInView.setQuizMenuController(quizMenuController);
        }
        if (quizMenuView != null) {
            quizMenuView.setQuizMenuController(quizMenuController);
        }
        if (quickstartView != null) {
            quickstartView.setQuizMenuController(quizMenuController);
        }
        return this;
    }

    public AppBuilder addSelectExistingQuizController() {
        final SelectExistingQuizController selectExistingQuizController = new SelectExistingQuizController(viewManagerModel);
        if (selectExistingQuizView != null) {
            selectExistingQuizView.setSelectExistingQuizController(selectExistingQuizController);
        }
        if (quizMenuView != null) {
            quizMenuView.setSelectExistingQuizController(selectExistingQuizController);
        }
        if (selectExistingQuizView != null) {
            selectExistingQuizView.setQuizMenuController(new QuizMenuController(viewManagerModel));
        }
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.firePropertyChange();

        return application;
    }
    

}
