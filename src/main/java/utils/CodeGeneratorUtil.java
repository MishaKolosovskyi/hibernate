package utils;

import java.util.Random;

public class CodeGeneratorUtil {

   public static String codeGenerator() {
        return String.format("%04d", new Random().nextInt(10000));
    }
}
