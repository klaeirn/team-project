package app;

import javax.swing.*;
import java.awt.*;

import data_access.FileLeaderboardDataAccessObject;
import data_access.FileQuizDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.HashtoQuizDataAccessObject;
import data_access.QuizApiDataAccessObject;
import data_access.ReadHashToQuizDataAccessObject;
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
import interface_adapter.preview_quiz.PreviewQuizViewModel;
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
import interface_adapter.view_leaderboard.ViewLeaderboardController;
import interface_adapter.view_leaderboard.ViewLeaderboardPresenter;
import interface_adapter.view_leaderboard.ViewLeaderboardViewModel;
import interface_adapter.validate_question.ValidateQuestionController;
import interface_adapter.validate_question.ValidateQuestionPresenter;
import interface_adapter.validate_question.ValidateQuestionViewModel;
import interface_adapter.take_shared_quiz.TakeSharedQuizController;
import interface_adapter.take_shared_quiz.TakeSharedQuizPresenter;
import interface_adapter.take_shared_quiz.TakeSharedQuizViewModel;
import interface_adapter.preview_quiz.PreviewQuizViewModel;
import interface_adapter.preview_quiz.PreviewQuizPresenter;
import interface_adapter.preview_quiz.PreviewQuizController;

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
import use_cases.preview_quiz.PreviewQuizInputBoundary;
import use_cases.preview_quiz.PreviewQuizInteractor;
import use_cases.preview_quiz.PreviewQuizOutputBoundary;
import use_cases.quickstart.QuickstartInputBoundary;
import use_cases.quickstart.QuickstartInteractor;
import use_cases.share_quiz.ShareQuizDataAccessInterface;
import use_cases.share_quiz.ShareQuizInputBoundary;
import use_cases.share_quiz.ShareQuizInteractor;
import use_cases.take_quiz.TakeQuizInputBoundary;
import use_cases.take_quiz.TakeQuizInteractor;
import use_cases.take_quiz.TakeQuizOutputBoundary;
import use_cases.take_shared_quiz.TakeSharedQuizInputBoundary;
import use_cases.take_shared_quiz.TakeSharedQuizInteractor;
import use_cases.take_shared_quiz.TakeSharedQuizDataAccessInterface;
import use_cases.take_shared_quiz.TakeSharedQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizInputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizInteractor;
import use_cases.view_results.ViewResultsInputBoundary;
import use_cases.view_results.ViewResultsInteractor;
import use_cases.view_results.ViewResultsOutputBoundary;
import use_cases.view_leaderboard.LeaderboardDataAccessInterface;
import use_cases.view_leaderboard.ViewLeaderboardInputBoundary;
import use_cases.view_leaderboard.ViewLeaderboardInteractor;
import use_cases.view_leaderboard.ViewLeaderboardOutputBoundary;
import use_cases.validate_question.ValidateQuestionInputBoundary;
import use_cases.validate_question.ValidateQuestionInteractor;
import use_cases.validate_question.ValidateQuestionOutputBoundary;

