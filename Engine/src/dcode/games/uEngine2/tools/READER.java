package dcode.games.uEngine2.tools;

import java.io.*;

/**
 * @author dusakus
 */
class READER {

    private File F;
    private BufferedReader br;

    public READER(File f) throws FileNotFoundException {
        F = f;
        br = new BufferedReader(new FileReader(F));
    }

    public String nextLine() throws IOException {
        if (br.ready()) {
            return br.readLine();
        }
        return null;
    }

    public void Reset() throws FileNotFoundException {
        br = new BufferedReader(new FileReader(F));
    }
}
