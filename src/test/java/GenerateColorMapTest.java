import com.physmo.colormapper.GenerateColorMap;
import org.junit.Test;

import java.awt.*;

public class GenerateColorMapTest {

    @Test
    public void t1() {

        System.out.println(GenerateColorMap.getDestIndex(4,0));
        System.out.println(GenerateColorMap.getDestIndex(4,1));
        System.out.println(GenerateColorMap.getDestIndex(4,2));
        System.out.println(GenerateColorMap.getDestIndex(4,3));
        System.out.println(GenerateColorMap.getDestIndex(4,4));
    }

    @Test
    public void t2() {
        Color[] guide = new Color[] {
                new Color(132, 136, 63),
                new Color(86, 91, 59),
                new Color(57, 59, 26),
                new Color(44, 52, 39)
        };

        Color[] colors = GenerateColorMap.generateMapFromPreset(guide);

        System.out.println("done");
    }
}
