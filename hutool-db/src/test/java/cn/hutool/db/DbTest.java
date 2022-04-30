package cn.hutool.db;

import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.Condition;
import cn.hutool.log.StaticLog;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Db对象单元测试
 * @author looly
 *
 */
public class DbTest {

	@Test
	public void queryTest() throws SQLException {
		final List<Entity> find = Db.use().query("select * from user where age = ?", 18);
		Assert.assertEquals("王五", find.get(0).get("name"));
	}

	@Test
	public void findTest() throws SQLException {
		final List<Entity> find = Db.use().find(Entity.create("user").set("age", 18));
		Assert.assertEquals("王五", find.get(0).get("name"));
	}

	@Test
	public void pageTest() throws SQLException {
		// 测试数据库中一共4条数据，第0页有3条，第1页有1条
		final List<Entity> page0 = Db.use().page(Entity.create("user"), 0, 3);
		Assert.assertEquals(3, page0.size());
		final List<Entity> page1 = Db.use().page(Entity.create("user"), 1, 3);
		Assert.assertEquals(1, page1.size());
	}

	@Test
	public void pageTest2() throws SQLException {
		final String sql = "select * from user order by name";
		// 测试数据库中一共4条数据，第0页有3条，第1页有1条
		final List<Entity> page0 = Db.use().page(
				sql, Page.of(0, 3));
		Assert.assertEquals(3, page0.size());

		final List<Entity> page1 = Db.use().page(
				sql, Page.of(1, 3));
		Assert.assertEquals(1, page1.size());
	}

	@Test
	public void pageWithParamsTest() throws SQLException {
		final String sql = "select * from user where name = ?";
		final PageResult<Entity> result = Db.use().page(
				sql, Page.of(0, 3), "张三");

		Assert.assertEquals(2, result.getTotal());
		Assert.assertEquals(1, result.getTotalPage());
		Assert.assertEquals(2, result.size());
	}

	@Test
	public void countTest() throws SQLException {
		final long count = Db.use().count("select * from user");
		Assert.assertEquals(4, count);
	}

	@Test
	public void countTest2() throws SQLException {
		final long count = Db.use().count("select * from user order by name DESC");
		Assert.assertEquals(4, count);
	}

	@Test
	public void findLikeTest() throws SQLException {
		// 方式1
		List<Entity> find = Db.use().find(Entity.create("user").set("name", "like 王%"));
		Assert.assertEquals("王五", find.get(0).get("name"));

		// 方式2
		find = Db.use().findLike("user", "name", "王", Condition.LikeType.StartWith);
		Assert.assertEquals("王五", find.get(0).get("name"));

		// 方式3
		find = Db.use().query("select * from user where name like ?", "王%");
		Assert.assertEquals("王五", find.get(0).get("name"));
	}

	@Test
	public void findByTest() throws SQLException {
		final List<Entity> find = Db.use().findBy("user",
				Condition.parse("age", "> 18"),
				Condition.parse("age", "< 100")
		);
		for (final Entity entity : find) {
			StaticLog.debug("{}", entity);
		}
		Assert.assertEquals("unitTestUser", find.get(0).get("name"));
	}

	@Test
	@Ignore
	public void txTest() throws SQLException {
		Db.use().tx(db -> {
			db.insert(Entity.create("user").set("name", "unitTestUser2"));
			db.update(Entity.create().set("age", 79), Entity.create("user").set("name", "unitTestUser2"));
			db.del("user", "name", "unitTestUser2");
		});
	}

	@Test
	@Ignore
	public void queryFetchTest() throws SQLException {
		// https://gitee.com/dromara/hutool/issues/I4JXWN
		Db.use().query((conn->{
			final PreparedStatement ps = conn.prepareStatement("select * from table",
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			ps.setFetchSize(Integer.MIN_VALUE);
			ps.setFetchDirection(ResultSet.FETCH_FORWARD);
			return ps;
		}), new EntityListHandler());
	}
}
