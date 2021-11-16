package fr.alkanife.botcommons;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class YMLReader {

    public static HashMap<String, Object> read(String fileName) throws FileNotFoundException {
        return read(new File(Utils.absolutePath() + "/" + fileName + ".yml"));
    }

    public static HashMap<String, Object> read(File file) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);

        Yaml yaml = new Yaml();

        return yaml.load(inputStream);
    }

}
