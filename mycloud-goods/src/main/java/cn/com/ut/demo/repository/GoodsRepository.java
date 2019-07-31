package cn.com.ut.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cn.com.ut.demo.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, String> {

	Goods findByGoodsId(String goodsId);

	@Transactional
	@Modifying
	@Query("update Goods g set g.goodsName = :goodsName where g.goodsId = :goodsId")
	int updateGoodsById(@Param("goodsName") String goodsName, @Param("goodsId") String goodsId);

	@Transactional
	@Modifying
	@Query("update Goods g set g.goodsName = :goodsName, g.version = :version + 1 where g.goodsId = :goodsId and g.version = :version")
	int updateGoodsById(@Param("goodsName") String goodsName, @Param("goodsId") String goodsId,
			@Param("version") int version);

	@Transactional
	@Query(nativeQuery = true, value = "insert into goods(goods_id,goods_name) select :goodsId, :goodsName from dual where not exists (select 1 from goods where goods_name = :goodsName)")
	void insertSelect(@Param("goodsName") String goodsName, @Param("goodsId") String goodsId);
}
