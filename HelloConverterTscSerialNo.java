import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;

public class HelloConverterTscSerialNo {

  public static void main(String[] args) {
    if (args == null || args.length == 0) {
      System.out.println("> java HelloConverterTscSerialNo <- show help\nif you want to convert from decimal to hexadeciaml, Enter as below:\n\nparam[0] mandatry: type of conversion. (0 <- hex to dec, 1 <- dec to hex)\nparam[1] mandatry: value of tscSerialNo in hexadecimal which you want to convert.\nparam[3] optional: value of shortCardModelId. (if you want to convert the support Mifare UL digit 2, otherwise empty)\n\nex hex to dec.\n> java HelloConverterTscSerialNo 0 FF\nex dec to hex.\n> java HelloConverterTscSerialNo 1 255\nex Mifare UL.\n> java HelloConverterTscSerialNo 0 255 2\n\n");
      return;
    }

    if (args.length < 2) {
      System.out.println("Missing mandatory param");
    }

    int shortCardModel = 0;
    if (args.length > 2)
      shortCardModel = 2;
    if (Integer.parseInt(args[0]) == 0) 
      System.out.println(convertTscSNHexToTscSNDec(args[1], shortCardModel));
    else

      System.out.println(convertTscSNDecToTscSNHex(Long.parseLong(args[1]), shortCardModel));
  }

  public static byte[] decodeHex(String hexString) {
    return HexFormat.of().parseHex(hexString);
  }
  public static String encodeByte(byte[] bytes) {
    return HexFormat.of().formatHex(bytes);
  }
  /**
   * Convert the number in decimal to in hexadecimal.
   * @param decNum
   * @return HexString
   */
  public static String convertDecimalToHexadecimal(Long decNum) {
    return Long.toHexString(decNum);
  }

  /**
   * Convert the number in hexadecimal to in decimal.
   * @param hexNum
   * @return HexString
   */
  public static Long convertHexadecimalToDecimal(String hexNum) {
    return Long.parseLong(hexNum, 16);
  }

  /**
   * Convert the value of tsc serial number in decimal to in hexadecimal.
   * @param tscSNDec
   * @param shortCardModel
   * @return the value of tsc serial number in hexadecimal
   */
  public static String convertTscSNDecToTscSNHex(Long tscSNDec, Integer shortCardModel) {
    //System.out.println("tscSNDec: " + tscSNDec + ", shortCardModel: " + shortCardModel);
    if (tscSNDec == null || shortCardModel == null)
      System.out.println( "parametri sono obbligatori.");

    if (shortCardModel == 2) {
      //1. convert from Dec to Hex
      String tscSNHex = convertDecimalToHexadecimal(tscSNDec);
      //2. decoding in byte[]
      byte[] ary = decodeHex(tscSNHex);
      //3. revers array
      reverse(ary);
      //4. encoding in hexadecimal
      return encodeByte(ary);
    } else {
      return convertDecimalToHexadecimal(tscSNDec);
    }
  }

  /**
   * Convert the value of tsc serial number in hexadecimal to in decimal.
   * @param tscSNHex
   * @param shortCardModel
   * @return the value of tsc serial number in decimal
   */
  public static Long convertTscSNHexToTscSNDec(String tscSNHex, Integer shortCardModel) {
    //System.out.println("tscSNHex: " + tscSNHex + ", shortCardModel: " + shortCardModel);
    if (tscSNHex == null || shortCardModel == null)
      System.out.println( "parametri sono obbligatori.");

    if (tscSNHex.length()%2 !=0)
      tscSNHex = "0" + tscSNHex;
    if (shortCardModel == 2) {
      //1. decoding in byte[]
      byte[] ary = decodeHex(tscSNHex);
      //2. revers array
      reverse(ary);
      //3. encoding in hexadecimal
      String tscSNHexEndianReversed = encodeByte(ary);
      //4. convert from Hex to Dec
      return convertHexadecimalToDecimal(tscSNHexEndianReversed);
    } else {
      return convertHexadecimalToDecimal(tscSNHex);
    }
  }

  /**
   * Reverses the elements inside the provided byte array.
   *
   * @param bytes The byte array to be reversed.
   */
  public static void reverse(byte[] bytes) {
      int start = 0;
      int end = bytes.length - 1;

      while (start < end) {
          // Swap the elements at start and end indices
          byte temp = bytes[start];
          bytes[start] = bytes[end];
          bytes[end] = temp;

          // Move the start and end indices towards the center
          start++;
          end--;
      }
  }
}