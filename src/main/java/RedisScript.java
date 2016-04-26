import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class RedisScript {
    public static void main(String[] args) throws IOException {
        Long startTime = System.currentTimeMillis();

        String host = "localhost";
        String relativeFilepath = "scripts/expire_keys.lua";
        String keyMatch = "keymatch";
        String fieldMatch = "field";
        int count = 5000;

        Jedis jedis = new Jedis(host);
        String fileString = readFile(relativeFilepath, Charset.defaultCharset());
        ScanParams scanParams = new ScanParams();
        scanParams.match(keyMatch).count(count);

        String cursor = "-1";
        while (!cursor.equals("0")) {
            if (cursor.equals("-1")) cursor = "0";

            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            //returns the return value of the lua script
            Object result = jedis.eval(fileString, scanResult.getResult(), Collections.singletonList(fieldMatch));
            System.out.println(result);
            cursor = scanResult.getStringCursor();
        }

        Long duration = System.currentTimeMillis() - startTime;
        System.out.println("Process took: " + duration/1000 + " (s)");
    }

    static String readFile(String path, Charset encoding) throws IOException
    {
        File file = new File(RedisScript.class.getResource(path).getFile());
        byte[] encoded = Files.readAllBytes(Paths.get(file.toString()));
        return new String(encoded, encoding);
    }
}
