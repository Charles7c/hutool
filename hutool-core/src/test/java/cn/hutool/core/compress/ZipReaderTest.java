package cn.hutool.core.compress;

import cn.hutool.core.lang.Console;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

public class ZipReaderTest {

	@Test
	@Ignore
	public void unzipTest() {
		final File unzip = ZipUtil.unzip("d:/java.zip", "d:/test/java");
		Console.log(unzip);
	}
}
