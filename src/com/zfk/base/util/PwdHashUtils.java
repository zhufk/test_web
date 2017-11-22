package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.math.BigInteger;
/*   4:    */import java.security.NoSuchAlgorithmException;
/*   5:    */import java.security.SecureRandom;
/*   6:    */import java.security.spec.InvalidKeySpecException;
/*   7:    */import javax.crypto.SecretKey;
/*   8:    */import javax.crypto.SecretKeyFactory;
/*   9:    */import javax.crypto.spec.PBEKeySpec;
/*  10:    */
/*  63:    */public class PwdHashUtils
/*  64:    */{
/*  65:    */  public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
/*  66:    */  public static final int SALT_BYTE_SIZE = 16;
/*  67:    */  public static final int HASH_BYTE_SIZE = 16;
/*  68:    */  public static final int PBKDF2_ITERATIONS = 1000;
/*  69:    */  public static final int ITERATION_INDEX = 0;
/*  70:    */  public static final int SALT_INDEX = 1;
/*  71:    */  public static final int PBKDF2_INDEX = 2;
/*  72:    */  public static final String SEPARATOR = ":";
/*  73:    */  
/*  74:    */  public static String createHash(String password)
/*  75:    */    throws NoSuchAlgorithmException, InvalidKeySpecException
/*  76:    */  {
/*  77: 77 */    return createHash(password.toCharArray());
/*  78:    */  }
/*  79:    */  
/*  87:    */  public static String createHash(char[] password)
/*  88:    */    throws NoSuchAlgorithmException, InvalidKeySpecException
/*  89:    */  {
/*  90: 90 */    SecureRandom random = new SecureRandom();
/*  91: 91 */    byte[] salt = new byte[16];
/*  92: 92 */    random.nextBytes(salt);
/*  93:    */    
/*  95: 95 */    byte[] hash = pbkdf2(password, salt, 1000, 16);
/*  96:    */    
/*  97: 97 */    return "1000:" + toHex(salt) + ":" + toHex(hash);
/*  98:    */  }
/*  99:    */  
/* 111:    */  public static boolean validatePassword(String password, String correctHash)
/* 112:    */    throws NoSuchAlgorithmException, InvalidKeySpecException
/* 113:    */  {
/* 114:114 */    return validatePassword(password.toCharArray(), correctHash);
/* 115:    */  }
/* 116:    */  
/* 129:    */  public static boolean validatePassword(char[] password, String correctHash)
/* 130:    */    throws NoSuchAlgorithmException, InvalidKeySpecException
/* 131:    */  {
/* 132:132 */    String[] params = correctHash.split(":");
/* 133:133 */    int iterations = Integer.parseInt(params[0]);
/* 134:134 */    byte[] salt = fromHex(params[1]);
/* 135:135 */    byte[] hash = fromHex(params[2]);
/* 136:    */    
/* 138:138 */    byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
/* 139:    */    
/* 141:141 */    return slowEquals(hash, testHash);
/* 142:    */  }
/* 143:    */  
/* 154:    */  private static boolean slowEquals(byte[] a, byte[] b)
/* 155:    */  {
/* 156:156 */    int diff = a.length ^ b.length;
/* 157:157 */    for (int i = 0; (i < a.length) && (i < b.length); i++) {
/* 158:158 */      diff |= a[i] ^ b[i];
/* 159:    */    }
/* 160:160 */    return diff == 0;
/* 161:    */  }
/* 162:    */  
/* 175:    */  private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
/* 176:    */    throws NoSuchAlgorithmException, InvalidKeySpecException
/* 177:    */  {
/* 178:178 */    PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
/* 179:179 */    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
/* 180:180 */    return skf.generateSecret(spec).getEncoded();
/* 181:    */  }
/* 182:    */  
/* 189:    */  private static byte[] fromHex(String hex)
/* 190:    */  {
/* 191:191 */    byte[] binary = new byte[hex.length() / 2];
/* 192:192 */    for (int i = 0; i < binary.length; i++) {
/* 193:193 */      binary[i] = ((byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16));
/* 194:    */    }
/* 195:195 */    return binary;
/* 196:    */  }
/* 197:    */  
/* 204:    */  private static String toHex(byte[] array)
/* 205:    */  {
/* 206:206 */    BigInteger bi = new BigInteger(1, array);
/* 207:207 */    String hex = bi.toString(16);
/* 208:208 */    int paddingLength = array.length * 2 - hex.length();
/* 209:209 */    if (paddingLength > 0) {
/* 210:210 */      return String.format(new StringBuilder().append("%0").append(paddingLength).append("d").toString(), new Object[] { Integer.valueOf(0) }) + hex;
/* 211:    */    }
/* 212:212 */    return hex;
/* 213:    */  }
/* 214:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.PwdHashUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */