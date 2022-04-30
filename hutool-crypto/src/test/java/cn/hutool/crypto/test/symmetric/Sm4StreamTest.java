package cn.hutool.crypto.test.symmetric;

import cn.hutool.crypto.symmetric.SM4;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * https://gitee.com/dromara/hutool/issues/I4EMST
 */
public class Sm4StreamTest {

	private static final SM4 sm4 = new SM4();

	private static final boolean IS_CLOSE = false;

	@Test
	@Ignore
	public void sm4Test(){
		final String source = "d:/test/sm4_1.txt";
		final String target = "d:/test/sm4_2.data";
		final String target2 = "d:/test/sm4_3.txt";
		encrypt(source, target);
		decrypt(target, target2);
	}

	public static void encrypt(final String source, final String target) {
		try (final InputStream input = new FileInputStream(source);
			 final OutputStream out = new FileOutputStream(target)) {
			sm4.encrypt(input, out, IS_CLOSE);
			System.out.println("============encrypt end");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void decrypt(final String source, final String target) {
		try (final InputStream input = new FileInputStream(source);
			 final OutputStream out = new FileOutputStream(target)) {
			sm4.decrypt(input, out, IS_CLOSE);
			System.out.println("============decrypt end");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
