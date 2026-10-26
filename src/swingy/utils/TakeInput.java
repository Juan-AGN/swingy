package swingy.utils;

import java.io.IOException;

public final class TakeInput {
    private static String savedSettings;

    public static void initTerminal() throws IOException, InterruptedException {
        Process p = new ProcessBuilder("sh", "-c", "stty -g")
                .redirectInput(ProcessBuilder.Redirect.INHERIT).start();
        savedSettings = new String(p.getInputStream().readAllBytes()).trim();
        if (p.waitFor() != 0 || savedSettings.isEmpty())
            throw new IOException("not a terminal");

        new ProcessBuilder("sh", "-c", "stty -icanon -echo")
                .inheritIO().start().waitFor();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                new ProcessBuilder("sh", "-c", "stty " + savedSettings)
                        .inheritIO().start().waitFor();
            } catch (Exception ignored) {}
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }));
    }

    public static int getInput() {
        try {
            if (System.in.available() == 0)
                return 0;
            int key = System.in.read();
            while (System.in.available() > 0)
                System.in.read();
            return key;
        } catch (IOException e) {
            return -1;
        }
    }

}
