package cn.hutool.cron.pattern;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务表达式工具类
 *
 * @author looly
 *
 */
public class CronPatternUtil {

	/**
	 * 列举指定日期之后（到开始日期对应年年底）内第一个匹配表达式的日期
	 *
	 * @param pattern 表达式
	 * @param start 起始时间
	 * @param isMatchSecond 是否匹配秒
	 * @return 日期
	 * @since 4.5.8
	 */
	public static Date nextDateAfter(final CronPattern pattern, final Date start, final boolean isMatchSecond) {
		final List<Date> matchedDates = matchedDates(pattern, start.getTime(), DateUtil.endOfYear(start).getTime(), 1, isMatchSecond);
		if (CollUtil.isNotEmpty(matchedDates)) {
			return matchedDates.get(0);
		}
		return null;
	}

	/**
	 * 列举指定日期之后（到开始日期对应年年底）内所有匹配表达式的日期
	 *
	 * @param patternStr 表达式字符串
	 * @param start 起始时间
	 * @param count 列举数量
	 * @param isMatchSecond 是否匹配秒
	 * @return 日期列表
	 */
	public static List<Date> matchedDates(final String patternStr, final Date start, final int count, final boolean isMatchSecond) {
		return matchedDates(patternStr, start, DateUtil.endOfYear(start), count, isMatchSecond);
	}

	/**
	 * 列举指定日期范围内所有匹配表达式的日期
	 *
	 * @param patternStr 表达式字符串
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param count 列举数量
	 * @param isMatchSecond 是否匹配秒
	 * @return 日期列表
	 */
	public static List<Date> matchedDates(final String patternStr, final Date start, final Date end, final int count, final boolean isMatchSecond) {
		return matchedDates(patternStr, start.getTime(), end.getTime(), count, isMatchSecond);
	}

	/**
	 * 列举指定日期范围内所有匹配表达式的日期
	 *
	 * @param patternStr 表达式字符串
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param count 列举数量
	 * @param isMatchSecond 是否匹配秒
	 * @return 日期列表
	 */
	public static List<Date> matchedDates(final String patternStr, final long start, final long end, final int count, final boolean isMatchSecond) {
		return matchedDates(new CronPattern(patternStr), start, end, count, isMatchSecond);
	}

	/**
	 * 列举指定日期范围内所有匹配表达式的日期
	 *
	 * @param pattern 表达式
	 * @param start 起始时间
	 * @param end 结束时间
	 * @param count 列举数量
	 * @param isMatchSecond 是否匹配秒
	 * @return 日期列表
	 */
	public static List<Date> matchedDates(final CronPattern pattern, final long start, final long end, final int count, final boolean isMatchSecond) {
		Assert.isTrue(start < end, "Start date is later than end !");

		final List<Date> result = new ArrayList<>(count);
		final long step = isMatchSecond ? DateUnit.SECOND.getMillis() : DateUnit.MINUTE.getMillis();
		for (long i = start; i < end; i += step) {
			if (pattern.match(i, isMatchSecond)) {
				result.add(DateUtil.date(i));
				if (result.size() >= count) {
					break;
				}
			}
		}
		return result;
	}
}
