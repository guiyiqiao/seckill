package ink.carnation.seckill.common.mapper;

import ink.carnation.seckill.common.model.entity.Item;
import ink.carnation.seckill.common.model.vo.ItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author 桂乙侨
 * @Date 2020/5/19 9:58
 * @Version 1.0
 */
@Mapper
public interface SeckillMapper {

    @Select("select " +
            "m.id item_id, " +
            "m.goods_id, " +
            "m.item_price, " +
            "m.item_stock, " +
            "m.start_date, " +
            "m.version ,"+
            "m.status, "+
            "m.end_date, " +
            "g.goods_name, " +
            "g.goods_img, " +
            "g.goods_detail, " +
            "g.goods_price " +
            "from goods as g,item as m " +
            "where g.id = m.goods_id ")
    List<ItemVo> listItemVo();

    @Update(" update item " +
            "set item_stock = item_stock -1 ," +
            "version = ${version}+1 "+
            "where id =  #{itemId}  " +
            "and item_stock > 0 " +
            "and start_date < now() " +
            "and end_date > now() " +
            "and version = #{version} " +
            "and status = 1 ")
    int execute(@Param("itemId") Integer itemId,@Param("version") Integer version);

    @Select("select id, " +
            "goods_id, " +
            "item_price, " +
            "item_stock, " +
            "start_date, " +
            "version ,"+
            "end_date, "+
            "status "+" from item where id = #{itemId} and item_stock > 0 ")
    Item getVersion(Integer itemId);

    @Select("select " +
            "id, " +
            "goods_id, " +
            "item_price, " +
            "item_stock, " +
            "start_date, " +
            "version ,"+
            "end_date, "+
            "status "+
            "from item "+
            "where status = 0 " +
            "and start_date < now() "+
            "and end_date > now() "
    )
    List<Item> listItemForTask();
}
