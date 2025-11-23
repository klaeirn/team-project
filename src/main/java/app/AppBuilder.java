package app;

import javax.swing.*;
import java.awt.*;

import data_access.FileQuizDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.HashtoQuizDataAccessObject;
import data_access.QuizApiDataAccessObject;
import entities.QuestionFactory;
import entities.QuizFactory;
import entities.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_username.ChangeUsernameController;
import interface_adapter.change_username.ChangeUsernamePresenter;
import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.create_quiz.CreateQuizPresenter;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.logged_in.LoggedInController;
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
import interface_adapter.select_existing_quiz.SelectExistingQuizPresenter;
import interface_adapter.select_existing_quiz.SelectExistingQuizViewModel;
import interface_adapter.share_quiz.ShareQuizController;
import interface_adapter.share_quiz.ShareQuizPresenter;
import interface_adapter.share_quiz.ShareQuizViewModel;
import interface_adapter.take_quiz.TakeQuizController;
import interface_adapter.take_quiz.TakeQuizPresenter;
import interface_adapter.take_quiz.TakeQuizViewModel;
import interface_adapter.view_results.ViewResultsController;
import interface_adapter.view_results.ViewResultsPresenter;
import interface_adapter.view_results.ViewResultsViewModel;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardViewModel;
import interface_adapter.validate_question.ValidateQuestionController;
import interface_adapter.validate_question.ValidateQuestionPresenter;
import interface_adapter.validate_question.ValidateQuestionViewModel;

import use_cases.create_quiz.CreateQuizInputBoundary;
import use_cases.create_quiz.CreateQuizInteractor;
import use_cases.create_quiz.CreateQuizOutputBoundary;
import use_cases.create_quiz.CreateQuizOutputData;
import use_cases.login.LoginInputBoundary;
import use_cases.login.LogInInteractor;
import use_cases.login.LoginOutputBoundary;

import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameInteractor;
import use_cases.change_username.ChangeUsernameOutputBoundary;
import use_cases.quickstart.QuickstartInputBoundary;
import use_cases.quickstart.QuickstartInteractor;
import use_cases.share_quiz.ShareQuizDataAccessInterface;
import use_cases.share_quiz.ShareQuizInputBoundary;
import use_cases.share_quiz.ShareQuizInteractor;
import use_cases.take_quiz.TakeQuizInputBoundary;
import use_cases.take_quiz.TakeQuizInteractor;
import use_cases.take_quiz.TakeQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizInputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizInteractor;
import use_cases.view_results.ViewResultsDataAccessInterface;
import use_cases.view_results.ViewResultsInputBoundary;
import use_cases.view_results.ViewResultsInteractor;
import use_cases.view_results.ViewResultsOutputBoundary;
import use_cases.validate_question.ValidateQuestionInputBoundary;
import use_cases.validate_question.ValidateQuestionInteractor;
import use_cases.validate_question.ValidateQuestionOutputBoundary;

