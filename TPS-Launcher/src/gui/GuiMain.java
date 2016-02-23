package gui;

import javafx.stage.Stage;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public abstract class GuiMain {
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        GuiMain.mainStage = mainStage;
    }
}
