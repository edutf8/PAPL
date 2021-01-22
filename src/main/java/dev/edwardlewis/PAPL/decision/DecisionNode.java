package dev.edwardlewis.PAPL.decision;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecisionNode {

    private int nodeID;
    private int pathOneID;
    private int pathTwoID;

    private String description;
    private String questionOne;
    private String questionTwo;

    private DecisionNode pathOne;
    private DecisionNode pathTwo;

    private DecisionNode linkedNode;

}
