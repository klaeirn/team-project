package interface_adapter.view_results;

import interface_adapter.ViewModel;

public class ViewResultsViewModel extends ViewModel<ViewResultsState> {

    public ViewResultsViewModel() {
        super("view results");
        setState(new ViewResultsState());
    }
}


