package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.awt.Color;
/*   4:    */import java.awt.Font;
/*   5:    */import java.awt.Graphics;
/*   6:    */import java.awt.Graphics2D;
/*   7:    */import java.awt.image.BufferedImage;
/*   8:    */import java.io.IOException;
/*   9:    */import java.util.Random;
/*  10:    */import javax.imageio.ImageIO;
/*  11:    */import javax.servlet.http.HttpServletRequest;
/*  12:    */import javax.servlet.http.HttpServletResponse;
/*  13:    */import javax.servlet.http.HttpSession;
/*  14:    */
/*  18:    */public class ImageCodeUtils
/*  19:    */{
/*  20:    */  public static final int WIDTH = 120;
/*  21:    */  public static final int HEIGHT = 32;
/*  22:    */  
/*  23:    */  public static void writeImage(HttpServletRequest request, HttpServletResponse response)
/*  24:    */    throws IOException
/*  25:    */  {
/*  26: 26 */    String createTypeFlag = request.getParameter("createTypeFlag");
/*  27: 27 */    if (StringUtils.isBlank(createTypeFlag).booleanValue()) {
/*  28: 28 */      createTypeFlag = "nl";
/*  29:    */    }
/*  30:    */    
/*  31: 31 */    BufferedImage bi = new BufferedImage(120, 32, 1);
/*  32:    */    
/*  33: 33 */    Graphics g = bi.getGraphics();
/*  34:    */    
/*  35: 35 */    setBackGround(g);
/*  36:    */    
/*  39: 39 */    drawRandomLine(g);
/*  40:    */    
/*  45: 45 */    String random = drawRandomNum((Graphics2D)g, new String[] { createTypeFlag });
/*  46:    */    
/*  47: 47 */    request.getSession().setAttribute("checkcode", random);
/*  48:    */    
/*  56: 56 */    response.setDateHeader("Expires", 0L);
/*  57:    */    
/*  58: 58 */    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
/*  59:    */    
/*  60: 60 */    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
/*  61:    */    
/*  62: 62 */    response.setHeader("Pragma", "no-cache");
/*  63:    */    
/*  65: 65 */    response.setContentType("image/jpeg");
/*  66:    */    
/*  68: 68 */    ImageIO.write(bi, "jpg", response.getOutputStream());
/*  69:    */  }
/*  70:    */  
/*  75:    */  private static void setBackGround(Graphics g)
/*  76:    */  {
/*  77: 77 */    g.setColor(Color.WHITE);
/*  78:    */    
/*  79: 79 */    g.fillRect(0, 0, 120, 32);
/*  80:    */  }
/*  81:    */  
/*  87:    */  private static void setBorder(Graphics g)
/*  88:    */  {
/*  89: 89 */    g.setColor(Color.BLUE);
/*  90:    */    
/*  91: 91 */    g.drawRect(1, 1, 118, 30);
/*  92:    */  }
/*  93:    */  
/*  98:    */  private static void drawRandomLine(Graphics g)
/*  99:    */  {
/* 100:100 */    g.setColor(Color.GREEN);
/* 101:    */    
/* 102:102 */    for (int i = 0; i < 5; i++) {
/* 103:103 */      int x1 = new Random().nextInt(120);
/* 104:104 */      int y1 = new Random().nextInt(32);
/* 105:105 */      int x2 = new Random().nextInt(120);
/* 106:106 */      int y2 = new Random().nextInt(32);
/* 107:107 */      g.drawLine(x1, y1, x2, y2);
/* 108:    */    }
/* 109:    */  }
/* 110:    */  
/* 119:    */  private static String drawRandomNum(Graphics2D g, String... createTypeFlag)
/* 120:    */  {
/* 121:121 */    g.setColor(Color.RED);
/* 122:    */    
/* 123:123 */    g.setFont(new Font("宋体", 1, 20));
/* 124:    */    
/* 125:125 */    String baseChineseChar = "的一了是我不在人们有来他这上着个地到大里说就去子得也和那要下看天时过出小么起你都把好还多没为又可家学只以主会样年想生同老中十从自面前头道它后然走很像见两用她国动进成回什边作对开而己些现山民候经发工向事命给长水几义三声于高手知理眼志点心战二问但身方实吃做叫当住听革打呢真全才四已所敌之最光产情路分总条白话东席次亲如被花口放儿常气五第使写军吧文运再果怎定许快明行因别飞外树物活部门无往船望新带队先力完却站代员机更九您每风级跟笑啊孩万少直意夜比阶连车重便斗马哪化太指变社似士者干石满日决百原拿群究各六本思解立河村八难早论吗根共让相研今其书坐接应关信觉步反处记将千找争领或师结块跑谁草越字加脚紧爱等习阵怕月青半火法题建赶位唱海七女任件感准张团屋离色脸片科倒睛利世刚且由送切星导晚表够整认响雪流未场该并底深刻平伟忙提确近亮轻讲农古黑告界拉名呀土清阳照办史改历转画造嘴此治北必服雨穿内识验传业菜爬睡兴形量咱观苦体众通冲合破友度术饭公旁房极南枪读沙岁线野坚空收算至政城劳落钱特围弟胜教热展包歌类渐强数乡呼性音答哥际旧神座章帮啦受系令跳非何牛取入岸敢掉忽种装顶急林停息句区衣般报叶压慢叔背细";
/* 126:    */    
/* 127:127 */    String baseNumLetter = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
/* 128:    */    
/* 129:129 */    String baseNum = "0123456789";
/* 130:    */    
/* 131:131 */    String baseLetter = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
/* 132:    */    
/* 133:133 */    if ((createTypeFlag.length > 0) && (null != createTypeFlag[0])) {
/* 134:134 */      if (createTypeFlag[0].equals("ch"))
/* 135:    */      {
/* 136:136 */        return createRandomChar(g, baseChineseChar); }
/* 137:137 */      if (createTypeFlag[0].equals("nl"))
/* 138:    */      {
/* 139:139 */        return createRandomChar(g, baseNumLetter); }
/* 140:140 */      if (createTypeFlag[0].equals("n"))
/* 141:    */      {
/* 142:142 */        return createRandomChar(g, baseNum); }
/* 143:143 */      if (createTypeFlag[0].equals("l"))
/* 144:    */      {
/* 145:145 */        return createRandomChar(g, baseLetter);
/* 146:    */      }
/* 147:    */    }
/* 148:    */    else {
/* 149:149 */      return createRandomChar(g, baseNumLetter);
/* 150:    */    }
/* 151:    */    
/* 152:152 */    return "";
/* 153:    */  }
/* 154:    */  
/* 160:    */  private static String createRandomChar(Graphics2D g, String baseChar)
/* 161:    */  {
/* 162:162 */    StringBuffer sb = new StringBuffer();
/* 163:163 */    int x = 5;
/* 164:164 */    String ch = "";
/* 165:    */    
/* 166:166 */    for (int i = 0; i < 4; i++)
/* 167:    */    {
/* 168:168 */      int degree = new Random().nextInt() % 30;
/* 169:169 */      ch = baseChar.charAt(new Random().nextInt(baseChar.length())) + "";
/* 170:170 */      sb.append(ch);
/* 171:    */      
/* 172:172 */      g.rotate(degree * 3.141592653589793D / 180.0D, x, 20.0D);
/* 173:173 */      g.drawString(ch, x, 20);
/* 174:    */      
/* 175:175 */      g.rotate(-degree * 3.141592653589793D / 180.0D, x, 20.0D);
/* 176:176 */      x += 30;
/* 177:    */    }
/* 178:178 */    return sb.toString();
/* 179:    */  }
/* 180:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.ImageCodeUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */