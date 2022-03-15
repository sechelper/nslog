import com.beust.jcommander.JCommander;
import icu.nslog.NslogLaunch;

import java.io.IOException;
import java.util.Arrays;

/**
 * @className: NslogCli
 * @description: TODO
 * @author: cookun
 * @date: 3/4/22
 **/
public class NslogCli {
    public static void main(String[] args) throws IOException {
        NslogLaunch launch = new NslogLaunch();
        System.out.println(Arrays.toString(args));
        JCommander.newBuilder()
                .addObject(launch)
                .build()
                .parse(args);
        launch.run();
    }

}
