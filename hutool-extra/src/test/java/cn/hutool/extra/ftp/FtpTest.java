package cn.hutool.extra.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.extra.ssh.Sftp;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class FtpTest {

	@Test
	@Ignore
	public void cdTest() {
		final Ftp ftp = new Ftp("looly.centos");

		ftp.cd("/file/aaa");
		Console.log(ftp.pwd());

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void uploadTest() {
		final Ftp ftp = new Ftp("localhost");

		final boolean upload = ftp.upload("/temp", FileUtil.file("d:/test/test.zip"));
		Console.log(upload);

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void reconnectIfTimeoutTest() throws InterruptedException {
		final Ftp ftp = new Ftp("looly.centos");

		Console.log("打印pwd: " + ftp.pwd());

		Console.log("休眠一段时间，然后再次发送pwd命令，抛出异常表明连接超时");
		Thread.sleep(35 * 1000);

		try{
			Console.log("打印pwd: " + ftp.pwd());
		}catch (final FtpException e) {
			e.printStackTrace();
		}

		Console.log("判断是否超时并重连...");
		ftp.reconnectIfTimeout();

		Console.log("打印pwd: " + ftp.pwd());

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void recursiveDownloadFolder() {
		final Ftp ftp = new Ftp("looly.centos");
		ftp.recursiveDownloadFolder("/",FileUtil.file("d:/test/download"));

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void recursiveDownloadFolderSftp() {
		final Sftp ftp = new Sftp("127.0.0.1", 22, "test", "test");

		ftp.cd("/file/aaa");
		Console.log(ftp.pwd());
		ftp.recursiveDownloadFolder("/",FileUtil.file("d:/test/download"));

		IoUtil.close(ftp);
	}

	@Test
	@Ignore
	public void downloadTest() {
		final Ftp ftp = new Ftp("localhost");

		final List<String> fileNames = ftp.ls("temp/");
		for(final String name: fileNames) {
			ftp.download("",
					name,
					FileUtil.file("d:/test/download/" + name));
		}
	}
}
