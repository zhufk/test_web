package com.zfk.base.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageCodeUtils {
	public static final int WIDTH = 120;
	public static final int HEIGHT = 32;

	public static void writeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String createTypeFlag = request.getParameter("createTypeFlag");
		if (StringUtils.isBlank(createTypeFlag).booleanValue()) {
			createTypeFlag = "nl";
		}

		BufferedImage bi = new BufferedImage(120, 32, 1);

		Graphics g = bi.getGraphics();

		setBackGround(g);

		drawRandomLine(g);

		String random = drawRandomNum((Graphics2D) g, new String[] { createTypeFlag });

		request.getSession().setAttribute("checkcode", random);

		response.setDateHeader("Expires", 0L);

		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

		response.addHeader("Cache-Control", "post-check=0, pre-check=0");

		response.setHeader("Pragma", "no-cache");

		response.setContentType("image/jpeg");

		ImageIO.write(bi, "jpg", response.getOutputStream());
	}

	private static void setBackGround(Graphics g) {
		g.setColor(Color.WHITE);

		g.fillRect(0, 0, 120, 32);
	}

	private static void setBorder(Graphics g) {
		g.setColor(Color.BLUE);

		g.drawRect(1, 1, 118, 30);
	}

	private static void drawRandomLine(Graphics g) {
		g.setColor(Color.GREEN);

		for (int i = 0; i < 5; i++) {
			int x1 = new Random().nextInt(120);
			int y1 = new Random().nextInt(32);
			int x2 = new Random().nextInt(120);
			int y2 = new Random().nextInt(32);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	private static String drawRandomNum(Graphics2D g, String... createTypeFlag) {
		g.setColor(Color.RED);

		g.setFont(new Font("宋体", 1, 20));

		String baseChineseChar = "的一了是我不在人们有来他这上着个地到大里说就去子得也和那要下看天时过出小么起你都把好还多没为又可家学只以主会样年想生同老中十从自面前头道它后然走很像见两用她国动进成回什边作对开而己些现山民候经发工向事命给长水几义三声于高手知理眼志点心战二问但身方实吃做叫当住听革打呢真全才四已所敌之最光产情路分总条白话东席次亲如被花口放儿常气五第使写军吧文运再果怎定许快明行因别飞外树物活部门无往船望新带队先力完却站代员机更九您每风级跟笑啊孩万少直意夜比阶连车重便斗马哪化太指变社似士者干石满日决百原拿群究各六本思解立河村八难早论吗根共让相研今其书坐接应关信觉步反处记将千找争领或师结块跑谁草越字加脚紧爱等习阵怕月青半火法题建赶位唱海七女任件感准张团屋离色脸片科倒睛利世刚且由送切星导晚表够整认响雪流未场该并底深刻平伟忙提确近亮轻讲农古黑告界拉名呀土清阳照办史改历转画造嘴此治北必服雨穿内识验传业菜爬睡兴形量咱观苦体众通冲合破友度术饭公旁房极南枪读沙岁线野坚空收算至政城劳落钱特围弟胜教热展包歌类渐强数乡呼性音答哥际旧神座章帮啦受系令跳非何牛取入岸敢掉忽种装顶急林停息句区衣般报叶压慢叔背细";

		String baseNumLetter = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";

		String baseNum = "0123456789";

		String baseLetter = "ABCDEFGHJKLMNOPQRSTUVWXYZ";

		if ((createTypeFlag.length > 0) && (null != createTypeFlag[0])) {
			if (createTypeFlag[0].equals("ch")) {
				return createRandomChar(g, baseChineseChar);
			}
			if (createTypeFlag[0].equals("nl")) {
				return createRandomChar(g, baseNumLetter);
			}
			if (createTypeFlag[0].equals("n")) {
				return createRandomChar(g, baseNum);
			}
			if (createTypeFlag[0].equals("l")) {
				return createRandomChar(g, baseLetter);
			}
		} else {
			return createRandomChar(g, baseNumLetter);
		}

		return "";
	}

	private static String createRandomChar(Graphics2D g, String baseChar) {
		StringBuffer sb = new StringBuffer();
		int x = 5;
		String ch = "";

		for (int i = 0; i < 4; i++) {
			int degree = new Random().nextInt() % 30;
			ch = baseChar.charAt(new Random().nextInt(baseChar.length())) + "";
			sb.append(ch);

			g.rotate(degree * 3.141592653589793D / 180.0D, x, 20.0D);
			g.drawString(ch, x, 20);

			g.rotate(-degree * 3.141592653589793D / 180.0D, x, 20.0D);
			x += 30;
		}
		return sb.toString();
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.ImageCodeUtils JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */