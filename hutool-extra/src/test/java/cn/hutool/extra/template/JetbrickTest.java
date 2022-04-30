package cn.hutool.extra.template;

import cn.hutool.core.map.Dict;
import cn.hutool.core.text.StrUtil;
import cn.hutool.extra.template.engine.jetbrick.JetbrickEngine;
import org.junit.Assert;
import org.junit.Test;

public class JetbrickTest {

	@Test
	public void jetbrickEngineTest() {
		//classpath模板
		final TemplateConfig config = new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH)
				.setCustomEngine(JetbrickEngine.class);
		final TemplateEngine engine = TemplateUtil.createEngine(config);
		final Template template = engine.getTemplate("jetbrick_test.jetx");
		final String result = template.render(Dict.create().set("name", "hutool"));
		Assert.assertEquals("你好,hutool", StrUtil.trim(result));
	}

	@Test
	public void jetbrickEngineWithStringTest() {
		// 字符串模板
		final TemplateConfig config = new TemplateConfig("templates", TemplateConfig.ResourceMode.STRING)
				.setCustomEngine(JetbrickEngine.class);
		final TemplateEngine engine = TemplateUtil.createEngine(config);
		final Template template = engine.getTemplate("hello,${name}");
		final String result = template.render(Dict.create().set("name", "hutool"));
		Assert.assertEquals("hello,hutool", StrUtil.trim(result));
	}
}
