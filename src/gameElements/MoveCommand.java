package gameElements;

public class MoveCommand extends Command{

    public MoveCommand(int from, int to, int num)
    {
        type = "move";
        message = from + " " + to + " " + num;
    }
}
