public class ExEmployeeAlreadyInAnotherTeam extends Exception {
    public ExEmployeeAlreadyInAnotherTeam() { super(" has already joined another team: "); }
    public ExEmployeeAlreadyInAnotherTeam(String leaderName, String teamName) { super(leaderName + " has already joined another team: " + teamName); }
}