package interface_adapter.quickstart;

import interface_adapter.ViewModel;

public class QuickstartViewModel extends ViewModel<QuickstartState> {

    public QuickstartViewModel() {
        super("quickstart");
        setState(new QuickstartState());
    }
}
