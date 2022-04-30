package cn.hutool.extra.qrcode;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.StrUtil;

/**
 * Qrcode异常
 *
 * @author xiaoleilu
 */
public class QrCodeException extends RuntimeException {
	private static final long serialVersionUID = 8247610319171014183L;

	public QrCodeException(final Throwable e) {
		super(ExceptionUtil.getMessage(e), e);
	}

	public QrCodeException(final String message) {
		super(message);
	}

	public QrCodeException(final String messageTemplate, final Object... params) {
		super(StrUtil.format(messageTemplate, params));
	}

	public QrCodeException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public QrCodeException(final String message, final Throwable throwable, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, throwable, enableSuppression, writableStackTrace);
	}

	public QrCodeException(final Throwable throwable, final String messageTemplate, final Object... params) {
		super(StrUtil.format(messageTemplate, params), throwable);
	}
}
