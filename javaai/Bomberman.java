import im.antoine.bombjava.Client;
import im.antoine.bombjava.Duration;
import im.antoine.bombjava.GameState;
import im.antoine.bombjava.PlayerState;
import java.lang.Integer;
import java.util.Date;

class Bomberman implements Runnable
{
    volatile GameState state;
    Client client;

    Bomberman(String host, int port)
    {
        try {
            client = new Client(host, port);
            client.open();
            Thread stateThread = new Thread(this);
            state = client.nextState();
            stateThread.start();
        } catch (Exception e) {e.printStackTrace();}
    }

    public void run()
    {
        try {
        while(client.hasNext())
        {
            state = client.nextState();
        }
        } catch(Exception e) {e.printStackTrace();}
    }

    public void loop()
    {
        Player player = null;
        try
        {
        while(true)
        {
            if(state == null) continue;
            if(player == null)
                player = new Player(state.getPlayerState().getX(), state.getPlayerState().getY());
            GameState old = state;
            Player.Move move = player.update(state);
            switch(move)
            {
                case UP:
                    client.goUp();
                    break;
                case DOWN:
                    client.goDown();
                    break;
                case LEFT:
                    client.goLeft();
                    break;
                case RIGHT:
                    client.goRight();
                    break;
                case BOMB:
                    client.putBomb();
                    break;
                default:
                    break;
            }

            // if(old == state)
            //     Thread.sleep(80);
            /*
            int i = 0;
            while(old == state && i < 10)
            {
                i++;
            }
            while(!player.sync(state));*/
        }
        } catch(Exception e)
        {e.printStackTrace(); }
    }

    public static void main(String[] args)
    {
        try {
            int pt = -1;
            if(args.length > 0) pt = Integer.parseInt(args[0]);

            System.out.printf("args.length=%d\n", args.length);

            Bomberman bomberman = new Bomberman("localhost", pt);
            bomberman.loop();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