import view.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);
    final QuestionFactory questionFactory = new QuestionFactory();
    final QuizFactory quizFactory = new QuizFactory();
    final QuizApiDataAccessObject quizApiDataAccessObject = new QuizApiDataAccessObject(questionFactory, quizFactory);
    final FileQuizDataAccessObject quizFileDataAccessObject = new FileQuizDataAccessObject("quizzes.json");


    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;
    private ChangeUsernameView changeUsernameView;
    private ChangeUsernameViewModel changeUsernameViewModel;
    private QuizMenuView quizMenuView;
    private QuizMenuViewModel quizMenuViewModel;
    private QuickstartView quickstartView;
    private QuickstartViewModel quickStartViewModel;
    private SelectExistingQuizView selectExistingQuizView;
    private SelectExistingQuizViewModel selectExistingQuizViewModel;
    private TakeQuizView takeQuizView;
    private TakeQuizViewModel takeQuizViewModel;
    private TakeQuizController takeQuizController;
    private SelectExistingQuizController selectExistingQuizController;
    private QuickstartPresenter quickstartPresenter;
    private SelectExistingQuizPresenter selectExistingQuizPresenter;
    private ShareQuizController shareQuizController;
    private ShareQuizView shareQuizView;
    private ShareQuizViewModel shareQuizViewModel;
    private ShareQuizPresenter shareQuizPresenter;
    private CreateQuizViewModel createQuizViewModel;
    private CreateQuizView createQuizView;
    private ViewResultsViewModel viewResultsViewModel;
    private ResultsView resultsView;
    private ViewResultsController viewResultsController;
    private LeaderboardViewModel leaderboardViewModel;
    private LeaderboardView leaderboardView;
    private LeaderboardController leaderboardController;
    private ValidateQuestionViewModel validateQuestionViewModel;
    private ValidateQuestionView validateQuestionView;


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

    public AppBuilder addTakeQuizView() {
        takeQuizViewModel = new TakeQuizViewModel();
        takeQuizView = new TakeQuizView(takeQuizViewModel);
        cardPanel.add(takeQuizView, takeQuizView.getViewName());
        return this;
    }

    public AppBuilder addResultsView() {
        viewResultsViewModel = new ViewResultsViewModel();
        resultsView = new ResultsView(viewResultsViewModel);
        cardPanel.add(resultsView, resultsView.getViewName());
        return this;
    }

    public AppBuilder addLeaderboardView() {
        leaderboardViewModel = new LeaderboardViewModel();
        leaderboardView = new LeaderboardView(leaderboardViewModel);
        cardPanel.add(leaderboardView, leaderboardView.getViewName());
        return this;
    }

    public AppBuilder addQuickstartUseCase() {
        quickstartPresenter = new QuickstartPresenter(viewManagerModel, quickStartViewModel);
        final QuickstartInputBoundary interactor = new QuickstartInteractor(
                quickstartPresenter,
                quizApiDataAccessObject,
                userDataAccessObject // implements SelectExistingQuizDataAccessInterface for current username
        );
        final QuickstartController controller = new QuickstartController(interactor);
        if (quickstartView != null) {
            quickstartView.setQuickstartController(controller);
            quickStartViewModel.addPropertyChangeListener(quickstartView);
        }
        return this;
    }

    public AppBuilder addTakeQuizUseCase() {
        final TakeQuizOutputBoundary takeQuizPresenter = new TakeQuizPresenter(takeQuizViewModel, viewManagerModel);
        final TakeQuizInputBoundary takeQuizInteractor = new TakeQuizInteractor(takeQuizPresenter);
        takeQuizController = new TakeQuizController(takeQuizInteractor);

        if (takeQuizView != null) {
            takeQuizView.setTakeQuizController(takeQuizController);
            takeQuizView.setViewManagerModel(viewManagerModel);
            takeQuizViewModel.addPropertyChangeListener(takeQuizView);
        }

        return this;
    }

    public AppBuilder addViewResultsUseCase() {
        final ViewResultsOutputBoundary viewResultsPresenter = new ViewResultsPresenter(viewResultsViewModel, viewManagerModel);
        // TODO Add compatibility with existing quiz DAO (FileQuizDataAccessObject) so that we can view results for existing quizzes too.
        final ViewResultsDataAccessInterface viewResultsDAO = quizApiDataAccessObject;

        final ViewResultsInputBoundary viewResultsInteractor =
                new ViewResultsInteractor(viewResultsPresenter, viewResultsDAO);

        viewResultsController = new ViewResultsController(viewResultsInteractor);

        if (resultsView != null) {
            resultsView.setViewResultsController(viewResultsController);
            resultsView.setViewManagerModel(viewManagerModel);
            viewResultsViewModel.addPropertyChangeListener(resultsView);
        }

        return this;
    }

    public AppBuilder wireControllers() {
        if (quickstartPresenter != null && takeQuizController != null) {
            quickstartPresenter.setTakeQuizController(takeQuizController);
        }

        if (selectExistingQuizPresenter != null && takeQuizController != null) {
            selectExistingQuizPresenter.setTakeQuizController(takeQuizController);
        }
        if (takeQuizView != null && viewResultsController != null) {
            takeQuizView.setViewResultsController(viewResultsController);
        }

        if (leaderboardView != null) {
            leaderboardController = new LeaderboardController(viewManagerModel);
            leaderboardView.setLeaderboardController(leaderboardController);
            leaderboardView.setViewManagerModel(viewManagerModel);
        }

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
        final QuizFactory quizFactory = new QuizFactory();
        final CreateQuizInputBoundary createQuizInteractor = new CreateQuizInteractor(
                quizFileDataAccessObject,
                quizFactory,
                userDataAccessObject,
                createQuizOutputBoundary
                );



        CreateQuizController createQuizController = new CreateQuizController(createQuizInteractor);
//        LoggedInController loggedInController = new LoggedInController();
//        createQuizView.setLoggedInController(loggedInController);
        createQuizView.setCreateQuizController(createQuizController);
        createQuizView.setViewManagerModel(viewManagerModel);
        loggedInView.setCreateQuizController(createQuizController);

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

    public AppBuilder addShareQuizView() {
        shareQuizViewModel = new ShareQuizViewModel();
        shareQuizView = new ShareQuizView(shareQuizViewModel);
        cardPanel.add(shareQuizView, shareQuizView.getViewName());
        return this;
    }

    public AppBuilder addSelectExistingQuizController() {
        // Build Select Existing Quiz use case stack
        selectExistingQuizPresenter = new SelectExistingQuizPresenter(selectExistingQuizViewModel, viewManagerModel);
        final SelectExistingQuizInputBoundary selectInteractor = new SelectExistingQuizInteractor(
                userDataAccessObject,
                selectExistingQuizPresenter);

        ShareQuizPresenter shareQuizPresenter = new ShareQuizPresenter(shareQuizViewModel, viewManagerModel);
        ShareQuizDataAccessInterface dataAccessInterface = new HashtoQuizDataAccessObject();

        ShareQuizInputBoundary shareQuizInteractor = new ShareQuizInteractor(dataAccessInterface, shareQuizPresenter);
        selectExistingQuizController = new SelectExistingQuizController(viewManagerModel, selectInteractor);
        ShareQuizController shareQuizController = new ShareQuizController(shareQuizInteractor);
        if (selectExistingQuizView != null) {
            selectExistingQuizView.setSelectExistingQuizController(selectExistingQuizController);
            selectExistingQuizViewModel.addPropertyChangeListener(selectExistingQuizView);
        }
        if (quizMenuView != null) {
            quizMenuView.setSelectExistingQuizController(selectExistingQuizController);
        }
        if (selectExistingQuizView != null) {
            selectExistingQuizView.setQuizMenuController(new QuizMenuController(viewManagerModel));
            selectExistingQuizView.setShareQuizController(shareQuizController);
        }
        return this;
    }

    public AppBuilder addValidateQuestionView() {
        validateQuestionViewModel = new ValidateQuestionViewModel();
        validateQuestionView = new ValidateQuestionView(validateQuestionViewModel);
        cardPanel.add(validateQuestionView, validateQuestionView.getViewName());
        return this;
    }

    public AppBuilder addValidateQuestionUseCase() {
        final ValidateQuestionOutputBoundary validateQuestionOutputBoundary = 
            new ValidateQuestionPresenter(validateQuestionViewModel, createQuizViewModel, viewManagerModel);
        final ValidateQuestionInputBoundary validateQuestionInteractor = 
            new ValidateQuestionInteractor(null, validateQuestionOutputBoundary);

        ValidateQuestionController validateQuestionController = 
            new ValidateQuestionController(validateQuestionInteractor);
        
        if (validateQuestionView != null) {
            validateQuestionView.setValidateQuestionController(validateQuestionController);
            validateQuestionView.setViewManagerModel(viewManagerModel);
            validateQuestionViewModel.addPropertyChangeListener(validateQuestionView);
        }
        
        if (createQuizView != null) {
            createQuizView.setValidateQuestionViewModel(validateQuestionViewModel);
        }
        
        if (validateQuestionView != null && createQuizViewModel != null) {
            validateQuestionView.setCreateQuizViewModel(createQuizViewModel);
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

