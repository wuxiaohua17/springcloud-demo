package cn.com.ut.demo.repository;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.com.ut.demo.entity.ShopManage;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/04/22 16:12
 */
public interface ShopManageRepository extends JpaRepository<ShopManage, String> {

	boolean existsByManagerId(String managerId);

	@Query("select sm from ShopManage sm where id = ?1")
	ShopManage getByKey(String key);

	@Query("select sm from ShopManage sm where managerId = 'zhangsan' or sm.mainStoreId = (SELECT  mainStoreId from ShopManage where managerId = 'zhangsan' and storeLevel = 2)")
	List<ShopManage> query();

	List<ShopManage> findByManagerIdOrSubStoreId(String managerId, String subStoreId);

	List<ShopManage> findByManagerIdLike(String managerId);

	List<ShopManage> findByManagerIdContains(String managerId);

	List<ShopManage> findByManagerIdStartsWith(String managerId);

	List<ShopManage> findByManagerIdEndsWith(String managerId);

	List<ShopManage> findByManagerIdIsEndingWith(String managerId);

	@Transactional
	int deleteByIdIn(Collection<String> idList);

	@Transactional
	int removeByIdIn(String... idList);

}
