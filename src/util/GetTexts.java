package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetTexts {
    private static GetTexts instance;
    private BufferedReader bufferedReader;

    private GetTexts() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static GetTexts getInstance() {
        if (instance == null) {
            instance = new GetTexts();
        }
        return instance;
    }

    public String readLine() {
        String input = null;
        try {
            input = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
