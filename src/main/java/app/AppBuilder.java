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
//import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.quiz_menu.QuizMenuController;
import interface_adapter.quiz_menu.QuizMenuPresenter;

import use_cases.login.LoginInputBoundary;
import use_cases.login.LogInInteractor;
import use_cases.login.LoginOutputBoundary;

import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameInteractor;
import use_cases.change_username.ChangeUsernameOutputBoundary;
import use_cases.quiz_menu.QuizMenuInputBoundary;
import use_cases.quiz_menu.QuizMenuInteractor;

import view.ChangeUsernameView;
import view.LoggedInView;
import view.LoginView;
import view.QuizMenuView;
import view.QuickstartView;
import view.ViewManager;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);


    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;
    private ChangeUsernameView changeUsernameView;
    private ChangeUsernameViewModel changeUsernameViewModel;
    private QuizMenuView quizMenuView;
    private QuickstartView quickstartView;


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
        quizMenuView = new QuizMenuView();
        cardPanel.add(quizMenuView, quizMenuView.getViewName());
        return this;
    }

    public AppBuilder addQuickstartView() {
        quickstartView = new QuickstartView();
        cardPanel.add(quickstartView, quickstartView.getViewName());
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

    public AppBuilder addQuizMenuUseCase() {
        final QuizMenuPresenter quizMenuPresenter = new QuizMenuPresenter(viewManagerModel);
        final QuizMenuInputBoundary quizMenuInteractor = new QuizMenuInteractor(quizMenuPresenter);
        final QuizMenuController quizMenuController = new QuizMenuController(quizMenuInteractor);
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
    
    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.firePropertyChange();

        return application;
    }

}
