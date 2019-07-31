package cn.com.ut;

import org.junit.Test;

/**
 * @author wuxiaohua
 * @since 2018/7/10
 */
public class TestCase {

	@Test
	public void test() {

		StringBuilder sb = new StringBuilder(
				"select new cn.com.ut.biz.pojo.AuditRespVO(ma.id as applyId,m.id as appId,ma.companyName,m.name as mallName,ma.master,ma.status,m.logo,m.scope,ma.createTime as applyTime,m.userId as userName) from cn.com.ut.biz.entity.Mallapp m , cn.com.ut.biz.entity.Mallappapply ma where "
						+ "m.id = ma.appId ");
		sb.append(" and ma.status = ").append(1).append("and ma.isDel ='N'");
		System.out.println(sb.toString());

	}
}
