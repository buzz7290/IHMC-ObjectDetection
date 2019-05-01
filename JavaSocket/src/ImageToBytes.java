import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ImageToBytes {
    private int width;
    private int height;
    private int size;
    public byte[] convertImgToBytes(String pathToImage){
        try {
            File file = new File(pathToImage);
            BufferedImage bi = ImageIO.read(file);

            width = bi.getWidth();
            height = bi.getHeight();

            int[] dataBuffInt = bi.getRGB(0, 0, width, height, null,0, width);
            ByteBuffer bb = ByteBuffer.allocate(dataBuffInt.length*4);
            IntBuffer intBuffer = bb.asIntBuffer();
            intBuffer.put(dataBuffInt);
            byte[] imageBytes = bb.array();
            size = imageBytes.length;
            return  imageBytes;
        } catch (IOException e) {
            System.out.println("Could not find image");;
            return null;
        }
    }

    public byte[] convertDimensionToBytes(String pathToImage){
        try {
            File file = new File(pathToImage);
            BufferedImage bi = ImageIO.read(file);

            width = bi.getWidth();
            height = bi.getHeight();
            size = convertImgToBytes(pathToImage).length;

            int[] wh = {size, width, height};
            ByteBuffer byteBuffer = ByteBuffer.allocate(wh.length*4);
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.put(wh);
            byte[] byteImageDimension = byteBuffer.array();

            return byteImageDimension;
        } catch (IOException e) {
            System.out.println("Could not find image");;
            return null;
        }
    }
}
