package it.polimi.ingsw.utils;

public enum Divinity {
    Apollo,
    Artemis,
    Athena,
    Atlas,
    Demeter,
    Hephaestus,
    Minotaur,
    Pan,
    Prometheus,
    Default;

    public static String getDescrption(String divinity){
        return switch (divinity) {
            case "apollo" -> "God Of Music \n"+
            "Your Move: Your Worker may \n"+
            "move into an opponent Worker’s \n"+
            "space by forcing their Worker to\n"+
            "the space yours just vacated.\n";
            case "artemis" -> "Goddess of the Hunt\n"+
            "Your Move: Your Worker may\n"+
            "move one additional time, but not\n"+
            "back to its initial space.\n";
            case "athena" -> "Goddess of Wisdom\n"+
            "Opponent’s Turn: If one of your\n"+
            "Workers moved up on your last\n" +
            "turn.\n";
            case "atlas" ->"Titan Shouldering the Heavens\n"+
            "Your Build: Your Worker may\n"+
            "build a dome at any level.\n";
            case "demeter" -> "Goddess of the Harvest\n"+
            "Your Build: Your Worker may\n"+
            "build one additional time, but not\n"+
            "on the same space.\n";
            case "hephaestus" -> "God of Blacksmiths\n"+
            "Your Build: Your Worker may\n"+
            "build one additional block (not\n"+
            "dome) on top of your first block\n";
            case "minotaur" -> "Bull-headed Monster\n"+
            "Your Move: Your Worker may\n"+
            "move into an opponent Worker’s\n"+
            "space, if their Worker can be\n"+
            "forced one space straight backwards to an\n"+
            "unoccupied space at any level.\n";
            case "pan" -> "God of the Wild\n"+
            "Win Condition: You also win if\n"+
            "your Worker moves down two or\n"+
            "more levels.\n";
            case "prometheus" -> "Titan Benefactor of Mankind\n"+
            "Your Turn: If your Worker does\n"+
            "not move up, it may build both\n"+
            "before and after moving.\n";
            default -> null;
        };
    }
}
