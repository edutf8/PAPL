package dev.edwardlewis.PAPL;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.edwardlewis.PAPL.decision.DecisionMap;
import dev.edwardlewis.PAPL.decision.DecisionNode;
import dev.edwardlewis.PAPL.exception.DecisionException;

import java.io.FileNotFoundException;

@Route
public class MainView extends VerticalLayout {

    private final DecisionMap decisionMap = new DecisionMap();

    private final Button restartButton = new Button("Restart", buttonClickEvent -> {
        try {
            nextNode(decisionMap.entryPoint());
        } catch (DecisionException decisionException) {
            System.out.println(decisionException.getMessage());
        }
    });

    public MainView() throws FileNotFoundException {
        restartButton.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_PRIMARY);
        setAlignItems(Alignment.CENTER);
        getStyle().set("background-color", "#264653");
        getStyle().set("text-align", "center");
        setHeightFull();
        H1 h1 = new H1("Welcome back to Boris' Brexit Deal Hunter, are you ready to save/screw the country?");
        h1.getStyle().set("color", "white");
        H3 h3 = new H3("Created by Edward Lewis - UP943116");
        h3.getStyle().set("color", "white");
        Button button = new Button("I dare", buttonClickEvent -> {
            try {
                nextNode(decisionMap.entryPoint());
            } catch (DecisionException decisionException) {
                System.out.println(decisionException.getMessage());
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_PRIMARY);
        add(h1, h3, button);
    }

    private void nextNode(DecisionNode decisionNode) throws DecisionException {
        removeAll();
        H1 h1 = new H1(decisionNode.getDescription());
        h1.getStyle().set("color", "white");
        if (decisionNode.getPathOneID() == -1 && decisionNode.getPathTwoID() == -1)
            add(h1, restartButton);
        else if (decisionNode.getPathOneID() == -1 && decisionNode.getPathTwoID() != -1)
            throw new DecisionException("Path One ID = -1 but Path Two does not (" + decisionNode.getPathTwoID() + ")");
        else if (decisionNode.getPathOneID() != -1 && decisionNode.getPathTwoID() == -1)
            throw new DecisionException("Path Two ID = -1 but Path One does not (" + decisionNode.getPathOneID() + ")");
        else {
            Button questionOne = new Button(decisionNode.getQuestionOne(), buttonClickEvent -> {
                try {
                    nextNode(decisionNode.getPathOne());
                } catch (DecisionException decisionException) {
                    System.out.println(decisionException.getMessage());
                }
            });
            questionOne.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_PRIMARY);
            Button questionTwo = new Button(decisionNode.getQuestionTwo(), buttonClickEvent -> {
                try {
                    nextNode(decisionNode.getPathTwo());
                } catch (DecisionException decisionException) {
                    System.out.println(decisionException.getMessage());
                }
            });
            questionTwo.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_PRIMARY);
            add(h1, questionOne, questionTwo);
        }
    }

}
