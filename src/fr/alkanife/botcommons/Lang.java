package fr.alkanife.botcommons;

import java.text.MessageFormat;
import java.util.HashMap;

public class Lang {

    private static String missingTranslation = "Missing translation:";

    private static HashMap<String, Object> translations = new HashMap<>();

    public static void load() throws Exception {
        HashMap<String, Object> data = YMLReader.read("lang");

        if (data == null)
            throw new Exception("Data is null");

        translations = data;
    }

    public static String t(String key, String... values) {
        if (translations.containsKey(key)) {
            MessageFormat messageFormat = new MessageFormat(String.valueOf(translations.get(key)));
            return messageFormat.format(values);
        } else return "{" + missingTranslation + " " + key + "}";
    }

    public static String getMissingTranslation() {
        return missingTranslation;
    }

    public static void setMissingTranslation(String missingTranslation) {
        Lang.missingTranslation = missingTranslation;
    }

    public static HashMap<String, Object> getTranslations() {
        return translations;
    }
}