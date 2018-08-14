package com.viewol.web.ucard.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码生成器类，可生成数字、大写、小写字母及三者混合类型的验证码。 支持自定义验证码字符数量； 支持自定义验证码图片的大小； 支持自定义需排除的特殊字符；
 * 支持自定义干扰线的数量； 支持自定义验证码图文颜色
 */
public class SecurityImage {
	private static Random random = new Random();

	/*
	 * 绘制干扰线
	 */
	public static void drowLine(Graphics g, int width, int height) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/*
	 * 获得颜色
	 */
	public static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 生成验证码图片
	 *
	 * @param securityCode 验证码字符
	 * @return BufferedImage 图片
	 */
	public static BufferedImage createImage(String securityCode) {
		// 验证码长度
		int codeLength = securityCode.length();
		// 字体大小
		int fSize = 50;
		// 图片宽度
		int width = 145;
		// 图片高度
		int height = 58;
		// 图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		// 设置背景色
		g.setColor(Color.WHITE);
		// 填充背景
		g.fillRect(0, 0, width, height);
		// 设置边框颜色
		g.setColor(Color.LIGHT_GRAY);
		// 边框字体样式
		g.setFont(new Font("Arial", Font.BOLD, height - 2));
		// g.setFont(getFont());
		// 绘制边框
		g.drawRect(0, 0, width - 1, height - 1);
		// 绘制噪点
//		Random rand = new Random();
		// 设置噪点颜色
		g.setColor(Color.BLACK);
//		for (int i = 0; i < codeLength * 6; i++) {
//			int x = rand.nextInt(width);
//			int y = rand.nextInt(height);
//			// 绘制1*1大小的矩形
//			g.drawRect(x, y, 1, 1);
//		}

		// 绘制干扰线
		for (int i = 0; i <= 100; i++) {
			drowLine(g, width, height);
		}

		// 绘制验证码
		int codeY = height - 10;
		// 设置字体颜色和样式
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		g.setFont(new Font("Arial", 2, fSize));
		for (int i = 0; i < codeLength; i++) {
			g.drawString(String.valueOf(securityCode.charAt(i)), i * 13 + 5, codeY);
		}
		// 关闭资源
		g.dispose();
		return image;
	}

	/**
	 * 返回验证码图片的流格式
	 *
	 * @param securityCode 验证码
	 * @return ByteArrayInputStream 图片流
	 */
	public static byte[] getImageAsInputStream(String securityCode) {
		BufferedImage image = createImage(securityCode);
		// 创建储存图片二进制流的输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 创建ImageOutputStream流
		ImageOutputStream imageOutputStream = null;
		try {
			imageOutputStream = ImageIO.createImageOutputStream(baos);
			// 将二进制数据写进ByteArrayOutputStream
			ImageIO.write(image, "jpg", imageOutputStream);

			return baos.toByteArray();
		} catch (IOException e) {

		} finally {
		}
		return null;
	}

}