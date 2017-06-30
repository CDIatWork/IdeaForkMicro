package at.irian.cdiatwork.ideafork.dev;

import org.apache.meecrowave.Meecrowave;

public class DevUserServerStarter {
    public static void main(String[] args) {
        new Meecrowave().bake().await();
    }
}
