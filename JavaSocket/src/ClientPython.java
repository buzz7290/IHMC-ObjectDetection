import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class ClientPython {
    public static String path = "door.jpg";
    private String host = ;
    private int port = ;
    public static void main(String[] args) {
        try {
            ImageToBytes converter = new ImageToBytes();
            System.out.println("Client Started");
            Socket soc = new Socket(host, port);

            byte[] imageBytes = converter.convertImgToBytes(path);
            byte[] dimensionBytes = converter.convertDimensionToBytes(path);

            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            dos.write(dimensionBytes);
            dos.write(imageBytes);
            System.out.println("image sent");

            BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            try {
                int[] result = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
                System.out.println(Arrays.toString(result));
            } catch (NullPointerException ne) {
                System.out.println("No object detected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }
}
