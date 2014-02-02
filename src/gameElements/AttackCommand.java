package gameElements;

public class AttackCommand extends Command{

    public AttackCommand(int from, int to, int num)
    {
        type = "attack";
        message = from + " " + to + " " + num;
    }
}