import view.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout,
            viewManagerModel);

    final FileUserDataAccessObject userDataAccessObject =
            new FileUserDataAccessObject("users.csv", userFactory);
    final QuestionFactory questionFactory = new QuestionFactory();
    final QuizFactory quizFactory = new QuizFactory();
    final QuizApiDataAccessObject quizApiDataAccessObject =
            new QuizApiDataAccessObject(questionFactory, quizFactory);
    final FileQuizDataAccessObject quizFileDataAccessObject =
            new FileQuizDataAccessObject("quizzes.json");
    final FileLeaderboardDataAccessObject leaderboardDataAccessObject = new FileLeaderboardDataAccessObject("leaderboards.json");


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
    private TakeQuizInputBoundary takeQuizInteractor;
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
    private ViewLeaderboardViewModel viewLeaderboardViewModel;
    private LeaderboardView leaderboardView;
    private LeaderboardController leaderboardController;
    private ViewLeaderboardController viewLeaderboardController;
    private ValidateQuestionViewModel validateQuestionViewModel;
    private ValidateQuestionView validateQuestionView;
    private TakeSharedQuizView takeSharedQuizView;
    private TakeSharedQuizPresenter takeSharedQuizPresenter;
    private TakeSharedQuizViewModel takeSharedQuizViewModel;
    private TakeSharedQuizController takeSharedQuizController;
    private PreviewQuizView previewQuizView;
    private PreviewQuizViewModel previewQuizViewModel;


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
        final LoginOutputBoundary loginOutputBoundary =
                new LoginPresenter(viewManagerModel, loggedInViewModel,
                        loginViewModel);
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

    public AppBuilder addTakeSharedQuizView() {
        takeSharedQuizViewModel = new TakeSharedQuizViewModel();
        takeSharedQuizView = new TakeSharedQuizView(takeSharedQuizViewModel,
                loggedInViewModel);
        cardPanel.add(takeSharedQuizView, takeSharedQuizView.getViewName());
        return this;
    }

    public AppBuilder addResultsView() {
        viewResultsViewModel = new ViewResultsViewModel();
        resultsView = new ResultsView(viewResultsViewModel);
        cardPanel.add(resultsView, resultsView.getViewName());
        return this;
    }

    public AppBuilder addLeaderboardView() {
        viewLeaderboardViewModel = new ViewLeaderboardViewModel();
        leaderboardView = new LeaderboardView(viewLeaderboardViewModel);
        cardPanel.add(leaderboardView, leaderboardView.getViewName());
        return this;
    }

    public AppBuilder addQuickstartUseCase() {
        quickstartPresenter = new QuickstartPresenter(viewManagerModel, quickStartViewModel);
        final QuickstartInputBoundary interactor = new QuickstartInteractor(
                quickstartPresenter,
                quizApiDataAccessObject,
                userDataAccessObject
        );
        final QuickstartController controller = new QuickstartController(interactor);
        if (quickstartView != null) {
            quickstartView.setQuickstartController(controller);
            quickStartViewModel.addPropertyChangeListener(quickstartView);
        }
        if (quickstartView != null && takeQuizController != null) {
            quickstartView.setTakeQuizController(takeQuizController);
        }
        return this;
    }

    public AppBuilder addTakeQuizUseCase() {
        final TakeQuizOutputBoundary takeQuizPresenter = new TakeQuizPresenter(takeQuizViewModel, viewManagerModel);
        takeQuizInteractor = new TakeQuizInteractor(takeQuizPresenter);
        takeQuizController = new TakeQuizController(takeQuizInteractor);

        if (takeQuizView != null) {
            takeQuizView.setTakeQuizController(takeQuizController);
            takeQuizView.setViewManagerModel(viewManagerModel);
            takeQuizViewModel.addPropertyChangeListener(takeQuizView);
        }

        if (takeQuizView != null && viewResultsController != null) {
            takeQuizView.setViewResultsController(viewResultsController);
        }

        return this;
    }

    public AppBuilder addTakeSharedQuizUseCase() {
        takeSharedQuizPresenter = new TakeSharedQuizPresenter(takeSharedQuizViewModel, viewManagerModel);
        final TakeSharedQuizDataAccessInterface dataAccess = new HashtoQuizDataAccessObject("hashtoquiz.json");
        //final TakeSharedQuizDataAccessInterface dataAccess = new HashtoQuizDataAccessObject();

        final TakeSharedQuizInputBoundary interactor = new TakeSharedQuizInteractor(dataAccess, takeSharedQuizPresenter);
        takeSharedQuizController = new TakeSharedQuizController(interactor, viewManagerModel);

        if (takeSharedQuizView != null) {
            takeSharedQuizView.setController(takeSharedQuizController);
            takeSharedQuizView.setViewManagerModel(viewManagerModel);
            takeSharedQuizViewModel.addPropertyChangeListener(takeSharedQuizView);
        }
//        if (loggedInView != null) {
//            loggedInView.setTakeSharedQuizController(takeSharedQuizController);
//        }
        if (quizMenuView != null) {
            quizMenuView.setTakeSharedQuizController(takeSharedQuizController);
        }

        return this;
    }

    public AppBuilder addViewResultsUseCase() {
        final ViewResultsOutputBoundary viewResultsPresenter = new ViewResultsPresenter(viewResultsViewModel, viewManagerModel);
        // Quiz object is passed directly from TakeQuiz - no data access needed here

        final ViewResultsInputBoundary viewResultsInteractor =
                new ViewResultsInteractor(viewResultsPresenter, leaderboardDataAccessObject);

        viewResultsController = new ViewResultsController(viewResultsInteractor);

        if (resultsView != null) {
            resultsView.setViewResultsController(viewResultsController);
            resultsView.setViewManagerModel(viewManagerModel);
            viewResultsViewModel.addPropertyChangeListener(resultsView);
        }

        return this;
    }

    public AppBuilder addViewLeaderboardUseCase() {
        final ViewLeaderboardOutputBoundary viewLeaderboardPresenter = new ViewLeaderboardPresenter(viewLeaderboardViewModel, viewManagerModel);
        final LeaderboardDataAccessInterface leaderboardDAO = leaderboardDataAccessObject;

        final ViewLeaderboardInputBoundary viewLeaderboardInteractor =
                new ViewLeaderboardInteractor(viewLeaderboardPresenter, leaderboardDAO);

        viewLeaderboardController = new ViewLeaderboardController(viewLeaderboardInteractor);

        if (leaderboardView != null) {
            viewLeaderboardViewModel.addPropertyChangeListener(leaderboardView);
        }

        return this;
    }

    public AppBuilder wireControllers() {
        if (leaderboardView != null) {
            leaderboardController = new LeaderboardController(viewManagerModel);
            leaderboardView.setLeaderboardController(leaderboardController);
            leaderboardView.setViewManagerModel(viewManagerModel);
        }

        if (takeSharedQuizPresenter != null && takeQuizController != null) {
            takeSharedQuizPresenter.setTakeQuizController(takeQuizController);
        }

        if (resultsView != null && viewLeaderboardController != null) {
            resultsView.setViewLeaderboardController(viewLeaderboardController);
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
        final ChangeUsernameOutputBoundary changeUsernameOutputBoundary = new ChangeUsernamePresenter(changeUsernameViewModel,
                viewManagerModel,loggedInViewModel);
        final ChangeUsernameInputBoundary changeUsernameInteractor = new ChangeUsernameInteractor(
                userDataAccessObject, changeUsernameOutputBoundary);

        ChangeUsernameController changeUsernameController = new ChangeUsernameController(changeUsernameInteractor);
        changeUsernameView.setChangeUsernameController(changeUsernameController);
        loggedInView.setChangeUsernameController(changeUsernameController);

        return this;
    }

    public AppBuilder addQuizMenuUseCase() {
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
        if (selectExistingQuizView != null) {
            selectExistingQuizView.setQuizMenuController(quizMenuController);
        }
        return this;
    }

    public AppBuilder addShareQuizView() {
        shareQuizViewModel = new ShareQuizViewModel();
        shareQuizView = new ShareQuizView(shareQuizViewModel, viewManagerModel);

        cardPanel.add(shareQuizView, shareQuizView.getViewName());
        return this;
    }

    public AppBuilder addSelectExistingQuizUseCase() {
        selectExistingQuizPresenter = new SelectExistingQuizPresenter(selectExistingQuizViewModel, null);
        final SelectExistingQuizInputBoundary selectInteractor = new SelectExistingQuizInteractor(
                userDataAccessObject,
                selectExistingQuizPresenter
        );

        selectExistingQuizController = new SelectExistingQuizController(viewManagerModel, selectInteractor);

        if (selectExistingQuizView != null) {
            selectExistingQuizView.setSelectExistingQuizController(selectExistingQuizController);
            selectExistingQuizViewModel.addPropertyChangeListener(selectExistingQuizView);
        }
        if (selectExistingQuizView != null && takeQuizController != null) {
            selectExistingQuizView.setTakeQuizController(takeQuizController);
        }
        if (quizMenuView != null) {
            quizMenuView.setSelectExistingQuizController(selectExistingQuizController);
        }
        return this;
    }

    public AppBuilder addShareQuizUseCase() {
        ShareQuizPresenter shareQuizPresenter = new ShareQuizPresenter(shareQuizViewModel, viewManagerModel);
        ShareQuizDataAccessInterface dataAccessInterface = new HashtoQuizDataAccessObject("hashtoquiz.json");
        ShareQuizInputBoundary shareQuizInteractor = new ShareQuizInteractor(dataAccessInterface, shareQuizPresenter);
        shareQuizController = new ShareQuizController(shareQuizInteractor);

        if (selectExistingQuizView != null) {
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
            new ValidateQuestionInteractor(validateQuestionOutputBoundary);

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

    public AppBuilder addPreviewQuizView() {
        previewQuizViewModel = new PreviewQuizViewModel();
        previewQuizView = new PreviewQuizView(previewQuizViewModel, viewManagerModel);
        cardPanel.add(previewQuizView, previewQuizView.getViewName());
        return this;
    }

    public AppBuilder addPreviewQuizUseCase() {
        final PreviewQuizOutputBoundary previewPresenter = new PreviewQuizPresenter(viewManagerModel, previewQuizViewModel);
        final PreviewQuizInputBoundary previewInteractor = new PreviewQuizInteractor(quizFileDataAccessObject,
                previewPresenter, quizFactory);
        final PreviewQuizController previewController = new PreviewQuizController(previewInteractor);
        if (createQuizView != null) {
            createQuizView.setPreviewQuizController(previewController);
        }
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Trivia Quiz");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.firePropertyChange();

        return application;
    }

}

